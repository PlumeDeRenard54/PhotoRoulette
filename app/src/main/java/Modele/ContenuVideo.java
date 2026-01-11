package Modele;

import android.provider.MediaStore;

public class ContenuVideo implements Contenu {
    User proprietaire;
    MediaStore.Video video;
    ContenuVideo (User p, MediaStore.Video v) {
        this.proprietaire = p;
        this.video = v;
    }

    public void lecture(){}
    public void supprimer(){}
}
