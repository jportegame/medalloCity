package inmovil

class Punto(private var _x: Double ,private var _y:Double) {
  def x = _x
  def x_=(x: Double) = _x = x
  
  def y = _y
  def y_=(direccion: Double) = _y = y
}