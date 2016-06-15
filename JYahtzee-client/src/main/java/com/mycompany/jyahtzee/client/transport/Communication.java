/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jyahtzee.client.transport;
import com.mycompany.jyahtzee.client.JYahtzeeClient;
import com.mycompany.jyahtzee.client.hash.Hash;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

//import static com.mycompany.jyahtzee.client.JYahtzeeClient.client;

/**
 *
 * @author rosanne
 */
public class Communication implements Runnable{
    private Client client;
    private Thread activity;
    private boolean exitRequested = false;

    public Communication()
    {
        activity = new Thread(this);
        activity.start();
        client = new Client("localhost", 4321);
        try {
            client.connect();
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        String reason = "";
        boolean result;
        int resultInt;
        while(!exitRequested)
        {
            try {
                synchronized (JYahtzeeClient.com) {
                    JYahtzeeClient.com.wait();
                }
                if(exitRequested){
                    break;
                }
                reason = JYahtzeeClient.com.getAbout();

                switch (reason){
                    // authentification
                    case Protocole.CMD_AUTH:
                        result = authentification(JYahtzeeClient.com.getArgUserName(), JYahtzeeClient.com.getArgPWD());
                        JYahtzeeClient.com.setResultBool(result);
                        synchronized (JYahtzeeClient.com) {
                            JYahtzeeClient.com.notify();
                        }
                        break;
                    // registration
                    case Protocole.CMD_INSCRIPTION:
                        result = inscription(JYahtzeeClient.com.getArgUserName(), JYahtzeeClient.com.getArgPWD());
                        JYahtzeeClient.com.setResultBool(result);
                        synchronized (JYahtzeeClient.com) {
                            JYahtzeeClient.com.notify();
                        }
                        break;
                    // create game
                    case Protocole.CMD_CREATION:
                        result = create();
                        JYahtzeeClient.com.setResultBool(result);
                        synchronized (JYahtzeeClient.com) {
                            JYahtzeeClient.com.notify();
                        }
                        break;
                    // join game
                    case Protocole.CMD_JOIN:
                        result = join(JYahtzeeClient.com.getId());
                        JYahtzeeClient.com.setResultBool(result);
                        synchronized (JYahtzeeClient.com) {
                            JYahtzeeClient.com.notify();
                        }
                        break;
                    // play
                    //case Protocole.:

                    // roll dice
                    case Protocole.CMD_ROLL:
                        resultInt = rollDice(Integer.parseInt(JYahtzeeClient.com.getId()));
                        JYahtzeeClient.com.setResultInt(resultInt);
                        synchronized (JYahtzeeClient.com) {
                            JYahtzeeClient.com.notify();
                        }
                        break;
                    // get games
                    case Protocole.CMD_GETGAMES:
                        result = getGames(JYahtzeeClient.com.newListGame());
                        JYahtzeeClient.com.setResultBool(result);
                        synchronized (JYahtzeeClient.com) {
                            JYahtzeeClient.com.notify();
                        }
                        break;
                    // quit
                    case Protocole.CMD_BYE:
                        quit();
                        break;
                }
            }
            catch (InterruptedException | IOException e){
                System.out.println(e.getMessage());
            }

        }
    }
    public boolean authentification(String username, String mdp) throws IOException
    {
        String hashedMdp;
        String serverMsg;
        Hash hasher = new Hash();
        String id;
        client.sendMessage(Protocole.CMD_AUTH);
        serverMsg = client.receiveMessage();
        if(!serverMsg.equals(Protocole.CMD_ACK))
        {
            return false;
        }
        client.sendMessage(Protocole.CMD_USERNAME);
        serverMsg = client.receiveMessage();
        if(!serverMsg.equals(Protocole.CMD_ACK))
        {
            return false;
        }
        client.sendMessage(username);
        serverMsg = client.receiveMessage();
        if(!serverMsg.equals(Protocole.CMD_OK))
        {
            return false;
        }
        client.sendMessage(Protocole.CMD_MDP);
        serverMsg = client.receiveMessage();
        if(!serverMsg.equals(Protocole.CMD_ACK))
        {
            return false;
        }
        try
        {
            hashedMdp = hasher.createHash(mdp);
        }
        catch(NoSuchAlgorithmException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
        client.sendMessage(hashedMdp);
        serverMsg = client.receiveMessage();
        if(!serverMsg.equals(Protocole.CMD_OK))
        {
            return false;
        }
        id = client.receiveMessage();
        if(id.equals(Integer.toString(0)))
        {
            return false;
        }
        return true;
    }
    public boolean inscription(String username, String mdp) throws IOException
    {
        String serverMsg;
        String hashedMdp;
        Hash hasher = new Hash();
        client.sendMessage(Protocole.CMD_INSCRIPTION);
        serverMsg = client.receiveMessage();
        if(!serverMsg.equals(Protocole.CMD_ACK))
        {
            return false;
        }
        client.sendMessage(Protocole.CMD_USERNAME);
        serverMsg = client.receiveMessage();
        if(!serverMsg.equals(Protocole.CMD_ACK))
        {
            return false;
        }
        client.sendMessage(username);
        serverMsg = client.receiveMessage();
        if(!serverMsg.equals(Protocole.CMD_OK))
        {
            return false;
        }
        client.sendMessage(Protocole.CMD_MDP);
        serverMsg = client.receiveMessage();
        if(!serverMsg.equals(Protocole.CMD_ACK))
        {
            return false;
        }
        //Hash du mot de passe
        try
        {
            hashedMdp = hasher.createHash(mdp);
        }
        catch(NoSuchAlgorithmException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
        client.sendMessage(hashedMdp);
        serverMsg = client.receiveMessage();
        if(!serverMsg.equals(Protocole.CMD_OK))
        {
            return false;
        }
        return true;
    }
    public boolean create() throws IOException
    {
        String serverMsg;
        client.sendMessage(Protocole.CMD_CREATION);
        serverMsg = client.receiveMessage();
        if(!serverMsg.equals(Protocole.CMD_OK))
        {
            return false;
        }
        return true;
    }
    public boolean join(String id) throws IOException
    {
        String serverMsg;
        client.sendMessage(Protocole.CMD_JOIN);
        serverMsg = client.receiveMessage();
        if(!serverMsg.equals(Protocole.CMD_ACK))
        {
            return false;
        }
        client.sendMessage(id);
        serverMsg = client.receiveMessage();
        if(!serverMsg.equals(Protocole.CMD_OK))
        {
            return false;
        }
        return true;
    }

    public boolean observe(String id) throws IOException
    {
        String serverMsg;
        client.sendMessage(Protocole.CMD_OBSERVE);
        serverMsg = client.receiveMessage();
        if(!serverMsg.equals(Protocole.CMD_ACK))
        {
            return false;
        }
        client.sendMessage(id);
        serverMsg = client.receiveMessage();
        if(!serverMsg.equals(Protocole.CMD_OK))
        {
            return false;
        }
        return true;
    }

    public boolean play() throws IOException
    {
        return true;
    }

    public void quit()
    {
        try {
            client.sendMessage(Protocole.CMD_BYE);
            this.activity.interrupt();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public int rollDice(int id) throws IOException
    {
        String serverMsg;
        int diceValue;
        client.sendMessage(Protocole.CMD_ROLL);
        serverMsg = client.receiveMessage();
        
        // Si le serveur ne répond pas, alors on retourne la valeure 0;
        // L'utilisateur de la fonction comprendra de cet état, une valeure invalide.
        if (!serverMsg.equals(Protocole.CMD_ACK))
        {
            return 0;
        }
        else 
        {
            client.sendMessage(Integer.toString(id));
            serverMsg = client.receiveMessage();
            
            if (serverMsg.equals(Protocole.CMD_KO)) 
            {
                return 0;
            }
            else
            {
                serverMsg = client.receiveMessage();
                diceValue = Integer.parseInt(serverMsg);
                
                return diceValue;
            }
        }
    }

    public boolean getGames(ArrayList<ArrayList<String>> list) throws IOException
    {
        String serverMsg;
        client.sendMessage(Protocole.CMD_GETGAMES);
        serverMsg = client.receiveMessage();
        if(!serverMsg.equals(Protocole.CMD_ACK))
        {
            return false;
        }
        // wait for the GETGAMES command that means the end of the transmission
        while(!serverMsg.equals(Protocole.CMD_GETGAMES))
        {
            ArrayList<String> tmp = new ArrayList<>();
            // add to the tmp list all the data received until the END command
            if(!serverMsg.equals(Protocole.CMD_ACK)) {
                while (!(serverMsg = client.receiveMessage()).equals(Protocole.CMD_END)) {
                    tmp.add(serverMsg);
                }
                // add the game to the final list
                list.add(tmp);
                client.sendMessage(Protocole.CMD_ACK);
            }
            serverMsg = client.receiveMessage();
        }
        return true;
    }
    
}
