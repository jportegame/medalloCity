package movil
import inmovil._
import scala.collection.mutable.Set

case class Vehiculo(var placa: String = "")(pos: Punto, vel: Velocidad) extends Movil(pos, vel) with MovimientoUniforme {

  private var _detenido = false

  def detenido = _detenido
  def detenido_=(detenido: Boolean) = _detenido = detenido

}

object Vehiculo {

  val setPlacas: Set[String] = Set()

  val pCarros: Double = 0.4 //Simulacion.parametros.pametrosSimulacion.proporciones.carros
  val pMotos: Double = 0.3 + pCarros //Simulacion.parametros.pametrosSimulacion.proporciones.motos + pCarros
  val pBuses: Double = 0.15 + pMotos //Simulacion.parametros.pametrosSimulacion.proporciones.buses + pMotos
  val pCamiones: Double = 0.1 + pBuses //Simulacion.parametros.pametrosSimulacion.proporciones.camiones + pBuses
  val pMotoTaxis: Double = 0.05 + pCamiones //Simulacion.parametros.pametrosSimulacion.proporciones.motoTaxis + pCamiones

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