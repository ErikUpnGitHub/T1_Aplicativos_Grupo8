package com.example.t1_aplicativos_grupo8.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val idusuario: Int = 0,
    val nombre: String,
    val apellido: String,
    val email: String,
    val contrasenia: String,
    val telefono: String?,
    val idrol: Int
)
