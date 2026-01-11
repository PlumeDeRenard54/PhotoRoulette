package com.example.photoroulette

import android.content.DialogInterface
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

        rejoindrePartie.setOnClickListener {
            val textPseudo = pseudo.text.toString()
            if (textPseudo.isEmpty()) {
                Toast.makeText(this, "Veuillez saisir un prénom correct", Toast.LENGTH_SHORT).show()
            } else {
                // Créer le Builder
                val builder = AlertDialog.Builder(this, R.style.MonDialoguePerso)
                builder.setTitle("Rejoindre une partie")
                builder.setMessage("Saisissez le code de la partie à rejoindre")

                // Inflate le layout personnalisé
                val customLayout: View = layoutInflater.inflate(R.layout.code_party_layout, null)
                builder.setView(customLayout)

                // IMPORTANT : On met le listener à 'null' ici pour éviter la fermeture automatique
                builder.setPositiveButton("Rejoindre", null)
                builder.setNegativeButton("Annuler") { dialog, _ -> dialog.dismiss() }

                // Création du dialogue
                val dialog = builder.create()

                // Appliquer les bords ronds (assure-toi d'avoir un drawable avec une couleur de fond, ex: fond_dialogue)
                // Si tu utilises bouton_circulaire tel quel, le fond risque d'être transparent.
                dialog.window?.setBackgroundDrawableResource(R.drawable.font_dialogue)
                // Conseil : utilise plutôt R.drawable.fond_dialogue créé ci-dessus si bouton_circulaire n'a pas de <solid>

                dialog.show()

                // On récupère le bouton APRÈS le show() et on remplace son comportement
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                    val editText: EditText = customLayout.findViewById(R.id.entrodePart)
                    val codePartie = editText.text.toString()

                    if (codePartie.isEmpty()) {
                        // Le code est vide/invalide : on affiche l'erreur MAIS on ne ferme pas le dialogue
                        Toast.makeText(this, "Veuillez saisir un code correct", Toast.LENGTH_SHORT).show()
                    } else {
                        // Le code est bon : on traite et on ferme manuellement
                        Toast.makeText(this, "Code: $codePartie", Toast.LENGTH_SHORT).show()

                        // TODO: Ajouter ta logique pour rejoindre la partie ici

                        dialog.dismiss()
                    }
                }
            }
        }
    }
}