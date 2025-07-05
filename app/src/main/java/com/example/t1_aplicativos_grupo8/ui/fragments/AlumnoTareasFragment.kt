package com.example.t1_aplicativos_grupo8.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.ui.adapters.TareaConNotaAdapter
import com.example.t1_aplicativos_grupo8.ui.viewmodel.TareaConNotaViewModel

class AlumnoTareasFragment : Fragment() {

    private var idclase: Int = -1
    private var idusuario: Int = -1

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: TareaConNotaAdapter
    private lateinit var viewModel: TareaConNotaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idclase = it.getInt("idclase")
            idusuario = it.getInt("idusuario")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alumno_tareas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = view.findViewById(R.id.recyclerTareasAlumno)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = TareaConNotaAdapter(emptyList())
        recycler.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[TareaConNotaViewModel::class.java]

        viewModel.tareas.observe(viewLifecycleOwner) {
            adapter.actualizar(it)
        }

        viewModel.cargarTareasConNota(idclase, idusuario)
    }
}
