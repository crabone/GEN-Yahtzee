package com.mycompany.jyahtzee.server;

import com.mycompany.jyahtzee.server.database.Database;
import com.mycompany.jyahtzee.server.transport.MultiThreadedServer;
import java.io.IOException;


public class JYahtzeeServer
{
    public static Database db;
    public static void main(String[] args) throws IOException {
        Database db;
        MultiThreadedServer server = new MultiThreadedServer(4321);
        server.serveClients();
        
    }
}
