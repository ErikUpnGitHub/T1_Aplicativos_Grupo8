package com.example.t1_aplicativos_grupo8.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.t1_aplicativos_grupo8.data.Clase
import com.example.t1_aplicativos_grupo8.data.Profesor
import com.example.t1_aplicativos_grupo8.db.AppDatabase
import kotlinx.coroutines.launch

class ClasesProfesorViewModel(application: Application) : AndroidViewModel(application) {
    private val claseDao = AppDatabase.getDatabase(application).claseDao()
    private val _clases = MutableLiveData<List<Clase>>()
    val clases: LiveData<List<Clase>> get() = _clases

    companion object {
        private const val TAG = "ClasesProfesorViewModel"
    }

    fun cargarClasesDelProfesor(idProfesor: Int) {
        viewModelScope.launch {
            // Obtener TODAS las clases
            val todasLasClases = claseDao.obtenerTodas()
            Log.d(TAG, "Todas las clases en la base de datos:")
            todasLasClases.forEach { Log.d(TAG, it.toString()) }

            // Obtener clases por ID de profesor
            val clasesPorProfesor = claseDao.obtenerPorProfesor(idProfesor)
            Log.d(TAG, "Clases del profesor con ID $idProfesor:")
            clasesPorProfesor.forEach { Log.d(TAG, it.toString()) }

            _clases.value = clasesPorProfesor
        }
    }
}
