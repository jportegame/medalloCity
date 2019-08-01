package movil
import inmovil._
import java.util.Random
class Bus(p:Punto, v : Velocidad) extends Vehiculo()(p,v){
  this.placa = this.generarPlacaAleatoria()
  
  def generarPlacaAleatoria():String={
    val random = new Random()
      var letters:Array[String] = "QWERTYUIOPLKJHGFDSAZXCVBNM1234567890".split("")
      var placa = ""
      for (i<- 0 to 6) {
        val index:Int=((random.nextFloat()*35).round)
        placa=placa+letters(index)
      }
      placa
  }
}