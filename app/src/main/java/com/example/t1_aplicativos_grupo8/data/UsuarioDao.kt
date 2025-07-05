package com.example.t1_aplicativos_grupo8.data

import androidx.room.*

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuario")
    suspend fun listar(): List<Usuario>

    @Query("SELECT * FROM usuario WHERE email = :email AND contrasenia = :contrasenia")
    suspend fun login(email: String, contrasenia: String): Usuario?

    @Query("SELECT * FROM usuario WHERE email = :email")
    suspend fun buscarPorEmail(email: String): Usuario?

    @Update
    suspend fun actualizar(usuario: Usuario)

    @Query("SELECT * FROM usuario WHERE nombre LIKE '%' || :query || '%' OR email LIKE '%' || :query || '%'")
    suspend fun buscar(query: String): List<Usuario>

    @Insert
    suspend fun insertarYObtenerId(usuario: Usuario): Long

    @Query("SELECT * FROM usuario WHERE idusuario = :id")
    suspend fun obtenerPorIdSync(id: Int): Usuario
}
