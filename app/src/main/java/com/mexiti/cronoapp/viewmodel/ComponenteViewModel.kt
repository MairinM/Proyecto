package com.mexiti.cronoapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mexiti.cronoapp.model.Componente
import com.mexiti.cronoapp.repository.ComponenteRepository
import com.mexiti.cronoapp.state.ComponenteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComponenteViewModel @Inject constructor(
    private val repository: ComponenteRepository
) : ViewModel() {

    var state by mutableStateOf(ComponenteState())
        private set

    var carrito by mutableStateOf<List<Componente>>(emptyList())
        private set

    fun agregarAlCarrito(componente: Componente) {
        carrito = carrito + componente
    }

    fun vaciarCarrito() {
        carrito = emptyList()
    }

    fun toggleCarrito(mostrar: Boolean) {
        state = state.copy(mostrarCarrito = mostrar)
    }

    fun onCategoriaSeleccionada(categoria: String) {
        state = state.copy(categoriaSeleccionada = categoria)
    }

    fun poblarBaseDeDatos() {
        viewModelScope.launch(Dispatchers.IO) {
            val comp1 = Componente(id = 1, nombre = "Módulo ESP32", categoria = "Placas", precioBase = 120.0, precioMayoreo = 100.0, imageUrl = "https://upload.wikimedia.org/wikipedia/commons/b/b5/ESP32.jpg")
            val comp2 = Componente(id = 2, nombre = "Multímetro Digital", categoria = "Herramientas", precioBase = 350.0, precioMayoreo = 300.0, imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c5/Digital_Multimeter_Aka.jpg/640px-Digital_Multimeter_Aka.jpg")
            val comp3 = Componente(id = 3, nombre = "Compuerta AND 74LS08", categoria = "Semiconductores", precioBase = 15.0, precioMayoreo = 10.0, imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1d/7400.jpg/640px-7400.jpg")
            val comp4 = Componente(id = 4, nombre = "Protoboard", categoria = "Herramientas", precioBase = 85.0, precioMayoreo = 70.0, imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Breadboard.jpg/640px-Breadboard.jpg")
            val comp5 = Componente(id = 5, nombre = "Sensor Ultrasónico HC-SR04", categoria = "Sensores", precioBase = 45.0, precioMayoreo = 35.0, imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/25/HC-SR04.jpg/640px-HC-SR04.jpg")

            repository.addComponente(comp1)
            repository.addComponente(comp2)
            repository.addComponente(comp3)
            repository.addComponente(comp4)
            repository.addComponente(comp5)
        }
    }

    fun consultarComponentePorId() {
        val idLong = state.idBusqueda.toLongOrNull()
        if (idLong != null) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getComponenteById(idLong).collect { item ->
                    state = state.copy(componenteConsultado = item)
                }
            }
        } else {
            state = state.copy(componenteConsultado = null)
        }
    }

    fun onIdBusquedaChange(value: String) { state = state.copy(idBusqueda = value) }
    fun onNombreChange(value: String) { state = state.copy(nombre = value) }
    fun onCategoriaChange(value: String) { state = state.copy(categoria = value) }
    fun onPrecioBaseChange(value: String) { state = state.copy(precioBase = value) }
    fun onPrecioMayoreoChange(value: String) { state = state.copy(precioMayoreo = value) }
    fun onImageUrlChange(value: String) { state = state.copy(imageUrl = value) }
    fun limpiarEstado() { state = ComponenteState() }

    fun cargarComponente(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getComponenteById(id).collect { item ->
                if (item != null) {
                    state = state.copy(
                        id = item.id, nombre = item.nombre, categoria = item.categoria,
                        precioBase = item.precioBase.toString(), precioMayoreo = item.precioMayoreo.toString(),
                        imageUrl = item.imageUrl
                    )
                }
            }
        }
    }
}