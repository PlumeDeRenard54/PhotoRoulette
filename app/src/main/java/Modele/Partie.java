package Modele;

import java.util.ArrayList;
import java.util.List;

public class Partie {
    ArrayList<User> joueurs;
    ArrayList<Manche> listeManche;

    void ajouterJoueur(User j){
        joueurs.add(j);
    }

    void supprimerJoueur(User j){
        joueurs.remove(j);
    }

    void creerManches(){}

    void start() {
        for(Manche m : listeManche){
            m.start();
        }
    }
}
