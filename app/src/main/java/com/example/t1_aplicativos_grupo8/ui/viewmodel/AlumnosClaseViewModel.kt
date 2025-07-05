package com.example.t1_aplicativos_grupo8.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.t1_aplicativos_grupo8.data.Usuario
import com.example.t1_aplicativos_grupo8.db.AppDatabase
import kotlinx.coroutines.launch

class AlumnosClaseViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).matriculaDao()
    private val _alumnos = MutableLiveData<List<Usuario>>()
    val alumnos: LiveData<List<Usuario>> get() = _alumnos

    fun cargarAlumnosDeClase(idclase: Int) {
        viewModelScope.launch {
            _alumnos.value = dao.obtenerAlumnosDeClase(idclase)
        }
    }
}
