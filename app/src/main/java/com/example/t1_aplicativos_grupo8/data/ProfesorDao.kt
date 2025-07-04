package com.example.t1_aplicativos_grupo8.data

import androidx.room.*

@Dao
interface ProfesorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(profesor: Profesor)

    @Query("SELECT * FROM profesor")
    suspend fun obtenerTodos(): List<Profesor>

    @Query("SELECT * FROM profesor WHERE idusuario = :id")
    suspend fun obtenerPorId(id: Int): Profesor?
}