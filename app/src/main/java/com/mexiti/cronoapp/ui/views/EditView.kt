package com.mexiti.cronoapp.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mexiti.cronoapp.model.Componente
import com.mexiti.cronoapp.ui.components.MainIconButton
import com.mexiti.cronoapp.ui.components.MainTitle
import com.mexiti.cronoapp.viewmodel.ComponenteDataViewModel
import com.mexiti.cronoapp.viewmodel.ComponenteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditView(
    navController: NavController,
    componenteVM: ComponenteViewModel,
    dataVM: ComponenteDataViewModel,
    id: Long
) {
    LaunchedEffect(Unit) { componenteVM.cargarComponente(id) }
    val state = componenteVM.state

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { MainTitle(title = "Editar Componente") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = MaterialTheme.colorScheme.onPrimary),
                navigationIcon = { MainIconButton(icon = Icons.Default.ArrowBack) { navController.popBackStack() } }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).padding(16.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(value = state.nombre, onValueChange = { componenteVM.onNombreChange(it) }, label = { Text("Nombre del componente") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(value = state.categoria, onValueChange = { componenteVM.onCategoriaChange(it) }, label = { Text("Categoría (ej. Sensores, Placas)") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(value = state.precioBase, onValueChange = { componenteVM.onPrecioBaseChange(it) }, label = { Text("Precio Base") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(value = state.precioMayoreo, onValueChange = { componenteVM.onPrecioMayoreoChange(it) }, label = { Text("Precio Mayoreo") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(value = state.imageUrl, onValueChange = { componenteVM.onImageUrlChange(it) }, label = { Text("URL de la Imagen") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val pBase = state.precioBase.toDoubleOrNull() ?: 0.0
                    val pMayoreo = state.precioMayoreo.toDoubleOrNull() ?: 0.0
                    dataVM.updateComponente(
                        Componente(id = state.id, nombre = state.nombre, categoria = state.categoria, precioBase = pBase, precioMayoreo = pMayoreo, imageUrl = state.imageUrl)
                    )
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Actualizar Registro")
            }
        }
    }
}