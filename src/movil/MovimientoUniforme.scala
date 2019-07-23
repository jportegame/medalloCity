package movil
import inmovil._

trait MovimientoUniforme {
  val posicion: Punto
  val velocidad: Velocidad 
  def anguloDireccion(p1:Punto,p2:Punto): Double
  def avance(dt:Double):Unit ={
    posicion.x = posicion.x + dt*(velocidad.magnitud*Math.cos(this.velocidad.direccion.valor))
    posicion.y =posicion.y + dt*(velocidad.magnitud*Math.sin(this.velocidad.direccion.valor))
  }
}