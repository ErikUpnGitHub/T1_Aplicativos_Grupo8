package com.example.t1_aplicativos_grupo8

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val idrol = intent.getIntExtra("idrol", 0)
        val nombreUsuario = intent.getStringExtra("nombreUsuario") ?: "Usuario"

        // Redirigir según rol
        when (idrol) {
            1 -> {
                startActivity(Intent(this, EstudianteActivity::class.java).apply {
                    putExtra("idrol", idrol)
                    putExtra("nombreUsuario", nombreUsuario)
                })
                finish()
                return
            }
            2 -> {
                startActivity(Intent(this, ProfesorActivity::class.java).apply {
                    putExtra("idrol", idrol)
                    putExtra("nombreUsuario", nombreUsuario)
                })
                finish()
                return
            }
            3 -> {
                startActivity(Intent(this, AdminActivity::class.java).apply {
                    putExtra("idrol", idrol)
                    putExtra("nombreUsuario", nombreUsuario)
                })
                finish()
                return
            }
        }

        // Si no es ninguno de los anteriores, carga layout normal
        setContentView(R.layout.activity_home)

        // Referencias
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)
        val toolbar: Toolbar = findViewById(R.id.toolbar)

        // Toolbar como ActionBar
        setSupportActionBar(toolbar)

        // Botón de hamburguesa
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Cargar menú según rol (por si quieres manejar otro menú para otros roles)
        val menu: Menu = navigationView.menu
        menu.clear()
        navigationView.inflateMenu(R.menu.menu_estudiante)

        // Mostrar nombre en el header
        val headerView = navigationView.getHeaderView(0)
        val headerText = headerView.findViewById<TextView>(R.id.nav_header_title)
        headerText.text = "¡Hola, $nombreUsuario!"

        // Detectar clics en el menú
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Acción para "Inicio"
                }
                R.id.nav_courses -> {
                    // Acción para "Mis Cursos"
                }
                R.id.nav_logout -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }
}
