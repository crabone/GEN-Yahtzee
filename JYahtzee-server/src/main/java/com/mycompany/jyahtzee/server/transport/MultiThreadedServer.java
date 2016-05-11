package com.mycompany.jyahtzee.server.transport;

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

    public MultiThreadedServer(int port) {
        this.port = port;
    }
    
    public void serveClients() {        
        new Thread(new ReceptionistWorker()).start();
    }
    
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
        }

        @Override
        public void run() {
            String line;
            boolean shouldRun = true;
            boolean ok;
            
            //writer.println("Welcome to our Yahtzee server.");
            //writer.flush();
            
            try {
                while ((shouldRun == true) && (line = reader.readLine()) != null) {
                    /*if (line.equalsIgnoreCase("bye")) {
                        shouldRun = false;
                    }
                    writer.println("> " + line);
                    writer.flush();*/
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
                            ok = Fonction_de_création_d_une_partie;
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
                            String id;
                            writer.println(Protocole.CMD_ACK);
                            writer.flush();
                            id = reader.readLine();
                            if(id == null)
                            {
                                    writer.println(Protocole.CMD_KO);
                                    writer.flush();
                                    break;
                            }
                            ok = fonction_rejoindre_partie(id);
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
                            String idGame;
                            writer.println(Protocole.CMD_ACK);
                            writer.flush();
                            idGame = reader.readLine();
                            if(id == null)
                            {
                                    writer.println(Protocole.CMD_KO);
                                    writer.flush();
                                    break;
                            }
                            ok = fonction_observer_partie(idGame);
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
                            ok = fonction_enregistrer_score(idScore);
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
                
                clientSocket.close();
                reader.close();
                writer.close();
            } catch (IOException ex) {
                /*
                Penser à faire des fonction cleanup / disconnect.
                */
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException ex1) {
                        Logger.getLogger(MultiThreadedServer.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
                if (writer != null) {
                    writer.close();
                }
                if (clientSocket != null) {
                    try {
                        clientSocket.close();
                    } catch (IOException ex1) {
                        Logger.getLogger(MultiThreadedServer.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
                Logger.getLogger(MultiThreadedServer.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        private void auth() throws IOException
        {
            String mdp;
            String username;
            String line;

            writer.println(Protocole.CMD_ACK);
            writer.flush();
            while((line = reader.readLine()) == Protocole.CMD_USERNAME)
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
            while((line = reader.readLine()) == Protocole.CMD_MDP)
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
            fonction_verification_BDD(username, mdp);        	        	
        }
        private void inscription() throws IOException
        {
            String mdp;
            String username;
            String line;

            writer.println(Protocole.CMD_ACK);
            writer.flush();
            while((line = reader.readLine()) == Protocole.CMD_USERNAME)
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
            while((line = reader.readLine()) == Protocole.CMD_MDP)
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
            fonction_inscription_BDD(username, mdp);   
        }
        
    }
}
