package com.example.t1_aplicativos_grupo8.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.data.ClaseConDetalles

class MatriculaCursoAdapter(
    private var lista: List<ClaseConDetalles>,
    private val onSeleccionar: (Int) -> Unit
) : RecyclerView.Adapter<MatriculaCursoAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtCurso = view.findViewById<TextView>(R.id.txtCursoNombre)
        val txtProfesor = view.findViewById<TextView>(R.id.txtProfesor)
        val txtHorarios = view.findViewById<TextView>(R.id.txtHorarios)
        val btnRegistrar = view.findViewById<Button>(R.id.btnRegistrar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_matricula_curso, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]
        holder.txtCurso.text = item.curso.nombre
        holder.txtProfesor.text = "Profesor: ${item.profesor.nombre} ${item.profesor.apellido}"
        holder.txtHorarios.text = "Horarios:\n" + item.horarios.joinToString("\n") {
            "${it.dia}: ${it.hora_inicio} - ${it.hora_fin}"
        }
        holder.btnRegistrar.setOnClickListener {
            onSeleccionar(item.clase.idclase)
        }
    }

    override fun getItemCount(): Int = lista.size

    fun actualizar(nuevaLista: List<ClaseConDetalles>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}
