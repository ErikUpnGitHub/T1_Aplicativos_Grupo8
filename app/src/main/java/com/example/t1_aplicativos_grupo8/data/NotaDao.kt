package com.example.t1_aplicativos_grupo8.data

import androidx.room.*

@Dao
interface NotaDao {
    @Insert
    suspend fun insertar(nota: Nota)

    @Update
    suspend fun actualizar(nota: Nota)

    @Query("SELECT * FROM nota WHERE idclase = :idclase AND idusuario = :idusuario")
    suspend fun obtenerNotasDeEstudiante(idclase: Int, idusuario: Int): List<Nota>

    @Query("SELECT * FROM nota WHERE idclase = :idclase")
    suspend fun obtenerPorClase(idclase: Int): List<Nota>
}
