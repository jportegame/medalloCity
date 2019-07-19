package ciudad
import inmovil.Punto
class Interseccion(val xi : Int, val yi:Int, val nombre:String = "") extends Punto(xi,yi) {
  override def toString() = s"$nombre"
}