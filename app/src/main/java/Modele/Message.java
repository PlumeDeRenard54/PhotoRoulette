package Modele;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {

    public final String type;

    public final String contenu;

    public Message(String type,String contenu){
        this.contenu = contenu;
        this.type = type;
    }

    public JSONObject getJson() {

        try {
            return new JSONObject()
                    .put("type",type)
                    .put("contenu",contenu);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static Message fromJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        return new Message(jsonObject.getString("type"),jsonObject.getString("contenu"));
    }

}
