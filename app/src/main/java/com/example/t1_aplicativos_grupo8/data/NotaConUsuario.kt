package com.example.t1_aplicativos_grupo8.data

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class NotaConUsuario(
    @Embedded val nota: Nota,
    @ColumnInfo(name = "nombres") val nombres: String,
    @ColumnInfo(name = "apellidos") val apellidos: String,
    @ColumnInfo(name = "correo") val correo: String
) {
    val nombreCompleto: String get() = "$nombres $apellidos"
}