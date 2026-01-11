package Modele.Contenu;

import android.provider.MediaStore;

import org.json.JSONException;
import org.json.JSONObject;

import Modele.User;

public class ContenuVideo implements Contenu {
    MediaStore.Video video;
    public ContenuVideo ( MediaStore.Video v) {
        this.video = v;
    }

    public void lecture(){}
    public void supprimer(){}

    @Override
    public JSONObject toJson() {
        try {
            return new JSONObject()
                    .put("type","video");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static Contenu fromJson(JSONObject jsonObject){
        return new ContenuVideo(null);
    }
}
