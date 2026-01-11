package Modele;

import org.json.JSONException;
import org.json.JSONObject;

import Modele.Server.Server;

public class User implements SeriJSon{

    /**
     * est ce que l'utilisateur a bien fini de load les données
     */
    private boolean isLoaded = false;
    private final String name;
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

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public String getName(){return name;}

    public void resetScore(){this.score = 0;}

    public void addScore(int i){this.score += i;}

    public int getScore(){return score;}
}
