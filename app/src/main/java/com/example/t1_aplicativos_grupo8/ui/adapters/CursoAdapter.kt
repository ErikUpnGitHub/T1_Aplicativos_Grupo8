package com.example.t1_aplicativos_grupo8.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.data.Curso

class CursoAdapter(
    private var lista: List<Curso>,
    private val onEditarClick: (Curso) -> Unit
) : RecyclerView.Adapter<CursoAdapter.CursoViewHolder>() {

    class CursoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView = view.findViewById(R.id.txtNombreCurso)
        val txtDescripcion: TextView = view.findViewById(R.id.txtDescripcionCurso)
        val btnEditar: Button = view.findViewById(R.id.btnEditarCurso)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CursoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_curso, parent, false)
        return CursoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CursoViewHolder, position: Int) {
        val curso = lista[position]
        holder.txtNombre.text = curso.nombre
        holder.txtDescripcion.text = curso.descripcion ?: ""
        holder.btnEditar.setOnClickListener { onEditarClick(curso) }
    }

    override fun getItemCount(): Int = lista.size

    fun actualizar(nuevaLista: List<Curso>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}
