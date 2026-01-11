package Modele.Contenu;

import android.provider.MediaStore;

import org.json.JSONException;
import org.json.JSONObject;

import Modele.User;

public class ContenuImage implements Contenu {
    private MediaStore.Images image;
    public ContenuImage(MediaStore.Images m){
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
                    .put("type", "image");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static Contenu fromJson(JSONObject jsonObject){
        return new ContenuImage(null);
    }
}
