package EntradaSimulacion
import net.liftweb.json._
import net.liftweb.json.JsonAST.JValue
class PametrosSimulacionJson(val dt: Int, val tRefresh: Int, val vehiculos: EntradaVehiculosJson, val velocidad: EntradaVelocidadJson, val proporciones: ProporcionesJson)