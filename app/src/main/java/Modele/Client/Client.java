package Modele.Client;

import Modele.Message;
import Modele.MessageTypes;
import Modele.Partie;
import Modele.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * CLient coté client
 */
public class Client {

    /**
     * Nom/ip du server
     */
    private static String host = "127.0.0.1";

    /**
     * Instance du Clie t
     */
    private static Client singleton ;

    /**
     * Socket client
     */
    private Socket socket;

    /**
     * Sortie de données
     */
    private final PrintWriter out;

    /**
     * Entree de données
     */
    private final BufferedReader in;

    /**
     * Ecoute des messages
     */
    private final Thread listener;

    /**
     * Partie jouée en ce moment
     */
    Partie partie = null;

    /**
     * COnstructeur
     */
    private Client() {
        try {
            this.socket = new Socket(host, 45600);
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            this.listener = new Thread(()->{
                while (true){
                    Message message;
                    try {
                        message = Message.fromJson(this.in.readLine());

                        switch (message.type){

                            //Un joueur rejoint la game
                            case join:
                                partie.ajouterJoueur(new User(message.contenu));

                            //Recuperation des données de la partie
                            case roomData:
                                this.partie = Partie.fromJson(new JSONObject(message.contenu));

                                //Envoi du message pour sotifier que les données sont bien recues
                                send(new Message(MessageTypes.loaded,"blblb"));

                            //lancement de la partie          ,
                            case launch:
                                this.partie.start();

                        }
                    } catch (JSONException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            this.listener.start();

        }catch (IOException e){
            throw new RuntimeException();
        }
    }

    /**
     * Setting du host
     * @param host nom de domaine/ip du server
     */
    public void setHost(String host){
        Client.host = host;
    }

    /**
     * Renvoie l'instance du client
     * @return instance
     */
    public static Client getInstance(){
        if (singleton == null){
            singleton = new Client();
        }
        return singleton;
    }

    /**
     * Envoi de message au client
     * @param message message a transmettre
     */
    public void send(Message message){
        out.println(message.toJson());
        out.flush();
    }

}
