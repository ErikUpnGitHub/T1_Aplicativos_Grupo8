package com.example.t1_aplicativos_grupo8.data

import androidx.room.*

@Dao
interface RolDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarRol(rol: Rol)

    @Query("SELECT * FROM rol")
    suspend fun obtenerRoles(): List<Rol>

    @Query("SELECT * FROM rol WHERE idrol = :id")
    suspend fun obtenerRolPorId(id: Int): Rol?
}