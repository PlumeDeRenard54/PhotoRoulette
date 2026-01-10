package com.example.photoroulette.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * Classe singleton gerant le Server
 */
public class Server {

    /**
     * Instance de server
     */
    private static Server singleton;

    private final ServerSocket serverSocket;

    private volatile List<Socket> socketList;

    private Thread clientSearch;

    private Server() throws IOException {
        this.serverSocket = new ServerSocket();

        //Thread de recherche de client
        this.clientSearch = new Thread(()->{
            while (true) {
                Socket s = null;
                try {
                    s = this.serverSocket.accept();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                socketList.add(s);
            }
        });
        this.clientSearch.start();

    }

    public Server getServer(){
        if (singleton == null){
            try {
                singleton = new Server();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return singleton;
    }
}
