package com.example.t1_aplicativos_grupo8.data

import androidx.room.*

@Dao
interface HorarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(horario: Horario)

    @Update
    suspend fun actualizar(horario: Horario)

    @Query("SELECT * FROM horario")
    suspend fun listar(): List<Horario>

    @Query("SELECT * FROM horario WHERE dia LIKE '%' || :query || '%'")
    suspend fun buscar(query: String): List<Horario>
}
