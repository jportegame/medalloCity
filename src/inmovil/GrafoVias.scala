package inmovil
import scalax.collection.Graph
import scalax.collection.GraphPredef._, scalax.collection.GraphEdge._
import scalax.collection.edge.WDiEdge
import ciudad.Interseccion
import ciudad.Via
import ciudad.Sentido
import scala.collection.mutable.ArrayBuffer

object GrafoVias {
  var g = Graph[Interseccion, WDiEdge]()
  def construir(vias: ArrayBuffer[Via]):Unit = {
    vias.foreach(x=> {
      if((x.sentido.nombre).equalsIgnoreCase("Un Sentido")){
        g = (g + WDiEdge(x.origen, x.fin)(x.distancia))
      }else{
        g = (g + WDiEdge(x.origen, x.fin)(x.distancia))
        g = (g + WDiEdge(x.fin, x.origen)(x.distancia))
      }
    })
    

    
  }
}