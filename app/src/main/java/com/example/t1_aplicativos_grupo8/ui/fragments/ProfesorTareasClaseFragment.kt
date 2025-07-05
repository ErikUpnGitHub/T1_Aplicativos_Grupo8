package com.example.t1_aplicativos_grupo8.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.ui.adapters.TareaAdapter
import com.example.t1_aplicativos_grupo8.ui.viewmodel.TareaViewModel

class ProfesorTareasClaseFragment : Fragment() {

    private var idclase: Int = -1
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TareaAdapter
    private lateinit var viewModel: TareaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idclase = arguments?.getInt("idclase") ?: -1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profesor_tareas_clase, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerTareas)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = TareaAdapter(emptyList()) { tarea ->
            val fragment = ProfesorNotasTareaFragment().apply {
                arguments = Bundle().apply {
                    putInt("idclase", tarea.idclase)
                    putString("titulo", tarea.titulo)
                }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit()
        }

        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[TareaViewModel::class.java]

        viewModel.tareas.observe(viewLifecycleOwner) {
            adapter.actualizar(it)
        }

        viewModel.obtenerPorClase(idclase)
    }
}
