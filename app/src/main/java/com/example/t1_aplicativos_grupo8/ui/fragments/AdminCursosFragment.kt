package com.example.t1_aplicativos_grupo8.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.data.Curso
import com.example.t1_aplicativos_grupo8.ui.adapters.CursoAdapter
import com.example.t1_aplicativos_grupo8.ui.viewmodel.CursoViewModel

class AdminCursosFragment : Fragment() {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: CursoAdapter
    private lateinit var viewModel: CursoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_admin_cursos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = view.findViewById(R.id.recyclerCursos)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = CursoAdapter(emptyList()) { curso -> mostrarDialogoEditar(curso) }
        recycler.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[CursoViewModel::class.java]

        viewModel.cursos.observe(viewLifecycleOwner) {
            adapter.actualizar(it)
        }

        viewModel.cargar()

        val btnAgregar = view.findViewById<Button>(R.id.btnAgregarCurso)
        btnAgregar.setOnClickListener { mostrarDialogoAgregar() }

        val editBuscar = view.findViewById<EditText>(R.id.editBuscarCurso)
        editBuscar.addTextChangedListener(object : android.text.TextWatcher {
            override fun afterTextChanged(s: android.text.Editable?) {
                val texto = s.toString().trim()
                if (texto.isEmpty()) viewModel.cargar()
                else viewModel.buscar(texto)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun mostrarDialogoAgregar() {
        val builder = android.app.AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_agregar_curso, null)
        builder.setView(view)
        builder.setTitle("Nuevo Curso")

        val txtNombre = view.findViewById<EditText>(R.id.editNombreCurso)
        val txtDescripcion = view.findViewById<EditText>(R.id.editDescripcionCurso)

        builder.setPositiveButton("Guardar") { dialog, _ ->
            val curso = Curso(
                nombre = txtNombre.text.toString(),
                descripcion = txtDescripcion.text.toString()
            )
            viewModel.insertar(curso)
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }

        builder.create().show()
    }

    private fun mostrarDialogoEditar(curso: Curso) {
        val builder = android.app.AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_agregar_curso, null)
        builder.setView(view)
        builder.setTitle("Editar Curso")

        val txtNombre = view.findViewById<EditText>(R.id.editNombreCurso)
        val txtDescripcion = view.findViewById<EditText>(R.id.editDescripcionCurso)

        txtNombre.setText(curso.nombre)
        txtDescripcion.setText(curso.descripcion ?: "")

        builder.setPositiveButton("Actualizar") { dialog, _ ->
            val actualizado = curso.copy(
                nombre = txtNombre.text.toString(),
                descripcion = txtDescripcion.text.toString()
            )
            viewModel.actualizar(actualizado)
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }

        builder.create().show()
    }
}
