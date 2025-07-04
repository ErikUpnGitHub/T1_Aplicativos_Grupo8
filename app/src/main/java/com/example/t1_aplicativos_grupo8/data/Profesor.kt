package com.example.t1_aplicativos_grupo8.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profesor")
data class Profesor(
    @PrimaryKey val idusuario: Int,
    val especialidad: String
)