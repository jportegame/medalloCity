package movil
import inmovil._
class Camion(p:Punto, v : Velocidad) extends Vehiculo()(p,v){
  this.placa = this.generarPlacaAleatoria()
  
  def generarPlacaAleatoria():String={
    "PLACA DEL CAMION ALEATORIA"
  }
}