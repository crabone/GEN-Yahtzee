package com.mycompany.jyahtzee.client;

import com.mycompany.jyahtzee.client.transport.Client;
import java.io.IOException;

/**
 *
 * @author crab_one
 */
public class JYahtzeeClient {
    
    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 4321);
        client.connect();
        client.sendMessage();
    }
}
