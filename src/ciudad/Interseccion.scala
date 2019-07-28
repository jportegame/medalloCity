package ciudad
import inmovil.Punto
import scala.collection.mutable.ArrayBuffer
case class Interseccion(xi: Int, yi: Int, val nombre: String = "") extends Punto(xi, yi) { //Mirar si es case

  override def toString() = s"$nombre" //Borrar

}
