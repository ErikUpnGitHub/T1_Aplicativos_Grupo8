package com.example.t1_aplicativos_grupo8

data class Alumno(
    val id: Int,
    val nombre: String,
    val email: String,
    val password: String,
    val birthdate: String,
    val celular: String,
    val direccion: String
) {
    override fun toString(): String = nombre
}



