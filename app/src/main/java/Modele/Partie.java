package Modele;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Partie {
    ArrayList<User> joueurs;
    ArrayList<Manche> listeManche;

    int nbManches = 10 ;

    void ajouterJoueur(User j){
        joueurs.add(j);
    }

    void supprimerJoueur(User j){
        joueurs.remove(j);
    }

    void creerManches(){
        listeManche = new ArrayList<Manche>();
        for (int i =0; i < nbManches; i++){
            User joueur = joueurs.get(ThreadLocalRandom.current().nextInt(0, joueurs.size()));
            listeManche.add(new Manche(joueur,100));
        }
    }

    void start() {
        for(Manche m : listeManche){
            m.start();
        }
    }
}
