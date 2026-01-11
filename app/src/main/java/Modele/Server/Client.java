package Modele.Server;

import Modele.Message;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Client d'un point de vue server
 */
public class Client {
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;

    private Room room;

    //Lecture des données venant du client
    private final Thread listener;

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream(),true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        this.listener = new Thread(()->{
            while (true){
                Message message;
                try {
                     message = Message.fromJson(this.in.readLine());

                     switch (message.type){
                         case "join" :

                         //Traitement des messages
                     }
                } catch (JSONException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        this.listener.start();
    }

    /**
     * Envoi de message au client
     * @param message message a transmettre
     */
    public void send(Message message){
        out.println(message.getJson());
        out.flush();
    }


    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
