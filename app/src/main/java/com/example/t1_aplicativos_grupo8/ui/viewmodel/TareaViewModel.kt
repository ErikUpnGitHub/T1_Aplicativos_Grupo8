package com.example.t1_aplicativos_grupo8.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.t1_aplicativos_grupo8.data.*
import com.example.t1_aplicativos_grupo8.db.AppDatabase
import kotlinx.coroutines.launch

class TareaViewModel(application: Application) : AndroidViewModel(application) {
    private val tareaDao = AppDatabase.getDatabase(application).tareaDao()
    private val notaDao = AppDatabase.getDatabase(application).notaDao()
    private val matriculaDao = AppDatabase.getDatabase(application).matriculaDao()

    private val _tareas = MutableLiveData<List<Tarea>>()
    val tareas: LiveData<List<Tarea>> get() = _tareas

    fun obtenerPorClase(idclase: Int) {
        viewModelScope.launch {
            _tareas.value = tareaDao.obtenerPorClase(idclase)
        }
    }

    fun registrarTareaConNotas(tarea: Tarea) {
        viewModelScope.launch {
            tareaDao.insertar(tarea)
            val alumnos = matriculaDao.obtenerAlumnosDeClase(tarea.idclase)
            alumnos.forEach { usuario ->
                val nota = Nota(
                    idnota = 0,
                    idusuario = usuario.idusuario,
                    idclase = tarea.idclase,
                    tipo = tarea.titulo,
                    valor = null,
                    fecha = tarea.fecha_entrega
                )
                notaDao.insertar(nota)
            }
            obtenerPorClase(tarea.idclase)
        }
    }
}
