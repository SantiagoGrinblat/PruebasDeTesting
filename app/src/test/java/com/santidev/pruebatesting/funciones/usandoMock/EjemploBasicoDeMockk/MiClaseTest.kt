package com.santidev.pruebatesting.funciones.usandoMock.EjemploBasicoDeMockk

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class MiClaseTest {
  
  private lateinit var servicioMock: Servicio
  private lateinit var miClase: MiClase
  
  @Before
  fun funcionDeClases() {
    servicioMock = mockk()
    miClase = MiClase(servicioMock)
  }
  
  @Test
  fun `procesar que devuelve el mensaje del servidor`() {
    //Arrange
    //servicioMock = <- clase dentro del before, por si solo no hace nada
    //no es necesario ponerlo, pero pueder ser mas didactico que este en pantalla aunque no haga nada
    //cuando es buena idea ponerlo en el Arrange?
    //solo es necesario (y recomendado) ponerlo en el Arrange si vas a especificar un comportamiento distinto al que definiste por defecto en el before.
    
    
    //MockK en si es una libreria que crea un objeto falso de la clase que le indicas
    //mockk = es una funcion, entre los ( <> ) les decis que CLASE queres simular y se ejecuta como una funcion
    
    every { servicioMock.obtenerMensaje() } returns "Hola simulado"
    //every Es una funcion de MockK que dice "cuando llamen a este metodo, hace esto":
    //dentro de las llaves pongo la funcion que quiero simular
    //returns devuelve el valor que quiero que devuelva
    //importante! returns (lleva: S), porque es una funcion de mockk no la palabra clave de kotlin.
    //en Kotlin, return y throw son palabras reservadas del lenguaje.
    //por eso MockK usa returns y throws (en plural) para sus funciones.
    
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
    
    every { servicioMock.obtenerMensaje() } throws Exception("Error de conexion")
    //cuando llamo al metodo, simulo un error de conexion
    //assertThrows verifica que ese error ocurra correctamente
    
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
    
    every { servicioMock.obtenerMensaje() } returns "Hola mundo"
    //importante recordar usar el returns, si NO lo ponemos el error sera este =
    //no answer provided for Servicio(#1).obtenerMensaje(). el error te esta diciendo:
    //"Loco, me pediste que use este servicio falso, pero no me dijiste que responder cuando le preguntan por el mensaje".
    
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
    
    every { servicioMock.obtenerMensaje() } returns "Hola mundo"
    
    miClase.procesarSaludo()
    
    verify(atLeast = 1) { servicioMock.obtenerMensaje() }
    //atLeast = quiero que se ejecute un minimo de 1 vez
    //si intento 0 veces -> falla
    //si intento 1, 2 o 3 veces -> pasa
  }
  
  //atMost -> maximo de veces:
  @Test
  fun `verificamos que se ejecute como maximo 3 veces`() {
    
    every { servicioMock.obtenerMensaje() } returns "Hola mundo por 2"
    
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
  fun `verificamos que el metodo se ejecute entre 1 y 3 veces`() {
    
    every { servicioMock.obtenerMensaje() } returns "Hola mundo por 3"
    
    miClase.procesarSaludo()
    miClase.procesarSaludo()
    
    verify(atLeast = 1, atMost = 3) { servicioMock.obtenerMensaje() }
    //quiero que se ejecute un minimo de 1 vez y que sea un maximo de 3 veces
    //se llamo entre 1 y 3 veces -> pasa
    //se llamo 0 o 4 veces -> falla
    //dato extra: si intentas 0 veces, atMost = 3 tambien pasa (porque cero es menor que tres).
    //si queres que se llame al menos una vez pero maximo tres, usas la combinacion de atLeast y atMost.
  }
  
  //Relaxed =
  @Test
  fun `verificamos que devuelva un mensaje`() {
    servicioMock = mockk<Servicio>(relaxed = true) //el problema que resuelve relaxed:
    //cuando creas un mock normal, MockK es estricto. si no declaras every para cada metodo, explota
    //osea que literalmente relaxed le dice al codigo, relajate y deja que pase,
    //pero no va a pasar solo, lo voy a pasar con los valores minimos para que no se rompa
    //si no declaras every mientas usas relaxed -> devuelve valores minimos por defecto:
    //String = "", Int = 0, Boolean = false, List = emptyList()
    //Dato extra: Esto es muy util cuando tenes interfaces grandes y solo te interesa testear un metodo especifico.
    //ignorando todos los demas llamados secundarios.
    every { servicioMock.obtenerMensaje() } returns "Hola mundo desde el server"
    
    miClase = MiClase(servicioMock)
    //cuando hacemos el llamado en relaxed en el nuevo servicioMock, tenemos que tambien agregar la clase.
    //si mas tarde en el test creas un nuevo mock con mockk(relaxed = true), ese es un objeto distinto en la memoria.
    //porque en el before lo estoy configurando de manera estricta sin relaxed
    //si no actualizas miClase, esta seguira apuntando al mock viejo (el del before), y por eso el test falla.
    
    val resultado = miClase.procesarSaludo()
    
    assertEquals("Hola mundo desde el server", resultado)
    verify(exactly = 1) { servicioMock.obtenerMensaje() }
    
    //relaxed NO reemplaza a every.
    //usar relaxed cuando:
    //no te importa el valor de retorno, solo queres verificar llamadas (verify)
    //usae every cuando:
    //necesitas controlar el resultado, el valor afecta la logica
  }
}
