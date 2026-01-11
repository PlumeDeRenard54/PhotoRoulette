package Modele.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe singleton gerant le Server
 */
public class Server {

    /**
     * Instance de server
     */
    private static Server singleton;

    /**
     * Socket du server
     */
    private final ServerSocket serverSocket;

    /**
     * Liste des clients
     */
    private volatile List<Client> socketList;

    /**
     * Map de toutes les rooms
     */
    private volatile Map<String, Room> rooms;

    /**
     * Thread de recherche de clients en continu
     */
    private Thread clientSearch;

    /**
     * Constructeur
     */
    private Server() throws IOException {
        this.rooms = new HashMap<>();
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

    /**
     * Acces au singleton
     * @return instance du server
     */
    public static Server getServer(){
        if (singleton == null){
            try {
                singleton = new Server();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return singleton;
    }

    /**
     * Acces a la liste des rooms
     * @return map de rooms
     */
    public Map<String, Room> getRooms() {
        return rooms;
    }

}
