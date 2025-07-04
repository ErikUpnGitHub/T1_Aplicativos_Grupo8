package com.example.t1_aplicativos_grupo8.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.data.*
import com.example.t1_aplicativos_grupo8.ui.adapters.EstudianteAdapter
import com.example.t1_aplicativos_grupo8.ui.viewmodel.EstudianteViewModel

class AdminEstudiantesFragment : Fragment() {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: EstudianteAdapter
    private lateinit var viewModel: EstudianteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_admin_estudiantes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = view.findViewById(R.id.recyclerEstudiantes)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = EstudianteAdapter(emptyList()) { usuario, estudiante ->
            mostrarDialogoEditar(usuario, estudiante)
        }
        recycler.adapter = adapter

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))[EstudianteViewModel::class.java]

        viewModel.estudiantes.observe(viewLifecycleOwner) {
            adapter.actualizar(it)
        }

        viewModel.cargar()

        view.findViewById<Button>(R.id.btnAgregarEstudiante).setOnClickListener {
            mostrarDialogoAgregar()
        }
    }

    private fun mostrarDialogoAgregar() {
        val builder = android.app.AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_agregar_estudiante, null)
        builder.setView(view)
        builder.setTitle("Nuevo Estudiante")

        val txtNombre = view.findViewById<EditText>(R.id.editNombre)
        val txtApellido = view.findViewById<EditText>(R.id.editApellido)
        val txtEmail = view.findViewById<EditText>(R.id.editEmail)
        val txtContra = view.findViewById<EditText>(R.id.editContrasenia)
        val txtCarrera = view.findViewById<EditText>(R.id.editCarrera)

        builder.setPositiveButton("Guardar") { dialog, _ ->
            val usuario = Usuario(0, txtNombre.text.toString(), txtApellido.text.toString(), txtEmail.text.toString(), txtContra.text.toString(), "", 1)
            val estudiante = Estudiante(0, txtCarrera.text.toString())
            viewModel.insertar(usuario, estudiante)
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }

    private fun mostrarDialogoEditar(usuario: Usuario, estudiante: Estudiante) {
        val builder = android.app.AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_agregar_estudiante, null)
        builder.setView(view)
        builder.setTitle("Editar Estudiante")

        val txtNombre = view.findViewById<EditText>(R.id.editNombre)
        val txtApellido = view.findViewById<EditText>(R.id.editApellido)
        val txtEmail = view.findViewById<EditText>(R.id.editEmail)
        val txtContra = view.findViewById<EditText>(R.id.editContrasenia)
        val txtCarrera = view.findViewById<EditText>(R.id.editCarrera)

        txtNombre.setText(usuario.nombre)
        txtApellido.setText(usuario.apellido)
        txtEmail.setText(usuario.email)
        txtContra.setText(usuario.contrasenia)
        txtCarrera.setText(estudiante.carrera)

        builder.setPositiveButton("Actualizar") { dialog, _ ->
            val nuevoUsuario = usuario.copy(
                nombre = txtNombre.text.toString(),
                apellido = txtApellido.text.toString(),
                email = txtEmail.text.toString(),
                contrasenia = txtContra.text.toString()
            )
            val nuevoEstudiante = estudiante.copy(
                carrera = txtCarrera.text.toString()
            )
            viewModel.actualizar(nuevoUsuario, nuevoEstudiante)
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }

        builder.create().show()
    }
}
