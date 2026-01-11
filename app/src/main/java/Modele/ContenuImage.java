package Modele;

import android.provider.MediaStore;

import org.json.JSONException;
import org.json.JSONObject;

public class ContenuImage implements Contenu {
    //TODO repetition
    User proprietaire;
    MediaStore.Images image;
    ContenuImage(User proprietaire, MediaStore.Images m){
        this.proprietaire = proprietaire;
        image = m;
    }
    public void lecture() {}
    public void supprimer(){}

    /**
     * Methode de serialisation Json
     *
     * @return Objet Json
     */
    @Override
    public JSONObject toJson() {
        try {
            return new JSONObject()
                    .put("proprietaire", proprietaire.toJson());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
