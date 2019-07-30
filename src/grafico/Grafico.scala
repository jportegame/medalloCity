package grafico
import scala.collection.mutable.ArrayBuffer
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
  def this(vias:ArrayBuffer[Via]){
    this()
    /* XYLineAndShapeRenderer sirve para cambiar colores, formas y demas a los datos,
     * segun su orden de entrada
     */
    var renderer: XYLineAndShapeRenderer = new XYLineAndShapeRenderer()
    //Para evitar un error al momento de settear un valor por default de color o demas
  	renderer.setAutoPopulateSeriesStroke(false)
  	renderer.setAutoPopulateSeriesPaint(false)
  	
  	// Color y ancho por defecto
    renderer.setBaseStroke(new BasicStroke(4))
    renderer.setBasePaint(Color.decode("#cccccc"))
    val dataset=cargarMapa(vias,renderer)
    var xyScatterChart: JFreeChart = ChartFactory.createScatterPlot(
  	null, 
  	null, 
  	null, 
  	dataset,
  	PlotOrientation.VERTICAL, false, false, false)

  	//Obtengo mi grafico para agregar estilos
  	var plot: XYPlot = xyScatterChart.getXYPlot()
  	
  	// Añadimos estilos
  	//Fondo blanco
  	plot.setBackgroundPaint(Color.WHITE)
  	
  	//Se quitan los ejes cordenados
  	var range:  ValueAxis = plot.getRangeAxis()
    range.setVisible(false)
    var domain: ValueAxis = plot.getDomainAxis()
    domain.setVisible(false)
    
    this.cargarIntersecciones(dataset, renderer, plot)
    //Seteo todos los cambios a mi grafico
  	plot.setRenderer(renderer)
  	
  	/* Creamos la ventana, ponemos un titulo, le ponemos el grafico al contenedor,
  	 * lo hacemos visible y le damos un tamaño pre definido
  	 */
  	var ventana: ChartFrame = new ChartFrame("vehTraffic", xyScatterChart);
  	ventana.setVisible(true);
  	ventana.setSize(1300, 700);
  	//Al cerrar la ventana acabe el programa
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