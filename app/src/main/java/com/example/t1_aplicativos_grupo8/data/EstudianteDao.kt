package com.example.t1_aplicativos_grupo8.data

import androidx.room.*

@Dao
interface EstudianteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(estudiante: Estudiante)

    @Query("SELECT * FROM estudiante")
    suspend fun obtenerTodos(): List<Estudiante>

    @Query("SELECT * FROM estudiante WHERE idusuario = :id")
    suspend fun obtenerPorId(id: Int): Estudiante?
}
