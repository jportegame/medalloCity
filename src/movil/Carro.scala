package movil
import inmovil._
class Carro(p:Punto, v : Velocidad) extends Vehiculo()(p,v){
  this.placa = this.generarPlacaAleatoria()

  def generarPlacaAleatoria():String={
    "PLACA DEL CARRO ALEATORIA"
  }
}