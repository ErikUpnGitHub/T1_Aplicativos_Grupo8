package com.example.t1_aplicativos_grupo8.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.data.Horario

class HorarioAdapter(
    private var lista: List<Horario>,
    private val onEditar: (Horario) -> Unit
) : RecyclerView.Adapter<HorarioAdapter.HorarioViewHolder>() {

    inner class HorarioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtDia: TextView = view.findViewById(R.id.txtDia)
        val txtHora: TextView = view.findViewById(R.id.txtHora)

        init {
            view.setOnClickListener {
                onEditar(lista[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorarioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_horario, parent, false)
        return HorarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: HorarioViewHolder, position: Int) {
        val horario = lista[position]
        holder.txtDia.text = horario.dia
        holder.txtHora.text = "${horario.hora_inicio} - ${horario.hora_fin}"
    }

    override fun getItemCount(): Int = lista.size

    fun actualizar(nuevaLista: List<Horario>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}
