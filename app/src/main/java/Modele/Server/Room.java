package Modele.Server;

import java.util.ArrayList;

/**
 * Rooms o`u se trouvent les joueurs
 */
public class Room {

    /**
     * Liste des joueurs
     */
    private ArrayList<Client> joueurs = new ArrayList<>();

    /**
     * Si la partie est en cours
     */
    private boolean enCours = false;

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
            joueurs.add(joueur);
            joueur.setRoom(this);
            return true;
        }

        return false;
    }

    public boolean isAvaliable(){
        return (!this.enCours && joueurs.size()<nbJoueursMax );
    }
}
