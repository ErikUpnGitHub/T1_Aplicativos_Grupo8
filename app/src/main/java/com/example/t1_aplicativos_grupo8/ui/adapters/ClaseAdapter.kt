package com.example.t1_aplicativos_grupo8.ui.adapters

import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.data.Clase

class ClaseAdapter(
    private var lista: List<Clase>,
    private val onEditarClick: (Clase) -> Unit
) : RecyclerView.Adapter<ClaseAdapter.ClaseViewHolder>() {

    class ClaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtInfo: TextView = view.findViewById(R.id.txtInfoClase)
        val btnEditar: Button = view.findViewById(R.id.btnEditarClase)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_clase, parent, false)
        return ClaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClaseViewHolder, position: Int) {
        val clase = lista[position]
        holder.txtInfo.text = "Curso ID: ${clase.idcurso}, Profesor ID: ${clase.idusuario}, Aula: ${clase.aula}, Capacidad: ${clase.capacidad}"
        holder.btnEditar.setOnClickListener { onEditarClick(clase) }
    }

    override fun getItemCount(): Int = lista.size

    fun actualizar(nuevaLista: List<Clase>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}
