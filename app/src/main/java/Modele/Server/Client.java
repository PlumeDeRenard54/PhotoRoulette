package Modele.Server;

import Modele.Message;
import Modele.MessageTypes;

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

    /**
     * Socket client
     */
    private final Socket socket;

    private String nom;

    /**
     * Sortie d'ecriture vers le client
     */
    private final PrintWriter out;

    /**
     * Arrivée des données depuis le client
     */
    private final BufferedReader in;

    /**
     * Room o`u se trouve le client
     */
    private Room room;

    /**
     * Lecture des données venant du client
     */

    private final Thread listener;

    /**
     * Constructeur
     * @param socket socket relié au client
     */
    public Client(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream(),true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        /*
        * Ecoutes les messages venant du client
         */
        this.listener = new Thread(()->{
            while (true){
                Message message;
                try {
                     message = Message.fromJson(this.in.readLine());

                    //Traitement des messages

                     switch (message.type){
                         //Message utilisé pour rejoindre une room
                         case join :
                            this.setRoom(Server.getServer().getRooms().get(message.contenu));
                            break;

                         //Demande de la liste de rooms
                         case askRooms :
                             StringBuilder roomList = new StringBuilder();
                             for (String room : Server.getServer().getAvaliableRooms()){
                                 roomList.append(room).append("\n");
                             }
                             send(new Message(MessageTypes.roomList,roomList.toString()));
                             break;

                         //Setting du nom
                         case setName:
                             this.nom = message.contenu;
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



    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
