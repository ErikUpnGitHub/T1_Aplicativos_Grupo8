package com.example.t1_aplicativos_grupo8

data class Notificacion(
    val id: Int,
    val docenteEmail: String,
    val mensaje: String,
    val cursoId: Int?,
    var leido: Boolean
) {
    override fun toString(): String = mensaje
}
