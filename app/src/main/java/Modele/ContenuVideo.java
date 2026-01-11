package Modele;

import android.provider.MediaStore;

import org.json.JSONException;
import org.json.JSONObject;

public class ContenuVideo implements Contenu {
    User proprietaire;
    MediaStore.Video video;
    ContenuVideo (User p, MediaStore.Video v) {
        this.proprietaire = p;
        this.video = v;
    }

    public void lecture(){}
    public void supprimer(){}

    @Override
    public JSONObject toJson() {
        try {
            return new JSONObject()
                    .put("proprietaire", proprietaire.toJson())
                    .put("type","video");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static Contenu fromJson(JSONObject jsonObject){
        try {
            return new ContenuImage(User.fromJson(jsonObject.getJSONObject("proprietaire")),null);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
