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

class Grafico{
  val coloresIntersecciones:Map[Interseccion, Color]=Map()

  def this(vias:ArrayBuffer[Via]){
    this()
    var renderer: XYLineAndShapeRenderer = new XYLineAndShapeRenderer()
  	renderer.setAutoPopulateSeriesStroke(false)
  	renderer.setAutoPopulateSeriesPaint(false)
  	
    renderer.setBaseStroke(new BasicStroke(4))
    renderer.setBasePaint(Color.decode("#cccccc"))
    
    val dataset=cargarMapa(vias,renderer)
    
    var xyScatterChart: JFreeChart = ChartFactory.createScatterPlot(
  	null, 
  	null, 
  	null, 
  	dataset,
  	PlotOrientation.VERTICAL, false, false, false)


  	var plot: XYPlot = xyScatterChart.getXYPlot()

  	plot.setBackgroundPaint(Color.WHITE)
  	

  	var range:  ValueAxis = plot.getRangeAxis()
    range.setVisible(true)
    var domain: ValueAxis = plot.getDomainAxis()
    domain.setVisible(true)
    
    this.cargarIntersecciones(dataset, renderer, plot)

  	plot.setRenderer(renderer)
  	

  	var ventana: ChartFrame = new ChartFrame("vehTraffic", xyScatterChart);
  	ventana.setVisible(true);
  	ventana.setSize(1300, 700);
  	ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  def cargarMapa(vias:ArrayBuffer[Via],renderer:XYLineAndShapeRenderer):XYSeriesCollection={
    var dataset: XYSeriesCollection = new XYSeriesCollection();
    for(via<-vias){
      val nuevaVia: XYSeries  = new XYSeries(via.nombre+"-"+via.origen.nombre+"-"+via.fin.nombre)
    	nuevaVia.add(via.origen.xi, via.origen.yi)
    	nuevaVia.add(via.fin.xi, via.fin.yi)
      dataset.addSeries(nuevaVia)
      renderer.setSeriesShapesVisible(dataset.getSeriesCount, false)
    }
    return dataset
  }
  
  def cargarIntersecciones(dataset:XYSeriesCollection,renderer:XYLineAndShapeRenderer,plot:XYPlot){
    val random = new Random()
    val intersecciones:ArrayBuffer[Interseccion]=GrafoVias.listaDeNodos
    for(interseccion<-intersecciones){
      val color=Color.decode(randomHex())
      renderer.setSeriesPaint(dataset.getSeriesCount,color)
      val label: XYTextAnnotation = new XYTextAnnotation(interseccion.nombre,interseccion.xi, interseccion.yi+(350)*((random.nextFloat()*2).round-1))
    	label.setPaint(color)
    	plot.addAnnotation(label)
    	this.coloresIntersecciones+=(interseccion->color)
    }
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