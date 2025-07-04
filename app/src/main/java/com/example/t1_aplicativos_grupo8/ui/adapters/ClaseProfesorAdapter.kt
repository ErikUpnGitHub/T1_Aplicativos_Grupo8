package com.example.t1_aplicativos_grupo8.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.data.Clase

class ClaseProfesorAdapter(
    private var lista: List<Clase>,
    private val onClick: (Clase) -> Unit
) : RecyclerView.Adapter<ClaseProfesorAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtInfo: TextView = view.findViewById(R.id.txtClaseInfo)

        init {
            view.setOnClickListener {
                onClick(lista[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_clase_profesor, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val clase = lista[position]
        holder.txtInfo.text = "Clase ${clase.idclase} - Aula ${clase.aula}"
    }

    fun actualizar(nuevaLista: List<Clase>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}
