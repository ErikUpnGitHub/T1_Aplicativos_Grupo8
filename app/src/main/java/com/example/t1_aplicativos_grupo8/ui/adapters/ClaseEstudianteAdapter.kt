package com.example.t1_aplicativos_grupo8.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.data.Clase
import com.example.t1_aplicativos_grupo8.data.ClaseConCurso


class ClaseEstudianteAdapter(
    private var clases: List<ClaseConCurso>,
    private val onClick: (ClaseConCurso) -> Unit
) : RecyclerView.Adapter<ClaseEstudianteAdapter.ClaseViewHolder>() {

    inner class ClaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnClase: Button = itemView.findViewById(R.id.btnClaseItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClaseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_clase_estudiante, parent, false)
        return ClaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClaseViewHolder, position: Int) {
        val claseConCurso = clases[position]
        holder.btnClase.text = claseConCurso.nombreCurso
        holder.btnClase.setOnClickListener { onClick(claseConCurso) }
    }

    override fun getItemCount(): Int = clases.size

    fun actualizar(nuevasClases: List<ClaseConCurso>) {
        clases = nuevasClases
        notifyDataSetChanged()
    }
}

