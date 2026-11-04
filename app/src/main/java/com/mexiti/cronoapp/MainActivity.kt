package com.mexiti.cronoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.mexiti.cronoapp.navigation.NavManager
import com.mexiti.cronoapp.ui.theme.CronoAppTheme
import com.mexiti.cronoapp.viewmodel.ComponenteDataViewModel
import com.mexiti.cronoapp.viewmodel.ComponenteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inyectamos los ViewModels correctos de la base de datos
        val componenteVM: ComponenteViewModel by viewModels()
        val dataVM: ComponenteDataViewModel by viewModels()

        setContent {
            CronoAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    // Llamamos al NavManager en lugar de a la pantalla directamente
                    NavManager(componenteVM = componenteVM, dataVM = dataVM)
                }
            }
        }
    }
}