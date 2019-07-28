package movil
import inmovil._
import ciudad.Interseccion
import ciudad.Via
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Set
import scalax.collection.edge.WLDiEdge

case class Vehiculo(var placa:String = "")(pos: Punto, vel: Velocidad) extends Movil(pos, vel) with MovimientoUniforme {

  private var _detenido = false

  def detenido = _detenido
  def detenido_=(detenido: Boolean) = _detenido = detenido
  



}

object Vehiculo {

  val setVehiculos:Set[Vehiculo] = Set()

  val pCarros = 0.4
  val pMotos = 0.3 + pCarros
  val pBuses = 0.15 + pMotos
  val pCamiones = 0.1 + pBuses
  val pMotoTaxis = 0.05 + pCamiones

  def apply(pos: Punto, vel: Velocidad): Vehiculo = {
    val r = new scala.util.Random
    val numeroAleatorio = r.nextDouble()
    

    if (numeroAleatorio <= pCarros) {
      return new Carro(pos, vel)

    } else if (numeroAleatorio <= pMotos) {
      return new Moto(pos, vel)

    } else if (numeroAleatorio <= pBuses) {
      return new Bus(pos, vel)

    } else if (numeroAleatorio <= pCamiones) {
      return new Camion(pos, vel)

    } else {
      return new MotoTaxi(pos, vel)
      

    }

  }



}