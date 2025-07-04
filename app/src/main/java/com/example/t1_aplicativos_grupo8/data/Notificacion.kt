package com.example.t1_aplicativos_grupo8.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notificacion")
data class Notificacion(
    @PrimaryKey(autoGenerate = true) val idnotificacion: Int = 0,
    val idusuario: Int?,
    val idtarea: Int?,
    val titulo: String,
    val mensaje: String?,
    val fecha: String, // formato: "2025-07-05 18:30"
    val leida: Boolean = false
)
