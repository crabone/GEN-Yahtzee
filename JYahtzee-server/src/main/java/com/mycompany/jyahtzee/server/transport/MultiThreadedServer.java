package com.mycompany.jyahtzee.server.transport;

import com.mycompany.jyahtzee.manager.GameManager;
import com.mycompany.jyahtzee.server.JYahtzeeServer;
import com.mycompany.jyahtzee.server.hash.Hash;
import com.mycompany.jyahtzee.server.database.Database;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.security.NoSuchAlgorithmException;
import java.lang.Integer;
import java.util.HashMap;



import static com.mycompany.jyahtzee.server.JYahtzeeServer.db;

public class MultiThreadedServer
{
    // for the database connexion
    public static final String DB_PWD = "";
    public static final String DB_USERNAME = "root";

    private static final Logger LOG = Logger.getLogger(MultiThreadedServer.class.getName());

    final int port;
    boolean connected;
    HashMap<Integer,ServantWorker> playerSocket;
    

    /**
     * Construit un serveur multithreadé, en spécifiant le port d'écoute.
     *
     * @param port
     */
    public MultiThreadedServer(int port)
    {
        this.port = port;
        connected = false;
    }

    /**
     * Cette méthode est appelé après construction de l'objet, servant à
     * recevoir les clients.
     */
    public void serveClients()
    {
        new Thread(new ReceptionistWorker()).start();
    }

    /**
     * Cette classe est chargé de réceptionner les clients qui arrivent sur le
     * serveur.
     */
    private class ReceptionistWorker implements Runnable
    {

        @Override
        public void run()
        {
            ServerSocket serverSocket;

            try
            {
                serverSocket = new ServerSocket(port);
            }
            catch (IOException ex)
            {
                Logger.getLogger(MultiThreadedServer.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }

            while (true)
            {
                LOG.log(Level.INFO, "Waiting (blocking) for a new client on port {0}", port);
                try
                {
                    Socket clientSocket = serverSocket.accept();
                    new Thread(new ServantWorker(clientSocket)).start();
                }
                catch (IOException ex)
                {
                    Logger.getLogger(MultiThreadedServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    /**
     * Cette classe est chargée de servir les clients après qu'ils aient été
     * reçu.
     */
    private class ServantWorker implements Runnable
    {

        Socket clientSocket;
        BufferedReader reader = null;
        PrintWriter writer = null;
        int idPlayer = 0;

        public ServantWorker(Socket clientSocket)
        {
            try
            {
                this.clientSocket = clientSocket;
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writer = new PrintWriter(clientSocket.getOutputStream());
            }
            catch (IOException ex)
            {
                Logger.getLogger(MultiThreadedServer.class.getName()).log(Level.SEVERE, null, ex);
            }

            connected = true;
        }

        @Override
        public void run()
        {
            String line;
            boolean shouldRun = true;
            int idPartie = 0;
            int idTemp=0;
            boolean ok;

            try
            {
                while ((shouldRun == true) && (line = reader.readLine()) != null)
                {
                    switch (line)
                    {
                        case (Protocole.CMD_HI):
                            sendMessage(Protocole.CMD_HI);
                            break;
                        case (Protocole.CMD_AUTH):
                            int authOk;
                            authOk = authenticate();
                            if (authOk != 0)
                            {
                                idPlayer = authOk;
                                //playerSocket.put(idPlayer, this);
                                
                            }
                            break;
                        case (Protocole.CMD_INSCRIPTION):
                            register();
                            break;
                        case (Protocole.CMD_CREATION):
                            idTemp = 0;
                            idTemp = JYahtzeeServer.gameManager.createGame(idPlayer);
                            ok = JYahtzeeServer.gameManager.joinGame(idTemp, idPlayer);
                            if (idTemp != 0 && ok)
                            {
                                idPartie = idTemp;
                                sendMessage(Protocole.CMD_OK);
                            }
                            else
                            {
                                sendMessage(Protocole.CMD_KO);
                            }
                            break;
                        case (Protocole.CMD_JOIN):
                            idTemp = 0;
                            sendMessage(Protocole.CMD_ACK);
                            idTemp = Integer.parseInt(reader.readLine());
                            if (idTemp == 0)
                            {
                                sendMessage(Protocole.CMD_KO);
                                break;
                            }
                            ok = JYahtzeeServer.gameManager.joinGame(idTemp, idPlayer);
                            if (ok)
                            {
                                idPartie = idTemp;
                                sendMessage(Protocole.CMD_OK);
                            }
                            else
                            {
                                sendMessage(Protocole.CMD_KO);
                            }
                            break;
                        case (Protocole.CMD_ROLL_THE_DICES):
                            // Fonction pour lancer les dés                            
                            sendMessage(Protocole.CMD_ACK);
                            JYahtzeeServer.gameManager.rollInGame(idPartie);
                            
                            break;
                        /*case (Protocole.CMD_DECISION):
                         String idScore;
                         sendMessage(Protocole.CMD_ACK);
                         idScore = reader.readLine();
                         if (idScore == null) {
                         sendMessage(Protocole.CMD_KO);
                         break;
                         }
                         //ok = fonction_enregistrer_score(idScore);
                         if(ok)
                         {
                         sendMessage(Protocole.CMD_OK);
                         }
                         else
                         {
                         sendMessage(Protocole.CMD_KO);
                         }                             
                         break;
                         */
                        case (Protocole.CMD_GETGAMES):
                            sendGames();
                            break;
                        case (Protocole.CMD_ROLL):
                            sendMessage(Protocole.CMD_ACK);
                            int diceId;
                            diceId = Integer.parseInt(reader.readLine());
                            
                            if (diceId < 0 || diceId > 5)
                            {
                                sendMessage(Protocole.CMD_KO);
                            }
                            else 
                            {
                                sendMessage(Protocole.CMD_OK);
                            }
                            
                            // Appeler la fonction de roll et envoyer la valeur.
                            break;
                        case (Protocole.CMD_BYE):
                            sendMessage(Protocole.CMD_BYE);
                            shouldRun = false;
                            break;
                        default:
                            sendMessage("Command not known");
                            break;
                    }
                }

                disconnect();
            }
            catch (IOException ex)
            {
                try
                {
                    cleanup();
                }
                catch (IOException ex1)
                {
                    Logger.getLogger(MultiThreadedServer.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }

        /**
         * Méthode permettant de fermer tout les flux ouverts. Elle est
         * généralement appelée en cas de fermeture de la connexion.
         */
        private void cleanup() throws IOException
        {
            if (reader != null)
            {
                reader.close();
            }
            if (writer != null)
            {
                writer.close();
            }
            if (clientSocket != null)
            {
                clientSocket.close();
            }
        }

        private void sendMessage(String msg)
        {
            writer.write(msg);
            writer.write("\r\n");
            writer.flush();
        }

        /**
         * Cette méthode est appelé lorsque qu'un client ce déconnecte du
         * serveur.
         */
        private void disconnect() throws IOException
        {
            connected = false;
            cleanup();
        }

        /**
         * return player id if found, 0 if not
         */
        private int authenticate() throws IOException
        {
            String mdp;
            String username;
            String line;
            int id = 0;

            sendMessage(Protocole.CMD_ACK);
            // wait for the username command
            while (!(line = reader.readLine()).equals(Protocole.CMD_USERNAME))
            {
                sendMessage(Protocole.CMD_KO);
            }
            sendMessage(Protocole.CMD_ACK);
            // receive the username
            username = reader.readLine();
            if (username == null)
            {
                sendMessage(Protocole.CMD_KO);
                return 0;
            }
            sendMessage(Protocole.CMD_OK);
            // wait for MDP command
            while (!(line = reader.readLine()).equals(Protocole.CMD_MDP))
            {
                sendMessage(Protocole.CMD_KO);
            }
            sendMessage(Protocole.CMD_ACK);
            // receive the hashed pwd
            mdp = reader.readLine();
            if (mdp == null)
            {
                sendMessage(Protocole.CMD_KO);
                return 0;
            }
            sendMessage(Protocole.CMD_OK);
            // connection to the database
            try
            {
		        JYahtzeeServer.db.connecter("jdbc:mysql://localhost:3306/Yahtzee", DB_USERNAME, DB_PWD);
                // verify that the user with this pwd is correct
                id = JYahtzeeServer.db.verify(username, mdp);
            }
            catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            }
            finally
            {
                JYahtzeeServer.db.disconnect();
            }
            sendMessage(Integer.toString(id));
            return id;
        }

        private void register() throws IOException
        {
            String mdp;
            String username;
            String line;

            sendMessage(Protocole.CMD_ACK);
            line = reader.readLine();
            // wait the username command
            if (!line.equals(Protocole.CMD_USERNAME))
            {
                sendMessage(Protocole.CMD_KO);
                return;
            }
            sendMessage(Protocole.CMD_ACK);
            // receive the username
            username = reader.readLine();
            if (username == null)
            {
                sendMessage(Protocole.CMD_KO);
                return;
            }
            // check if the username is already registered
            try
            {
                JYahtzeeServer.db.connecter("jdbc:mysql://localhost:3306/Yahtzee", DB_USERNAME, DB_PWD);
                if (JYahtzeeServer.db.playerExist(username) != 0)
                {
                    sendMessage(Protocole.CMD_KO);
                    return;
                }
            }
            catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
                return;
            }
            sendMessage(Protocole.CMD_OK);
            // wait for the MDP command
            while (!(line = reader.readLine()).equals(Protocole.CMD_MDP))
            {
                sendMessage(Protocole.CMD_KO);
            }
            sendMessage(Protocole.CMD_ACK);
            // receive the pwd
            mdp = reader.readLine();
            if (mdp == null)
            {
                sendMessage(Protocole.CMD_KO);
                return;
            }
            sendMessage(Protocole.CMD_OK);
            // store the new player in the database
            try
            {
                if(!JYahtzeeServer.db.connected())
                {
                    JYahtzeeServer.db.connecter("jdbc:mysql://localhost:3306/Yahtzee", DB_USERNAME, DB_PWD);
                }
                JYahtzeeServer.db.insertPlayer(username, mdp);
            }
            catch (SQLException ex)
            {
                sendMessage(Protocole.CMD_KO);
                System.out.println(ex.getMessage());
            }
            finally
            {
                JYahtzeeServer.db.disconnect();
            }
        }
        public void sendGames() throws IOException
        {
            ArrayList<ArrayList<String>> games = null;
            String client;
            sendMessage(Protocole.CMD_ACK);
            // Get games from the database
            try
            {
                if(!JYahtzeeServer.db.connected())
                {
                    JYahtzeeServer.db.connecter("jdbc:mysql://localhost:3306/Yahtzee", DB_USERNAME, DB_PWD);
                }
                games = db.getGames();
            }
            catch (SQLException e)
            {
                sendMessage(Protocole.CMD_KO);
                System.out.println(e.getMessage());
                return;
            }
            finally
            {
                JYahtzeeServer.db.disconnect();
            }
            if(games.size() == 0)
            {
                sendMessage(Protocole.CMD_GETGAMES);
                return;
            }
            for(ArrayList<String> e : games)
            {
                sendMessage(Protocole.CMD_START);
                // for each element of a game
                for(String s : e)
                {
                    sendMessage(s);
                }
                // after sending all data of a game
                sendMessage(Protocole.CMD_END);
                client = reader.readLine();
                // wait the ACK command
                if (!client.equals(Protocole.CMD_ACK))
                {
                    sendMessage(Protocole.CMD_KO);
                    return;
                }
            }
            sendMessage(Protocole.CMD_GETGAMES);
        }
    }
}
