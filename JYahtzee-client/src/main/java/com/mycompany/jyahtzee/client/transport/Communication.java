/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jyahtzee.client.transport;

import java.io.IOException;

/**
 *
 * @author rosanne
 */
public class Communication {
    private static Client client;
    
    public Communication(Client client)
    {
        this.client = client;
    }
    // MDP déjà hashé??
    public static boolean authentification(String username, String mdp) throws IOException
    {
        String serverMsg;
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
        client.sendMessage(mdp);
        serverMsg = client.receiveMessage();
        if(!serverMsg.equals(Protocole.CMD_OK))
        {
            return false;
        }
        return true;
    }
    public static boolean inscription(String username, String mdp) throws IOException
    {
        String serverMsg;
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
        client.sendMessage(mdp);
        serverMsg = client.receiveMessage();
        if(!serverMsg.equals(Protocole.CMD_OK))
        {
            return false;
        }
        return true;
    }
    public static boolean create() throws IOException
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
    public static boolean join(String id) throws IOException
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
    public static boolean observe(String id) throws IOException
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
    public static boolean play() throws IOException
    {
        return true;
    }
    public static void quit() throws IOException
    {
        client.sendMessage(Protocole.CMD_BYE);
    }
    
}
