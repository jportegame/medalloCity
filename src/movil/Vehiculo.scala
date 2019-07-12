package movil
import inmovil._
class Vehiculo(val pos:Punto, val vel : Velocidad, val placa:String) extends Movil(pos,vel) with MovimientoUniforme {
  
}