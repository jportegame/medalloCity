package movil
import inmovil._
abstract class Movil(val posicion: Punto, val velocidad:Velocidad) {
  
  def anguloDireccion(): Double={
    velocidad.direccion.valor
  }
  def avance(dt:Double):Unit
}