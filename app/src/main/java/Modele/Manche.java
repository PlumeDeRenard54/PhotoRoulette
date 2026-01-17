package Modele;

import org.json.JSONException;
import org.json.JSONObject;
import Modele.Client.Client;
import java.time.LocalDateTime;

public class Manche implements SeriJSon {

    private User proprietaire;
    private int pointMax;
    private String tickDepart; // Stocké en String pour faciliter le transfert JSON
    private String typeContenu; // ex: "image" ou "video"
    private String dataContenu; // La String en Base64

    /**
     * Constructeur complet
     */
    public Manche(User proprietaire, int pointMax, String typeContenu, String dataContenu) {
        this.proprietaire = proprietaire;
        this.pointMax = pointMax;
        this.typeContenu = typeContenu;
        this.dataContenu = dataContenu;
    }

    // Constructeur sans contenu (si nécessaire)
    public Manche(User proprietaire, int pointMax) {
        this(proprietaire, pointMax, "inconnu", null);
    }

    public void start() {
        // Logique de démarrage
        this.tickDepart = LocalDateTime.now().toString();
        System.out.println("Manche démarrée. Type: " + typeContenu);
    }

    /**
     * Calcul des points (simplifié)
     */
    private int calculerPoint() {
        if (tickDepart == null) return 0;
        // Exemple basique : points fixes pour l'instant
        return pointMax;
    }

    public void play(User u, String response) {
        int tmp = 0;
        if (response.equals(this.proprietaire.getName())) {
            tmp = calculerPoint();
            u.addScore(tmp); // Utilisez addScore défini dans User
        }
        // Attention : Client.getInstance() est côté Client uniquement.
        // Si ce code tourne sur le Serveur, il faudra adapter cette ligne.
        if (Client.getInstance() != null) {
            Client.getInstance().play(tmp);
        }
    }

    @Override
    public JSONObject toJson() {
        try {
            return new JSONObject()
                    .put("proprietaire", proprietaire.toJson())
                    .put("pointMax", pointMax)
                    .put("tickDepart", tickDepart)
                    // On intègre directement les infos du contenu ici
                    .put("typeContenu", typeContenu)
                    .put("dataContenu", dataContenu);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static Manche fromJson(JSONObject jsonObject) {
        try {
            // Récupération des champs simples
            String type = jsonObject.optString("typeContenu", "image");
            String data = jsonObject.optString("dataContenu", "");
            int pMax = jsonObject.getInt("pointMax");

            User prop = User.fromJson(jsonObject.getJSONObject("proprietaire"));

            Manche m = new Manche(prop, pMax, type, data);

            if (jsonObject.has("tickDepart")) {
                m.tickDepart = jsonObject.getString("tickDepart");
            }
            return m;

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    // Getters / Setters
    public void setContenu(String type, String base64) {
        this.typeContenu = type;
        this.dataContenu = base64;
    }

    public String getDataContenu() {
        return dataContenu;
    }
}