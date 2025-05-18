package com.example.t1_aplicativos_grupo8

data class Curso(
    val id: Int,
    val nombre: String,
    val codigo: String,
    val salon: String,
    val pabellon: String,
    val nombreProfesor: String,
    val correoProfesor: String,
    val listaEstudiantes: List<String>,
    val asistencias: String,
    val cantidadAlumnos: Int,
    val horario: String,
    val dia: String,
    val hora: String
)
