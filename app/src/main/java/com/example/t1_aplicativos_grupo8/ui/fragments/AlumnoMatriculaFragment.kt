package com.example.t1_aplicativos_grupo8.ui.fragments

import android.util.Log
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import com.example.t1_aplicativos_grupo8.R
import com.example.t1_aplicativos_grupo8.data.ClaseConDetalles
import com.example.t1_aplicativos_grupo8.ui.adapters.AlumnoMatriculaAdapter
import com.example.t1_aplicativos_grupo8.ui.viewmodel.MatriculaCursoViewModel
import java.text.SimpleDateFormat
import java.util.*

class AlumnoMatriculaFragment : Fragment() {

    private lateinit var viewModel: MatriculaCursoViewModel
    private lateinit var adapter: AlumnoMatriculaAdapter
    private var idUsuario: Int = -1
    private var claseSeleccionada: ClaseConDetalles? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idUsuario = arguments?.getInt("idusuario") ?: -1
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_alumno_matricula, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerMatriculaCursos)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = AlumnoMatriculaAdapter(emptyList()) { clase ->
            // Mostrar ventana emergente de confirmación
            AlertDialog.Builder(requireContext())
                .setTitle("Confirmar matrícula")
                .setMessage("¿Deseas matricularte en el curso \"${clase.curso.nombre}\"?")
                .setPositiveButton("Sí") { _, _ ->
                    val fecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                    Log.d("AlumnoMatricula", "Registrando matrícula: usuario=$idUsuario, clase=${clase.clase.idclase}, curso=${clase.curso.nombre}, fecha=$fecha")
                    viewModel.registrarMatricula(idUsuario, clase.clase.idclase, fecha)
                    Toast.makeText(requireContext(), "Matriculado exitosamente", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }

        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this)[MatriculaCursoViewModel::class.java]
        viewModel.clases.observe(viewLifecycleOwner) { adapter.actualizar(it) }
        viewModel.cargarClases()

        // Puedes ocultar este botón si ya no lo usas
        view.findViewById<Button>(R.id.btnConfirmarMatricula).setOnClickListener {
            Toast.makeText(requireContext(), "Selecciona un curso primero", Toast.LENGTH_SHORT).show()
        }
    }
}
