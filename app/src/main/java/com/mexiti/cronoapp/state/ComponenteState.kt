package com.mexiti.cronoapp.state

import com.mexiti.cronoapp.model.Componente

data class ComponenteState(
    val id: Long = 0,
    val nombre: String = "",
    val categoria: String = "", // Para los formularios Add/Edit
    val precioBase: String = "",
    val precioMayoreo: String = "",
    val imageUrl: String = "",

    val componenteConsultado: Componente? = null,
    val idBusqueda: String = "",

    // Controles de UI nuevos
    val categoriaSeleccionada: String = "Todos",
    val mostrarCarrito: Boolean = false
)