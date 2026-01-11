package Modele;

import android.provider.MediaStore;

public class Manche {
    Contenu contenu;
    User proprietaire;
    int pointMax;
    long tickDepart;

    Manche(User propietaire, int pointMax){
            this.proprietaire = propietaire;
            this.pointMax = pointMax;
        }
    void setContenu(MediaStore.Images m){
        contenu = new ContenuImage(proprietaire,m);
    }
    void setContenu(MediaStore.Video m){
        contenu = new ContenuVideo(proprietaire,m);
    }

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
