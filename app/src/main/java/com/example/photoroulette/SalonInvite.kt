package com.example.photoroulette

import Modele.Client
import Modele.Partie
import android.os.Bundle
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

        // Gestion des Insets (barres système)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Récupération des données
        // Connexion initiale au serveur
        Thread {
        val client = Client.getInstance()
        val codePartie = findViewById<TextView>(R.id.text_code_room)
        val partie = client.getPartie()
        codePartie.text = partie.getNumRoom()
        val barProgression = findViewById<ProgressBar>(R.id.progressBar)
        barProgression.progress = 2
        }.start()
        }
}