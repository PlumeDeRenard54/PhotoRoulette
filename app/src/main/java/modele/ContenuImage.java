package modele;

import android.provider.MediaStore;

public class ContenuImage implements Contenu{
    User proprietaire;
    MediaStore.Video video;

    public void setProprietaire(User p) {
        this.proprietaire = p;
    }

    public void lecture() {}
}
