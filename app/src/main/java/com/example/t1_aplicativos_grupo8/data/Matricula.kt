package com.example.t1_aplicativos_grupo8.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matricula")
data class Matricula(
    @PrimaryKey(autoGenerate = true) val idmatricula: Int = 0,
    val idusuario: Int,
    val idclase: Int,
    val fecha_matricula: String
)