package ciudad

class Sentido(){
  var nombre:String =_
  
  private def this(nombre:String){
    this()
    this.nombre= nombre
  }

}

object Sentido{
  def unaVia:Sentido = {
    new Sentido("Un sentido")
  }
  def dobleVia:Sentido = {
    new Sentido("Doble via")
  }
}
