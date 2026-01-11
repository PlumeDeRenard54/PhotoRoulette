package Modele.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Modele.Message;
import Modele.MessageTypes;
import Modele.Partie;

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

    /**
     * Nombre maximum de joueurs
     */
    private int nbJoueursMax = 10;

    private Partie partie;

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

    public boolean isAvaliable(){
        return (!this.enCours && joueurs.size()<nbJoueursMax );
    }

    /**
     * Envoie un message à tous les joueurs de la room
     * @param message message à envoyer
     */
    public void broadCast(Message message){
        for (Client c : joueurs.values()){
            c.send(message);
        }
    }

    public void launch(){
        broadCast(new Message(MessageTypes.launch,"blblblbl"));
    }

    public Partie getPartie() {
        return partie;
    }

    public void setPartie(Partie partie) {
        this.partie = partie;
    }

    public void setEnCours(boolean b){this.enCours=b;}

    public void clearAjoue(){
        for (Client c : joueurs.values()){
            c.setAJoue(false);
        }
    }

    public boolean tousJoue(){
        boolean tmp = true;
        for (Client c : joueurs.values()){
            tmp = tmp && c.isaJoue();
        }
        return tmp;
    }
}
