package com.example.t1_aplicativos_grupo8.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tarea")
data class Tarea(
    @PrimaryKey(autoGenerate = true) val idtarea: Int = 0,
    val idclase: Int,
    val titulo: String,
    val descripcion: String?,
    val tipo: String?,
    val fecha_entrega: Date
)
