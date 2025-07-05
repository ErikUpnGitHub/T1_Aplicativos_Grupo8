package com.example.t1_aplicativos_grupo8.data

import androidx.room.*

@Dao
interface TareaDao {
    @Insert
    suspend fun insertar(tarea: Tarea): Long

    @Query("SELECT * FROM tarea WHERE idclase = :idclase")
    suspend fun obtenerPorClase(idclase: Int): List<Tarea>

    @Query("SELECT * FROM tarea WHERE idtarea = :idtarea")
    suspend fun obtenerPorId(idtarea: Int): Tarea?

    @Query("""
    SELECT t.titulo, t.descripcion, n.valor 
    FROM tarea t
    LEFT JOIN nota n ON n.tipo = t.titulo AND n.idusuario = :idUsuario AND n.idclase = :idClase
    WHERE t.idclase = :idClase
""")
    suspend fun obtenerTareasConNotas(idClase: Int, idUsuario: Int): List<TareaConNota>
}
