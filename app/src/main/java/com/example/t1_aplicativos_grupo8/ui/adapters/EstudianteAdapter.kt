package com.example.t1_aplicativos_grupo8.ui.adapters

import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.data.*

class EstudianteAdapter(
    private var lista: List<Pair<Usuario, Estudiante>>,
    private val onEditarClick: (Usuario, Estudiante) -> Unit
) : RecyclerView.Adapter<EstudianteAdapter.EstudianteViewHolder>() {

    class EstudianteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView = view.findViewById(R.id.txtNombreEstudiante)
        val txtEmail: TextView = view.findViewById(R.id.txtEmailEstudiante)
        val txtCarrera: TextView = view.findViewById(R.id.txtCarrera)
        val btnEditar: Button = view.findViewById(R.id.btnEditarEstudiante)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstudianteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_estudiante, parent, false)
        return EstudianteViewHolder(view)
    }

    override fun onBindViewHolder(holder: EstudianteViewHolder, position: Int) {
        val (usuario, estudiante) = lista[position]
        holder.txtNombre.text = "${usuario.nombre} ${usuario.apellido}"
        holder.txtEmail.text = usuario.email
        holder.txtCarrera.text = "Carrera: ${estudiante.carrera}"
        holder.btnEditar.setOnClickListener { onEditarClick(usuario, estudiante) }
    }

    override fun getItemCount(): Int = lista.size

    fun actualizar(nuevaLista: List<Pair<Usuario, Estudiante>>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}
