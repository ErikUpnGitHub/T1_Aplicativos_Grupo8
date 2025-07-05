package com.example.t1_aplicativos_grupo8.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.ui.adapters.HorarioAdapter
import com.example.t1_aplicativos_grupo8.ui.viewmodel.HorarioViewModel

class ProfesorClaseDetalleFragment : Fragment() {

    private var idclase: Int = -1
    private lateinit var viewModel: HorarioViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HorarioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idclase = arguments?.getInt("idclase") ?: -1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profesor_clase_detalle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvTitulo = view.findViewById<TextView>(R.id.tvTituloClase)
        tvTitulo.text = "Clase ID: $idclase"

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

        // Botones (nuevos IDs y textos)
        view.findViewById<Button>(R.id.btnAlumnosClase).setOnClickListener {
            val fragment = ProfesorAlumnosClaseFragment().apply {
                arguments = Bundle().apply {
                    putInt("idclase", idclase)
                }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit()
        }


        view.findViewById<Button>(R.id.btnRegistrarTareaClase).setOnClickListener {
            mostrarDialogoRegistrarTarea()
        }

        //view.findViewById<Button>(R.id.btnAsignarNotaClase).setOnClickListener {
        //asignar nota
        //}

        view.findViewById<Button>(R.id.btnVerTareasClase).setOnClickListener {
            val fragment = ProfesorTareasClaseFragment().apply {
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

    private fun mostrarDialogoRegistrarTarea() {
        val builder = android.app.AlertDialog.Builder(requireContext())
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_registrar_tarea, null)
        builder.setView(view)
        builder.setTitle("Registrar Tarea")

        val txtTitulo = view.findViewById<android.widget.EditText>(R.id.editTituloTarea)
        val txtDescripcion = view.findViewById<android.widget.EditText>(R.id.editDescripcionTarea)
        val txtFecha = view.findViewById<android.widget.EditText>(R.id.editFechaEntregaTarea)

        txtFecha.setOnClickListener {
            val calendario = java.util.Calendar.getInstance()
            val datePicker = android.app.DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    val fechaSeleccionada = String.format("%04d-%02d-%02d", year, month + 1, day)
                    txtFecha.setText(fechaSeleccionada)
                },
                calendario.get(java.util.Calendar.YEAR),
                calendario.get(java.util.Calendar.MONTH),
                calendario.get(java.util.Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }


        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        val tareaViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[com.example.t1_aplicativos_grupo8.ui.viewmodel.TareaViewModel::class.java]

        builder.setPositiveButton("Registrar") { dialog, _ ->
            try {
                val fecha = sdf.parse(txtFecha.text.toString()) ?: java.util.Date()

                val tarea = com.example.t1_aplicativos_grupo8.data.Tarea(
                    idclase = idclase,
                    titulo = txtTitulo.text.toString(),
                    descripcion = txtDescripcion.text.toString(),
                    tipo = "Tarea",
                    fecha_entrega = fecha
                )

                tareaViewModel.registrarTareaConNotas(tarea)
            } catch (e: Exception) {
                android.widget.Toast.makeText(requireContext(), "Fecha inválida", android.widget.Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }

        builder.create().show()
    }

}
