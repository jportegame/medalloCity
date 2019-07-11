package inmovil
import scala.collection.mutable.ArrayBuffer
import movil.Vehiculo
import ciudad._
object Simulacion extends Runnable {
  var t:Double = 0
  var dt:Double = 0
  var tRefresh = 0
  val minVeh = 0
  val maxVeh = 0
  val minVel = 0
  val maxVel = 0
  
  
  var listaVehiculos = ArrayBuffer.empty[Vehiculo]
  var listaVias = ArrayBuffer.empty[Via]
  var listaDestinos = ArrayBuffer.empty[VehiculoDestino]
  var hilo: Thread = _
  
  
  def run() {
  while (true) {
   listadevehiculosOSimilar.foreach(_.mover(dt))
   t += dt
   Grafico.graficarVehiculos(listadevehiculosOSimilar)
   Thread.sleep(tRefresh)
   }
  }
  
}