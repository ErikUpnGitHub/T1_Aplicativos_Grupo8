package com.example.t1_aplicativos_grupo8.ui.adapters

import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.data.ClaseConDetalles

class AlumnoMatriculaAdapter(
    private var lista: List<ClaseConDetalles>,
    private val onMatricularClick: (ClaseConDetalles) -> Unit
) : RecyclerView.Adapter<AlumnoMatriculaAdapter.MatriculaViewHolder>() {

    inner class MatriculaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtCurso: TextView = view.findViewById(R.id.txtCurso)
        val txtProfesor: TextView = view.findViewById(R.id.txtProfesor)
        val txtHorarios: TextView = view.findViewById(R.id.txtHorarios)
        val btnMatricular: Button = view.findViewById(R.id.btnMatricular)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatriculaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_matricula_curso, parent, false)
        return MatriculaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatriculaViewHolder, position: Int) {
        val item = lista[position]
        holder.txtCurso.text = item.curso.nombre
        holder.txtProfesor.text = "Profesor: ${item.profesor.nombre} ${item.profesor.apellido}"

        val horarios = item.horarios.joinToString("\n") { "${it.dia}: ${it.hora_inicio} - ${it.hora_fin}" }
        holder.txtHorarios.text = horarios

        holder.btnMatricular.setOnClickListener {
            onMatricularClick(item)
        }
    }

    override fun getItemCount() = lista.size

    fun actualizar(nuevaLista: List<ClaseConDetalles>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}
