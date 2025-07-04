package com.example.t1_aplicativos_grupo8.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.t1_aplicativos_grupo8.data.Profesor
import com.example.t1_aplicativos_grupo8.data.Usuario
import com.example.t1_aplicativos_grupo8.db.AppDatabase
import kotlinx.coroutines.launch

class ProfesorNombreViewModel(application: Application) : AndroidViewModel(application) {
    private val profesorDao = AppDatabase.getDatabase(application).profesorDao()
    private val usuarioDao = AppDatabase.getDatabase(application).usuarioDao()

    private val _profesoresConNombre = MutableLiveData<List<ProfesorConNombre>>()
    val profesoresConNombre: LiveData<List<ProfesorConNombre>> get() = _profesoresConNombre

    fun cargarProfesoresConNombre() {
        viewModelScope.launch {
            val profesores = profesorDao.obtenerTodos()
            val usuarios = usuarioDao.listar()

            val lista = profesores.mapNotNull { profesor ->
                val usuario = usuarios.find { it.idusuario == profesor.idusuario }
                usuario?.let {
                    ProfesorConNombre(
                        idusuario = it.idusuario,
                        nombreCompleto = "${it.nombre} ${it.apellido}",
                        especialidad = profesor.especialidad
                    )
                }
            }

            _profesoresConNombre.value = lista
        }
    }
}

data class ProfesorConNombre(
    val idusuario: Int,
    val nombreCompleto: String,
    val especialidad: String
)
