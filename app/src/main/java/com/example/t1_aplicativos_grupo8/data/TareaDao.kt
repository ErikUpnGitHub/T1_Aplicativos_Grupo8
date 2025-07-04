package com.example.t1_aplicativos_grupo8.data

import androidx.room.*

@Dao
interface TareaDao {
    @Insert
    suspend fun insertar(tarea: Tarea)

    @Query("SELECT * FROM tarea WHERE idclase = :idclase")
    suspend fun obtenerPorClase(idclase: Int): List<Tarea>

    @Query("SELECT * FROM tarea WHERE idtarea = :idtarea")
    suspend fun obtenerPorId(idtarea: Int): Tarea?
}
