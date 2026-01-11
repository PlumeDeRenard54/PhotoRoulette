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

public class Message {

    public final String type;

    public final String contenu;

    public Message(String type,String contenu){
        this.contenu = contenu;
        this.type = type;
    }

    public Message(Bitmap image){
        this.type = "image";

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // On compresse l'image pour qu'elle soit moins lourde à envoyer
        image.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        this.contenu = Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    /**
     * Reconvertit le texte Base64 en image Bitmap
     */
    public static Bitmap toBitmap(String contenu) {
        byte[] decodedString = Base64.decode(contenu, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    @NonNull
    public String toString(){
        return this.contenu + " (" + this.type + ")";
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

    public Bitmap getImage(){
        return toBitmap(this.contenu);
    }

    public static Message fromJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject.getString("type").equals("image")){
            return new Message(toBitmap(jsonObject.getString("contenu")));
        }else {
            return new Message(jsonObject.getString("type"), jsonObject.getString("contenu"));
        }
    }

    public static void main(String[] args){
        //Tests de seria
        int w = 40, h = 40;
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        Bitmap bmp = Bitmap.createBitmap(w, h, conf);

        String data = "DONNEES";
        String type = "TYPE";

        Message message = new Message(data,type);

        if (!message.toString().equals(data+" (" + type + ")")){
            System.out.println("Erreur constructeur de base");
            System.out.println(message);
        }

        if (message.getJson().toString().equals("feur")){
            System.out.println("Erreur seria de base");
            System.out.println(message);
        }

        message = new Message(bmp);

        if (message.toString().equals("Feur")){
            System.out.println("Erreur constructeur image");
            System.out.println(message);
        }




    }
}
