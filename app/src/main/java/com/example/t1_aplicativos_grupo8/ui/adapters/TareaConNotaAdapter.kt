package com.example.t1_aplicativos_grupo8.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.data.TareaConNota

class TareaConNotaAdapter(
    private var lista: List<TareaConNota>
) : RecyclerView.Adapter<TareaConNotaAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTitulo = view.findViewById<TextView>(R.id.txtTituloTarea)
        val txtDescripcion = view.findViewById<TextView>(R.id.txtDescripcionTarea)
        val txtNota = view.findViewById<TextView>(R.id.txtNotaTarea)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tarea_con_nota, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tarea = lista[position]
        holder.txtTitulo.text = tarea.titulo
        holder.txtDescripcion.text = tarea.descripcion
        holder.txtNota.text = "Nota: ${tarea.valor ?: "Sin calificar"}"
    }

    fun actualizar(nuevaLista: List<TareaConNota>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}
