package movil
import inmovil._
abstract class Movil(val posicion: Punto, val velocidad:Velocidad) {
  def anguloAlQueSeDirige(p1:Punto,p2:Punto): Double={ //REVISAR ESTO QUE SIGNIFICA
  1.0
  }
  def avance(dt:Double):Unit
}