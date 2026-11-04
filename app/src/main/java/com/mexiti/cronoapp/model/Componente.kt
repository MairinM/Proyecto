package com.mexiti.cronoapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "componentes")
data class Componente(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String,
    val categoria: String, // Nueva columna
    val precioBase: Double,
    val precioMayoreo: Double,
    val imageUrl: String
)