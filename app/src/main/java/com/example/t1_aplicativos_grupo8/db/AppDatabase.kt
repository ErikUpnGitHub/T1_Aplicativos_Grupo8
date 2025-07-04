package com.example.t1_aplicativos_grupo8.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.t1_aplicativos_grupo8.data.Clase
import com.example.t1_aplicativos_grupo8.data.ClaseDao
import com.example.t1_aplicativos_grupo8.data.Curso
import com.example.t1_aplicativos_grupo8.data.CursoDao
import com.example.t1_aplicativos_grupo8.data.Estudiante
import com.example.t1_aplicativos_grupo8.data.EstudianteDao
import com.example.t1_aplicativos_grupo8.data.Horario
import com.example.t1_aplicativos_grupo8.data.HorarioDao
import com.example.t1_aplicativos_grupo8.data.Matricula
import com.example.t1_aplicativos_grupo8.data.MatriculaDao
import com.example.t1_aplicativos_grupo8.data.Nota
import com.example.t1_aplicativos_grupo8.data.NotaDao
import com.example.t1_aplicativos_grupo8.data.Notificacion
import com.example.t1_aplicativos_grupo8.data.NotificacionDao
import com.example.t1_aplicativos_grupo8.data.Profesor
import com.example.t1_aplicativos_grupo8.data.ProfesorDao
import com.example.t1_aplicativos_grupo8.data.Rol
import com.example.t1_aplicativos_grupo8.data.RolDao
import com.example.t1_aplicativos_grupo8.data.Tarea
import com.example.t1_aplicativos_grupo8.data.TareaDao
import com.example.t1_aplicativos_grupo8.data.Usuario
import com.example.t1_aplicativos_grupo8.data.UsuarioDao
import com.example.t1_aplicativos_grupo8.data.Converters

@Database(
    entities = [
        Usuario::class, Rol::class,
        Estudiante::class, Profesor::class,
        Curso::class, Clase::class,
        Horario::class, Matricula::class, Nota::class,
        Tarea::class, Notificacion::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun rolDao(): RolDao
    abstract fun estudianteDao(): EstudianteDao
    abstract fun profesorDao(): ProfesorDao
    abstract fun cursoDao(): CursoDao
    abstract fun claseDao(): ClaseDao
    abstract fun horarioDao(): HorarioDao
    abstract fun matriculaDao(): MatriculaDao
    abstract fun notaDao(): NotaDao
    abstract fun tareaDao(): TareaDao
    abstract fun notificacionDao(): NotificacionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mi_bd_universidad"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
