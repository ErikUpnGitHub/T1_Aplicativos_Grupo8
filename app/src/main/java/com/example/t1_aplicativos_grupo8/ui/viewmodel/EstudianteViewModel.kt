package com.example.t1_aplicativos_grupo8.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.t1_aplicativos_grupo8.data.*
import com.example.t1_aplicativos_grupo8.db.AppDatabase
import kotlinx.coroutines.launch

class EstudianteViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val usuarioDao = db.usuarioDao()
    private val estudianteDao = db.estudianteDao()

    private val _estudiantes = MutableLiveData<List<Pair<Usuario, Estudiante>>>()
    val estudiantes: LiveData<List<Pair<Usuario, Estudiante>>> get() = _estudiantes

    fun cargar() {
        viewModelScope.launch {
            val usuarios = usuarioDao.listar().filter { it.idrol == 1 }
            val lista = usuarios.mapNotNull { usuario ->
                val est = estudianteDao.obtenerPorId(usuario.idusuario)
                est?.let { Pair(usuario, it) }
            }
            _estudiantes.value = lista
        }
    }

    fun insertar(usuario: Usuario, estudiante: Estudiante) {
        viewModelScope.launch {
            val id = usuarioDao.insertarYObtenerId(usuario).toInt()
            estudianteDao.insertar(estudiante.copy(idusuario = id))
            cargar()
        }
    }

    fun actualizar(usuario: Usuario, estudiante: Estudiante) {
        viewModelScope.launch {
            usuarioDao.actualizar(usuario)
            estudianteDao.insertar(estudiante)
            cargar()
        }
    }
}
