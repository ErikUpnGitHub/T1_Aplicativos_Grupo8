package com.example.t1_aplicativos_grupo8

import android.util.Log
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.t1_aplicativos_grupo8.data.Usuario
import com.example.t1_aplicativos_grupo8.db.AppDatabase
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import androidx.core.net.toUri

class MainActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializamos Room
        db = AppDatabase.getDatabase(applicationContext)

        val emailEditText = findViewById<TextInputEditText>(R.id.emailEditText)
        val passwordEditText = findViewById<TextInputEditText>(R.id.passwordEditText)
        val loginButton = findViewById<MaterialButton>(R.id.loginButton)
        val goToRegister = findViewById<TextView>(R.id.goToRegisterText)

        lifecycleScope.launch {
            val usuarios = db.usuarioDao().listar()
            for (usuario in usuarios) {
                Log.d("usuario", usuario.toString())
            }
        }

        // Abrir redes
        fun openWebPage(url: String) {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.facebookIcon).setOnClickListener {
            openWebPage("https://www.facebook.com/UPN")
        }
        findViewById<ImageView>(R.id.twitterIcon).setOnClickListener {
            openWebPage("https://x.com/upn_oficial")
        }
        findViewById<ImageView>(R.id.youtubeIcon).setOnClickListener {
            openWebPage("https://www.youtube.com/c/UniversidadPrivadadelNorteTV")
        }
        findViewById<ImageView>(R.id.instagramIcon).setOnClickListener {
            openWebPage("https://www.instagram.com/upn/")
        }
        findViewById<ImageView>(R.id.linkedinIcon).setOnClickListener {
            openWebPage("https://www.linkedin.com/school/universidad-privada-del-norte/")
        }

        // Navegar a registro
        goToRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        // Login con Room
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch {
                    val usuario = db.usuarioDao().login(email, password)
                    runOnUiThread {
                        if (usuario != null) {
                            val intent = Intent(this@MainActivity, HomeActivity::class.java)
                            intent.putExtra("idrol", 3)
                            intent.putExtra("idusuario", usuario.idusuario)
                            intent.putExtra("nombreUsuario", usuario.nombre) // <== ESTE es importante
                            startActivity(intent)

                        } else {
                            Toast.makeText(this@MainActivity, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // OPCIONAL: Agrega un usuario de prueba si no existe
        lifecycleScope.launch {
            val existe = db.usuarioDao().login("test@upn.com", "1234")
            if (existe == null) {
                db.usuarioDao().insertarYObtenerId(
                    Usuario(
                        nombre = "Test",
                        apellido = "User",
                        email = "test@upn.com",
                        contrasenia = "1234",
                        telefono = "999999999",
                        idrol = 1
                    )
                )
            }
        }
    }
}