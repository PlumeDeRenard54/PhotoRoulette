package Modele.Contenu;

import org.json.JSONException;
import org.json.JSONObject;

public interface Contenu {
    //lancer la lecture du média
    public void lecture();
    //suppime la video/image du serveur et de chez les utilisateurs
    public void supprimer();

    JSONObject toJson();

    static Contenu fromJson(JSONObject jsonObject){
        try {
            if(jsonObject.getString("type").equals("video")){
                return ContenuVideo.fromJson(jsonObject);
            }else{
                return ContenuImage.fromJson(jsonObject);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


}
