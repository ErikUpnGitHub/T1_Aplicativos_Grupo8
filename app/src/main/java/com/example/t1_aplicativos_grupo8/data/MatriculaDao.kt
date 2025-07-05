package com.example.t1_aplicativos_grupo8.data

import androidx.room.*

@Dao
interface MatriculaDao {
    @Insert
    suspend fun insertar(matricula: Matricula)

    @Query("SELECT * FROM matricula")
    suspend fun obtenerTodas(): List<Matricula>

    @Query("SELECT * FROM matricula WHERE idusuario = :idUsuario")
    suspend fun obtenerPorUsuario(idUsuario: Int): List<Matricula>

    @Query("""
    SELECT u.* FROM usuario u
    INNER JOIN matricula m ON m.idusuario = u.idusuario
    WHERE m.idclase = :idclase
    """)
    suspend fun obtenerAlumnosDeClase(idclase: Int): List<Usuario>

}
