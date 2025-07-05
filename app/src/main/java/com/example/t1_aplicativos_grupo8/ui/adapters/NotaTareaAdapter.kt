package com.example.t1_aplicativos_grupo8.ui.adapters

import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.data.Nota
import com.example.t1_aplicativos_grupo8.data.NotaConUsuario

class NotaTareaAdapter(
    private var lista: List<NotaConUsuario>,
    private val onEditarNotaClick: (NotaConUsuario) -> Unit
) : RecyclerView.Adapter<NotaTareaAdapter.NotaViewHolder>() {

    inner class NotaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView = view.findViewById(R.id.txtNombreNota)
        val txtCorreo: TextView = view.findViewById(R.id.txtCorreoNota)
        val btnNota: Button = view.findViewById(R.id.btnEditarNota)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_nota_tarea, parent, false)
        return NotaViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val item = lista[position]
        holder.txtNombre.text = item.nombreCompleto
        holder.txtCorreo.text = item.correo
        holder.btnNota.text = item.nota.valor?.toString() ?: "-"
        holder.btnNota.setOnClickListener {
            onEditarNotaClick(item)
        }
    }

    override fun getItemCount(): Int = lista.size

    fun actualizar(nuevaLista: List<NotaConUsuario>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}