package com.example.t1_aplicativos_grupo8.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.data.NotaConUsuario
import com.example.t1_aplicativos_grupo8.ui.adapters.NotaTareaAdapter
import com.example.t1_aplicativos_grupo8.ui.viewmodel.NotaViewModel

class ProfesorNotasTareaFragment : Fragment() {

    private var idclase: Int = -1
    private lateinit var tituloTarea: String
    private lateinit var viewModel: NotaViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotaTareaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idclase = arguments?.getInt("idclase") ?: -1
        tituloTarea = arguments?.getString("titulo") ?: ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profesor_notas_tarea, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.recyclerNotasTarea)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = NotaTareaAdapter(emptyList()) { notaConUsuario ->
            mostrarDialogoEditarNota(notaConUsuario)
        }
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this)[NotaViewModel::class.java]

        viewModel.notasConUsuario.observe(viewLifecycleOwner) {
            adapter.actualizar(it)
        }

        viewModel.obtenerNotasConUsuario(idclase, tituloTarea)
    }

    private fun mostrarDialogoEditarNota(notaConUsuario: NotaConUsuario) {
        val input = EditText(requireContext()).apply {
            hint = "Nueva nota"
            setText(notaConUsuario.nota.valor?.toString() ?: "")
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Editar Nota")
            .setView(input)
            .setPositiveButton("Guardar") { dialog, _ ->
                val valor = input.text.toString().toDoubleOrNull()
                if (valor != null) {
                    val actualizada = notaConUsuario.nota.copy(valor = valor)
                    viewModel.actualizarNota(actualizada)
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
