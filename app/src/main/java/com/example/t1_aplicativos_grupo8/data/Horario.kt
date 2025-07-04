package com.example.t1_aplicativos_grupo8.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "horario")
data class Horario(
    @PrimaryKey(autoGenerate = true) val idhorario: Int = 0,
    val idclase: Int,
    val dia: String,
    val hora_inicio: String,
    val hora_fin: String
)
