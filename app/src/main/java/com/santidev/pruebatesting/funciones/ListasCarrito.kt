package com.santidev.pruebatesting.funciones

class ListasCarrito {
  fun agregarProducto(producto: String): List<String> {
    return listOf(producto)
  }
  fun eliminarProducto(producto: String): List<String> {
    return emptyList()
  }
  fun calcularTotal(precios: List<Double>): Double {
    return precios.sum()
  }
  fun estaVacio(): Boolean {
    return true
  }
  fun cantidadDeProductos(): Int {
    return 0
  }
}