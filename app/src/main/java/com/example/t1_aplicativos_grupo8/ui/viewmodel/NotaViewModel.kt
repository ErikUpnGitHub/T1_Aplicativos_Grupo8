package com.example.t1_aplicativos_grupo8.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.t1_aplicativos_grupo8.data.*
import com.example.t1_aplicativos_grupo8.db.AppDatabase
import kotlinx.coroutines.launch

class NotaViewModel(application: Application) : AndroidViewModel(application) {
    private val notaDao = AppDatabase.getDatabase(application).notaDao()

    private val _notasConUsuario = MutableLiveData<List<NotaConUsuario>>()
    val notasConUsuario: LiveData<List<NotaConUsuario>> get() = _notasConUsuario

    fun obtenerNotasConUsuario(idclase: Int, tipo: String) {
        viewModelScope.launch {
            _notasConUsuario.value = notaDao.obtenerConUsuarioPorClaseYTipo(idclase, tipo)
        }
    }

    fun actualizarNota(nota: Nota) {
        viewModelScope.launch {
            notaDao.actualizar(nota)
            obtenerNotasConUsuario(nota.idclase, nota.tipo)
        }
    }
}
