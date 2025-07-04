package com.example.t1_aplicativos_grupo8.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.t1_aplicativos_grupo8.data.Matricula
import com.example.t1_aplicativos_grupo8.db.AppDatabase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MatriculaViewModel(application: Application) : AndroidViewModel(application) {
    private val matriculaDao = AppDatabase.getDatabase(application).matriculaDao()

    private val _matriculas = MutableLiveData<List<Matricula>>()
    val matriculas: LiveData<List<Matricula>> get() = _matriculas

    fun cargarMatriculas() {
        viewModelScope.launch {
            _matriculas.value = matriculaDao.obtenerTodas()
        }
    }

    fun registrarMatricula(idUsuario: Int, idClase: Int) {
        viewModelScope.launch {
            val fechaActual = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val nueva = Matricula(
                idusuario = idUsuario,
                idclase = idClase,
                fecha_matricula = fechaActual
            )
            matriculaDao.insertar(nueva)
            cargarMatriculas()
        }
    }
}
