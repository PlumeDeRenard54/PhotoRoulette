package com.example.photoroulette

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
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
                // Logic for joining
            }
        }

        creerPartie.setOnClickListener {
            // Logic for creating
        }
    }
}