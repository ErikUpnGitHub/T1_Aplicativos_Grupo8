package com.example.t1_aplicativos_grupo8.ui.viewmodel

import android.app.Application
import android.util.Log
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

    companion object {
        private const val TAG = "ClaseViewModel"
    }

    fun cargarClases() {
        viewModelScope.launch {
            val listaClases = claseDao.obtenerTodas()
            Log.d(TAG, "📋 Clases cargadas (${listaClases.size}):")
            listaClases.forEach { Log.d(TAG, it.toString()) }

            _clases.value = listaClases
        }
    }

    fun cargarCursos() {
        viewModelScope.launch {
            val listaCursos = cursoDao.listar()
            Log.d(TAG, "📚 Cursos cargados (${listaCursos.size}):")
            listaCursos.forEach { Log.d(TAG, it.toString()) }

            _cursos.value = listaCursos
        }
    }

    fun cargarProfesores() {
        viewModelScope.launch {
            val listaProfesores = profesorDao.obtenerTodos()
            Log.d(TAG, "👨‍🏫 Profesores cargados (${listaProfesores.size}):")
            listaProfesores.forEach { Log.d(TAG, it.toString()) }

            _profesores.value = listaProfesores
        }
    }

    fun insertar(clase: Clase) {
        viewModelScope.launch {
            claseDao.insertar(clase)
            Log.d(TAG, "✅ Clase insertada: $clase")
            cargarClases()
        }
    }

    fun actualizar(clase: Clase) {
        viewModelScope.launch {
            claseDao.insertar(clase)
            Log.d(TAG, "🔄 Clase actualizada (insertar): $clase")
            cargarClases()
        }
    }
}
