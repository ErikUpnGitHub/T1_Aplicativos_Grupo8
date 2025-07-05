package com.example.t1_aplicativos_grupo8.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.ui.adapters.UsuarioAdapter
import com.example.t1_aplicativos_grupo8.ui.viewmodel.AlumnosClaseViewModel

class AlumnoParticipantesFragment : Fragment() {

    private var idclase: Int = -1
    private lateinit var recycler: RecyclerView
    private lateinit var viewModel: AlumnosClaseViewModel
    private lateinit var adapter: UsuarioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idclase = arguments?.getInt("idclase") ?: -1
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alumno_participantes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = view.findViewById(R.id.recyclerAlumnos)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = UsuarioAdapter(emptyList()) { }
        recycler.adapter = adapter

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
