package com.example.t1_aplicativos_grupo8

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val nombreInput = findViewById<TextInputEditText>(R.id.nombreEditText)
        val emailInput = findViewById<TextInputEditText>(R.id.emailEditText)
        val passwordInput = findViewById<TextInputEditText>(R.id.passwordEditText)
        val confirmPasswordInput = findViewById<TextInputEditText>(R.id.confirmPasswordEditText)
        val registerButton = findViewById<MaterialButton>(R.id.loginButton)
        val goToLogin = findViewById<TextView>(R.id.goToLoginText)

        goToLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val dbHelper = SqlLiteHelper(this)

        registerButton.setOnClickListener {
            val nombre = nombreInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString()
            val confirmPassword = confirmPasswordInput.text.toString()

            if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val registrado = dbHelper.AgregarUsuario(nombre, email, password)

            if (registrado) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                finish() // Cierra esta pantalla y vuelve al login
            } else {
                Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
