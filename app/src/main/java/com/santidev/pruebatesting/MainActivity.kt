package com.santidev.pruebatesting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.santidev.pruebatesting.ui.screens.InitialScreen
import com.santidev.pruebatesting.ui.theme.PruebaTestingTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      PruebaTestingTheme {
        InitialScreen()
      }
    }
  }
}
