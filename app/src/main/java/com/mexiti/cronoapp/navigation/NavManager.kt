package com.mexiti.cronoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mexiti.cronoapp.ui.views.AddView
import com.mexiti.cronoapp.ui.views.EditView
import com.mexiti.cronoapp.ui.views.HomeView
import com.mexiti.cronoapp.viewmodel.ComponenteDataViewModel
import com.mexiti.cronoapp.viewmodel.ComponenteViewModel

@Composable
fun NavManager(componenteVM: ComponenteViewModel, dataVM: ComponenteDataViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Home") {

        // Ruta 1: Pantalla Principal (Catálogo Amazon)
        composable("Home") {
            HomeView(navController = navController, componenteVM = componenteVM, dataVM = dataVM)
        }

        // Ruta 2: Pantalla para Agregar
        composable("AddView") {
            AddView(navController = navController, componenteVM = componenteVM, dataVM = dataVM)
        }

        // Ruta 3: Pantalla para Editar (Recibe el ID por parámetro)
        composable(
            route = "EditView/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            // Usamos backStackEntry explícitamente para evitar confusiones del compilador
            val id = backStackEntry.arguments?.getLong("id") ?: 0L
            EditView(navController = navController, componenteVM = componenteVM, dataVM = dataVM, id = id)
        }
    }
}