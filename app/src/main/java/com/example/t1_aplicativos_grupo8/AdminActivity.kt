package com.example.t1_aplicativos_grupo8

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.t1_aplicativos_grupo8.ui.fragments.AdminClasesFragment
import com.example.t1_aplicativos_grupo8.ui.fragments.AdminCursosFragment
import com.example.t1_aplicativos_grupo8.ui.fragments.AdminProfesoresFragment
import com.example.t1_aplicativos_grupo8.ui.fragments.AdminUsuariosFragment
import com.example.t1_aplicativos_grupo8.ui.fragments.AdminEstudiantesFragment
import com.example.t1_aplicativos_grupo8.ui.fragments.AdminHorariosFragment
import com.example.t1_aplicativos_grupo8.ui.fragments.AdminMatriculasFragment

class AdminActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.navigation_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout.closeDrawer(GravityCompat.START)

            when (menuItem.itemId) {

                R.id.nav_users -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content_frame, AdminUsuariosFragment())
                        .commit()
                }

                R.id.nav_students -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content_frame, AdminEstudiantesFragment())
                        .commit()
                }

                R.id.nav_professors -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content_frame, AdminProfesoresFragment())
                        .commit()
                }

                R.id.nav_courses -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content_frame, AdminCursosFragment())
                        .commit()
                }

                R.id.nav_classes -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content_frame, AdminClasesFragment())
                        .commit()
                }

                R.id.nav_schedules -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content_frame, AdminHorariosFragment())
                        .commit()
                }

                R.id.nav_enrollments -> {
                     supportFragmentManager.beginTransaction()
                        .replace(R.id.content_frame, AdminMatriculasFragment())
                        .commit()
                }

                // R.id.nav_notifications -> {
                //     supportFragmentManager.beginTransaction()
                //         .replace(R.id.content_frame, AdminNotificacionesFragment())
                //         .commit()
                // }

                R.id.nav_logout -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            }

            true
        }


    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
