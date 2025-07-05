package com.example.t1_aplicativos_grupo8.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.t1_aplicativos_grupo8.data.TareaConNota
import com.example.t1_aplicativos_grupo8.db.AppDatabase
import kotlinx.coroutines.launch

class TareaConNotaViewModel(application: Application) : AndroidViewModel(application) {
    private val tareaDao = AppDatabase.getDatabase(application).tareaDao()

    private val _tareas = MutableLiveData<List<TareaConNota>>()
    val tareas: LiveData<List<TareaConNota>> get() = _tareas

    fun cargarTareasConNota(idClase: Int, idUsuario: Int) {
        viewModelScope.launch {
            val lista = tareaDao.obtenerTareasConNotas(idClase, idUsuario)
            _tareas.value = lista
        }
    }
}
