package com.example.t1_aplicativos_grupo8.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clase")
data class Clase(
    @PrimaryKey(autoGenerate = true) val idclase: Int = 0,
    val idusuario: Int,
    val idcurso: Int,
    val aula: String,
    val capacidad: Int
)
