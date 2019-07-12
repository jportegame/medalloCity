package ciudad
import inmovil._

class Via (val origen:Interseccion , val fin:Interseccion, val velMaxima:Double , val tipoVia:TipoVia , val numero:Int, val sentido:Sentido ) extends Recta{
  type T = Interseccion
  
 
}