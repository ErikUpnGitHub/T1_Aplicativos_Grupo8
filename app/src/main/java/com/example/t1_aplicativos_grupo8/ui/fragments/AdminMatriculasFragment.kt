package com.example.t1_aplicativos_grupo8.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.data.Matricula
import com.example.t1_aplicativos_grupo8.ui.adapters.MatriculaAdapter
import com.example.t1_aplicativos_grupo8.ui.viewmodel.MatriculaViewModel

class AdminMatriculasFragment : Fragment() {

    private lateinit var viewModel: MatriculaViewModel
    private lateinit var adapter: MatriculaAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_admin_matriculas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerMatriculas)
        val btnRegistrar = view.findViewById<Button>(R.id.btnRegistrarMatricula)

        recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = MatriculaAdapter(emptyList())
        recycler.adapter = adapter

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))[MatriculaViewModel::class.java]

        viewModel.matriculas.observe(viewLifecycleOwner) {
            adapter.actualizar(it)
        }

        viewModel.cargarMatriculas()

        btnRegistrar.setOnClickListener {
            mostrarDialogoRegistro()
        }
    }

    private fun mostrarDialogoRegistro() {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_registrar_matricula, null)
        builder.setView(view)
        builder.setTitle("Registrar Matrícula")

        val editIdUsuario = view.findViewById<EditText>(R.id.editIdUsuario)
        val editIdClase = view.findViewById<EditText>(R.id.editIdClase)

        builder.setPositiveButton("Guardar") { dialog, _ ->
            val idUsuario = editIdUsuario.text.toString().toIntOrNull()
            val idClase = editIdClase.text.toString().toIntOrNull()

            if (idUsuario != null && idClase != null) {
                viewModel.registrarMatricula(idUsuario, idClase)
            } else {
                Toast.makeText(requireContext(), "Datos inválidos", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar", null)
        builder.create().show()
    }
}
