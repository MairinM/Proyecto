package com.mexiti.cronoapp.ui.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mexiti.cronoapp.viewmodel.ComponenteDataViewModel
import com.mexiti.cronoapp.viewmodel.ComponenteViewModel
import androidx.compose.material3.Badge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    navController: NavController,
    componenteVM: ComponenteViewModel,
    dataVM: ComponenteDataViewModel
) {
    val categoriasDisponibles = listOf("Todos", "Sensores", "Placas", "Herramientas", "Semiconductores")
    val componentes by dataVM.componenteList.collectAsState()
    val state = componenteVM.state
    val context = LocalContext.current

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(Color(0xFF232F3E))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Chopo Electronics", color = Color.White, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

                    Row {
                        // Botón de Carrito
                        IconButton(onClick = { componenteVM.toggleCarrito(true) }) {
                            BadgeBox(carritoSize = componenteVM.carrito.size)
                        }
                        // Botón de Agregar Componente
                        IconButton(onClick = {
                            componenteVM.limpiarEstado()
                            navController.navigate("AddView")
                        }) {
                            Icon(Icons.Filled.Add, contentDescription = "Agregar", tint = Color.White)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Buscador oculto que puedes usar presionando la lupa (Act 2)
                OutlinedTextField(
                    value = state.idBusqueda,
                    onValueChange = { componenteVM.onIdBusquedaChange(it) },
                    placeholder = { Text("Buscar por ID o presiona aquí si la BD está vacía ->") },
                    trailingIcon = {
                        IconButton(onClick = { componenteVM.poblarBaseDeDatos() }) {
                            Icon(Icons.Filled.Search, contentDescription = "Poblar")
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White, unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color(0xFFFF9900), unfocusedBorderColor = Color.Transparent
                    ),
                    singleLine = true
                )
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).fillMaxSize().background(Color(0xFFEAEDED))) {

            // Fila de Categorías
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categoriasDisponibles) { cat ->
                    FilterChip(
                        selected = cat == state.categoriaSeleccionada,
                        onClick = { componenteVM.onCategoriaSeleccionada(cat) },
                        label = { Text(cat) }
                    )
                }
            }

            // Filtro lógico: Muestra "Todos" o solo los de la categoría elegida
            val listaFiltrada = if (state.categoriaSeleccionada == "Todos") {
                componentes
            } else {
                componentes.filter { it.categoria == state.categoriaSeleccionada }
            }

            LazyVerticalGrid(
                columns = GridCells.Adaptive(160.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(listaFiltrada) { componente ->
                    ElevatedCard(
                        onClick = { navController.navigate("EditView/${componente.id}") },
                        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            AsyncImage(
                                model = componente.imageUrl,
                                contentDescription = null,
                                contentScale = ContentScale.Crop, // Crop ajusta la foto al marco
                                modifier = Modifier.fillMaxWidth().height(120.dp).clip(RoundedCornerShape(8.dp))
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Text(componente.nombre, color = Color.Black, style = MaterialTheme.typography.bodyMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
                            Text("$${componente.precioBase}", color = Color.Black, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = {
                                    componenteVM.agregarAlCarrito(componente)
                                    Toast.makeText(context, "Agregado al carrito", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.fillMaxWidth().height(36.dp),
                                shape = RoundedCornerShape(50),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD814), contentColor = Color.Black),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text("Agregar al carrito", style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }
                }
            }
        }

        // Diálogo del Carrito de Compras
        if (state.mostrarCarrito) {
            AlertDialog(
                onDismissRequest = { componenteVM.toggleCarrito(false) },
                title = { Text("Carrito de Compras") },
                text = {
                    if (componenteVM.carrito.isEmpty()) {
                        Text("El carrito está vacío")
                    } else {
                        val total = componenteVM.carrito.sumOf { it.precioBase }
                        Column {
                            componenteVM.carrito.forEach { item ->
                                Text("- ${item.nombre}: $${item.precioBase}")
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Total: $$total", fontWeight = FontWeight.Bold)
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        componenteVM.vaciarCarrito()
                        componenteVM.toggleCarrito(false)
                    }) {
                        Text("Comprar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { componenteVM.toggleCarrito(false) }) {
                        Text("Cerrar")
                    }
                }
            )
        }
    }
}

// Icono con contador para el carrito
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BadgeBox(carritoSize: Int) {
    Box {
        Icon(Icons.Outlined.ShoppingCart, contentDescription = "Carrito", tint = Color.White)
        if (carritoSize > 0) {
            Badge(
                containerColor = Color.Red,
                contentColor = Color.White,
                modifier = Modifier.align(Alignment.TopEnd).offset(x = 10.dp, y = (-5).dp)
            ) {
                Text(carritoSize.toString())
            }
        }
    }
}