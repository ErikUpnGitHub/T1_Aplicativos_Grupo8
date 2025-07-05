package com.example.t1_aplicativos_grupo8.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.ui.adapters.HorarioAdapter
import com.example.t1_aplicativos_grupo8.ui.viewmodel.HorarioViewModel

class AlumnoClaseDetalleFragment : Fragment() {

    private var idclase: Int = -1
    private lateinit var viewModel: HorarioViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HorarioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idclase = arguments?.getInt("idclase") ?: -1
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alumno_clase_detalle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvTitulo = view.findViewById<TextView>(R.id.tvTituloClase)
        tvTitulo.text = "Detalle de Clase ID: $idclase"

        recyclerView = view.findViewById(R.id.recyclerHorarios)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = HorarioAdapter(emptyList()) { }
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[HorarioViewModel::class.java]

        viewModel.cargarPorClase(idclase)
        viewModel.horarios.observe(viewLifecycleOwner) {
            adapter.actualizar(it)
        }

        view.findViewById<Button>(R.id.btnAlumnoVerTareas).setOnClickListener {
            val fragment = AlumnoTareasFragment().apply {
                arguments = Bundle().apply {
                    putInt("idclase", idclase)
                }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit()
        }

        view.findViewById<Button>(R.id.btnAlumnoVerParticipantes).setOnClickListener {
            val fragment = AlumnoParticipantesFragment().apply {
                arguments = Bundle().apply {
                    putInt("idclase", idclase)
                }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
