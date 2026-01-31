package com.example.photoroulette

import Modele.Client
import Modele.Partie
import Modele.User
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SalonInvite : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_salon_invite)

        // Récupération des vues
        val tvCodePartie = findViewById<TextView>(R.id.text_code_room)
        val barProgression = findViewById<ProgressBar>(R.id.progressBar)
        val layoutListeJoueurs = findViewById<LinearLayout>(R.id.layoutListeJoueurs)


        // Récupérer les données passées par MainActivity
        val codeRecu = intent.getStringExtra("codePartie")


        // Gestion des Insets (barres système)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Récupération des données
        val client = Client.getInstance()
        val partieEnCours = client.getPartie()

        if (partieEnCours != null) {
            // C'est bon, l'objet existe !
            tvCodePartie.text = partieEnCours.numRoom

            val adapter = UserAdapter(this, partieEnCours.joueurs as ArrayList<User>)

            // Parcour des joueurs et ajout de leur vue au LinearLayout
            for (i in 0 until adapter.count) {
                val joueurView = adapter.getView(i, null, layoutListeJoueurs)
                layoutListeJoueurs.addView(joueurView)
            }
        } else {
            // Cas de sécurité : Si on arrive ici et que la partie est null
            // (Par exemple si l'utilisateur a tué l'app et l'a relancée directement sur cet écran)
            tvCodePartie.text = "Erreur : Aucune partie chargée"
            // Idéalement, on renvoie l'utilisateur à l'accueil
            Toast.makeText(this, "Erreur de données, retour au menu", Toast.LENGTH_LONG).show()
            finish()
        }



        }
}