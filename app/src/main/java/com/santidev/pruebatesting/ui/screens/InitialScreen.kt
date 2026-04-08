package com.santidev.pruebatesting.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.santidev.pruebatesting.funciones.Calculadora
import com.santidev.pruebatesting.funciones.ListasCarrito
import com.santidev.pruebatesting.funciones.ValidarTexto

@Composable
fun InitialScreen() {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
//    val calculadora = Calculadora()
//    val establecido1 = 8
//    val establecido2 = 6
//
//    Column {
//      Text(text = "Resultado suma: ${calculadora.sumar(establecido1, establecido2)}")
//      Spacer(modifier = Modifier.height(6.dp))
//      Text(text = "Resultado resta: ${calculadora.restar(establecido1, establecido2)}")
//      Spacer(modifier = Modifier.height(6.dp))
//      Text(text = "Resultado multiplo: ${calculadora.multiplicar(establecido1, establecido2)}")
//      Spacer(modifier = Modifier.height(6.dp))
//      Text(text = "Resultado division: ${calculadora.dividir(establecido1, establecido2)}")
//    }

//    val validador = ValidarTexto()
//
//    Text(text = "Nombre válido: ${validador.validarNombre(nombre = "Juan")}")
//    Spacer(modifier = Modifier.height(6.dp))
//    Text(text = "Nombre vacío: ${validador.validarNombre(nombre = "")}")
//    Spacer(modifier = Modifier.height(6.dp))
//    Text(text = "Email válido: ${validador.validarEmail(email = "juan@gmail.com")}")
//    Spacer(modifier = Modifier.height(6.dp))
//    Text(text = "Email sin @: ${validador.validarEmail(email = "juangmail.com")}")
//    Spacer(modifier = Modifier.height(6.dp))
//    Text(text = "Password válida: ${validador.validarPassword(password = "abc123")}")
//    Spacer(modifier = Modifier.height(6.dp))
//    Text(text = "Password corta: ${validador.validarPassword(password = "ab")}")
//    Spacer(modifier = Modifier.height(6.dp))
//    Text(text = "Edad válida: ${validador.validarEdad(edad = 25)}")
//    Spacer(modifier = Modifier.height(6.dp))
//    Text(text = "Edad negativa: ${validador.validarEdad(edad = -1)}")
    
    val lista = ListasCarrito()
    
    Text(text = "Nombre producto: ${lista.agregarProducto(producto = "Mesa")}")
    Spacer(modifier = Modifier.height(6.dp))
    Text(text = "Nombre vacío: ${lista.eliminarProducto("")}")
    Spacer(modifier = Modifier.height(6.dp))
    Text(text = "total producto: ${lista.calcularTotal(listOf(30.8, 20.5))}")
    Spacer(modifier = Modifier.height(6.dp))
    Text(text = "Email sin @: ${lista.estaVacio()}")
    Spacer(modifier = Modifier.height(6.dp))
    Text(text = "Password válida: ${lista.cantidadDeProductos()}")
  }
}