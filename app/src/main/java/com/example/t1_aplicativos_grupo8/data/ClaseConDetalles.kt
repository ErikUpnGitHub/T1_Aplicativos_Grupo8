package com.example.t1_aplicativos_grupo8.data

import androidx.room.*

data class ClaseConDetalles(
    @Embedded val clase: Clase,

    @Relation(
        parentColumn = "idusuario",
        entityColumn = "idusuario"
    )
    val profesor: Usuario,

    @Relation(
        parentColumn = "idcurso",
        entityColumn = "idcurso"
    )
    val curso: Curso,

    @Relation(
        parentColumn = "idclase",
        entityColumn = "idclase"
    )
    val horarios: List<Horario>
)

