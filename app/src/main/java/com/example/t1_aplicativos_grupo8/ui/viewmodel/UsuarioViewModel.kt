package com.example.t1_aplicativos_grupo8.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.t1_aplicativos_grupo8.db.AppDatabase
import com.example.t1_aplicativos_grupo8.data.Usuario
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {
    private val usuarioDao = AppDatabase.getDatabase(application).usuarioDao()

    private val _usuarios = MutableLiveData<List<Usuario>>()
    val usuarios: LiveData<List<Usuario>> get() = _usuarios

    fun cargarUsuarios() {
        viewModelScope.launch {
            _usuarios.value = usuarioDao.listar()
        }
    }

    fun debugUsuarios() {
        viewModelScope.launch {
            val lista = usuarioDao.listar()
            Log.d("DEBUG_USUARIOS", "Usuarios encontrados: ${lista.size}")
            lista.forEach {
                Log.d("DEBUG_USUARIOS", it.toString())
            }
        }
    }

    fun insertar(usuario: Usuario) {
        viewModelScope.launch {
            usuarioDao.insertarYObtenerId(usuario)
            cargarUsuarios()
        }
    }

    fun actualizar(usuario: Usuario) {
        viewModelScope.launch {
            usuarioDao.actualizar(usuario)
            cargarUsuarios()
        }
    }

    fun buscar(query: String) {
        viewModelScope.launch {
            _usuarios.value = usuarioDao.buscar(query)
        }
    }

}