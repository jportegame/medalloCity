package inmovil
import scala.collection.mutable.ArrayBuffer
import movil.Vehiculo
import ciudad._
object Simulacion extends Runnable {
  var t:Double = 0
  var dt:Double = 0
  var tRefresh = 0
  val minVehiculos = 0
  val maxVehiculos = 0
  val minVelocidad = 0
  val maxVelocidad = 0
  
  
  var listaVehiculos = ArrayBuffer.empty[Vehiculo]
  var listaVias = ArrayBuffer.empty[Via]

  var hilo: Thread = _
  
  
  def run() {

  }
  
}