package com.example.t1_aplicativos_grupo8.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.ui.adapters.AlumnoClasesAdapter
import com.example.t1_aplicativos_grupo8.ui.viewmodel.ClasesEstudianteViewModel

class EstudianteMisClasesFragment : Fragment() {

    private lateinit var adapter: AlumnoClasesAdapter
    private lateinit var viewModel: ClasesEstudianteViewModel
    private var idUsuario: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idUsuario = arguments?.getInt("idusuario") ?: -1
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_estudiante_mis_clases, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerClasesAlumno)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = AlumnoClasesAdapter(emptyList()) { clase ->
            // Acción al hacer clic en una clase (puedes abrir un nuevo fragmento aquí)
        }

        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this)[ClasesEstudianteViewModel::class.java]
        viewModel.clases.observe(viewLifecycleOwner) { adapter.actualizar(it) }
        viewModel.cargarClasesDelAlumno(idUsuario)
    }
}
