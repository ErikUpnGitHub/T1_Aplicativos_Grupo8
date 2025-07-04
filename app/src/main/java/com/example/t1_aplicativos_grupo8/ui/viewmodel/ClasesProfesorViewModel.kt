package com.example.t1_aplicativos_grupo8.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.t1_aplicativos_grupo8.data.Clase
import com.example.t1_aplicativos_grupo8.data.Profesor
import com.example.t1_aplicativos_grupo8.db.AppDatabase
import kotlinx.coroutines.launch

class ClasesProfesorViewModel(application: Application) : AndroidViewModel(application) {
    private val claseDao = AppDatabase.getDatabase(application).claseDao()
    private val _clases = MutableLiveData<List<Clase>>()
    val clases: LiveData<List<Clase>> get() = _clases

    fun cargarClasesDelProfesor(idProfesor: Int) {
        viewModelScope.launch {
            _clases.value = claseDao.obtenerPorProfesor(idProfesor)
        }
    }
}
