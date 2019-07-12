package inmovil

class Velocidad (private var _magnitud:Double ,private var _direccion:Angulo) {
  def magnitud = _magnitud
  def magnitud_=(magnitud: Double) = _magnitud = magnitud
  
  def direccion = _direccion
  def direccion_=(direccion: Angulo) = _direccion = direccion
}

object Velocidad{
  def conversorMetroSegAKmHor(metroSeg : Double):Double ={
    val KmHor:Double = metroSeg*(3600/1000)
    KmHor
  }
  
  def conversorKmHorAMetroSeg(kmHor : Double):Double ={
    val metroSeg:Double = kmHor*(1000/3600)
    metroSeg
  }
}