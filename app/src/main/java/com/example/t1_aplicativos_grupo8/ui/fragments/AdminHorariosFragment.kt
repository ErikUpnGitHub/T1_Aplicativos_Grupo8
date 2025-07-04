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
import com.example.t1_aplicativos_grupo8.data.Horario
import com.example.t1_aplicativos_grupo8.ui.adapters.HorarioAdapter
import com.example.t1_aplicativos_grupo8.ui.viewmodel.HorarioViewModel

class AdminHorariosFragment : Fragment() {

    private lateinit var viewModel: HorarioViewModel
    private lateinit var adapter: HorarioAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_admin_horarios, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerHorarios)
        val btnAgregar = view.findViewById<Button>(R.id.btnAgregarHorario)

        adapter = HorarioAdapter(emptyList()) { mostrarDialogo(it) }
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))[HorarioViewModel::class.java]

        viewModel.horarios.observe(viewLifecycleOwner) {
            adapter.actualizar(it)
        }

        viewModel.cargarHorarios()

        btnAgregar.setOnClickListener {
            mostrarDialogo(null)
        }
    }

    private fun mostrarDialogo(horario: Horario?) {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_agregar_horario, null)
        builder.setView(view)
        builder.setTitle(if (horario == null) "Agregar Horario" else "Editar Horario")

        val editDia = view.findViewById<EditText>(R.id.editDia)
        val editInicio = view.findViewById<EditText>(R.id.editHoraInicio)
        val editFin = view.findViewById<EditText>(R.id.editHoraFin)
        val editIdClase = view.findViewById<EditText>(R.id.editIdClase)

        horario?.let {
            editDia.setText(it.dia)
            editInicio.setText(it.hora_inicio)
            editFin.setText(it.hora_fin)
            editIdClase.setText(it.idclase.toString())
        }

        builder.setPositiveButton("Guardar") { _, _ ->
            val nuevo = Horario(
                idhorario = horario?.idhorario ?: 0,
                dia = editDia.text.toString(),
                hora_inicio = editInicio.text.toString(),
                hora_fin = editFin.text.toString(),
                idclase = editIdClase.text.toString().toIntOrNull() ?: 0
            )
            if (horario == null) viewModel.insertar(nuevo)
            else viewModel.actualizar(nuevo)
        }

        builder.setNegativeButton("Cancelar", null)
        builder.create().show()
    }
}
