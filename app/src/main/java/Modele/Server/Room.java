package Modele.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Modele.Message;
import Modele.MessageTypes;

/**
 * Rooms o`u se trouvent les joueurs
 */
public class Room {

    /**
     * Liste des joueurs
     */
    private Map<String,Client> joueurs = new HashMap<>();

    /**
     * Si la partie est en cours
     */
    private boolean enCours = false;

    private boolean isPublic = false;

    /**
     * Nombre maximum de joueurs
     */
    private int nbJoueursMax = 10;


    /**
     * Methode pour rejoindre la room
     * @param joueur joueur voulant rejoindre
     * @return si la room a été rejointe
     */
    public boolean join(Client joueur){
        if (!this.enCours && joueurs.size()<nbJoueursMax ){
            //Notification de l'arrivée du joueur
            broadCast(new Message(MessageTypes.join,joueur.getNom()));

            //Ajout du joueur
            joueurs.put(joueur.getNom(), joueur);
            joueur.setRoom(this);
            return true;
        }

        return false;
    }

    public void togglePublic(){
        this.isPublic = !this.isPublic;
    }
    public boolean isAvaliable(){
        return (!this.enCours && joueurs.size()<nbJoueursMax );
    }

    public boolean isPublic(){return isPublic;}

    public void broadCast(Message message){
        for (Client c : joueurs.values()){
            c.send(message);
        }
    }
}
