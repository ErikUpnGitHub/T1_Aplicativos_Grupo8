package com.example.t1_aplicativos_grupo8.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.ui.adapters.UsuarioAdapter
import com.example.t1_aplicativos_grupo8.ui.viewmodel.AlumnosClaseViewModel

class ProfesorAlumnosClaseFragment : Fragment() {

    private var idclase: Int = -1
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UsuarioAdapter
    private lateinit var viewModel: AlumnosClaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idclase = arguments?.getInt("idclase") ?: -1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profesor_alumnos_clase, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerAlumnosClase)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = UsuarioAdapter(emptyList()) {} // sin edición
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[AlumnosClaseViewModel::class.java]

        viewModel.alumnos.observe(viewLifecycleOwner) {
            adapter.actualizar(it)
        }

        viewModel.cargarAlumnosDeClase(idclase)
    }
}
