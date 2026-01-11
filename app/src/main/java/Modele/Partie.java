package Modele;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Partie implements SeriJSon{
    ArrayList<User> joueurs;
    ArrayList<Manche> listeManche;

    int nbManches = 10 ;

    public JSONObject toJson(){
        try {

            JSONArray j = new JSONArray();
            for (User u : joueurs) {
                j.put(u.toJson());
            }

            JSONArray m = new JSONArray();
            for (Manche ma : listeManche) {
                m.put(ma.toJson());
            }

            return new JSONObject()
                    .put("joueurs", j)
                    .put("manches", m);
        }catch (JSONException e){
            throw new RuntimeException(e);
        }

    }

    public void ajouterJoueur(User j){
        joueurs.add(j);
    }

    public void supprimerJoueur(User j){
        joueurs.remove(j);
    }

    public void creerManches(){
        listeManche = new ArrayList<Manche>();
        for (int i =0; i < nbManches; i++){
            User joueur = joueurs.get(ThreadLocalRandom.current().nextInt(0, joueurs.size()));
            listeManche.add(new Manche(joueur,100));
        }
    }

    void start() {
        for(Manche m : listeManche){
            m.start();
        }
    }
}
