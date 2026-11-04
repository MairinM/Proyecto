package com.mexiti.cronoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mexiti.cronoapp.model.Componente
import com.mexiti.cronoapp.repository.ComponenteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComponenteDataViewModel @Inject constructor(
    private val repository: ComponenteRepository
) : ViewModel() {

    private val _componenteList = MutableStateFlow<List<Componente>>(emptyList())
    val componenteList = _componenteList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllComponentes().collect { item ->
                if (item.isNullOrEmpty()) {
                    _componenteList.value = emptyList()
                } else {
                    _componenteList.value = item
                }
            }
        }
    }

    fun addComponente(componente: Componente) = viewModelScope.launch { repository.addComponente(componente) }
    fun updateComponente(componente: Componente) = viewModelScope.launch { repository.updateComponente(componente) }
    fun deleteComponente(componente: Componente) = viewModelScope.launch { repository.deleteComponente(componente) }
}