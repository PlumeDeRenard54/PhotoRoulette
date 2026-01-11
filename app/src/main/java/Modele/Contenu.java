package Modele;

import org.json.JSONObject;

public interface Contenu {
    //lancer la lecture du média
    public void lecture();
    //suppime la video/image du serveur et de chez les utilisateurs
    public void supprimer();

    JSONObject toJson();


}
