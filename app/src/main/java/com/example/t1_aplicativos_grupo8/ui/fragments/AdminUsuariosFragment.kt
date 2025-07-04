package com.example.t1_aplicativos_grupo8.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.data.Usuario
import com.example.t1_aplicativos_grupo8.ui.adapters.UsuarioAdapter
import com.example.t1_aplicativos_grupo8.ui.viewmodel.UsuarioViewModel

class AdminUsuariosFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UsuarioAdapter
    private lateinit var viewModel: UsuarioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_admin_usuarios, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerUsuarios)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = UsuarioAdapter(emptyList()) { usuario ->
            mostrarDialogoEditar(usuario)
        }

        recyclerView.adapter = adapter


        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[UsuarioViewModel::class.java]

        viewModel.usuarios.observe(viewLifecycleOwner) {
            adapter.actualizar(it)
        }

        viewModel.debugUsuarios()
        viewModel.cargarUsuarios()

        val btnAgregar = view.findViewById<Button>(R.id.btnAgregarUsuario)
        btnAgregar.setOnClickListener {
            mostrarDialogoAgregar()
        }

        val editBuscar = view.findViewById<android.widget.EditText>(R.id.editBuscar)

        editBuscar.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {
                val query = s.toString().trim()
                if (query.isEmpty()) {
                    viewModel.cargarUsuarios()
                } else {
                    viewModel.buscar(query)
                }
            }
        })

    }

    private fun mostrarDialogoAgregar() {
        val builder = android.app.AlertDialog.Builder(requireContext())
        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.dialog_agregar_usuario, null)
        builder.setView(view)
        builder.setTitle("Nuevo Usuario")

        val txtNombre = view.findViewById<android.widget.EditText>(R.id.editNombre)
        val txtApellido = view.findViewById<android.widget.EditText>(R.id.editApellido)
        val txtEmail = view.findViewById<android.widget.EditText>(R.id.editEmail)
        val txtContra = view.findViewById<android.widget.EditText>(R.id.editContrasenia)
        val spinnerRol = view.findViewById<android.widget.Spinner>(R.id.spinnerRol)
        val opciones = arrayOf("Estudiante", "Profesor")
        spinnerRol.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opciones).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        builder.setPositiveButton("Guardar") { dialog, _ ->
            val rolSeleccionado = if (spinnerRol.selectedItemPosition == 0) 1 else 2
            val usuario = Usuario(
                idusuario = 0,
                nombre = txtNombre.text.toString(),
                apellido = txtApellido.text.toString(),
                email = txtEmail.text.toString(),
                contrasenia = txtContra.text.toString(),
                telefono = "",
                idrol = 3
            )
            viewModel.insertar(usuario)
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }

        builder.create().show()
    }

    private fun mostrarDialogoEditar(usuario: Usuario) {
        val builder = android.app.AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_agregar_usuario, null)
        builder.setView(view)
        builder.setTitle("Editar Usuario")

        val txtNombre = view.findViewById<android.widget.EditText>(R.id.editNombre)
        val txtApellido = view.findViewById<android.widget.EditText>(R.id.editApellido)
        val txtEmail = view.findViewById<android.widget.EditText>(R.id.editEmail)
        val txtContra = view.findViewById<android.widget.EditText>(R.id.editContrasenia)
        val spinnerRol = view.findViewById<android.widget.Spinner>(R.id.spinnerRol)
        val opciones = arrayOf("Estudiante", "Profesor")
        spinnerRol.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opciones).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        // Prellenar campos
        txtNombre.setText(usuario.nombre)
        txtApellido.setText(usuario.apellido)
        txtEmail.setText(usuario.email)
        txtContra.setText(usuario.contrasenia)

        builder.setPositiveButton("Actualizar") { dialog, _ ->
            val rolSeleccionado = if (spinnerRol.selectedItemPosition == 0) 1 else 2
            val actualizado = usuario.copy(
                nombre = txtNombre.text.toString(),
                apellido = txtApellido.text.toString(),
                email = txtEmail.text.toString(),
                contrasenia = txtContra.text.toString()
            )
            viewModel.actualizar(actualizado)
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }

        builder.create().show()
    }
}
