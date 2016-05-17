package com.mycompany.jyahtzee.client.transport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author crab_one
 */
public class Client {

    private static final Logger LOG = Logger.getLogger(Client.class.getName());
    
    private String serverHost;
    private int serverPort;
    private boolean connected = false;
    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;

    public Client(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }
    
    public void connect() {
        try {
            clientSocket = new Socket(serverHost, serverPort);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream());
            
            connected = true;
            LOG.info(reader.readLine());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            cleanup();
        }
    }

    public void disconnect() {
        connected = false;
        cleanup();
    }

    public void cleanup() {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (writer != null) {
            writer.close();
        }
        
        if (clientSocket != null) {
            try {
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void sendMessage(String msg) {                
        writer.write(msg);
        writer.flush();
        if(msg.equals(Protocole.CMD_BYE))
        {
            disconnect();
        }
    }
    public String receiveMessage()
    {
        String msg;
        try
        {
            msg = reader.readLine();
            return msg;
        }
        catch (IOException e)
        {
            return e.toString();
        }
    }

}
