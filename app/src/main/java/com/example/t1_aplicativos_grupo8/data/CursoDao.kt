package com.example.t1_aplicativos_grupo8.data

import androidx.room.*

@Dao
interface CursoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(curso: Curso)

    @Update
    suspend fun actualizar(curso: Curso)

    @Delete
    suspend fun eliminar(curso: Curso)

    @Query("SELECT * FROM curso ORDER BY nombre ASC")
    suspend fun listar(): List<Curso>

    @Query("SELECT * FROM curso WHERE nombre LIKE '%' || :query || '%' ORDER BY nombre ASC")
    suspend fun buscar(query: String): List<Curso>

    @Query("SELECT * FROM curso WHERE idcurso = :id")
    suspend fun obtenerPorId(id: Int): Curso?
}
