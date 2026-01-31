package com.example.photoroulette

import Modele.Client
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets // Return the insets
        }

        val pseudo = findViewById<EditText>(R.id.entrePseudo)
        val rejoindrePartie = findViewById<Button>(R.id.Rejoindre)
        val creerPartie = findViewById<Button>(R.id.CreerPartie)

        // Connexion initiale au serveur
        Thread {
            try {
                Client.getInstance()
                println("Connecté au serveur !")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()

        creerPartie.setOnClickListener {
            // Récupération du pseudo
            val textPseudo = pseudo.text.toString()
            if (textPseudo.isEmpty()) {
                Toast.makeText(this, "Veuillez entrer un pseudo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // On quitte la fonction
            }
            // On définit ce qui doit se passer quand la room sera chargée
            Client.getInstance().setOnRoomLoadedListener { partie ->
                // Le listener est appelé depuis le Thread du Client, donc on doit l'appeler sur le Thread principal
                runOnUiThread {
                    val intent = Intent(this@MainActivity, SalonInvite::class.java)
                    startActivity(intent)
                    Client.getInstance().setOnRoomLoadedListener(null) // On retire le listener
                }
            }
            // 2. On lance la demande réseau dans un thread
            Thread {
                val client = Client.getInstance()
                client.setName(textPseudo)
                client.creatRoom() // Le serveur répondra, déclenchera roomData -> onLoaded -> startActivity
            }.start()
            Toast.makeText(this, "Création de la salle...", Toast.LENGTH_SHORT).show()
        }


        rejoindrePartie.setOnClickListener {
            // Récupération du pseudo
            val textPseudo = pseudo.text.toString()
            if (textPseudo.isEmpty()) {
                Toast.makeText(this, "Veuillez entrer un pseudo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // On quitte la fonction
            }
            // Créer le Builder du dialogue (pour rentrer le code de la partie)
            val builder = AlertDialog.Builder(this, R.style.MonDialoguePerso)
            builder.setTitle("Rejoindre une partie")
            builder.setMessage("Saisissez le code de la partie à rejoindre")

            // Inflate le layout personnalisé
            val customLayout: View = layoutInflater.inflate(R.layout.code_party_layout, null)
            builder.setView(customLayout)
            builder.setPositiveButton("Rejoindre", null)
            builder.setNegativeButton("Annuler") { dialog, _ -> dialog.dismiss() }

            // Création du dialogue
            val dialog = builder.create()
            // Appliquer les bords ronds
            dialog.window?.setBackgroundDrawableResource(R.drawable.font_dialogue)
            dialog.show()

            // On override le listener du bouton positif pour éviter que le dialogue ne se ferme automatiquement si erreur
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val editText: EditText = customLayout.findViewById(R.id.entrodePart)
                val codePartie = editText.text.toString()

                if (codePartie.isEmpty()) {
                    Toast.makeText(this, "Code vide", Toast.LENGTH_SHORT).show()
                } else {
                    // On écoute la réception de la room
                    Client.getInstance().setOnRoomLoadedListener { partie ->
                        runOnUiThread {
                            dialog.dismiss() // On ferme le dialogue
                            val intent = Intent(this@MainActivity, SalonInvite::class.java)
                            startActivity(intent)
                            Client.getInstance().setOnRoomLoadedListener(null) // On retire le listener
                        }
                    }
                    //On envoie la demande
                    Thread {
                        val client = Client.getInstance()
                        client.setName(textPseudo)
                        client.joinRoom(codePartie)
                    }.start()

                    Toast.makeText(this, "Connexion au salon...", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
