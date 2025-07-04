package com.example.t1_aplicativos_grupo8.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "nota")
data class Nota(
    @PrimaryKey(autoGenerate = true) val idnota: Int = 0,
    val idusuario: Int,
    val idclase: Int,
    val tipo: String, // Título de la tarea
    val valor: Double?, // Puede ser null al inicio
    val fecha: Date
)
