package Modele;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Partie implements SeriJSon{

    public String numRoom ;
    private Map<String,User> joueurs;
    private ArrayList<Manche> listeManche;


    int nbManches = 10 ;

    int curManche = 0;

    public static Partie fromJson(JSONObject jsonObject){
        try {
            Partie partie = new Partie();
            partie.joueurs = new HashMap<>();
            partie.listeManche = new ArrayList<>();
            partie.numRoom = jsonObject.getString("numRoom");


            JSONArray joueurs = jsonObject.getJSONArray("joueurs");
            for (int i = 0; i < joueurs.length(); i++) {
                User u = User.fromJson(joueurs.getJSONObject(i));
                partie.joueurs.put(u.getName(),u);
            }

            JSONArray manches = jsonObject.getJSONArray("manches");
            for (int i = 0; i < manches.length(); i++) {
                partie.listeManche.add(Manche.fromJson(manches.getJSONObject(i)));
            }

            partie.nbManches = jsonObject.getInt("nbManches");

            return partie;
        }catch (JSONException e){throw new RuntimeException(e);}
    }

    public JSONObject toJson(){
        try {

            JSONArray j = new JSONArray();
            for (User u : joueurs.values()) {
                j.put(u.toJson());
            }

            JSONArray m = new JSONArray();
            for (Manche ma : listeManche) {
                m.put(ma.toJson());
            }

            return new JSONObject()
                    .put("joueurs", j)
                    .put("manches", m)
                    .put("nbManches",nbManches);
        }catch (JSONException e){
            throw new RuntimeException(e);
        }

    }

    public void ajouterJoueur(User j){
        joueurs.put(j.getName(), j);
    }

    public void supprimerJoueur(User j){
        joueurs.remove(j.getName());
    }

    /**
     * Creation aleatoire des manches
     */
    public void creerManches(){
        listeManche = new ArrayList<>();
        for (int i =0; i < nbManches; i++){
            int number = ThreadLocalRandom.current().nextInt(0, joueurs.size());
            User joueur = joueurs.get(joueurs.keySet().toArray()[number]);
            listeManche.add(new Manche(joueur,100));
        }
    }

    /**
     * Lance la prochaine manche
     */
    public void start() {
        this.listeManche.get(curManche).start();
        curManche++;
    }

    public Map<String,User> getJoueurs(){return this.joueurs;}

    public void clearLoaded(){
        this.getJoueurs().values().forEach((e)->{
            e.setLoaded(false);
        });
    }

    public User getBest(){
        User tmp = null;

        for (User u : this.joueurs.values()){
            if (tmp == null || tmp.score<u.score) {
                tmp = u;
            }
        }

        return tmp;
    }

    public boolean isFin(){return this.curManche-1==this.nbManches;}
}
