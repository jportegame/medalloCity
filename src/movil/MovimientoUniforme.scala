package movil
import inmovil._

trait MovimientoUniforme {
  val posicion: Punto
  val velocidad: Velocidad 
  def avance(dt:Double):Unit ={
    val cambioX = posicion.x + dt*(velocidad.magnitud*Math.cos(this.velocidad.direccion.valor.toRadians))
    val cambioY = posicion.y + dt*(velocidad.magnitud*Math.sin(this.velocidad.direccion.valor.toRadians))
    println("cambio en X:"+cambioX)
    println("cambio en Y:"+cambioY)
    posicion.x = cambioX
    posicion.y = cambioY
    
  }
}