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
import com.example.t1_aplicativos_grupo8.ui.adapters.ProfesorAdapter
import com.example.t1_aplicativos_grupo8.ui.viewmodel.ProfesorViewModel

class AdminProfesoresFragment : Fragment() {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ProfesorAdapter
    private lateinit var viewModel: ProfesorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_admin_profesores, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = view.findViewById(R.id.recyclerProfesores)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = ProfesorAdapter(emptyList()) { usuario, profesor ->
            mostrarDialogoEditar(usuario, profesor)
        }
        recycler.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[ProfesorViewModel::class.java]

        viewModel.profesores.observe(viewLifecycleOwner) {
            adapter.actualizar(it)
        }

        viewModel.cargar()

        view.findViewById<Button>(R.id.btnAgregarProfesor).setOnClickListener {
            mostrarDialogoAgregar()
        }
    }

    private fun mostrarDialogoAgregar() {
        val builder = android.app.AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_agregar_profesor, null)
        builder.setView(view)
        builder.setTitle("Nuevo Profesor")

        val txtNombre = view.findViewById<EditText>(R.id.editNombre)
        val txtApellido = view.findViewById<EditText>(R.id.editApellido)
        val txtEmail = view.findViewById<EditText>(R.id.editEmail)
        val txtContra = view.findViewById<EditText>(R.id.editContrasenia)
        val txtEspecialidad = view.findViewById<EditText>(R.id.editEspecialidad)

        builder.setPositiveButton("Guardar") { dialog, _ ->
            val usuario = Usuario(
                idusuario = 0,
                nombre = txtNombre.text.toString(),
                apellido = txtApellido.text.toString(),
                email = txtEmail.text.toString(),
                contrasenia = txtContra.text.toString(),
                telefono = "",
                idrol = 2
            )
            val profesor = Profesor(
                idusuario = 0,
                especialidad = txtEspecialidad.text.toString()
            )
            viewModel.insertar(usuario, profesor)
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }

        builder.create().show()
    }

    private fun mostrarDialogoEditar(usuario: Usuario, profesor: Profesor) {
        val builder = android.app.AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_agregar_profesor, null)
        builder.setView(view)
        builder.setTitle("Editar Profesor")

        val txtNombre = view.findViewById<EditText>(R.id.editNombre)
        val txtApellido = view.findViewById<EditText>(R.id.editApellido)
        val txtEmail = view.findViewById<EditText>(R.id.editEmail)
        val txtContra = view.findViewById<EditText>(R.id.editContrasenia)
        val txtEspecialidad = view.findViewById<EditText>(R.id.editEspecialidad)

        txtNombre.setText(usuario.nombre)
        txtApellido.setText(usuario.apellido)
        txtEmail.setText(usuario.email)
        txtContra.setText(usuario.contrasenia)
        txtEspecialidad.setText(profesor.especialidad)

        builder.setPositiveButton("Actualizar") { dialog, _ ->
            val nuevoUsuario = usuario.copy(
                nombre = txtNombre.text.toString(),
                apellido = txtApellido.text.toString(),
                email = txtEmail.text.toString(),
                contrasenia = txtContra.text.toString()
            )
            val nuevoProfesor = profesor.copy(
                especialidad = txtEspecialidad.text.toString()
            )
            viewModel.actualizar(nuevoUsuario, nuevoProfesor)
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }

        builder.create().show()
    }
}
