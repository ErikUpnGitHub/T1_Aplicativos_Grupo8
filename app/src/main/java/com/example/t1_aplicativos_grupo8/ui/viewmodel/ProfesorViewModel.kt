package com.example.t1_aplicativos_grupo8.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.t1_aplicativos_grupo8.data.*
import com.example.t1_aplicativos_grupo8.db.AppDatabase
import kotlinx.coroutines.launch
import android.util.Log

class ProfesorViewModel(application: Application) : AndroidViewModel(application) {

    private val usuarioDao = AppDatabase.getDatabase(application).usuarioDao()
    private val profesorDao = AppDatabase.getDatabase(application).profesorDao()

    private val _profesores = MutableLiveData<List<Pair<Usuario, Profesor>>>()
    val profesores: LiveData<List<Pair<Usuario, Profesor>>> get() = _profesores

    fun cargar() {
        viewModelScope.launch {
            val listaUsuarios = usuarioDao.listar().filter { it.idrol == 2 }
            val listaProfesores = profesorDao.obtenerTodos()
            val combinados = listaProfesores.mapNotNull { prof ->
                val usuario = listaUsuarios.find { it.idusuario == prof.idusuario }
                usuario?.let { Pair(it, prof) }
            }
            _profesores.value = combinados
        }
    }

    fun insertar(usuario: Usuario, profesor: Profesor) {
        viewModelScope.launch {
            try {
                Log.d("ProfesorViewModel", "Insertando usuario: $usuario")
                val idGenerado = usuarioDao.insertarYObtenerId(usuario).toInt()
                Log.d("ProfesorViewModel", "ID generado para usuario: $idGenerado")

                val profesorFinal = profesor.copy(idusuario = idGenerado)
                Log.d("ProfesorViewModel", "Insertando profesor: $profesorFinal")

                profesorDao.insertar(profesorFinal)

                Log.d("ProfesorViewModel", "Profesor insertado correctamente")

                cargar()
            } catch (e: Exception) {
                Log.e("ProfesorViewModel", "Error al insertar profesor", e)
            }
        }
    }


    fun actualizar(usuario: Usuario, profesor: Profesor) {
        viewModelScope.launch {
            usuarioDao.actualizar(usuario)
            profesorDao.insertar(profesor) // Reemplaza si ya existe
            cargar()
        }
    }
}
