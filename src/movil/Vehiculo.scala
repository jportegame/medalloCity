package movil
import inmovil._
import ciudad.Interseccion
import ciudad.Via
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Queue
import scalax.collection.edge.WLDiEdge

class Vehiculo(pos: Punto, vel: Velocidad, var placa: String, val recorrido: Queue[Via], val interseccionesRecorrido: Queue[Interseccion]) extends Movil(pos, vel) with MovimientoUniforme {
  private var _viaActual: Via = recorrido.dequeue()
  private var _interseccionDestino: Interseccion = interseccionesRecorrido.dequeue()
  private var _detenido = false
  Vehiculo.listaVehiculos += this

  def detenido = _detenido
  def detenido_=(detenido: Boolean) = _detenido = detenido

  def viaActual = _viaActual
  def viaActual_=(viaActual: Via) = _viaActual = viaActual

  def interseccionDestino = _interseccionDestino
  def interseccionDestino_=(interseccionDestino: Interseccion) = _interseccionDestino = interseccionDestino

  def mover(dt: Double): Unit = {
    if (!this.detenido) {
      this.avance(dt)
      /*val xViaFin = viaActual.fin.x
      val yViaFin = viaActual.fin.y*/

      val xViaFin = interseccionDestino.x
      val yViaFin = interseccionDestino.y
      val margenError = this.velocidad.magnitud + 5

      if (this.posicion.x > xViaFin - margenError && this.posicion.x < xViaFin + margenError && this.posicion.y > yViaFin - margenError && this.posicion.y < yViaFin + margenError) {
        this.posicion.x = xViaFin
        this.posicion.y = yViaFin
        if (!recorrido.isEmpty) {
          this.viaActual = recorrido.dequeue()
          this.interseccionDestino = interseccionesRecorrido.dequeue()
          this.velocidad.direccion.valor = Vehiculo.anguloEntreDosPuntos(this.posicion, interseccionDestino)
          if (this.velocidad.magnitud > viaActual.velMaxima) {
            this.velocidad.magnitud = viaActual.velMaxima
          }
        } else {
          this.detenido = true
        }
      }
    }
  }
}

object Vehiculo {

  val listaVehiculos = new ArrayBuffer[Vehiculo]

  val pCarros = 0.4
  val pMotos = 0.3 + pCarros
  val pBuses = 0.15 + pMotos
  val pCamiones = 0.1 + pBuses
  val pMotoTaxis = 0.05 + pCamiones

  def apply(): Vehiculo = {
    val r = new scala.util.Random
    val numeroAleatorio = r.nextDouble()
    val origen: Interseccion = GrafoVias.listaDeNodos(r.nextInt(GrafoVias.listaDeNodos.length))
    var destino: Interseccion = GrafoVias.listaDeNodos(r.nextInt(GrafoVias.listaDeNodos.length))
    while (origen == destino) {
      destino = GrafoVias.listaDeNodos(r.nextInt(GrafoVias.listaDeNodos.length))
    }
    val camino = (GrafoVias.n(origen)).shortestPathTo(GrafoVias.n(destino))

    val interseccionesRecorrido = Vehiculo.toQueue(camino.last.nodes.map(_.value).toList)
    val viasRecorrido = Vehiculo.toQueue(camino.last.edges.map(_.label.asInstanceOf[Via]).toList)
    val viaInicial = viasRecorrido.head
    val velocidadMaximaViaInicial:Double = viaInicial.velMaxima
    var magnitudVelocidadAleatoria = (r.nextDouble() * (Simulacion.maxVelocidad - Simulacion.minVelocidad)) + Simulacion.minVelocidad

    println("Velocidad incial 1:" + magnitudVelocidadAleatoria)
    if (magnitudVelocidadAleatoria > velocidadMaximaViaInicial) {
      magnitudVelocidadAleatoria = velocidadMaximaViaInicial
    }
    println("Velocidad incial 2:" + magnitudVelocidadAleatoria)
    magnitudVelocidadAleatoria = Velocidad.conversorKmHorAMetroSeg(magnitudVelocidadAleatoria)
    println("Velocidad incial 3: " + magnitudVelocidadAleatoria)

    println("Origen conceptual:" + origen)
    println("Destino conceputal:" + destino)
    println("Intersecciones: " + interseccionesRecorrido)
    println("Vias: " + viasRecorrido)

    interseccionesRecorrido.dequeue()
    val interseccionInicial = interseccionesRecorrido.head
    val velocidad = new Velocidad(magnitudVelocidadAleatoria, new Angulo(Vehiculo.anguloEntreDosPuntos(origen, interseccionInicial)))
    val puntoOrigen = new Punto(origen.x,origen.y)
    if (numeroAleatorio <= pCarros) {
      return new Carro(puntoOrigen, velocidad, "", viasRecorrido, interseccionesRecorrido)

    } else if (numeroAleatorio <= pMotos) {
      return new Moto(puntoOrigen, velocidad, "", viasRecorrido, interseccionesRecorrido)

    } else if (numeroAleatorio <= pBuses) {
      return new Bus(puntoOrigen, velocidad, "", viasRecorrido, interseccionesRecorrido)

    } else if (numeroAleatorio <= pCamiones) {
      return new Camion(puntoOrigen, velocidad, "", viasRecorrido, interseccionesRecorrido)

    } else {
      return new MotoTaxi(puntoOrigen, velocidad, "", viasRecorrido, interseccionesRecorrido)
    }

  }

  def toQueue[T](L: List[T]): Queue[T] = {
    val Q = new Queue[T]();
    L.foreach(f => Q += f)
    Q

  }

  def anguloEntreDosPuntos(p1: Punto, p2: Punto): Double = {
    val dy = p2.y - p1.y
    val dx = p2.x - p1.x
    val magnitudAngular = math.atan(dy / dx).toDegrees
    magnitudAngular
  }

}