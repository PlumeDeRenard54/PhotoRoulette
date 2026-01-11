package Modele;

import android.provider.MediaStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;

import Modele.Client.Client;
import Modele.Contenu.Contenu;
import Modele.Contenu.ContenuImage;
import Modele.Contenu.ContenuVideo;

public class Manche implements SeriJSon{
    Contenu contenu;
    User proprietaire;
    int pointMax;
    LocalDateTime tickDepart;

    Manche(User propietaire, int pointMax) {
        this.proprietaire = propietaire;
        this.pointMax = pointMax;
        contenu = null;
    }

    Manche(User proprietaire,int pointMax,Contenu contenu){
        this.proprietaire = proprietaire;
        this.pointMax = pointMax;
        this.contenu = contenu;
    }

    /**
     * Calcul des points
     * @return points gagnés
     */
    private int calculerPoint() {
        //à revoir pour le calcul des points
        int pointGagne = 1 / LocalDateTime.now().compareTo(tickDepart) * pointMax;
        return pointGagne;
    }

    /**
     * Methode lancée par la Partie pour lancer la manche
     */
    public void start() {
        // à completer
        contenu.lecture();
        tickDepart = LocalDateTime.now();
    }

    /**
     * Methode a lancer lors de la validation de la réponse
     * @param u utilisateur
     * @param response réponse donnée
     */
    public void play(User u, String response){
        int tmp = 0;
        if (response.equals(this.proprietaire.getName())){
            tmp = calculerPoint();
            u.score += tmp;
        }
        Client.getInstance().play(tmp);
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
                    Contenu.fromJson(jsonObject.getJSONObject("contenu")));
        }catch (JSONException e){throw new RuntimeException(e);}
    }

    void setContenu(MediaStore.Images m) {
        contenu = new ContenuImage(m);
    }

    void setContenu(MediaStore.Video m) {
        contenu = new ContenuVideo(m);
    }
}