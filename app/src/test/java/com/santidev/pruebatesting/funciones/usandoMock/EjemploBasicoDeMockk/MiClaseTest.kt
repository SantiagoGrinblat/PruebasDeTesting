package com.santidev.pruebatesting.funciones.usandoMock.EjemploBasicoDeMockk

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class MiClaseTest {
  
  @Test
  fun `procesar que devuelve el mensaje del servidor`() {
    //Arrange
    val servicioMock = mockk<Servicio>()
    //MockK en si es una libreria que crea un objeto falso de la clase que le indicas
    //mockk = es una funcion, entre los <> les decis que CLASE queres simular y se ejecuta como una funcion
    
    every { servicioMock.obtenerMensaje() } returns "Hola simulado"
    //every Es una funcion de MockK que dice "cuando llamen a este metodo, hace esto":
    //dentro de las llaves pongo la funcion que quiero simular
    //returns devuelve el valor que quiero que devuelva
    //importante returns (lleva: S), porque es una funcion de mockk no la palabra clave de kotlin.
    
    val miClase = MiClase(servicioMock)
    //esto se puede suplantar por un @Before
    
    //Act
    val resultado = miClase.procesarSaludo()
    
    //Assert
    assertEquals("Hola simulado", resultado)
    
    //verify se usa para confirmar que esto (el metodo) fue llamado
    verify { servicioMock.obtenerMensaje() }
    
    //no reemplaza verify a assertEquals.
    //uno dice si se llamo al servicio = verify
    //el otro si el resultado es correcto = assertEquals
    //cuando uso VERIFY ? = para conprobar que los pasos internos se ejecutaron.
    //Ejemplo del banco:
    //verify { sistema.registrar(100.0) } -> se registro el deposito?
    //verify { impresora.imprimirComprobante() } -> se imprimio el comprobante?
    //assertEquals(100.0, cuenta.getSaldo()) -> el saldo es correcto?
    //todo: En un test completo se usa los dos casos
  }
  
  //Hasta ahora usamos verify para simular una respuesta exitosa.
  //Pero en el mundo real tambien necesitas simular errores:
  @Test
  fun `procesar lanza excepcion cuando el servio falla`() {
    val servicioMock = mockk<Servicio>()
    every { servicioMock.obtenerMensaje() } throws Exception("Error de conexion")
    //cuando llamo al metodo, simulo un error de conexion
    //assertThrows verifica que ese error ocurra correctamente
    
    val miClase = MiClase(servicioMock)
    
    assertThrows<Exception> {
      miClase.procesarSaludo()
    }
    
  }
  
  //tambien podemos usar verify para generar varias llamadas.
  //por que importa?
  //imaginate que tenes un metodo que debería llamar al servicio de pago exactamente una vez
  //si lo llama dos veces, estas cobrando dos veces al cliente
  //por eso es importante verificar la cantidad exacta de llamados que hago
  @Test
  fun `verificamos que la cantidad de pago sea exactamente una`() {
    val servicioMock = mockk<Servicio>()
    every { servicioMock.obtenerMensaje() } returns "Hola mundo"
    //importante recordar usar el returns, si NO lo ponemos el error sera este =
    //no answer provided for Servicio(#1).obtenerMensaje() <- Servicio = mi clase, obtenerMensaje = metodo
    val miClase = MiClase(servicioMock)
    
    miClase.procesarSaludo() // exactly = 1
    miClase.procesarSaludo() // + exactly = 2
    //para poder usar en exactitud 2 elementos, tenes que agregrar un segundo elemento.
    //como habiamos echo en el carrito mejorado agregando un seguno producto.

    verify(exactly = 2) { servicioMock.obtenerMensaje() }
    //cuando usar exactly?
    //cuando es critico que un metodo se llame una cantidad especifica de veces
    //ejemplo: un pago no puede procesarse dos veces
  }
  
  //Ahora vamos a probar atLeast y atMost.
  //Ambas son variantes de verify para cuando no se necesita una cantidad exacta sino un rango
  
  //atLeast -> mínimo de veces:
  @Test
  fun `verificamos que se ejecute al menos 1 vez`() {
    val servicioMock = mockk<Servicio>()
    every { servicioMock.obtenerMensaje() } returns "Hola mundo"
    
    val miClase = MiClase(servicioMock)
    
    miClase.procesarSaludo()
    
    verify(atLeast = 1) { servicioMock.obtenerMensaje() }
    //atLeast = quiero que se ejecute un minimo de 1 vez
    //si intento 0 veces -> falla
    //si intento 1, 2 o 3 veces -> pasa
    
  }
  
  //atMost -> maximo de veces:
  @Test
  fun `verificamos que se ejecute como maximo 3 veces`() {
    val servicioMock = mockk<Servicio>()
    every { servicioMock.obtenerMensaje() } returns "Hola mundo por 2"
    
    val miClase = MiClase(servicioMock)
    
    miClase.procesarSaludo()
    miClase.procesarSaludo()
    miClase.procesarSaludo()
    
    verify(atMost = 3) { servicioMock.obtenerMensaje() }
    //atMost = quiero que se ejecute un maximo de 3 veces
    //si intento 4 veces -> falla
    //si intento 1, 2 o 3 veces -> pasa
  }
  
  //Combinados los dos:
  @Test
  fun `verificamos que el metodo se ejecute entre 1 y 3 veces`(){
    val servicioMock = mockk<Servicio>()
    every { servicioMock.obtenerMensaje() } returns "Hola mundo por 3"
    
    val miClase = MiClase(servicioMock)
    
    miClase.procesarSaludo()
    miClase.procesarSaludo()
    
    verify(atLeast = 1, atMost = 3) { servicioMock.obtenerMensaje() }
    //quiero que se ejecute un minimo de 1 vez y que sea un maximo de 3 veces
    //se llamo entre 1 y 3 veces -> pasa
    //se llamo 0 o 4 veces -> falla
  }
}

