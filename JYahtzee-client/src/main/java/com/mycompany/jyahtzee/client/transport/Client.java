package com.mycompany.jyahtzee.client.transport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    private static final Logger LOG = Logger.getLogger(Client.class.getName());

    private final String serverHost;
    private final int serverPort;
    private boolean connected = false;
    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;

    /**
     * Construit un nouveau client en spécifiant l'hôte et le port du serveur.
     *
     * @param serverHost
     * @param serverPort
     */
    public Client(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    /**
     * Méthode appelé lors de la connexion d'un client.
     *
     * @throws IOException
     */
    public void connect() throws IOException {
        try {
            clientSocket = new Socket(serverHost, serverPort);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            cleanup();
        }

        connected = true;
        //LOG.info(reader.readLine());
    }
    
    /**
     * Méthode permettant de fermer tout les flux ouverts. Elle est généralement
     * appelée en cas de fermeture de la connexion.
     * 
     * @throws java.io.IOException
     */
    public void cleanup() throws IOException {
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
     * 
     * @throws java.io.IOException
     */
    public void disconnect() throws IOException {
        connected = false;
        cleanup();
    }

    /**
     * Méthode utilitaire permettant d'envoyer un message au serveur.
     * 
     * @param msg
     * @throws IOException 
     */
    public void sendMessage(String msg) throws IOException {
        if(msg != null) {
            writer.write(msg);
            writer.write("\r\n");
            writer.flush();
        }

        if (msg.equals(Protocole.CMD_BYE)) {
            disconnect();
        }
    }

    /**
     * Méthode utilitaire permettant de recevoir un message de la part du serveur.
     * 
     * @return 
     */
    public String receiveMessage() {
        String msg;
        try {
            msg = reader.readLine();
            
            return msg;
        } catch (IOException e) {
            return e.toString();
        }
    }
    
    public void register(String username) throws IOException {
        sendMessage(Protocole.CMD_INSCRIPTION);
        sendMessage(Protocole.CMD_USERNAME);
        sendMessage(username);   
    }
    
    public void authenticate(String username) throws IOException {
        sendMessage(Protocole.CMD_AUTH);
        sendMessage(Protocole.CMD_USERNAME);
        sendMessage(username);
    }

}
