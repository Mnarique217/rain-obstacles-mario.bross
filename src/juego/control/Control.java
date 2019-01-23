
package juego.control;


import java.awt.event.KeyEvent;
import java.util.Observer;
import javax.swing.table.TableModel;
import juego.entidades.Jugador;
import juego.modelo.Modelo;



public class Control {

    public Control() {
        modelo=new Modelo();
    }
    
    public Control(Modelo mo) {
        modelo=mo;
    }
    
    public void addObserver(Observer o){
        modelo.addObserver(o);
       
    }
 

    public void iniciarComponentes() {
        modelo.iniciarComponentes();
    }


    public void mover(KeyEvent e) {
        modelo.mover(e);
    }

    public void moverBola() {
        modelo.moverPieza();
    }

    public void reiniciarMarcador(){
        modelo.reiniciarMarcador();
    }

    public void mover() {
        modelo.moverPieza();
    }

    public void revisar() {
        modelo.revisar();
    }

     public TableModel tablaModel() {
        return modelo.getTabla();
    }
     
    public void guardar(Jugador  p) {
        this.modelo.agregar(p);
    }


    public void borrar(int selectedRow) {
        modelo.borrar(selectedRow);

    }

    public void filtrar(String text) {
       modelo.filtro(text);
    }

    public void crearXml() {
        modelo.crearArchivoXML();
    }
    public void leerXml() {
        modelo.leerXML();
    }

    public void activarModoDatos(){
        modelo.activarModoDatos();
    }
    
    
    public void activarModoJuego() {
        modelo.activaModoJuego();
    }
    //--------------------------------------------------------------------------ATRIBUTOS
     private Modelo modelo;

}
