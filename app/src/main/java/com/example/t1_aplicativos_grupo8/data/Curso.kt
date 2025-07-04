package com.example.t1_aplicativos_grupo8.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "curso")
data class Curso(
    @PrimaryKey(autoGenerate = true) val idcurso: Int = 0,
    val nombre: String,
    val descripcion: String?
)
