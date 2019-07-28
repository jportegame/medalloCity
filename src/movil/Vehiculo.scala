package movil
import inmovil._
import ciudad.Interseccion
import ciudad.Via
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Queue
import scalax.collection.edge.WLDiEdge

class Vehiculo(pos: Punto, vel: Velocidad, val recorrido: Queue[Via], val interseccionesRecorrido: Queue[Interseccion]) extends Movil(pos, vel) with MovimientoUniforme {
  private var _placa: String = ""
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
        if (!interseccionesRecorrido.isEmpty) {
          this.viaActual = recorrido.dequeue()
          this.interseccionDestino = interseccionesRecorrido.dequeue()
          if (this.velocidad.magnitud > viaActual.velMaxima) {
            this.velocidad.magnitud = viaActual.velMaxima
          }
          velocidad.anguloYSentidoEntreDosPuntos(this.posicion, this.interseccionDestino)
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

    val interseccionesRecorrido = Vehiculo.toQueue(camino.get.nodes.map(_.value).toList)
    val viasRecorrido = Vehiculo.toQueue(camino.get.edges.map(_.label.asInstanceOf[Via]).toList)
    val viaInicial = viasRecorrido.head
    var magnitudVelocidadAleatoria = (r.nextDouble() * (Simulacion.maxVelocidad - Simulacion.minVelocidad)) + Simulacion.minVelocidad

    println("Velocidad incial 1:" + magnitudVelocidadAleatoria) //Borrar
    if (magnitudVelocidadAleatoria > viaInicial.velMaxima) {
      magnitudVelocidadAleatoria = viaInicial.velMaxima
    }
    println("Velocidad incial 2:" + magnitudVelocidadAleatoria) //Borrar
    magnitudVelocidadAleatoria = Velocidad.conversorKmHorAMetroSeg(magnitudVelocidadAleatoria)
    println("Velocidad incial 3: " + magnitudVelocidadAleatoria) //Borrar

    println("Origen conceptual:" + origen) //Borrar
    println("Destino conceputal:" + destino) //Borrar
    println("Intersecciones: " + interseccionesRecorrido) //Borrar
    println("Vias: " + viasRecorrido) //Borrar

    interseccionesRecorrido.dequeue()
    val interseccionInicial = interseccionesRecorrido.head
    val puntoOrigen = new Punto(origen.x, origen.y)

    if (numeroAleatorio <= pCarros) {
      val vehiculo = new Carro(puntoOrigen, new Velocidad(magnitudVelocidadAleatoria), viasRecorrido, interseccionesRecorrido)
      vehiculo.velocidad.anguloYSentidoEntreDosPuntos(origen, interseccionInicial)
      return vehiculo
    } else if (numeroAleatorio <= pMotos) {
      val vehiculo = new Moto(puntoOrigen, new Velocidad(magnitudVelocidadAleatoria), viasRecorrido, interseccionesRecorrido)
      vehiculo.velocidad.anguloYSentidoEntreDosPuntos(origen, interseccionInicial)
      return vehiculo
    } else if (numeroAleatorio <= pBuses) {
      val vehiculo = new Bus(puntoOrigen, new Velocidad(magnitudVelocidadAleatoria), viasRecorrido, interseccionesRecorrido)
      vehiculo.velocidad.anguloYSentidoEntreDosPuntos(origen, interseccionInicial)
      return vehiculo
    } else if (numeroAleatorio <= pCamiones) {
      val vehiculo = new Camion(puntoOrigen, new Velocidad(magnitudVelocidadAleatoria), viasRecorrido, interseccionesRecorrido)
      vehiculo.velocidad.anguloYSentidoEntreDosPuntos(origen, interseccionInicial)
      return vehiculo
    } else {
      val vehiculo = new MotoTaxi(puntoOrigen, new Velocidad(magnitudVelocidadAleatoria), viasRecorrido, interseccionesRecorrido)
      vehiculo.velocidad.anguloYSentidoEntreDosPuntos(origen, interseccionInicial)
      return vehiculo
    }

  }

  def toQueue[T](L: List[T]): Queue[T] = {
    val Q = new Queue[T]();
    L.foreach(f => Q += f)
    Q

  }

}