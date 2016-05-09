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
            
            writer.println("Welcome to our Yahtzee server.");
            writer.flush();
            
            try {
                while ((shouldRun == true) && (line = reader.readLine()) != null) {
                    if (line.equalsIgnoreCase("bye")) {
                        shouldRun = false;
                    }
                    writer.println("> " + line);
                    writer.flush();
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
        
    }
}