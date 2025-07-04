package com.example.t1_aplicativos_grupo8.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.data.Usuario

class UsuarioAdapter(
    private var lista: List<Usuario>,
    private val onEditarClick: (Usuario) -> Unit
) : RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {


    class UsuarioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView = view.findViewById(R.id.txtNombre)
        val txtEmail: TextView = view.findViewById(R.id.txtEmail)
        val btnEditar: Button = view.findViewById(R.id.btnEditar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_usuario, parent, false)
        return UsuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = lista[position]
        holder.txtNombre.text = "${usuario.nombre} ${usuario.apellido}"
        holder.txtEmail.text = usuario.email
        holder.btnEditar.setOnClickListener {
            onEditarClick(usuario)
        }
    }


    override fun getItemCount(): Int = lista.size

    fun actualizar(nuevaLista: List<Usuario>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }

}
