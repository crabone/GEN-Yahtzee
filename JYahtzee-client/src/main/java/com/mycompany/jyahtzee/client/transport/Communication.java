/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jyahtzee.client.transport;
import com.mycompany.jyahtzee.client.hash.Hash;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 *
 * @author rosanne
 */
public class Communication {
    private Client client;
    
    public Communication(Client client)
    {
        this.client = client;
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

    public void quit() throws IOException
    {
        client.sendMessage(Protocole.CMD_BYE);
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
