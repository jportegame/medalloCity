package ciudad
import inmovil.Punto
import scala.collection.mutable.ArrayBuffer
case class Interseccion(xi: Int, yi: Int, val nombre: String = "") extends Punto(xi, yi) {

  override def toString() = s"$nombre"

}
