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
                // Créer un AlertDialog
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Rejoindre une partie")
                builder.setMessage("Voulez-vous rejoindre la partie ?")

                // Set un layout pour l'AlertDialog
                val customLayout: View = layoutInflater.inflate(R.layout.code_party_layout, null)
                builder.setView(customLayout)

                // ajoute un bouton "Rejoindre"
                builder.setPositiveButton("Rejoindre") { dialog: DialogInterface?, which: Int ->
                    // envoie le code de la partie
                    val editText: EditText = customLayout.findViewById(R.id.entrodePart)
                    if(editText.text.toString().isEmpty()){
                        Toast.makeText(this, "Veuillez saisir un code correct", Toast.LENGTH_SHORT).show()
                    } else {
                    Toast.makeText(this, editText.text.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                // création du dialog
                val dialog = builder.create()
                dialog.show()
            }
        }
    }
}