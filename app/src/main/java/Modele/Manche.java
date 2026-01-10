package Modele;

public class Manche {
    Contenu contenu;
    int pointMax;
    long tickDepart;

    void calculerPoint(long tickFinale, User u){
        //à revoir pour le calcul des points
        int pointGagner = 1/((int)tickDepart - (int)tickFinale)*pointMax;
        u.score += pointGagner;
    }

    void start() {
        // à completer
        contenu.lecture();
    }
}
