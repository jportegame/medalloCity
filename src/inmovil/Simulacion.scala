package inmovil
import scala.collection.mutable.ArrayBuffer
import ciudad._
import grafico.Grafico
import movil._
import Json.Json
import EntradaSimulacion._
import SalidaSimulacion._
object Simulacion extends Runnable {
  val jsonAdmin = new Json[Salida]
  val ruta = System.getProperty("user.dir")+"\\src\\"
  val parametros = jsonAdmin.leerDatosIniciales(ruta+"parametros.json")
  var running = false
  val grafo = GrafoVias
  var t: Double = 0
  var dt: Double = 1
  val tRefresh = 1/10
  val minVehiculos = 1
  val maxVehiculos = 100
  val minVelocidad: Double = 40
  val maxVelocidad: Double = 80
  val totalVehiculos = 15 //Tiene que ser randomizada cuando funcine de verdad el proyecto y tiene que estar entre(minVehiculos,maxVehiculos)

  var listaVias = ArrayBuffer.empty[Via]
  val hilo = new Thread(Simulacion)
  var listaIntersecciones = ArrayBuffer.empty[Interseccion]
  def cargar() {

    val niquia = new Interseccion(300, 12000, "Niquia")
    val lauraAuto = new Interseccion(2400, 11400, "M. Laura Auto")
    val lauraReg = new Interseccion(2400, 12600, "M. Laura Reg")
    val ptoCero = new Interseccion(5400, 12000, "Pto 0")
    val mino = new Interseccion(6300, 15000, "Minorista")
    val villa = new Interseccion(6300, 19500, "Villanueva")
    val ig65 = new Interseccion(5400, 10500, "65 Igu")
    val robledo = new Interseccion(5400, 1500, "Exito Rob")
    val colReg = new Interseccion(8250, 12000, "Col Reg")
    val colFerr = new Interseccion(8250, 15000, "Col Ferr")
    val col65 = new Interseccion(8250, 10500, "Col 65")
    val col80 = new Interseccion(8250, 1500, "Col 80")
    val juanOr = new Interseccion(10500, 19500, "Sn Juan Ori")
    val maca = new Interseccion(10500, 12000, "Macarena")
    val expo = new Interseccion(12000, 13500, "Exposiciones")
    val reg30 = new Interseccion(13500, 15000, "Reg 30")
    val monte = new Interseccion(16500, 15000, "Monterrey")
    val agua = new Interseccion(19500, 15000, "Aguacatala")
    val viva = new Interseccion(21000, 15000, "Viva Env")
    val mayor = new Interseccion(23400, 15000, "Mayorca")
    val ferrCol = new Interseccion(8250, 15000, "Ferr Col")
    val ferrJuan = new Interseccion(10500, 15000, "Alpujarra")
    val sanDiego = new Interseccion(12000, 19500, "San Diego")
    val premium = new Interseccion(13500, 19500, "Premium")
    val pp = new Interseccion(16500, 19500, "Parque Pob")
    val santafe = new Interseccion(19500, 18750, "Santa Fe")
    val pqEnv = new Interseccion(21000, 18000, "Envigado")
    val juan65 = new Interseccion(10500, 10500, "Juan 65")
    val juan80 = new Interseccion(10500, 1500, "Juan 80")
    val _33_65 = new Interseccion(12000, 10500, "33 con 65")
    val bule = new Interseccion(12000, 7500, "Bulerias")
    val gema = new Interseccion(12000, 1500, "St Gema")
    val _30_65 = new Interseccion(13500, 10500, "30 con 65")
    val _30_70 = new Interseccion(13500, 4500, "30 con 70")
    val _30_80 = new Interseccion(13500, 1500, "30 con 80")
    val bol65 = new Interseccion(11100, 10500, "Boliv con 65")
    val gu10 = new Interseccion(16500, 12000, "Guay con 10")
    val terminal = new Interseccion(16500, 10500, "Term Sur")
    val gu30 = new Interseccion(13500, 12000, "Guay 30")
    val gu80 = new Interseccion(19500, 12000, "Guay 80")
    val _65_80 = new Interseccion(19500, 10500, "65 con 30")
    val gu_37S = new Interseccion(21000, 12000, "Guay con 37S")

    val intersect = ArrayBuffer(niquia, lauraAuto, lauraReg, ptoCero, mino, villa, ig65, robledo, colReg, colFerr, col65, col80, juanOr,
      maca, expo, reg30, monte, agua, viva, mayor, ferrCol, ferrJuan, sanDiego, premium, pp, santafe, pqEnv, juan65, juan80, _33_65,
      bule, gema, _30_65, _30_70, _30_80, bol65, gu10, terminal, gu30, gu80, _65_80, gu_37S)

    listaIntersecciones = intersect

    val vias = ArrayBuffer(
      new Via(niquia, lauraAuto, 80, TipoVia("Carrera"), Sentido.dobleVia, "64C", "Auto Norte"),
      new Via(niquia, lauraReg, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
      new Via(lauraAuto, lauraReg, 60, TipoVia("Calle"), Sentido.dobleVia, "94", "Pte Madre Laura"),
      new Via(lauraAuto, ptoCero, 80, TipoVia("Carrera"), Sentido.dobleVia, "64C", "Auto Norte"),
      new Via(lauraReg, ptoCero, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
      new Via(ptoCero, mino, 60, TipoVia("Calle"), Sentido.dobleVia, "58", "Oriental"),
      new Via(mino, villa, 60, TipoVia("Calle"), Sentido.dobleVia, "58", "Oriental"),
      new Via(ptoCero, ig65, 60, TipoVia("Calle"), Sentido.dobleVia, "55", "Iguaná"),
      new Via(ig65, robledo, 60, TipoVia("Calle"), Sentido.dobleVia, "55", "Iguaná"),
      new Via(ptoCero, colReg, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
      new Via(colReg, maca, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
      new Via(maca, expo, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
      new Via(expo, reg30, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
      new Via(reg30, monte, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
      new Via(monte, agua, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
      new Via(agua, viva, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
      new Via(viva, mayor, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", "Regional"),
      new Via(mino, ferrCol, 60, TipoVia("Carrera"), Sentido.dobleVia, "55", "Ferrocarril"),
      new Via(ferrCol, ferrJuan, 60, TipoVia("Carrera"), Sentido.dobleVia, "55", "Ferrocarril"),
      new Via(ferrJuan, expo, 60, TipoVia("Carrera"), Sentido.dobleVia, "55", "Ferrocarril"),
      new Via(villa, juanOr, 60, TipoVia("Carrera"), Sentido.dobleVia, "46", "Oriental"),
      new Via(juanOr, sanDiego, 60, TipoVia("Carrera"), Sentido.dobleVia, "46", "Oriental"),
      new Via(sanDiego, premium, 60, TipoVia("Carrera"), Sentido.dobleVia, "43A", "Av Pob"),
      new Via(premium, pp, 60, TipoVia("Carrera"), Sentido.dobleVia, "43A", "Av Pob"),
      new Via(pp, santafe, 60, TipoVia("Carrera"), Sentido.dobleVia, "43A", "Av Pob"),
      new Via(santafe, pqEnv, 60, TipoVia("Carrera"), Sentido.dobleVia, "43A", "Av Pob"),
      new Via(pqEnv, mayor, 60, TipoVia("Carrera"), Sentido.dobleVia, "43A", "Av Pob"),
      new Via(ferrCol, colReg, 60, TipoVia("Calle"), Sentido.dobleVia, "450", "Colombia"),
      new Via(colReg, col65, 60, TipoVia("Calle"), Sentido.dobleVia, "450", "Colombia"),
      new Via(col65, col80, 60, TipoVia("Calle"), Sentido.dobleVia, "450", "Colombia"),
      new Via(juanOr, ferrJuan, 60, TipoVia("Calle"), Sentido.dobleVia, "44", "Sn Juan"),
      new Via(ferrJuan, maca, 60, TipoVia("Calle"), Sentido.dobleVia, "44", "Sn Juan"),
      new Via(maca, juan65, 60, TipoVia("Calle"), Sentido.dobleVia, "44", "Sn Juan"),
      new Via(juan65, juan80, 60, TipoVia("Calle"), Sentido.dobleVia, "44", "Sn Juan"),
      new Via(sanDiego, expo, 60, TipoVia("Calle"), Sentido.dobleVia, "33", "33"),
      new Via(expo, _33_65, 60, TipoVia("Calle"), Sentido.dobleVia, "33", "33"),
      new Via(_33_65, bule, 60, TipoVia("Calle"), Sentido.dobleVia, "33", "33"),
      new Via(bule, gema, 60, TipoVia("Calle"), Sentido.dobleVia, "33", "33"),
      new Via(premium, reg30, 60, TipoVia("Calle"), Sentido.dobleVia, "30", "30"),
      new Via(reg30, _30_65, 60, TipoVia("Calle"), Sentido.dobleVia, "30", "30"),
      new Via(_30_65, _30_70, 60, TipoVia("Calle"), Sentido.dobleVia, "30", "30"),
      new Via(_30_70, _30_80, 60, TipoVia("Calle"), Sentido.dobleVia, "30", "30"),
      new Via(maca, bol65, 60, TipoVia("Diagonal"), Sentido.dobleVia, "74B", "Boliv"),
      new Via(bol65, bule, 60, TipoVia("Diagonal"), Sentido.dobleVia, "74B", "Boliv"),
      new Via(bule, _30_70, 60, TipoVia("Diagonal"), Sentido.dobleVia, "74B", "Boliv"),
      new Via(juan80, bule, 60, TipoVia("Transversal"), Sentido.dobleVia, "39B", "Nutibara"),
      new Via(pp, monte, 60, TipoVia("Calle"), Sentido.dobleVia, "10", "10"),
      new Via(monte, gu10, 60, TipoVia("Calle"), Sentido.dobleVia, "10", "10"),
      new Via(gu10, terminal, 60, TipoVia("Calle"), Sentido.dobleVia, "10", "10"),
      new Via(expo, gu30, 60, TipoVia("Carrera"), Sentido.dobleVia, "52", "Av Guay"),
      new Via(gu30, gu10, 60, TipoVia("Carrera"), Sentido.dobleVia, "52", "Av Guay"),
      new Via(gu10, gu80, 60, TipoVia("Carrera"), Sentido.dobleVia, "52", "Av Guay"),
      new Via(gu80, gu_37S, 60, TipoVia("Carrera"), Sentido.dobleVia, "52", "Av Guay"),
      new Via(lauraAuto, ig65, 60, TipoVia("Carrera"), Sentido.dobleVia, "65", "65"),
      new Via(ig65, col65, 60, TipoVia("Carrera"), Sentido.dobleVia, "65", "65"),
      new Via(juan65, col65, 60, TipoVia("Carrera"), Sentido.unaVia, "65", "65"),
      new Via(bol65, juan65, 60, TipoVia("Carrera"), Sentido.unaVia, "65", "65"),
      new Via(_33_65, bol65, 60, TipoVia("Carrera"), Sentido.unaVia, "65", "65"),
      new Via(_30_65, _33_65, 60, TipoVia("Carrera"), Sentido.unaVia, "65", "65"),
      new Via(_30_65, terminal, 60, TipoVia("Carrera"), Sentido.dobleVia, "65", "65"),
      new Via(terminal, _65_80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", "65"),
      new Via(robledo, col80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", "80"),
      new Via(col80, juan80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", "80"),
      new Via(juan80, gema, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", "80"),
      new Via(gema, _30_80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", "80"),
      new Via(_30_80, _65_80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", "80"),
      new Via(_65_80, gu80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", "80"),
      new Via(gu80, agua, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", "80"),
      new Via(agua, santafe, 60, TipoVia("Calle"), Sentido.dobleVia, "12S", "80"),
      new Via(viva, pqEnv, 60, TipoVia("Calle"), Sentido.dobleVia, "37S", "37S"),
      new Via(viva, gu_37S, 60, TipoVia("Calle"), Sentido.dobleVia, "63", "37S"))
    listaVias = vias
    grafo.construir(vias)
    println(VehiculoSimulacion.listaDeVehiculosSimulacion.length)
    val grafico = Grafico
    grafico.iniciarGrafico(vias)
    Simulacion.run()

  }
  def start() {
    if (running) {
      Simulacion.stop()
    }

    hilo.start
  }

  def stop() {
    Vehiculo.setPlacas.clear()
    VehiculoSimulacion.listaDeVehiculosSimulacion.clear()
    //Grafico.reiniciarVehiculos()
    running = false
    hilo.interrupt()
  }
  def run() {

    for (i <- 0 until Simulacion.totalVehiculos) {
      VehiculoSimulacion.apply()
    }

    //Parametros
    println("dt: " + Simulacion.dt)
    println("tRefresh: " + Simulacion.tRefresh)
    println("minVehiculos: " + Simulacion.minVehiculos)
    println("maxVehiculos: " + Simulacion.maxVehiculos)
    println("minVelocidad: " + Simulacion.minVelocidad)
    println("maxVelocidad: " + Simulacion.maxVelocidad)
    println("totalVehiculos: " + Simulacion.totalVehiculos)

    println("Proporcion carros: " + Vehiculo.pCarros)
    println("Proporcion moto: " + Vehiculo.pMotos)
    println("Proporcion buses: " + Vehiculo.pBuses)
    println("Proporcion camiones: " + Vehiculo.pCamiones)
    println("Proporcion mototaxis: " + Vehiculo.pMotoTaxis)

    //fin parametros
    // Prepruebas visuales que deben ser borradas-inicio

    val vehiculoSimulacion = VehiculoSimulacion.listaDeVehiculosSimulacion(0)

    val dequeInterseccion = vehiculoSimulacion.interseccionesRecorrido
    val dequeVias = vehiculoSimulacion.recorrido

    println(s"Interseccion destino Inicial: ${vehiculoSimulacion.interseccionDestino} ")
    var i = 3

    println(s"Paso 1: (${vehiculoSimulacion.vehiculo.posicion.x},${vehiculoSimulacion.vehiculo.posicion.y})")
    println(s"Paso 2: (${vehiculoSimulacion.interseccionDestino.x},${vehiculoSimulacion.interseccionDestino.y})")
    vehiculoSimulacion.interseccionesRecorrido.foreach(x => {
      println(s"Paso $i : (${x.x},${x.y})")
      i += 1

    })

    // Prepruebas visuales que deben ser borradas-fin

    Simulacion.running = true
    //CALCULOS DE PAJOY

    def contar(rec: ArrayBuffer[Interseccion]): scala.collection.mutable.Map[Interseccion, Int] = {
      var r = scala.collection.mutable.Map[Interseccion, Int]()
      rec.foreach(f => {
        if (!(r.contains(f))) {
          r += (f -> 1)
        } else {
          var aux = r.get(f).get.toInt
          r.remove(f)
          r += (f -> (1 + aux))
        }
      })
      r
    }

    val totalVehiculos = VehiculoSimulacion.listaDeVehiculosSimulacion.length
    val totalCarros = VehiculoSimulacion.listaDeVehiculosSimulacion.filter(_.vehiculo.isInstanceOf[Carro]).length
    val totalMotos = VehiculoSimulacion.listaDeVehiculosSimulacion.filter(_.vehiculo.isInstanceOf[Moto]).length
    val totalBuses = VehiculoSimulacion.listaDeVehiculosSimulacion.filter(_.vehiculo.isInstanceOf[Bus]).length
    val totalCamiones = VehiculoSimulacion.listaDeVehiculosSimulacion.filter(_.vehiculo.isInstanceOf[Camion]).length
    val totalMototaxis = VehiculoSimulacion.listaDeVehiculosSimulacion.filter(_.vehiculo.isInstanceOf[MotoTaxi]).length

    val vias = VehiculoSimulacion.listaDeVehiculosSimulacion.flatMap(_.recorridoCompleto).distinct.length
    val intersecciones = VehiculoSimulacion.listaDeVehiculosSimulacion.flatMap(_.interseccionesCompletas).distinct.length
    val viasUnSentido = VehiculoSimulacion.listaDeVehiculosSimulacion.flatMap(_.recorridoCompleto).filter(v => v.sentido.nombre == "Un sentido").distinct.length
    val viasDobleSentido = VehiculoSimulacion.listaDeVehiculosSimulacion.flatMap(_.recorridoCompleto).filter(v => v.sentido.nombre == "Doble via").distinct.length
    val velMaximaVias = VehiculoSimulacion.listaDeVehiculosSimulacion.flatMap(_.recorridoCompleto).distinct.map(_.velMaxima).max
    val velMinimaVias = VehiculoSimulacion.listaDeVehiculosSimulacion.flatMap(_.recorridoCompleto).distinct.map(_.velMaxima).min
    val longitudPromedio = (VehiculoSimulacion.listaDeVehiculosSimulacion.flatMap(_.recorridoCompleto).distinct.map(_.distancia).sum) / vias

    var origen = 0
    val mapPromedioOrigen = contar(VehiculoSimulacion.listaDeVehiculosSimulacion.map(_.interseccionesCompletas(0)))

    mapPromedioOrigen.foreach(f => {
      origen += f._2
    })
    val promedioOrigen = totalVehiculos.toDouble / (mapPromedioOrigen.size.toDouble)//Revisar

    var destino = 0
    val mapPromedioDestino = contar(VehiculoSimulacion.listaDeVehiculosSimulacion.map(_.interseccionesCompletas.last))

    mapPromedioDestino.foreach(f => {
      destino += f._2
    })
    val promedioDestino = totalVehiculos.toDouble / (mapPromedioDestino.size.toDouble)//Revisar

    val sinOrigen = listaIntersecciones.length - mapPromedioOrigen.size
    val sinDestino = listaIntersecciones.length - mapPromedioDestino.size
    val arrayDistancias = VehiculoSimulacion.listaDeVehiculosSimulacion.map(_.recorridoCompleto.map(_.distancia).reduce(_ + _))
    val distanciaMinima = arrayDistancias.min
    val distanciaMaxima = arrayDistancias.max
    val distanciaPromedio = (arrayDistancias.sum) / (arrayDistancias.length)
    println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT")
    println((mapPromedioOrigen.size))
    println((mapPromedioDestino.size))
    //FIN CALCULOS PAJOY
    val velocidadesVehiculos = ArrayBuffer[Double]()
    while (!VehiculoSimulacion.listaDeVehiculosSimulacion.isEmpty) {
      //Pruebas visuales hechas por juanes deben ser remplazadas por la funcion de pablo donde recibe una lista de [VehiculoSimulacion] -inicio
      println("t: " + Simulacion.t)
      val velocidades = VehiculoSimulacion.listaDeVehiculosSimulacion.map(_.vehiculo.velocidad.magnitud)
      velocidades.foreach(f => {
        velocidadesVehiculos += f
      })
      println("Interseccion destino: " + vehiculoSimulacion.interseccionDestino)
      println("angulo:" + vehiculoSimulacion.vehiculo.velocidad.direccion.valor)
      println("Componente velocidad X:" + vehiculoSimulacion.vehiculo.velocidad.sentidoX * vehiculoSimulacion.vehiculo.velocidad.magnitud * Math.cos(vehiculoSimulacion.vehiculo.velocidad.direccion.valor.toRadians))
      println("Componente velocidad Y:" + vehiculoSimulacion.vehiculo.velocidad.sentidoY * vehiculoSimulacion.vehiculo.velocidad.magnitud * Math.sin(vehiculoSimulacion.vehiculo.velocidad.direccion.valor.toRadians))
      
      val listaVehiculosLlegaron = new ArrayBuffer[VehiculoSimulacion]
      
      println(vehiculoSimulacion.vehiculo.posicion)
      val grafico = Grafico
      VehiculoSimulacion.listaDeVehiculosSimulacion.foreach(vehiculo=>{
        vehiculo.mover(Simulacion.dt)
        grafico.actualizarVehiculo(vehiculo)
        if(vehiculo.vehiculo.detenido){
          listaVehiculosLlegaron+=vehiculo
        }
      })
      
      listaVehiculosLlegaron.foreach(vehiculo=>vehiculo.pararVehiculo(vehiculo))
 
      //Pruebas visuales hechas por juanes deben ser remplazadas por la funcion de pablo donde recibe una lista de [VehiculoSimulacion] -fin

      Simulacion.t += Simulacion.dt

      //funcion de pajoy que recoje los datos desde la misma lista de [VehiculoSimulacion]

      Thread.sleep(tRefresh * 1000)
    }

    //Pajoy

    val tiempoRealidad = t
    val tiempoSimulacion = tiempoRealidad / tRefresh.toDouble
    val velVehiMax = (velocidadesVehiculos.max) * 3.6
    val velVehiMin = (velocidadesVehiculos.min) * 3.6
    val velVehiProm = ((velocidadesVehiculos.sum) / (velocidadesVehiculos.length)) * 3.6

    val salidaVehiculos = new SalidaVehiculos(totalVehiculos, totalCarros, totalMotos, totalBuses, totalCamiones, totalMototaxis)
    val vehiculosEnInterseccion = new VehiculosEnInterseccion(promedioOrigen, promedioDestino, sinOrigen, sinDestino)
    val mallaVial = new MallaVial(vias, intersecciones, viasUnSentido, viasDobleSentido, velMinimaVias, velMaximaVias, longitudPromedio, vehiculosEnInterseccion)
    val tiempos = new Tiempos(tiempoSimulacion, tiempoRealidad)
    val salidaVelocidades = new SalidaVelocidades(velVehiMin,velVehiMax,velVehiProm)
    val distancias = new Distancias(distanciaMinima,distanciaMaxima, distanciaPromedio)
    
    val resultados = new ResultadosSimulacion(salidaVehiculos, mallaVial, tiempos, salidaVelocidades, distancias)
    val salida = new Salida(resultados)
    jsonAdmin.escribirArchivo(ruta+"resultados.json", salida)
    
    //println(s"""$vias, $intersecciones, $viasUnSentido, $viasDobleSentido, $velMaximaVias, $velMinimaVias, $longitudPromedio, $PromedioOrigen, $PromedioDestino, $sinOrigen, $sinDestino,
    //$tiempoRealidad, $tiempoSimulacion, $velVehiMax, $velVehiMin, $velVehiProm""")
    //finPajoy
    // Postpruebas visuales que deben ser borradas-inicio
    println("Intersecciones: " + dequeInterseccion)
    println("Vias: " + dequeVias)
    // Postpruebas visuales que deben ser borradas-fin

  }

}