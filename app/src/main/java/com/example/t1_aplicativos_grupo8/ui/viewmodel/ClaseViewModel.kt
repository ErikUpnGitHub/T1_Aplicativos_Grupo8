package com.example.t1_aplicativos_grupo8.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.t1_aplicativos_grupo8.data.Clase
import com.example.t1_aplicativos_grupo8.data.Curso
import com.example.t1_aplicativos_grupo8.data.Profesor
import com.example.t1_aplicativos_grupo8.db.AppDatabase
import kotlinx.coroutines.launch

class ClaseViewModel(application: Application) : AndroidViewModel(application) {
    private val claseDao = AppDatabase.getDatabase(application).claseDao()
    private val cursoDao = AppDatabase.getDatabase(application).cursoDao()
    private val profesorDao = AppDatabase.getDatabase(application).profesorDao()

    private val _clases = MutableLiveData<List<Clase>>()
    val clases: LiveData<List<Clase>> get() = _clases

    private val _cursos = MutableLiveData<List<Curso>>()
    val cursos: LiveData<List<Curso>> get() = _cursos


    private val _profesores = MutableLiveData<List<Profesor>>()
    val profesores: LiveData<List<Profesor>> get() = _profesores

    fun cargarClases() {
        viewModelScope.launch {
            _clases.value = claseDao.obtenerTodas()
        }
    }

    fun cargarCursos() {
        viewModelScope.launch {
            _cursos.value = cursoDao.listar()
        }
    }

    fun insertar(clase: Clase) {
        viewModelScope.launch {
            claseDao.insertar(clase)
            cargarClases()
        }
    }

    fun actualizar(clase: Clase) {
        viewModelScope.launch {
            claseDao.insertar(clase)
            cargarClases()
        }
    }

    fun cargarProfesores() {
        viewModelScope.launch {
            _profesores.value = profesorDao.obtenerTodos()
        }
    }
}