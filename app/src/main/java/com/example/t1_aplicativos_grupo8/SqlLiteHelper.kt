package com.example.t1_aplicativos_grupo8

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf

class SqlLiteHelper(context: Context) : SQLiteOpenHelper(context, "Usuario.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val creation = """
            CREATE TABLE Usuario (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                email TEXT,
                password TEXT
            )
        """.trimIndent()
        db.execSQL(creation)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Usuario")
        onCreate(db)
    }

    fun AgregarUsuario(nombre: String, email: String, password: String): Boolean {
        val datos = ContentValues().apply {
            put("nombre", nombre)
            put("email", email)
            put("password", password)
        }

        val db = writableDatabase
        val resultado = db.insert("Usuario", null, datos)
        db.close()

        return resultado != -1L
    }

    fun iniciarSesion(email: String, password: String): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM Usuario WHERE email = ? AND password = ?"
        val cursor = db.rawQuery(query, arrayOf(email, password))

        val existe = cursor.count > 0

        cursor.close()
        db.close()

        return existe
    }

    fun obtenerUsuarios(): List<String> {
        val lista = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Usuario", null)

        if (cursor.moveToFirst()) {
            do {
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
                val password = cursor.getString(cursor.getColumnIndexOrThrow("password"))
                lista.add("Nombre: $nombre\nEmail: $email\nContraseña: $password\n")
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return lista
    }

    fun obtenerNombre(email: String, password: String): String? {
        val db = readableDatabase
        val query = "SELECT nombre FROM Usuario WHERE email = ? AND password = ?"
        val cursor = db.rawQuery(query, arrayOf(email, password))

        var nombre: String? = null
        if (cursor.moveToFirst()) {
            nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
        }

        cursor.close()
        db.close()
        return nombre
    }

}
