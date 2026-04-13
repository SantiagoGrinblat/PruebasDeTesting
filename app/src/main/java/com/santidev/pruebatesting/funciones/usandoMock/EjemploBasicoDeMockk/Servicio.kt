package com.santidev.pruebatesting.funciones.usandoMock.EjemploBasicoDeMockk

class Servicio {
  fun obtenerMensaje(): String {
    return "Hola desde el servidor :D"
  }
}

class MiClase(private val servicio: Servicio) {
  fun procesarSaludo(): String {
    return servicio.obtenerMensaje()
  }
}
