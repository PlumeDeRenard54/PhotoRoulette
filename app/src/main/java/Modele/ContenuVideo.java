package Modele;

import android.provider.MediaStore;

public class ContenuVideo implements Contenu {

    User proprietaire;
    MediaStore.Video video;

    public void setProprietaire(User p) {
        this.proprietaire = p;
    }

    public void lecture(){}
}
