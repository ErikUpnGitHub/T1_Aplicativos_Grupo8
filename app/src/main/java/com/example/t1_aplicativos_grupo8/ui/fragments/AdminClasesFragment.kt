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
import com.example.t1_aplicativos_grupo8.data.Clase
import com.example.t1_aplicativos_grupo8.ui.adapters.ClaseAdapter
import com.example.t1_aplicativos_grupo8.ui.viewmodel.ClaseViewModel
import com.example.t1_aplicativos_grupo8.ui.viewmodel.ProfesorNombreViewModel

class AdminClasesFragment : Fragment() {

    private lateinit var viewModel: ClaseViewModel
    private lateinit var adapter: ClaseAdapter
    private lateinit var profesorNombreViewModel: ProfesorNombreViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_admin_clases, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerClases)
        val btnAgregar = view.findViewById<Button>(R.id.btnAgregarClase)

        recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = ClaseAdapter(emptyList()) { mostrarDialogo(it) }
        recycler.adapter = adapter

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))[ClaseViewModel::class.java]
        profesorNombreViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[ProfesorNombreViewModel::class.java]

        viewModel.clases.observe(viewLifecycleOwner) {
            adapter.actualizar(it)
        }

        viewModel.cargarClases()

        btnAgregar.setOnClickListener {
            mostrarDialogo(null)
        }
    }

    private fun mostrarDialogo(clase: Clase?) {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_agregar_clase, null)
        builder.setView(view)
        builder.setTitle(if (clase == null) "Agregar Clase" else "Editar Clase")

        val spinnerProfesores = view.findViewById<Spinner>(R.id.spinnerProfesores)
        val txtAula = view.findViewById<EditText>(R.id.editAula)
        val txtCapacidad = view.findViewById<EditText>(R.id.editCapacidad)
        val spinnerCursos = view.findViewById<Spinner>(R.id.spinnerCursos)


        // Cargar profesores
        profesorNombreViewModel.profesoresConNombre.observe(viewLifecycleOwner) { lista ->
            val nombres = lista.map { "${it.nombreCompleto} - ${it.especialidad}" }
            val adapterProf = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nombres)
            adapterProf.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerProfesores.adapter = adapterProf

            clase?.let {
                val index = lista.indexOfFirst { p -> p.idusuario == clase.idusuario }
                if (index != -1) spinnerProfesores.setSelection(index)
            }
        }
        profesorNombreViewModel.cargarProfesoresConNombre()

        // Cargar cursos
        viewModel.cursos.observe(viewLifecycleOwner) { cursos ->
            val adapterCursos = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, cursos.map { it.nombre })
            adapterCursos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCursos.adapter = adapterCursos

            clase?.let {
                val index = cursos.indexOfFirst { c -> c.idcurso == clase.idcurso }
                if (index != -1) spinnerCursos.setSelection(index)
            }
        }
        viewModel.cargarCursos()

        // Si estamos editando, rellenar campos
        clase?.let {
            txtAula.setText(it.aula)
            txtCapacidad.setText(it.capacidad.toString())
        }

        builder.setPositiveButton("Guardar") { dialog, _ ->
            val profesorSeleccionado = profesorNombreViewModel.profesoresConNombre.value?.get(spinnerProfesores.selectedItemPosition)
            val cursoSeleccionado = viewModel.cursos.value?.get(spinnerCursos.selectedItemPosition)

            if (profesorSeleccionado == null || cursoSeleccionado == null) {
                Toast.makeText(requireContext(), "Debe seleccionar profesor y curso", Toast.LENGTH_SHORT).show()
                return@setPositiveButton
            }

            val nuevaClase = Clase(
                idclase = clase?.idclase ?: 0,
                idusuario = profesorSeleccionado.idusuario,
                idcurso = cursoSeleccionado.idcurso,
                aula = txtAula.text.toString(),
                capacidad = txtCapacidad.text.toString().toIntOrNull() ?: 0
            )

            if (clase == null) viewModel.insertar(nuevaClase)
            else viewModel.actualizar(nuevaClase)
        }

        builder.setNegativeButton("Cancelar", null)
        builder.create().show()
    }

}
