package db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.t1_aplicativos_grupo8.Alumno
import com.example.t1_aplicativos_grupo8.Curso
import com.example.t1_aplicativos_grupo8.Docente
import com.example.t1_aplicativos_grupo8.Notificacion

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "app.db"
        private const val DATABASE_VERSION = 3

        // TABLA - DOCENTE
        const val TABLE_DOCENTES = "docentes"
        const val COL_DOCENTE_ID = "id"
        const val COL_DOCENTE_NOMBRE = "nombre"
        const val COL_DOCENTE_EMAIL = "email"
        const val COL_DOCENTE_PASSWORD = "password"

        // TABLA - ALUMNO
        const val TABLE_ALUMNOS = "alumnos"
        const val COL_ALUMNO_ID = "id"
        const val COL_ALUMNO_NOMBRE = "nombre"
        const val COL_ALUMNO_EMAIL = "email"
        const val COL_ALUMNO_PASSWORD = "password"
        const val COL_ALUMNO_BIRTHDATE = "birthdate"
        const val COL_ALUMNO_CELULAR = "celular"
        const val COL_ALUMNO_DIRECCION = "direccion"  // NUEVA

        // TABLA - CURSO
        const val TABLE_CURSOS = "cursos"
        const val COL_CURSO_ID = "id"
        const val COL_CURSO_NOMBRE = "nombre"
        const val COL_CURSO_CODIGO = "codigo"
        const val COL_CURSO_SALON = "salon"
        const val COL_CURSO_PABELLON = "pabellon"
        const val COL_CURSO_NOMBRE_PROFESOR = "nombreProfesor"
        const val COL_CURSO_CORREO_PROFESOR = "correoProfesor"
        const val COL_CURSO_ESTUDIANTES = "estudiantes"
        const val COL_CURSO_ASISTENCIAS = "asistencias"
        const val COL_CURSO_CANTIDAD_ALUMNOS = "cantidad_alumnos"
        const val COL_CURSO_HORARIO = "horario"
        const val COL_CURSO_DIA = "dia"
        const val COL_CURSO_HORA = "hora"

        // TABLA - NOTIFICACION
        const val TABLE_NOTIFICACIONES = "notificaciones"
        const val COL_NOTIF_ID = "id"
        const val COL_NOTIF_DOCENTE_EMAIL = "docente_email"
        const val COL_NOTIF_MENSAJE = "mensaje"
        const val COL_NOTIF_CURSO_ID = "curso_id"
        const val COL_NOTIF_LEIDO = "leido"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        // CREAR TABLAS

        val createDocentesTable = """
            CREATE TABLE $TABLE_DOCENTES (
                $COL_DOCENTE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_DOCENTE_NOMBRE TEXT,
                $COL_DOCENTE_EMAIL TEXT,
                $COL_DOCENTE_PASSWORD TEXT
            )
        """.trimIndent()
        db?.execSQL(createDocentesTable)

        val createAlumnosTable = """
            CREATE TABLE $TABLE_ALUMNOS (
                $COL_ALUMNO_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_ALUMNO_NOMBRE TEXT,
                $COL_ALUMNO_EMAIL TEXT,
                $COL_ALUMNO_PASSWORD TEXT,
                $COL_ALUMNO_BIRTHDATE TEXT,
                $COL_ALUMNO_CELULAR TEXT,
                $COL_ALUMNO_DIRECCION TEXT
            )
        """.trimIndent()
        db?.execSQL(createAlumnosTable)

        val createCursosTable = """
            CREATE TABLE $TABLE_CURSOS (
                $COL_CURSO_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_CURSO_NOMBRE TEXT,
                $COL_CURSO_CODIGO TEXT,
                $COL_CURSO_SALON TEXT,
                $COL_CURSO_PABELLON TEXT,
                $COL_CURSO_NOMBRE_PROFESOR TEXT,
                $COL_CURSO_CORREO_PROFESOR TEXT,
                $COL_CURSO_ESTUDIANTES TEXT,
                $COL_CURSO_ASISTENCIAS TEXT,
                $COL_CURSO_CANTIDAD_ALUMNOS INTEGER,
                $COL_CURSO_HORARIO TEXT,
                $COL_CURSO_DIA TEXT,
                $COL_CURSO_HORA TEXT
            )
        """.trimIndent()
        db?.execSQL(createCursosTable)

        val createNotificacionesTable = """
            CREATE TABLE $TABLE_NOTIFICACIONES (
                $COL_NOTIF_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NOTIF_DOCENTE_EMAIL TEXT,
                $COL_NOTIF_MENSAJE TEXT,
                $COL_NOTIF_CURSO_ID INTEGER,
                $COL_NOTIF_LEIDO INTEGER DEFAULT 0
            )
        """.trimIndent()
        db?.execSQL(createNotificacionesTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("DROP TABLE IF EXISTS $TABLE_DOCENTES")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ALUMNOS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CURSOS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NOTIFICACIONES")
        onCreate(db)
    }

    // MÉTODOS PARA DOCENTE

    fun addDocente(nombre: String, email: String, password: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_DOCENTE_NOMBRE, nombre)
            put(COL_DOCENTE_EMAIL, email)
            put(COL_DOCENTE_PASSWORD, password)
        }
        return db.insert(TABLE_DOCENTES, null, values)
    }

    fun getAllDocentes(): List<Docente> {
        val docentes = mutableListOf<Docente>()
        val db = readableDatabase
        val cursor = db.query(TABLE_DOCENTES, null, null, null, null, null, null)
        if (cursor.moveToFirst()){
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_DOCENTE_ID))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COL_DOCENTE_NOMBRE))
                val email = cursor.getString(cursor.getColumnIndexOrThrow(COL_DOCENTE_EMAIL))
                val password = cursor.getString(cursor.getColumnIndexOrThrow(COL_DOCENTE_PASSWORD))
                docentes.add(Docente(id, nombre, email, password))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return docentes
    }

    fun authenticateDocente(email: String, password: String): Boolean {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_DOCENTES,
            arrayOf(COL_DOCENTE_ID),
            "$COL_DOCENTE_EMAIL = ? AND $COL_DOCENTE_PASSWORD = ?",
            arrayOf(email, password),
            null,
            null,
            null
        )
        val exists = cursor.moveToFirst()
        cursor.close()
        return exists
    }

    fun getDocenteByEmail(email: String): Docente? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_DOCENTES WHERE LOWER($COL_DOCENTE_EMAIL) = ?"
        val cursor = db.rawQuery(query, arrayOf(email.toLowerCase()))
        var docente: Docente? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_DOCENTE_ID))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COL_DOCENTE_NOMBRE))
            val emailDb = cursor.getString(cursor.getColumnIndexOrThrow(COL_DOCENTE_EMAIL))
            val password = cursor.getString(cursor.getColumnIndexOrThrow(COL_DOCENTE_PASSWORD))
            docente = Docente(id, nombre, emailDb, password)
        }
        cursor.close()
        return docente
    }

    // MÉTODOS PARA ALUMNO

    fun insertAlumno(alumno: Alumno): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_ALUMNO_NOMBRE, alumno.nombre)
            put(COL_ALUMNO_EMAIL, alumno.email)
            put(COL_ALUMNO_PASSWORD, alumno.password)
            put(COL_ALUMNO_BIRTHDATE, alumno.birthdate)
            put(COL_ALUMNO_CELULAR, alumno.celular)
            put(COL_ALUMNO_DIRECCION, alumno.direccion)
        }
        val result = db.insert(TABLE_ALUMNOS, null, values)
        return result != -1L
    }

    fun getAllAlumnos(): List<Alumno> {
        val alumnos = mutableListOf<Alumno>()
        val db = readableDatabase
        val cursor = db.query(TABLE_ALUMNOS, null, null, null, null, null, null)
        if (cursor.moveToFirst()){
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ALUMNO_ID))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COL_ALUMNO_NOMBRE))
                val email = cursor.getString(cursor.getColumnIndexOrThrow(COL_ALUMNO_EMAIL))
                val password = cursor.getString(cursor.getColumnIndexOrThrow(COL_ALUMNO_PASSWORD))
                val birthdate = cursor.getString(cursor.getColumnIndexOrThrow(COL_ALUMNO_BIRTHDATE))
                val celular = cursor.getString(cursor.getColumnIndexOrThrow(COL_ALUMNO_CELULAR))
                val direccion = cursor.getString(cursor.getColumnIndexOrThrow(COL_ALUMNO_DIRECCION))
                alumnos.add(Alumno(id, nombre, email, password, birthdate, celular, direccion))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return alumnos
    }

    fun deleteAlumno(alumnoId: Int): Boolean {
        val db = writableDatabase
        val rowsAffected = db.delete(TABLE_ALUMNOS, "$COL_ALUMNO_ID = ?", arrayOf(alumnoId.toString()))
        return rowsAffected > 0
    }

    fun authenticateAlumno(email: String, password: String): Boolean {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_ALUMNOS,
            arrayOf(COL_ALUMNO_ID),
            "$COL_ALUMNO_EMAIL = ? AND $COL_ALUMNO_PASSWORD = ?",
            arrayOf(email, password),
            null,
            null,
            null
        )
        val exists = cursor.moveToFirst()
        cursor.close()
        return exists
    }

    fun updateAlumnoPassword(email: String, newPassword: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_ALUMNO_PASSWORD, newPassword)
        }
        val rowsAffected = db.update(TABLE_ALUMNOS, values, "$COL_ALUMNO_EMAIL = ?", arrayOf(email))
        return rowsAffected > 0
    }

    fun getAlumnoByEmail(email: String): Alumno? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_ALUMNOS,
            arrayOf(COL_ALUMNO_ID, COL_ALUMNO_NOMBRE, COL_ALUMNO_EMAIL, COL_ALUMNO_PASSWORD, COL_ALUMNO_BIRTHDATE, COL_ALUMNO_CELULAR, COL_ALUMNO_DIRECCION),
            "$COL_ALUMNO_EMAIL = ?",
            arrayOf(email),
            null,
            null,
            null
        )
        var alumno: Alumno? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ALUMNO_ID))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COL_ALUMNO_NOMBRE))
            val emailDb = cursor.getString(cursor.getColumnIndexOrThrow(COL_ALUMNO_EMAIL))
            val password = cursor.getString(cursor.getColumnIndexOrThrow(COL_ALUMNO_PASSWORD))
            val birthdate = cursor.getString(cursor.getColumnIndexOrThrow(COL_ALUMNO_BIRTHDATE))
            val celular = cursor.getString(cursor.getColumnIndexOrThrow(COL_ALUMNO_CELULAR))
            val direccion = cursor.getString(cursor.getColumnIndexOrThrow(COL_ALUMNO_DIRECCION))
            alumno = Alumno(id, nombre, emailDb, password, birthdate, celular, direccion)
        }
        cursor.close()
        return alumno
    }

    fun updateAlumnoDireccion(email: String, direccion: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_ALUMNO_DIRECCION, direccion)
        }
        val rowsAffected = db.update(TABLE_ALUMNOS, values, "$COL_ALUMNO_EMAIL = ?", arrayOf(email))
        return rowsAffected > 0
    }

    // MÉTODOS PARA CURSO

    fun insertCurso(curso: Curso): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_CURSO_NOMBRE, curso.nombre)
            put(COL_CURSO_CODIGO, curso.codigo)
            put(COL_CURSO_SALON, curso.salon)
            put(COL_CURSO_PABELLON, curso.pabellon)
            put(COL_CURSO_NOMBRE_PROFESOR, curso.nombreProfesor)
            put(COL_CURSO_CORREO_PROFESOR, curso.correoProfesor)
            put(COL_CURSO_ESTUDIANTES, curso.listaEstudiantes.joinToString(","))
            put(COL_CURSO_ASISTENCIAS, curso.asistencias)
            put(COL_CURSO_CANTIDAD_ALUMNOS, curso.cantidadAlumnos)
            put(COL_CURSO_HORARIO, curso.horario)
            put(COL_CURSO_DIA, curso.dia)
            put(COL_CURSO_HORA, curso.hora)
        }
        val result = db.insert(TABLE_CURSOS, null, values)
        return result != -1L
    }

    fun getAllCursos(): List<Curso> {
        val cursos = mutableListOf<Curso>()
        val db = readableDatabase
        val cursor = db.query(TABLE_CURSOS, null, null, null, null, null, null)
        if (cursor.moveToFirst()){
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_CURSO_ID))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_NOMBRE))
                val codigo = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_CODIGO))
                val salon = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_SALON))
                val pabellon = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_PABELLON))
                val nombreProfesor = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_NOMBRE_PROFESOR))
                val correoProfesor = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_CORREO_PROFESOR))
                val estudiantesStr = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_ESTUDIANTES))
                val asistencias = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_ASISTENCIAS))
                val cantidadAlumnos = cursor.getInt(cursor.getColumnIndexOrThrow(COL_CURSO_CANTIDAD_ALUMNOS))
                val horario = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_HORARIO))
                val dia = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_DIA))
                val hora = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_HORA))
                val estudiantes = if(estudiantesStr.isNotEmpty()) estudiantesStr.split(",") else listOf()
                cursos.add(Curso(id, nombre, codigo, salon, pabellon, nombreProfesor, correoProfesor, estudiantes, asistencias, cantidadAlumnos, horario, dia, hora))
            } while(cursor.moveToNext())
        }
        cursor.close()
        return cursos
    }

    fun getCursosForDocente(email: String): List<Curso> {
        val cursos = mutableListOf<Curso>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_CURSOS,
            null,
            "$COL_CURSO_CORREO_PROFESOR = ?",
            arrayOf(email),
            null,
            null,
            null
        )
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_CURSO_ID))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_NOMBRE))
                val codigo = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_CODIGO))
                val salon = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_SALON))
                val pabellon = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_PABELLON))
                val nombreProfesor = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_NOMBRE_PROFESOR))
                val correoProfesor = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_CORREO_PROFESOR))
                val estudiantesStr = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_ESTUDIANTES))
                val asistencias = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_ASISTENCIAS))
                val cantidadAlumnos = cursor.getInt(cursor.getColumnIndexOrThrow(COL_CURSO_CANTIDAD_ALUMNOS))
                val horario = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_HORARIO))
                val dia = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_DIA))
                val hora = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_HORA))
                val estudiantes = if(estudiantesStr.isNotEmpty()) estudiantesStr.split(",") else listOf()
                cursos.add(Curso(id, nombre, codigo, salon, pabellon, nombreProfesor, correoProfesor, estudiantes, asistencias, cantidadAlumnos, horario, dia, hora))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return cursos
    }

    fun getCursosForAlumno(email: String): List<Curso> {
        val cursos = mutableListOf<Curso>()
        val db = readableDatabase
        // Buscamos el email dentro del campo de estudiantes
        val cursor = db.query(
            TABLE_CURSOS,
            null,
            "$COL_CURSO_ESTUDIANTES LIKE ?",
            arrayOf("%$email%"),
            null,
            null,
            null
        )
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_CURSO_ID))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_NOMBRE))
                val codigo = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_CODIGO))
                val salon = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_SALON))
                val pabellon = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_PABELLON))
                val nombreProfesor = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_NOMBRE_PROFESOR))
                val correoProfesor = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_CORREO_PROFESOR))
                val estudiantesStr = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_ESTUDIANTES))
                val asistencias = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_ASISTENCIAS))
                val cantidadAlumnos = cursor.getInt(cursor.getColumnIndexOrThrow(COL_CURSO_CANTIDAD_ALUMNOS))
                val horario = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_HORARIO))
                val dia = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_DIA))
                val hora = cursor.getString(cursor.getColumnIndexOrThrow(COL_CURSO_HORA))
                val estudiantes = if(estudiantesStr.isNotEmpty()) estudiantesStr.split(",") else listOf()
                cursos.add(Curso(id, nombre, codigo, salon, pabellon, nombreProfesor, correoProfesor, estudiantes, asistencias, cantidadAlumnos, horario, dia, hora))
            } while(cursor.moveToNext())
        }
        cursor.close()
        return cursos
    }

    fun deleteCurso(cursoId: Int): Boolean {
        val db = writableDatabase
        val rowsAffected = db.delete(TABLE_CURSOS, "$COL_CURSO_ID = ?", arrayOf(cursoId.toString()))
        return rowsAffected > 0
    }

    fun updateCursoAssignment(
        cursoId: Int,
        docente: Docente,
        cantidadAlumnos: Int,
        horario: String,
        salon: String,
        alumno: Alumno,
        dia: String,
        hora: String
    ): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_CURSO_NOMBRE_PROFESOR, docente.nombre)
            put(COL_CURSO_CORREO_PROFESOR, docente.email)
            put(COL_CURSO_CANTIDAD_ALUMNOS, cantidadAlumnos)
            put(COL_CURSO_HORARIO, horario)
            put(COL_CURSO_SALON, salon)
            // Guardamos el email del alumno para filtrar en la vista de cursos del alumno
            put(COL_CURSO_ESTUDIANTES, alumno.email)
            put(COL_CURSO_DIA, dia)
            put(COL_CURSO_HORA, hora)
        }
        val rowsAffected = db.update(TABLE_CURSOS, values, "$COL_CURSO_ID = ?", arrayOf(cursoId.toString()))
        return rowsAffected > 0
    }

    fun actualizarCursoDetalle(cursoId: Int, nuevoSalon: String, nuevoMaxAlumnos: Int): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_CURSO_SALON, nuevoSalon)
            put(COL_CURSO_CANTIDAD_ALUMNOS, nuevoMaxAlumnos)
        }
        val rowsAffected = db.update(TABLE_CURSOS, values, "$COL_CURSO_ID = ?", arrayOf(cursoId.toString()))
        db.close()
        return rowsAffected > 0
    }

    // MÉTODOS PARA NOTIFICACIONE

    fun insertNotificacion(docenteEmail: String, mensaje: String, cursoId: Int? = null): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NOTIF_DOCENTE_EMAIL, docenteEmail)
            put(COL_NOTIF_MENSAJE, mensaje)
            if (cursoId != null) {
                put(COL_NOTIF_CURSO_ID, cursoId)
            }
            put(COL_NOTIF_LEIDO, 0)  // 0 = no leída
        }
        val result = db.insert(TABLE_NOTIFICACIONES, null, values)
        return result != -1L
    }

    fun getNotificacionesByEmail(email: String): List<Notificacion> {
        val notificaciones = mutableListOf<Notificacion>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NOTIFICACIONES,
            null,
            "$COL_NOTIF_DOCENTE_EMAIL = ?",
            arrayOf(email),
            null,
            null,
            null
        )
        if (cursor.moveToFirst()){
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_NOTIF_ID))
                val docenteEmail = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOTIF_DOCENTE_EMAIL))
                val mensaje = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOTIF_MENSAJE))
                val cursoId = if (!cursor.isNull(cursor.getColumnIndexOrThrow(COL_NOTIF_CURSO_ID)))
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_NOTIF_CURSO_ID)) else null
                val leido = if (!cursor.isNull(cursor.getColumnIndexOrThrow(COL_NOTIF_LEIDO)))
                    (cursor.getInt(cursor.getColumnIndexOrThrow(COL_NOTIF_LEIDO)) == 1) else false
                notificaciones.add(Notificacion(id, docenteEmail, mensaje, cursoId, leido))
            } while(cursor.moveToNext())
        }
        cursor.close()
        return notificaciones
    }

    fun deleteNotificacion(notifId: Int): Boolean {
        val db = writableDatabase
        val rowsAffected = db.delete(TABLE_NOTIFICACIONES, "$COL_NOTIF_ID = ?", arrayOf(notifId.toString()))
        return rowsAffected > 0
    }

    fun updateNotificacionLeida(notifId: Int, leido: Boolean): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NOTIF_LEIDO, if (leido) 1 else 0)
        }
        val rowsAffected = db.update(TABLE_NOTIFICACIONES, values, "$COL_NOTIF_ID = ?", arrayOf(notifId.toString()))
        return rowsAffected > 0
    }
}





