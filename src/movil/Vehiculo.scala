package movil
import inmovil._
import ciudad.Interseccion
import ciudad.Via
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Queue
import scalax.collection.edge.WLDiEdge

class Vehiculo(var pos:Punto, var vel : Velocidad, var placa:String,val recorrido:Queue[Via]) extends Movil(pos,vel) with MovimientoUniforme {
  private var viaActual:Via = recorrido.dequeue()
  private var detenido = false
  Vehiculo.listaVehiculos+= this
  
  def mover(dt:Double):Unit = {
    if(!this.detenido){
      this.avance(dt)
      val xViaFin = viaActual.fin.x
      val yViaFin = viaActual.fin.y
      val margenError = this.velocidad.magnitud + 5
      
      if(this.posicion.x > xViaFin-margenError && this.posicion.x < xViaFin+margenError && this.posicion.y > yViaFin-margenError && this.posicion.y < yViaFin+margenError){
        this.posicion.x = xViaFin
        this.posicion.y = yViaFin
        if(!recorrido.isEmpty){
          viaActual = recorrido.dequeue()
          this.velocidad.direccion.valor = Vehiculo.anguloEntreDosPuntos(viaActual.origen, viaActual.fin)
          if(this.velocidad.magnitud > viaActual.velMaxima){
            this.velocidad.magnitud = viaActual.velMaxima
          }
        }else{
          this.detenido = true
        }

      }
    } 
  }
}

object Vehiculo{
  
  val listaVehiculos = new ArrayBuffer[Vehiculo]
  
  val pCarros = 0.4              
  val pMotos =  0.3+pCarros       
  val pBuses = 0.15+pMotos        
  val pCamiones = 0.1+pBuses      
  val pMotoTaxis = 0.05+pCamiones    
  
  
  def apply():Vehiculo={
    val r = new scala.util.Random
    val numeroAleatorio = r.nextDouble()
    val origen:Interseccion = Interseccion.listaIntersecciones(r.nextInt(Interseccion.listaIntersecciones.length))
    var destino:Interseccion = Interseccion.listaIntersecciones(r.nextInt(Interseccion.listaIntersecciones.length))
    while(origen == destino){
      destino = Interseccion.listaIntersecciones(r.nextInt(Interseccion.listaIntersecciones.length))
    }
    val camino = (GrafoVias.n(origen)).shortestPathTo(GrafoVias.n(destino))
    //val viasRecorridoList = camino.last.edges.map(_.label.asInstanceOf[Via]).toList
    //val interseccionesRecorrido = camino.last.nodes.map(_.asInstanceOf[Interseccion]).toList
    val viasRecorrido = Vehiculo.toQueue(camino.last.edges.map(_.label.asInstanceOf[Via]).toList)
    val viaInicial = viasRecorrido.head
    
    val velocidadMaximaViaInicial = viaInicial.velMaxima
    var magnitudVelocidadAleatoria = r.nextDouble()*(Simulacion.maxVelocidad-Simulacion.minVelocidad)+Simulacion.minVelocidad 
    if(magnitudVelocidadAleatoria>velocidadMaximaViaInicial){
      magnitudVelocidadAleatoria = velocidadMaximaViaInicial
    }
    
    val velocidad = new Velocidad(magnitudVelocidadAleatoria, new Angulo(Vehiculo.anguloEntreDosPuntos(origen,viaInicial.fin)))
    if(numeroAleatorio<=pCarros){
      return new Carro(origen,velocidad,"",viasRecorrido)
      
    }else if(numeroAleatorio<=pMotos){
      return new Moto(origen,velocidad,"",viasRecorrido)
      
    }else if(numeroAleatorio<=pBuses){
      return new Bus(origen,velocidad,"",viasRecorrido)
      
    }else if(numeroAleatorio<=pCamiones){
      return new Camion(origen,velocidad,"",viasRecorrido)
      
    }else{
      return new MotoTaxi(origen,velocidad,"",viasRecorrido)
    }

    


    
  }
  
  def toQueue[T](L:List[T]):Queue[T]={
    val Q=new Queue[T]();
    L.foreach(f=> Q+=f)
    Q
    
  }
  
  def anguloEntreDosPuntos(p1:Punto,p2:Punto): Double={
    val dy = Math.abs(p1.y - p2.y)
    val dx = Math.abs(p1.x - p2.x)
    val magnitudAngular = math.atan(dy/dx).toDegrees
    magnitudAngular
  }
  
}