package com.example.t1_aplicativos_grupo8.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rol")
data class Rol(
    @PrimaryKey(autoGenerate = true) val idrol: Int = 0,
    val descripcion: String
)