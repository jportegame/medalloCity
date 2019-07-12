package ciudad

class Sentido(var nombre:String){
    if(!(this.nombre.equals("Doble via")) && !(this.nombre.equals("Un sentido"))) {
      this.nombre = "Un sentido"
    }
}
