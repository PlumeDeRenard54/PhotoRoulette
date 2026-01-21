package com.example.photoroulette

import Modele.Client
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
                // Cela va déclencher le constructeur et donc la connexion socket
                val client = Client.getInstance()
                println("Connecté au serveur !")

                // Si vous avez besoin de modifier l'UI après la connexion,
                // n'oubliez pas de revenir sur le thread principal :
                // runOnUiThread(() -> { ... });
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()

        creerPartie.setOnClickListener {
            val textPseudo = pseudo.text.toString()
            if (textPseudo.isEmpty()) {
                Toast.makeText(this, "Veuillez entrer un pseudo", Toast.LENGTH_SHORT)
                    .show()
            } else {
            // CORRECTION : Lancement dans un Thread séparé pour éviter NetworkOnMainThreadException
            Thread {
                    try {
                        Client.getInstance().joinRoom("new")
                    } catch (e: Exception) {
                        e.printStackTrace()
                        // Revenir sur le thread principal pour afficher le Toast
                        runOnUiThread {
                            Toast.makeText(this, "Problème de connexion", Toast.LENGTH_SHORT).show()
                        }
                    }
                }.start()
            }
        }


        rejoindrePartie.setOnClickListener {
            val textPseudo = pseudo.text.toString()
            if (textPseudo.isEmpty()) {
                Toast.makeText(this, "Veuillez entrer un pseudo", Toast.LENGTH_SHORT).show()
            } else {
                // Créer le Builder
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
                        Toast.makeText(this, "Veuillez saisir un code correct", Toast.LENGTH_SHORT).show()
                    } else {
                        // CORRECTION : Lancement dans un Thread séparé
                        Thread {
                            try {
                                Client.getInstance().setName(textPseudo)
                                Client.getInstance().joinRoom(codePartie)
                                // Optionnel : Fermer le dialogue sur le thread principal si succès
                                runOnUiThread { dialog.dismiss() }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                runOnUiThread {
                                    Toast.makeText(this, "Problème de connexion", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }.start()
                    }
                }
            }
        }
    }
}