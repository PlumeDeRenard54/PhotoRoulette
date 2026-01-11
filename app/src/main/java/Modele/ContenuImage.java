package Modele;

import android.provider.MediaStore;

public class ContenuImage implements Contenu {
    User proprietaire;
    MediaStore.Images image;
    ContenuImage(User proprietaire, MediaStore.Images m){
        this.proprietaire = proprietaire;
        image = m;
    }
    public void lecture() {}
    public void supprimer(){}
}
