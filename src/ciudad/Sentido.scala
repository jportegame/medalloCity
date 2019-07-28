package ciudad

case class Sentido private (val nombre:String){

}

object Sentido{
  def unaVia:Sentido = {
    new Sentido("Un sentido")
  }
  def dobleVia:Sentido = {
    new Sentido("Doble via")
  }
}
