package Modele;

import Modele.Server.Server;

public class User {
    String name;
    int score;
    Server serveurUtilisateur;

    public User(String name){
        this.name = name;
        this.score = 0;
        serveurUtilisateur = null;
    }

    public Contenu creerContenu() {
        return null;
    }
}
