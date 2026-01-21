package Modele;

import android.widget.Toast;

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
    private static String host = "10.102.5.147";

    /**
     * Instance du Client
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

    private String name;

    /**
     * Constructeur
     */
    private Client() {
        try {
            this.socket = new Socket(host, 25565);
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            this.listener = new Thread(()->{
                while (true){
                    Message message;
                    try {
                        message = Message.fromJson(this.in.readLine());
                        System.out.println("Message reçu : " + message.toString());


                        switch (message.type){

                            //Un joueur rejoint la game
                            case join:
                                partie.ajouterJoueur(new User(message.contenu));
                                break;

                            //Recuperation des données de la partie
                            case roomData:
                                this.partie = Partie.fromJson(new JSONObject(message.contenu));

                                //Envoi du message pour notifier que les données sont bien recues
                                send(new Message(MessageTypes.loaded,"Données Chargées"));
                                break;

                            //lancement de la partie          ,
                            case launch:
                                //this.partie.start();
                                System.out.println("La partie commence !");
                                break;

                            case  error:
                                System.out.println("Erreur : " + message.contenu);
                                break;

                            case end:
                                break;
                                //TODO Gestion fin client

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

    /**
     * Envoie l'instruction au server de creer une room
     */
    public void creatRoom(){
        send(new Message(MessageTypes.createRoom,"69696969"));
        joinRoom("69696969");
    }


    /**
     * Envoie l'instruction au server de connecter le client à la room
     * @param roomName nom /code de la room
     */
    public void joinRoom(String roomName){
        send(new Message(MessageTypes.join,roomName));
    }

    /**
     * Envoie une requete au server pour lancer la partie
     * Si tout le monde a téléchargé les données, la partie se lance
     */
    public void launchGame(){
        send(new Message(MessageTypes.launch,"Demande de lancement"));
    }

    /**
     * Set son nom en local et en network
     * @param name nom
     */
    public void setName(String name){
        this.name = name;
        send(new Message(MessageTypes.setName,name));
    }

    public void play(int nbPoints){
        send(new Message(MessageTypes.play,nbPoints+""));
    }

    public static void main(String[] args){
        Client client = Client.getInstance();
    }

    public Partie getPartie(){
        return this.partie;
    }
}
