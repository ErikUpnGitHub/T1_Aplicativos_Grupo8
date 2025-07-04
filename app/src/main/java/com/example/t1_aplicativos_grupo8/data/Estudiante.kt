package com.example.t1_aplicativos_grupo8.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estudiante")
data class Estudiante(
    @PrimaryKey val idusuario: Int,
    val carrera: String
)
