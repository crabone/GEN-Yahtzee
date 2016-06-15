/**
 * Projet : Jyahtzee
 * @author Rosanne Combremont, Madolyne Dupraz, Kevin Ponce, Fabien Franchini, Ibrahim Ounon
 * Date : 15.06.16
 * Version : 3.5
 * Description : Cette class gère les protocoles de communication entre le client
 *               et le serveur.
 */
package com.mycompany.jyahtzee.server.transport;

import com.mycompany.jyahtzee.manager.GameManager;
import com.mycompany.jyahtzee.manager.Game;
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
    
    
    //Met à jour les vues des joueurs avec le score du joueur
    public void sendUpdateVue(int idPlayer,int score, int choice)
    {
        ServantWorker servant = playerSocket.get(idPlayer);
        try
        {
            servant.sendUpdateVue(score, choice);
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
                
    }
    
    //Envoi qui doit joueur
    public void sendTurnPlayer(int idPlayer)
    {
        ServantWorker servant = playerSocket.get(idPlayer);
        try
        {
            servant.sendTurnPlayer();
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Cette classe est chargé de réceptionner les clients qui arrivent sur le
     * serveur.
     */
    private class ReceptionistWorker implements Runnable
    {

        @Override
        //Thread qui gère les nouvelles connexions
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

            //Attend une connexion d'un nouveau joueur
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
     * Cette classe est chargée de servir les joueurs après qu'ils aient été
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
                //socket de communication
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
                    //Tache réalisé selon le protocole reçu du joueur
                    switch (line)
                    {
                        case (Protocole.CMD_HI):
                            sendMessage(Protocole.CMD_HI);
                            break;
                        case (Protocole.CMD_AUTH):
                            int authOk;
                            //Appel de la fonction pour l'authentification
                            authOk = authenticate();
                            if (authOk != 0)
                            {
                                idPlayer = authOk;
                                //playerSocket.put(idPlayer, this);
                            }
                            break;
                        case (Protocole.CMD_INSCRIPTION):
                            //Appel de la focntion pour l'authentification
                            register();
                            break;
                        case (Protocole.CMD_CREATION):
                            idTemp = 0;
                            //Appel de la fonction pour créer un jeu
                            idTemp = JYahtzeeServer.gameManager.createGame(idPlayer);
                            //Le joueur qui crée le jeu le rejoint
                            ok = JYahtzeeServer.gameManager.joinGame(idTemp, idPlayer);
                            if (idTemp != 0 && ok)
                            {
                                //Récupère l'id de la partie
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
                            //Récupère l'id de la partie à rejoindre
                            idTemp = Integer.parseInt(reader.readLine());
                            if (idTemp == 0)
                            {
                                sendMessage(Protocole.CMD_KO);
                                break;
                            }
                            //Rejoins une partie
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
                        case (Protocole.CMD_DECISION):
                            String idCase;
                            int decisionCase;
                            sendMessage(Protocole.CMD_ACK);
                            //Récupère le choix du joueur
                            idCase = reader.readLine();
                            if (idCase == null) 
                            {
                               sendMessage(Protocole.CMD_KO);
                               break;
                            }
                            //String le met en int
                            decisionCase = Integer.parseInt(idCase);
                            //Appel de la fonction pour joueur dans la case choisie
                            ok = JYahtzeeServer.gameManager.decisionPlayer(idPartie,idPlayer,decisionCase);
                            if(ok)
                            {
                               sendMessage(Protocole.CMD_OK);
                            }
                            else
                            {
                               sendMessage(Protocole.CMD_KO);
                            }                             
                            break;
                        case (Protocole.CMD_GETGAMES):
                            sendGames();
                            break;
                        case (Protocole.CMD_ROLL):
                            sendMessage(Protocole.CMD_ACK);
                            int diceId;
                            //Récupère le dés à jouer
                            diceId = Integer.parseInt(reader.readLine());
                            
                            if (diceId < 0 || diceId > 4)
                            {
                                sendMessage(Protocole.CMD_KO);
                            }
                            else 
                            {
                                sendMessage(Protocole.CMD_OK);
                                Game game = JYahtzeeServer.gameManager.getGame(idPartie);
                                //Lance le dés reçu dans la bonne partie
                                sendMessage(Integer.toString(game.rolle(diceId)));
                            }
                            
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

        //Envoi du message à un joueur
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
		JYahtzeeServer.db.connect();
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

        //Gère l'inscription d'un utilisateur
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
                JYahtzeeServer.db.connect();
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
                    JYahtzeeServer.db.connect();
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
        
        //Envoi la liste des parties aux clients
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
                    JYahtzeeServer.db.connect();
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
        
        //Envoi le choix et le score obtenu pour un joueur
        //Pas entièrement implémentée
        public void sendUpdateVue(int score, int choice) throws IOException
        {
            String line;

            sendMessage(Protocole.CMD_UPDATEVUE);
            
            if(!(reader.readLine()).equals(Protocole.CMD_ACK))
            {
                return;
            }
            //Envoi id du joueur qui a jouer
            sendMessage(Integer.toString(idPlayer));
            if(!(reader.readLine()).equals(Protocole.CMD_ACK))
            {
                return;
            }
            //Envoi le score qu'il a obtenu
            sendMessage(Integer.toString(score));
            if(!(reader.readLine()).equals(Protocole.CMD_ACK))
            {
                return;
            }
            
            //Envoi l'id de la case qu'il a joué
            sendMessage(Integer.toString(choice));
            if(!(reader.readLine()).equals(Protocole.CMD_ACK))
            {
                return;
            }     
                        
        }
        
        //Envoi le tour du joueur
        public void sendTurnPlayer()throws IOException
        {
            sendMessage(Protocole.CMD_PLAYERTURN);
            if(!(reader.readLine()).equals(Protocole.CMD_OK))
            {
                System.out.println("Probleme envoi du tour");
            }
        }
    }
}
