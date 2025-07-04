package com.example.t1_aplicativos_grupo8.db

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "nombre_de_tu_db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
        return INSTANCE!!
    }
}
