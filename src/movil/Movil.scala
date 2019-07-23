package movil
import inmovil._
abstract class Movil(val posicion: Punto, val velocidad:Velocidad) {
  def anguloDireccion(p1:Punto,p2:Punto): Double={ //REVISAR ESTO QUE SIGNIFICA
    val dy = Math.abs(p1.y - p2.y)
    val dx = Math.abs(p1.x - p2.x)
    val magnitudAngular = math.atan(dy/dx).toDegrees
    magnitudAngular
  }
  def avance(dt:Double):Unit
}