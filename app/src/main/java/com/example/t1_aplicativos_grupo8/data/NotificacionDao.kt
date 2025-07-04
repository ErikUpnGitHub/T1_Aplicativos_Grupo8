package com.example.t1_aplicativos_grupo8.data

import androidx.room.*

@Dao
interface NotificacionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(notificacion: Notificacion)

    @Query("SELECT * FROM notificacion WHERE idusuario = :idUsuario")
    suspend fun obtenerPorUsuario(idUsuario: Int): List<Notificacion>

    @Query("UPDATE notificacion SET leida = 1 WHERE idnotificacion = :id")
    suspend fun marcarComoLeida(id: Int)
}
