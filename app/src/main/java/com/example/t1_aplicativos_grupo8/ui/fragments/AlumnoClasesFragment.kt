package com.example.t1_aplicativos_grupo8.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.ui.adapters.ClaseEstudianteAdapter
import com.example.t1_aplicativos_grupo8.ui.viewmodel.ClasesEstudianteViewModel

class AlumnoClasesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ClaseEstudianteAdapter
    private lateinit var viewModel: ClasesEstudianteViewModel

    private var idusuario: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idusuario = arguments?.getInt("idusuario") ?: -1
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_alumno_clases, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.recyclerClasesAlumno)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = ClaseEstudianteAdapter(emptyList()) { claseConCurso ->
            val fragment = AlumnoClaseDetalleFragment().apply {
                arguments = Bundle().apply {
                    putInt("idclase", claseConCurso.clase.idclase)
                    putInt("idusuario", idusuario)
                }
            }

            parentFragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit()
        }

        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this)[ClasesEstudianteViewModel::class.java]
        viewModel.clases.observe(viewLifecycleOwner) {
            adapter.actualizar(it)
        }

        viewModel.cargarClasesDelAlumno(idusuario)
    }
}
