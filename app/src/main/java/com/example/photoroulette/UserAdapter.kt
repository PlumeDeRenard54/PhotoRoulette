package com.example.photoroulette

import Modele.User
import android.app.Activity
import android.content.Context
import android.widget.ArrayAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class UserAdapter(private val context: Activity, private val values: ArrayList<User>) : ArrayAdapter<User>(context, R.layout.carte_joueur_menu,values) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.carte_joueur_menu, parent, false)

        val imageView : ImageView =  view.findViewById(R.id.imageJoueur)
        val username : TextView = view.findViewById(R.id.nomJoueur);
        val points : TextView = view.findViewById(R.id.textPoints);
        val mancheGagnes : TextView = view.findViewById(R.id.textMancheGagnees);

        //Remplisage des données
        imageView.setImageResource(R.drawable.erwann); // To do : changer les images
        username.text = values[position].name;
        points.text = values[position].score.toString();
        mancheGagnes.text = "0"; // To do : changer la valeur

        return view;
    }

}