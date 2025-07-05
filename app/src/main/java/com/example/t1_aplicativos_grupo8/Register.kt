package com.example.t1_aplicativos_grupo8

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.t1_aplicativos_grupo8.data.Usuario
import com.example.t1_aplicativos_grupo8.db.AppDatabase
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class Register : AppCompatActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register) // ← cambia si tu XML se llama distinto

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "universidad-db"
        ).build()

        val nombreEditText = findViewById<TextInputEditText>(R.id.nombreEditText)
        val apellidoEditText = findViewById<TextInputEditText>(R.id.apellidoEditText)
        val emailEditText = findViewById<TextInputEditText>(R.id.emailEditText)
        val passwordEditText = findViewById<TextInputEditText>(R.id.passwordEditText)
        val confirmPasswordEditText = findViewById<TextInputEditText>(R.id.confirmPasswordEditText)
        val registerButton = findViewById<MaterialButton>(R.id.loginButton)
        val goToLogin = findViewById<TextView>(R.id.goToLoginText)

        registerButton.setOnClickListener {
            val nombre = nombreEditText.text.toString().trim()
            val apellido = apellidoEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirm = confirmPasswordEditText.text.toString().trim()

            if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Correo electrónico inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirm) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val usuarioExistente = db.usuarioDao().buscarPorEmail(email)

                if (usuarioExistente != null) {
                    runOnUiThread {
                        Toast.makeText(this@Register, "Ese correo ya está registrado", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                } else {
                    val nuevoUsuario = Usuario(
                        nombre = nombre,
                        apellido = apellido,
                        email = email,
                        contrasenia = password,
                        telefono = "",
                        idrol = 3
                    )

                    db.usuarioDao().insertarYObtenerId(nuevoUsuario)

                    runOnUiThread {
                        Toast.makeText(this@Register, "Registro exitoso", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@Register, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }

        goToLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
