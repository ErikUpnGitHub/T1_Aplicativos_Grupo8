package com.example.t1_aplicativos_grupo8.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.t1_aplicativos_grupo8.data.Curso
import com.example.t1_aplicativos_grupo8.db.AppDatabase
import kotlinx.coroutines.launch

class CursoViewModel(application: Application) : AndroidViewModel(application) {
    private val cursoDao = AppDatabase.getDatabase(application).cursoDao()

    private val _cursos = MutableLiveData<List<Curso>>()
    val cursos: LiveData<List<Curso>> get() = _cursos

    fun cargar() {
        viewModelScope.launch {
            _cursos.value = cursoDao.listar()
        }
    }

    fun insertar(curso: Curso) {
        viewModelScope.launch {
            cursoDao.insertar(curso)
            cargar()
        }
    }

    fun actualizar(curso: Curso) {
        viewModelScope.launch {
            cursoDao.actualizar(curso)
            cargar()
        }
    }

    fun buscar(query: String) {
        viewModelScope.launch {
            _cursos.value = cursoDao.buscar(query)
        }
    }
}
