package com.example.t1_aplicativos_grupo8.data

import androidx.room.*

@Dao
interface ClaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(clase: Clase)

    @Query("SELECT * FROM clase")
    suspend fun obtenerTodas(): List<Clase>

    @Query("SELECT * FROM clase WHERE idclase = :id")
    suspend fun obtenerPorId(id: Int): Clase?

    @Query("SELECT * FROM clase WHERE idusuario = :idProfesor")
    suspend fun obtenerPorProfesor(idProfesor: Int): List<Clase>

}
