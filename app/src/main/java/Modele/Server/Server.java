package Modele.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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

    private volatile List<Client> socketList;

    private Thread clientSearch;

    private Server() throws IOException {
        this.serverSocket = new ServerSocket(45600);
        this.socketList = new ArrayList<>();

        //Thread de recherche de client
        this.clientSearch = new Thread(()->{
            while (true) {
                Socket s = null;
                try {
                    s = this.serverSocket.accept();
                    socketList.add(new Client(s));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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
