package com.example.t1_aplicativos_grupo8

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Obtener id
        val emailEditText = findViewById<TextInputEditText>(R.id.emailEditText)
        val passwordEditText = findViewById<TextInputEditText>(R.id.passwordEditText)
        val loginButton = findViewById<MaterialButton>(R.id.loginButton)
        val goToRegister = findViewById<TextView>(R.id.goToRegisterText)

        //ABRIR ENLACE
        fun openWebPage(url: String) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        val facebookIcon = findViewById<ImageView>(R.id.facebookIcon)
        facebookIcon.setOnClickListener {
            openWebPage("https://www.facebook.com/UPN")
        }

        // ImageView de Twitter
        val twitterIcon = findViewById<ImageView>(R.id.twitterIcon)
        twitterIcon.setOnClickListener {
            openWebPage("https://x.com/upn_oficial")
        }

        // ImageView de YouTube
        val youtubeIcon = findViewById<ImageView>(R.id.youtubeIcon)
        youtubeIcon.setOnClickListener {
            openWebPage("https://www.youtube.com/c/UniversidadPrivadadelNorteTV")
        }

        // ImageView de Instagram
        val instagramIcon = findViewById<ImageView>(R.id.instagramIcon)
        instagramIcon.setOnClickListener {
            openWebPage("https://www.instagram.com/upn/")
        }

        // ImageView de LinkedIn
        val linkedinIcon = findViewById<ImageView>(R.id.linkedinIcon)
        linkedinIcon.setOnClickListener {
            openWebPage("https://www.linkedin.com/school/universidad-privada-del-norte/")
        }

        val dbHelper = SqlLiteHelper(this);

        goToRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val usuarioValido = dbHelper.iniciarSesion(email, password)
                if (usuarioValido) {
                    val nombre = dbHelper.obtenerNombre(email, password)

                    //ABRE PAGINA DE BIENVENIDA
                    val intent = Intent(this, login_successful::class.java)
                    intent.putExtra("nombreUsuario", nombre)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }

            val usuarios = dbHelper.obtenerUsuarios()
            for (usuario in usuarios) {
                println(usuario)
            }
        }

    }
}
