package com.example.t1_aplicativos_grupo8.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.data.Matricula

class MatriculaAdapter(private var lista: List<Matricula>) :
    RecyclerView.Adapter<MatriculaAdapter.MatriculaViewHolder>() {

    class MatriculaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtUsuario: TextView = view.findViewById(R.id.txtUsuario)
        val txtClase: TextView = view.findViewById(R.id.txtClase)
        val txtFecha: TextView = view.findViewById(R.id.txtFecha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatriculaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_matricula, parent, false)
        return MatriculaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatriculaViewHolder, position: Int) {
        val matricula = lista[position]
        holder.txtUsuario.text = "Estudiante ID: ${matricula.idusuario}"
        holder.txtClase.text = "Clase ID: ${matricula.idclase}"
        holder.txtFecha.text = "Fecha: ${matricula.fecha_matricula}"
    }

    override fun getItemCount(): Int = lista.size

    fun actualizar(nuevaLista: List<Matricula>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}
