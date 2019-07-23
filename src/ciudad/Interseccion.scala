package ciudad
import inmovil.Punto
import scala.collection.mutable.ArrayBuffer
case class Interseccion(val xi : Int, val yi:Int, val nombre:String = "") extends Punto(xi,yi) {
  Interseccion.listaIntersecciones += this
  
  
  override def toString() = s"$nombre"
  
}

object Interseccion{
  val listaIntersecciones = new ArrayBuffer[Interseccion]
}