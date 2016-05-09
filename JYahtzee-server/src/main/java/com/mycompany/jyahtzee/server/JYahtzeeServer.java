package com.mycompany.jyahtzee.server;

import com.mycompany.jyahtzee.server.transport.MultiThreadedServer;
import java.io.IOException;

/**
 *
 * @author crab_one
 */
public class JYahtzeeServer {
    public static void main(String[] args) throws IOException {
        MultiThreadedServer server = new MultiThreadedServer(4321);
        server.serveClients();
    }
}
