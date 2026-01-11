package Modele;

import org.json.JSONException;
import org.json.JSONObject;

import Modele.Server.Server;

public class User implements SeriJSon{
    String name;
    int score;

    public User(String name,int score){
        this.name = name;
        this.score = score;
    }

    public User(String name){
        this(name,0);
    }


    /**
     * Methode de serialisation Json
     *
     * @return Objet Json
     */
    @Override
    public JSONObject toJson() {
        try {
            return new JSONObject()
                    .put("name", name)
                    .put("score", score);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static User fromJson(JSONObject jsonObject){
        try {
            return new User(jsonObject.getString("name"),jsonObject.getInt("score"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
