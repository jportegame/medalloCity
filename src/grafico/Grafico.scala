package grafico
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Map
import ciudad._
import inmovil._
//Libreria para frames
import javax.swing.JFrame
//Libreria para lineas
import java.awt.BasicStroke;
//Libreria para colores
import java.awt.Color;
//JfreeChart
import org.jfree.chart.ChartFrame
import org.jfree.chart.ChartFactory
import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.plot.XYPlot
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import org.jfree.chart.ChartFrame
import org.jfree.chart.axis.ValueAxis
import org.jfree.chart.annotations.XYTextAnnotation

import java.util.Random

//Figuras geometricas
import java.awt.geom.Ellipse2D
import java.awt.Rectangle
import java.awt.Polygon

object Grafico{
  val coloresIntersecciones:Map[Interseccion, Color]=Map()
  val dataset: XYSeriesCollection = new XYSeriesCollection();
  val renderer: XYLineAndShapeRenderer = new XYLineAndShapeRenderer()
  var plot: XYPlot = null
  
  def iniciarGrafico(vias:ArrayBuffer[Via]){
  	
    this.renderer.setAutoPopulateSeriesStroke(false)
  	this.renderer.setAutoPopulateSeriesPaint(false)
  	
    this.renderer.setBaseStroke(new BasicStroke(4))
    this.renderer.setBasePaint(Color.decode("#cccccc"))
    
    cargarMapa(vias)
    
    val xyScatterChart: JFreeChart = ChartFactory.createScatterPlot(
  	null, 
  	null, 
  	null, 
  	this.dataset,
  	PlotOrientation.VERTICAL, false, false, false)


  	this.plot=xyScatterChart.getXYPlot()

  	this.plot.setBackgroundPaint(Color.WHITE)
  	

  	val range:  ValueAxis = plot.getRangeAxis()
    range.setVisible(true)
    val domain: ValueAxis = plot.getDomainAxis()
    domain.setVisible(true)
    
    this.cargarIntersecciones(plot)

  	this.plot.setRenderer(this.renderer)
  	

  	val ventana: ChartFrame = new ChartFrame("vehTraffic", xyScatterChart);
  	ventana.setVisible(true);
  	ventana.setSize(1300, 700);
  	ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  def cargarMapa(vias:ArrayBuffer[Via]){
    for(via<-vias){
      val nuevaVia: XYSeries  = new XYSeries(via.nombre+"-"+via.origen.nombre+"-"+via.fin.nombre)
    	nuevaVia.add(via.origen.xi, via.origen.yi)
    	nuevaVia.add(via.fin.xi, via.fin.yi)
      this.dataset.addSeries(nuevaVia)
      renderer.setSeriesShapesVisible(dataset.getSeriesCount, false)
    }
  }
  
  def cargarIntersecciones(plot:XYPlot){
    val random = new Random()
    val intersecciones:ArrayBuffer[Interseccion]=GrafoVias.listaDeNodos
    for(interseccion<-intersecciones){
      val color=Color.decode(randomHex())
      this.renderer.setSeriesPaint(this.dataset.getSeriesCount,color)
      val label: XYTextAnnotation = new XYTextAnnotation(interseccion.nombre,interseccion.xi, interseccion.yi+(350)*((random.nextFloat()*2).round-1))
    	label.setPaint(color)
    	plot.addAnnotation(label)
    	this.coloresIntersecciones+=(interseccion->color)
    }
  }
  
  def cargarVehiculo(vehiculoSimulacion:VehiculoSimulacion){
    val vehiculo=vehiculoSimulacion.vehiculo
    val punto=vehiculo.posicion
    println(vehiculo.placa)
    var vehiculoGrafico: XYSeries = new XYSeries(vehiculo.placa)
    vehiculoGrafico.add(punto.x, punto.y)
    this.dataset.addSeries(vehiculoGrafico)
    this.renderer.setSeriesShape(this.dataset.getSeriesCount, new Rectangle(-4,-4,6,6))
    this.renderer.setSeriesPaint(this.dataset.getSeriesCount,this.coloresIntersecciones(vehiculoSimulacion.interseccionDestino))
  }
  
  def randomHex():String={
      val random = new Random()
      var letters:Array[String] = "0123456789ABCDEF".split("")
      var color = "#"
      for (i<- 0 to 6) {
        val index:Int=((random.nextFloat()*15).round)
        color=color+letters(index)
      }
      color
  }
}