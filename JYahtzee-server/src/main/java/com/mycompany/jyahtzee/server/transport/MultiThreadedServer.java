package com.mycompany.jyahtzee.server.transport;

import com.mycompany.jyahtzee.manager.GameManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author crab_one
 */
public class MultiThreadedServer {

    private static final Logger LOG = Logger.getLogger(MultiThreadedServer.class.getName());
    
    final int port;
    boolean connected;

    /**
     * Construit un serveur multithreadé, en spécifiant le port d'écoute.
     * 
     * @param port
     */
    public MultiThreadedServer(int port) {
        this.port = port;
        connected = false;
    }
    
    /**
     * Cette méthode est appelé après construction de l'objet, servant à 
     * recevoir les clients.
     */
    public void serveClients() {        
        new Thread(new ReceptionistWorker()).start();
    }
    
    /**
     * Cette classe est chargé de réceptionner les clients qui arrivent sur
     * le serveur.
     */
    private class ReceptionistWorker implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket;
            
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException ex) {
                Logger.getLogger(MultiThreadedServer.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            
            while (true) {
                LOG.log(Level.INFO, "Waiting (blocking) for a new client on port {0}", port);
                try {
                    Socket clientSocket = serverSocket.accept();
                    new Thread(new ServantWorker(clientSocket)).start();
                } catch (IOException ex) {
                    Logger.getLogger(MultiThreadedServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }            
        }
        
    }
    
    /**
     * Cette classe est chargée de servir les clients après qu'ils aient été
     * reçu.
     */
    private class ServantWorker implements Runnable {
        
        Socket clientSocket;
        BufferedReader reader = null;
        PrintWriter writer = null;

        public ServantWorker(Socket clientSocket) {
            try {
                this.clientSocket = clientSocket;
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writer = new PrintWriter(clientSocket.getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(MultiThreadedServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            connected = true;
        }

        @Override
        public void run() {
            String line;
            boolean shouldRun = true;
            boolean ok;
            GameManager gameManager = new GameManager();
            
            //writer.println("Welcome to our Yahtzee server.");
            //writer.flush();
            
            try {
                while ((shouldRun == true) && (line = reader.readLine()) != null) {
                    
                    switch(line)
                    {
                        case (Protocole.CMD_HI):
                            writer.println(Protocole.CMD_HI);
                            writer.flush();
                            break;
                        case (Protocole.CMD_AUTH):
                            auth();
                            break;
                        case (Protocole.CMD_INSCRIPTION):
                            inscription();
                            break;
                        case (Protocole.CMD_CREATION):
                            ok = gameManager.createGame();
                            if(ok)
                            {
                                    writer.println(Protocole.CMD_OK);
                            }
                            else
                            {
                                    writer.println(Protocole.CMD_KO);
                            }                		
                            writer.flush();
                            break;
                        case (Protocole.CMD_JOIN):
                            int id;
                            writer.println(Protocole.CMD_ACK);
                            writer.flush();
                            id = Integer.parseInt(reader.readLine());
                            if(id == 0)
                            {
                                    writer.println(Protocole.CMD_KO);
                                    writer.flush();
                                    break;
                            }
                            ok = gameManager.joinGame(id,player);
                            if(ok)
                            {
                                    writer.println(Protocole.CMD_OK);
                            }
                            else
                            {
                                    writer.println(Protocole.CMD_KO);
                            }
                            writer.flush();
                            break;
                        case (Protocole.CMD_OBSERVE):
                            int idGame;
                            writer.println(Protocole.CMD_ACK);
                            writer.flush();
                            idGame = Integer.parseInt(reader.readLine());
                            /*
                            if(id == 0)
                            {
                                    writer.println(Protocole.CMD_KO);
                                    writer.flush();
                                    break;      
                            }
                            */
                            ok = gameManager.observeGame(idGame);
                            if(ok)
                            {
                                    writer.println(Protocole.CMD_OK);
                            }
                            else
                            {
                                    writer.println(Protocole.CMD_KO);
                            }
                            writer.flush();
                            break;
                        case (Protocole.CMD_ROLL_THE_DICES):
                            // Fonction pour lancer les dés
                            break;
                        case (Protocole.CMD_DECISION):
                            String idScore;
                            writer.println(Protocole.CMD_ACK);
                            writer.flush();
                            idScore = reader.readLine();
                            if(idScore == null)
                            {
                                    writer.println(Protocole.CMD_KO);
                                    writer.flush();
                                    break;
                            }
                            /*
                            //ok = fonction_enregistrer_score(idScore);
                            if(ok)
                            {
                                    writer.println(Protocole.CMD_OK);
                            }
                            else
                            {
                                    writer.println(Protocole.CMD_KO);
                            }
                            */
                            writer.flush();
                            break;
                        case (Protocole.CMD_BYE):
                            writer.println(Protocole.CMD_BYE);
                            writer.flush();
                            shouldRun = false;
                            break;
                        default:
                            writer.println("Command not known");
                            break;
                    }
                }
                
                disconnect();
            } catch (IOException ex) {
                try {
                    cleanup();
                } catch (IOException ex1) {
                    Logger.getLogger(MultiThreadedServer.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }            
        }
        
        /**
         * Méthode permettant de fermer tout les flux ouverts. Elle est généralement
         * appelée en cas de fermeture de la connexion.
         */
        private void cleanup() throws IOException
        {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (clientSocket != null) {
                clientSocket.close();
            }
        }
        
        /**
         * Cette méthode est appelé lorsque qu'un client ce déconnecte du serveur.
         */
        private void disconnect() throws IOException
        {
            connected = false;
            cleanup();
        }
        
        private void auth() throws IOException
        {
            String mdp;
            String username;
            String line;

            writer.println(Protocole.CMD_ACK);
            writer.flush();
            while((line = reader.readLine()).equals(Protocole.CMD_USERNAME))
            {
        	writer.println("Not a correct command");
            	writer.flush();
            }
            writer.println(Protocole.CMD_ACK);
            writer.flush();
            username = reader.readLine();
            if(username == null)
            {
            	writer.println(Protocole.CMD_KO);
            	writer.flush();
            	return;
            }
            writer.println(Protocole.CMD_OK);
            writer.flush();
            while((line = reader.readLine()).equals(Protocole.CMD_MDP))
            {
                writer.println("Not a correct command");
            	writer.flush();
            }
            writer.println(Protocole.CMD_ACK);
            writer.flush();
            mdp = reader.readLine();
            if(mdp == null)
            {
                writer.println(Protocole.CMD_KO);
        	writer.flush();
        	return;
            }
            writer.println(Protocole.CMD_OK);
            writer.flush();
        }
        
        private void inscription() throws IOException
        {
            String mdp;
            String username;
            String line;

            writer.println(Protocole.CMD_ACK);
            writer.flush();
            while((line = reader.readLine()).equals(Protocole.CMD_USERNAME))
            {
        	writer.println("Not a correct command");
            	writer.flush();
            }
            writer.println(Protocole.CMD_ACK);
            writer.flush();
            username = reader.readLine();
            if(username == null)
            {
                writer.println(Protocole.CMD_KO);
        	writer.flush();
                return;
            }
            writer.println(Protocole.CMD_OK);
            writer.flush();
            while((line = reader.readLine()).equals(Protocole.CMD_MDP))
            {
            	writer.println("Not a correct command");
            	writer.flush();
            }
            writer.println(Protocole.CMD_ACK);
            writer.flush();
            mdp = reader.readLine();
            if(mdp == null)
            {
                writer.println(Protocole.CMD_KO);
        	writer.flush();
        	return;
            }
            writer.println(Protocole.CMD_OK);
            writer.flush();
            //fonction_inscription_BDD(username, mdp); 
        }
        
    }
}
