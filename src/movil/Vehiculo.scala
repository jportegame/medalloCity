package movil
import inmovil._
class Vehiculo(var pos:Punto, var vel : Velocidad, var placa:String) extends Movil(pos,vel) with MovimientoUniforme {
  
}