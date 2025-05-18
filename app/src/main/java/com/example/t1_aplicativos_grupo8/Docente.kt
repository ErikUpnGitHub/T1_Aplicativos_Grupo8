// Docente.kt
package com.example.t1_aplicativos_grupo8

data class Docente(
    val id: Int,
    val nombre: String,
    val email: String,
    val password: String
) {
    override fun toString(): String = nombre
}

