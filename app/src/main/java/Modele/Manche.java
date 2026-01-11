package Modele;

import android.provider.MediaStore;

import org.json.JSONException;
import org.json.JSONObject;

public class Manche implements SeriJSon{
    Contenu contenu;
    User proprietaire;
    int pointMax;
    long tickDepart;

    Manche(User propietaire, int pointMax) {
        this.proprietaire = propietaire;
        this.pointMax = pointMax;
        contenu = null;
    }

    Manche(User proprietaire,int pointMax,Contenu contenu,long tickDepart){
        this.proprietaire = proprietaire;
        this.pointMax = pointMax;
        this.contenu = contenu;
        this.tickDepart = tickDepart;
    }

    void setContenu(MediaStore.Images m) {
        contenu = new ContenuImage(proprietaire, m);
    }

    void setContenu(MediaStore.Video m) {
        contenu = new ContenuVideo(proprietaire, m);
    }

    void calculerPoint(long tickFinale, User u) {
        //à revoir pour le calcul des points
        int pointGagner = 1 / ((int) tickDepart - (int) tickFinale) * pointMax;
        u.score += pointGagner;
    }

    void start() {
        // à completer
        contenu.lecture();
    }

    void supprimer() {
        if (contenu != null) {
            contenu.supprimer();
        }
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
                    .put("contenu", contenu.toJson())
                    .put("proprietaire", proprietaire.toJson())
                    .put("pointMax", pointMax)
                    .put("tickeDepart", tickDepart);
        }catch (JSONException e){throw new RuntimeException(e);}
    }

    public static Manche fromJson(JSONObject jsonObject){
        try {
            return new Manche(
                    User.fromJson(jsonObject.getJSONObject("proprietaire")),
                    jsonObject.getInt("pointMax"),
                    Contenu.fromJson(jsonObject.getJSONObject("contenu")),
                    jsonObject.getLong("tickDepart"));
        }catch (JSONException e){throw new RuntimeException(e);}
    }
}