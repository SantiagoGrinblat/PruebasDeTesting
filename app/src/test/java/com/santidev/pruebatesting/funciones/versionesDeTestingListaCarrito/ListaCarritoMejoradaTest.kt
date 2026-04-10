package com.santidev.pruebatesting.funciones.versionesDeTestingListaCarrito

import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ListaCarritoMejoradaTest {
  
  //En la version mejorada arrancamos como deberia hacerse un buen test =
  //Patron: AAA = Arrange: Organizar, Act: Actuar, Assert: Afirmar.
  //Before: para ahorrar llamados de funcion base
  //lateinit var: para inicializar la variable mas tarden
  
  lateinit var listaCarritoMejorada: ListaCarritoMejorada
  
  @Before
  fun funcionPrevia() {
    listaCarritoMejorada = ListaCarritoMejorada()
  }
  
  @Test
  fun `agregamos un producto con su nombre y precio`() {
    // Sin @Before = AAA explicito, el Arrange esta adentro del test
    // Con @Before = AAA implicito, el Arrange esta afuera del test
    // El patron no desaparece, solo el Arrange se mueve al @Before
    
    // Arrange implícito = @Before ya inicializo listaCarritoMejorada
    
    // Act - lo que ejecuto
    listaCarritoMejorada.agregarProducto("Mesa", 50.0)
    listaCarritoMejorada.agregarProducto("Silla", 20.0)
    
    // Assert - lo que verifico
    assertEquals(2, listaCarritoMejorada.cantidadDeProductos())
  }
  
  @Test
  fun `verificamos que el total de mesa(50,0) y silla(20,0) sea 70,0`() {
    
    //agrego los productos, porque necesito saber cuales son sus precios para poder hacer el calculo total
    listaCarritoMejorada.agregarProducto("Mesa", 50.0)
    listaCarritoMejorada.agregarProducto("Silla", 20.0)

    val resultado = listaCarritoMejorada.calcularTotal() //<- se puede reemplazar si hace falta.
    //la forma de "guardar" el resultado en una variable, es mas un hecho didactica y de comprension.
    //que necesaria en realidad.
    //tranquilamente podrias eliminar la variable y directamente llamar a calcular total en el assertEquals y estaria bien
    //assertEquals(70.0, listaCarritoMejorada.calcularTotal(), 00.0)
    
    assertEquals(70.0, resultado, 00.0)
  }
  
  @Test
  fun `eliminamos un producto de la lista y verificamos que solo quede 1`() {
    
    listaCarritoMejorada.agregarProducto("Mesa", 50.0)
    listaCarritoMejorada.agregarProducto("Silla", 20.0)

    listaCarritoMejorada.eliminarProducto("Mesa")
    //eliminamos un producto segun su nombre

    assertEquals(1, listaCarritoMejorada.cantidadDeProductos())
    
    //Una de las formas de ver el proceso que hace el test es usar println
    //listaCarritoMejorada.agregarProducto("Mesa", 50.0)
    //println("Después de agregar Mesa: ${listaCarritoMejorada.cantidadDeProductos()} productos")
    //se agrega el primer producto y se guarda internamente en el mutableListOf.
    
    //listaCarritoMejorada.agregarProducto("Silla", 20.0)
    //println("Después de agregar Silla: ${listaCarritoMejorada.cantidadDeProductos()} productos")
    //se agrega el segundo producto y se guarda internamente en el mutableListOf.
    //marcara como que ya hay dos productos
    
    //listaCarritoMejorada.eliminarProducto("Mesa")
    //println("Después de eliminar Mesa: ${listaCarritoMejorada.cantidadDeProductos()} productos")
    //mostrara el elemento que se elimino y cuantos quedan
    
    //assertEquals(1, listaCarritoMejorada.cantidadDeProductos())
    //println("Resultado esperado: 1, Resultado obtenido: ${listaCarritoMejorada.cantidadDeProductos()}")
    //por ultimo muestra el resultado espero y el resultado obtenido.
    //todo: ESTO ES SOLO CON FINES DIDACTICOS - en produccion real no se usa println en los tests
  }
  
  @Test
  fun `verificamos que el carrito este vacio, si lo esta devuelve true`() {
    assertTrue(listaCarritoMejorada.estaVacio())
  }
  
}