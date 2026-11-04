package com.mexiti.cronoapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mexiti.cronoapp.model.Componente

@Database(entities = [Componente::class], version = 1, exportSchema = false)
abstract class ComponenteDataBase : RoomDatabase() {
    abstract fun componenteDao(): ComponenteDatabaseDao
}