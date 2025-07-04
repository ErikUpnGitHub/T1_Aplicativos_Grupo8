package com.example.t1_aplicativos_grupo8.ui.adapters

import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.data.*

class ProfesorAdapter(
    private var lista: List<Pair<Usuario, Profesor>>,
    private val onEditarClick: (Usuario, Profesor) -> Unit
) : RecyclerView.Adapter<ProfesorAdapter.ProfesorViewHolder>() {

    class ProfesorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView = view.findViewById(R.id.txtNombreProfesor)
        val txtEmail: TextView = view.findViewById(R.id.txtEmailProfesor)
        val txtEspecialidad: TextView = view.findViewById(R.id.txtEspecialidad)
        val btnEditar: Button = view.findViewById(R.id.btnEditarProfesor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfesorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_profesor, parent, false)
        return ProfesorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfesorViewHolder, position: Int) {
        val (usuario, profesor) = lista[position]
        holder.txtNombre.text = "${usuario.nombre} ${usuario.apellido}"
        holder.txtEmail.text = usuario.email
        holder.txtEspecialidad.text = "Especialidad: ${profesor.especialidad}"
        holder.btnEditar.setOnClickListener { onEditarClick(usuario, profesor) }
    }

    override fun getItemCount(): Int = lista.size

    fun actualizar(nuevaLista: List<Pair<Usuario, Profesor>>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}
