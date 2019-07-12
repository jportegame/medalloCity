package movil
import inmovil._

trait MovimientoUniforme {
  val posicion: Punto
  val velocidad: Velocidad 
  def anguloDireccion(): Double
  def avance(dt:Double):Unit ={
    var xn:Double = posicion.x + dt*(velocidad.magnitud*Math.cos(this.anguloDireccion))
    var yn:Double = posicion.y + dt*(velocidad.magnitud*Math.sin(this.anguloDireccion))
    posicion.x =xn
    posicion.y =yn
  }
}