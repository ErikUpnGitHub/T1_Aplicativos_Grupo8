package com.example.t1_aplicativos_grupo8.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.t1_aplicativos_grupo8.data.*
import com.example.t1_aplicativos_grupo8.db.AppDatabase
import kotlinx.coroutines.launch

class MatriculaCursoViewModel(application: Application) : AndroidViewModel(application) {
    private val claseDao = AppDatabase.getDatabase(application).claseDao()
    private val usuarioDao = AppDatabase.getDatabase(application).usuarioDao()
    private val horarioDao = AppDatabase.getDatabase(application).horarioDao()
    private val cursoDao = AppDatabase.getDatabase(application).cursoDao() // ← agregado
    private val matriculaDao = AppDatabase.getDatabase(application).matriculaDao()

    private val _clases = MutableLiveData<List<ClaseConDetalles>>()
    val clases: LiveData<List<ClaseConDetalles>> get() = _clases

    fun cargarClases() {
        viewModelScope.launch {
            val clases = claseDao.obtenerTodas()
            val detalles = clases.mapNotNull { clase ->
                val profesor = usuarioDao.obtenerPorIdSync(clase.idusuario) ?: return@mapNotNull null
                val curso = cursoDao.obtenerPorId(clase.idcurso) ?: return@mapNotNull null
                val horarios = horarioDao.obtenerPorClase(clase.idclase)
                ClaseConDetalles(clase, profesor, curso, horarios)
            }
            _clases.value = detalles
        }
    }

    fun registrarMatricula(idUsuario: Int, idClase: Int, fechaMatricula: String) {
        viewModelScope.launch {
            val matricula = Matricula(
                idusuario = idUsuario,
                idclase = idClase,
                fecha_matricula = fechaMatricula
            )
            matriculaDao.insertar(matricula)
        }
    }
}
