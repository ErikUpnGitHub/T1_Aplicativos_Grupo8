package com.example.t1_aplicativos_grupo8.ui.adapters

import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.data.Tarea
import java.text.SimpleDateFormat
import java.util.*

class TareaAdapter(
    private var lista: List<Tarea>,
    private val onBotonClick: (Tarea) -> Unit
) : RecyclerView.Adapter<TareaAdapter.TareaViewHolder>() {

    inner class TareaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTitulo: TextView = view.findViewById(R.id.txtTituloTarea)
        val txtFecha: TextView = view.findViewById(R.id.txtFechaTarea)
        val btnAccion: Button = view.findViewById(R.id.btnAccionTarea)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tarea, parent, false)
        return TareaViewHolder(view)
    }

    override fun onBindViewHolder(holder: TareaViewHolder, position: Int) {
        val tarea = lista[position]
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        holder.txtTitulo.text = tarea.titulo
        holder.txtFecha.text = "Entrega: ${sdf.format(tarea.fecha_entrega)}"
        holder.btnAccion.setOnClickListener {
            onBotonClick(tarea)
        }
    }

    override fun getItemCount(): Int = lista.size

    fun actualizar(nuevaLista: List<Tarea>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}
