package inmovil
import scalax.collection.Graph
import scalax.collection.GraphPredef._, scalax.collection.GraphEdge._
import scalax.collection.edge.WLDiEdge
import ciudad.Interseccion
import ciudad.Via
import ciudad.Sentido
import scala.collection.mutable.ArrayBuffer

object GrafoVias {
  var g = Graph[Interseccion, WLDiEdge]()
  def construir(vias: ArrayBuffer[Via]):Unit = {
    vias.foreach(x=> {
      if((x.sentido.nombre).equalsIgnoreCase("Un Sentido")){
        g = (g + WLDiEdge(x.origen, x.fin)(x.distancia,x))
      }else{
        g = (g + WLDiEdge(x.origen, x.fin)(x.distancia,x))
        g = (g + WLDiEdge(x.fin, x.origen)(x.distancia,x))
      }
    })
    

    
  }
}