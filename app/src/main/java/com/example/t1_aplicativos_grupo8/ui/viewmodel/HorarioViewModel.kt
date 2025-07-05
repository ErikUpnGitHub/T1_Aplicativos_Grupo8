package com.example.t1_aplicativos_grupo8.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.t1_aplicativos_grupo8.data.Horario
import com.example.t1_aplicativos_grupo8.db.AppDatabase
import kotlinx.coroutines.launch

class HorarioViewModel(application: Application) : AndroidViewModel(application) {
    private val horarioDao = AppDatabase.getDatabase(application).horarioDao()

    private val _horarios = MutableLiveData<List<Horario>>()
    val horarios: LiveData<List<Horario>> get() = _horarios

    fun cargarHorarios() {
        viewModelScope.launch {
            _horarios.value = horarioDao.listar()
        }
    }

    fun insertar(horario: Horario) {
        viewModelScope.launch {
            horarioDao.insertar(horario)
            cargarHorarios()
        }
    }

    fun actualizar(horario: Horario) {
        viewModelScope.launch {
            horarioDao.actualizar(horario)
            cargarHorarios()
        }
    }

    fun buscar(query: String) {
        viewModelScope.launch {
            _horarios.value = horarioDao.buscar(query)
        }
    }

    fun cargarPorClase(idclase: Int) {
        viewModelScope.launch {
            _horarios.value = horarioDao.obtenerPorClase(idclase)
        }
    }

}
