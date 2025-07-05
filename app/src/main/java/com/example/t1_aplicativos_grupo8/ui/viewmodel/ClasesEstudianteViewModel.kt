package com.example.t1_aplicativos_grupo8.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.t1_aplicativos_grupo8.data.Clase
import com.example.t1_aplicativos_grupo8.data.ClaseConCurso
import com.example.t1_aplicativos_grupo8.db.AppDatabase
import kotlinx.coroutines.launch


class ClasesEstudianteViewModel(application: Application) : AndroidViewModel(application) {
    private val matriculaDao = AppDatabase.getDatabase(application).matriculaDao()
    private val claseDao = AppDatabase.getDatabase(application).claseDao()
    private val cursoDao = AppDatabase.getDatabase(application).cursoDao()

    private val _clases = MutableLiveData<List<ClaseConCurso>>()
    val clases: LiveData<List<ClaseConCurso>> get() = _clases

    fun cargarClasesDelAlumno(idUsuario: Int) {
        viewModelScope.launch {
            val matriculas = matriculaDao.obtenerPorUsuario(idUsuario)
            val clasesConCurso = matriculas.mapNotNull { matricula ->
                val clase = claseDao.obtenerPorId(matricula.idclase)
                val curso = clase?.let { cursoDao.obtenerPorId(it.idcurso) }
                if (clase != null && curso != null) {
                    ClaseConCurso(clase, curso.nombre)
                } else null
            }
            _clases.value = clasesConCurso
        }
    }
}
