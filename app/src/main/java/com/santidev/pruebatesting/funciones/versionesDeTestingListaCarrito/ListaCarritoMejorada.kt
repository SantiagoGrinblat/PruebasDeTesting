package com.santidev.pruebatesting.funciones.versionesDeTestingListaCarrito


class ListaCarritoMejorada {
  
  private val productos = mutableListOf<ProductoVersionMejorada>()
  // se utiliza para crear colecciones ordenadas y redimensionables que permiten agregar, eliminar y actualizar elementos dinamicamente despues de su inicializacion
  
  fun agregarProducto(nombre: String, precio: Double): List<ProductoVersionMejorada> {
    if (nombre.isBlank() || precio <= 0) {
      throw IllegalArgumentException("Mensaje de error: El nombre O el precio son incorrectos")
    }
      productos.add(ProductoVersionMejorada(nombre, precio))
      return productos.toList()
  }
  //se crea un objeto ProductoVersionMejorada con el nombre y precio recibidos
  //y lo agregamos a la lista interna de productos
  //y hacemos que nos retorne la lista de productos. con los elementos ya agregados
  
  fun eliminarProducto(nombre: String): List<ProductoVersionMejorada> {
    if (nombre.isBlank()) {
      throw IllegalArgumentException("El nombre del producto esta vacio")
    }
    if (productos.none {it.nombre == nombre}) {
      throw IllegalArgumentException("No hay un producto con ese nombre")
    }
    productos.removeIf { it.nombre == nombre }
    return productos.toList()
  }
  //eliminamos en producto basandonos en el nombre
  //y retornamos la nueva lista de productos
  
  fun calcularTotal(): Double {
    return productos.sumOf { it.precio }
  }
  //calcularTotal() ya no recibe parametros porque los precios estan dentro de cada Producto.
  //Eso es una mejora real sobre a la version base donde habia que pasar una lista de precios por separado.
  //donde teniamos que crear una nueva lista de precios imaginaria.
  
  fun estaVacio(): Boolean {
    return productos.isEmpty()
  }
  
  fun cantidadDeProductos(): Int {
    return productos.size
  }
  
}