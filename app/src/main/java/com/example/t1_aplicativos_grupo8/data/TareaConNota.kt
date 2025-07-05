package com.example.t1_aplicativos_grupo8.data

data class TareaConNota(
    val titulo: String,
    val descripcion: String,
    val valor: Double? // puede ser null si aún no está calificada
)
