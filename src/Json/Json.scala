package Json
import scala.collection.mutable._
import net.liftweb.json._
import net.liftweb.json.JsonAST.JValue
import java.io._
import scala.io.Source
class Json {
  def leerDatosIniciales(ruta: String) {
    val cadena = Source.fromFile(ruta).getLines.mkString
    val json = parse(cadena)
  }
}