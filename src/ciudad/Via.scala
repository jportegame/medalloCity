package ciudad
import inmovil._

class Via (var origen:Interseccion , var fin:Interseccion, var velMaxima:Double , var tipoVia:TipoVia , var numero:Int, var sentido:Sentido ) extends Recta{
  type T = Interseccion
  
 
}