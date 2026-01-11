package Modele;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.provider.MediaStore.Images;
import android.util.Base64;
import android.view.View;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

/**
 * Classe d'empaquetement des données pour envoi
 */
public class Message {

    /**
     * Type du message
     */
    public final MessageTypes type;

    /**
     * Contenu du message
     */
    public final String contenu;

    /**
     * Constructeur de base
     *
     * @param type    Type du message
     * @param contenu COntenu du message
     */
    public Message(MessageTypes type, String contenu) {
        this.contenu = contenu;
        this.type = type;
    }

    /**
     * Creation de message image
     *
     * @param image image a empaqueter
     */
    public Message(Bitmap image) {
        this.type = MessageTypes.image;

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //Compression de l'image
        image.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);

        //Serialisation
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        this.contenu = Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    /**
     * Deserialisation de l'image
     */
    public static Bitmap toBitmap(String contenu) {
        byte[] decodedString = Base64.decode(contenu, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    /**
     * Affichage de base à fin de débug
     *
     * @return affichage string
     */
    @NonNull
    public String toString() {
        return this.contenu + " (" + this.type + ")";
    }

    /**
     * SeriaJson pour envoi
     *
     * @return objet Json
     */
    public JSONObject getJson() {

        try {
            return new JSONObject()
                    .put("type", type.toString())
                    .put("contenu", contenu);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


    public Bitmap getImage() {
        return toBitmap(this.contenu);
    }

    /**
     * Deserialisation depuis Json
     *
     * @param json String en json
     * @return Message deserialisé
     * @throws JSONException idk
     */
    public static Message fromJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject.getString("type").equals("image")) {
            return new Message(toBitmap(jsonObject.getString("contenu")));
        } else {
            return new Message(MessageTypes.valueOf(jsonObject.getString("type")), jsonObject.getString("contenu"));
        }
    }

}