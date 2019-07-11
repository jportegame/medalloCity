package movil
import inmovil._
abstract class Movil(var posicion: Punto, var velocidad:Velocidad) {
  
  def anguloDireccion(): Double={
    velocidad.direccion.valor
  }
  def avance(dt:Double):Unit
}