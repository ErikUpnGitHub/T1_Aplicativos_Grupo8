package com.example.t1_aplicativos_grupo8.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.data.ClaseConCurso

class AlumnoClasesAdapter(
    private var clases: List<ClaseConCurso>,
    private val onClick: (ClaseConCurso) -> Unit
) : RecyclerView.Adapter<AlumnoClasesAdapter.ClaseViewHolder>() {

    inner class ClaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnClase: Button = itemView.findViewById(R.id.btnClase)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_clase_alumno, parent, false)
        return ClaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClaseViewHolder, position: Int) {
        val clase = clases[position]
        holder.btnClase.text = clase.nombreCurso
        holder.btnClase.setOnClickListener { onClick(clase) }
    }

    override fun getItemCount(): Int = clases.size

    fun actualizar(nuevaLista: List<ClaseConCurso>) {
        clases = nuevaLista
        notifyDataSetChanged()
    }
}
