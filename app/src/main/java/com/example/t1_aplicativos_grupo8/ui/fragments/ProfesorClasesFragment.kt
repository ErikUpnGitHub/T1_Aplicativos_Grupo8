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
import com.example.t1_aplicativos_grupo8.ui.adapters.ClaseProfesorAdapter
import com.example.t1_aplicativos_grupo8.ui.viewmodel.ClasesProfesorViewModel

class ProfesorClasesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ClasesProfesorViewModel
    private lateinit var adapter: ClaseProfesorAdapter

    private var idProfesor: Int = -1 // <-- Cámbialo según el ID del profesor actual

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idProfesor = arguments?.getInt("idusuario", -1) ?: -1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profesor_clases, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerClasesProfesor)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = ClaseProfesorAdapter(emptyList()) { claseSeleccionada ->
            val fragment = ProfesorClaseDetalleFragment().apply {
                arguments = Bundle().apply {
                    putInt("idclase", claseSeleccionada.idclase)
                }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment) // Asegúrate que tu layout tenga este ID
                .addToBackStack(null)
                .commit()
        }

        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[ClasesProfesorViewModel::class.java]

        viewModel.clases.observe(viewLifecycleOwner) { lista ->
            adapter.actualizar(lista)
        }

        viewModel.cargarClasesDelProfesor(idProfesor)
    }
}
