package movil
import inmovil._
class MotoTaxi(p:Punto, v : Velocidad) extends Vehiculo()(p,v){
  this.placa = this.generarPlacaAleatoria()
  Vehiculo.setVehiculos += this
  def generarPlacaAleatoria():String={
    "PLACA DEL MOTO TAXI ALEATORIA"
  }
}