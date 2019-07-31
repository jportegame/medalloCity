package main
import inmovil.Simulacion
object Main{
  val hilo = new Thread(Simulacion)
  def main(args: Array[String]){
    Simulacion.cargar()
  }
  
}