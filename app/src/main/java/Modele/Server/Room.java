package Modele.Server;

import java.util.ArrayList;

public class Room {

    private ArrayList<Client> joueurs = new ArrayList<>();

    private boolean enCours = false;

    private int nbJoueursMax = 10;


    public boolean join(Client joueur){
        if (!this.enCours && joueurs.size()<nbJoueursMax ){
            joueurs.add(joueur);
            joueur.setRoom(this);
            return true;
        }

        return false;
    }
}
