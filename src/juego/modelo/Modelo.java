
package juego.modelo;

import java.applet.Applet;
import java.applet.AudioClip;
import juego.entidades.TablaModel;
import juego.entidades.Jugadores;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import static juego.Aplicacion.CANT_PIEZAS;

import juego.entidades.Piezas;
import juego.entidades.Jugador;
import juego.xml.UtilidadesXML;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 * @author xxxx
 */
public class Modelo extends Observable{

    int n;
    Random r;
    
    public Modelo() {
        
        n=0;
        r=new Random();
        piezas=new Piezas();
        this.jugador = new Jugador();
        finalizo=false;
        cantidadPiezas=1;
        aumentar=false;
        registrado=false;
        estadoFiltro=false;
        jugadores=new Jugadores();
        tabla=new TablaModel(jugadores);
    }

    @Override
    public void addObserver(Observer o) {
        super.addObserver(o);
        actualizar("componentes ajustados");
    }

    public void iniciarComponentes() {
        jugador.iniciarJugador();
    }
    
    public void reiniciarMarcador(){
        piezas.reiniciar();
        cantidadPiezas=1;
        jugador.iniciarJugador();
    }

    private void actualizar(Object o) {
        this.setChanged();
        this.notifyObservers(o);
    }

    public void mover(KeyEvent e) {
        jugador.moverMario(e);
    }

    public void moverPieza() {
        
        for(int i=0; i<cantidadPiezas; i++)
        piezas.obtenerPieza(i).aplicarMovimiento();
    }
    
    public void revisar(){
          
            temp=false;
            golpeado();
            for(int i=0; i<cantidadPiezas && temp==false; i++){
                n=piezas.obtenerPieza(i).getPosV();
                if(n>550){
                    temp=true;piezas.obtenerPieza(i).iniciarPieza(r.nextInt(800));}
            }
       
        
            if(temp && cantidadPiezas <CANT_PIEZAS){
                cantidadPiezas++; aumentar=true; actualizar(" aumenta numero de piezas");aumentar=false;}

         }
    
    private void golpeado() {
        int x;
        int limite;
        int limitev;
        finalizo=false;
        
         for(int i=0; i<cantidadPiezas && !finalizo; i++){
            limitev= piezas.obtenerPieza(i).getPosV();
            x= piezas.obtenerPieza(i).getPosH();
            limite=jugador.getPosH();
             if(limite-50<=x && x<= limite+100  && limitev>450 ){
                 temp=true;
                 aplicaPuntos(piezas.obtenerPieza(i).getNombre());
                 piezas.obtenerPieza(i).iniciarPieza(r.nextInt(800));
             }
         }
    }

    public void aplicaPuntos(String x){
        
        if("ab".contains(x)){
            
            jugador.setCantVidas(-1);
            actualizar("choqueee.....");
        }
        else{
            audio2.play();
            jugador.setPuntaje(1);
            actualizar(jugador.getPuntaje());
        }
        
        
        if(jugador.getCantVidas()<1){
            audio1.play();
            finalizo=true;actualizar("finn");
        }
    }
  
    public Jugador getJugador() {
        return jugador;
    }

    public Piezas getPiezas() {
        return piezas;
    }

    public int getCantidadPiezas() {
        return cantidadPiezas;
        
    }

    public boolean isAumentar() {
        return aumentar;
    }
 
    public boolean isFinalizo() {
        return finalizo;
    }
    public void refrescar(){
    this.setChanged();
    this.notifyObservers();
    }

    public TablaModel getTabla() {
        return tabla;
    }

    public void filtro(String n){
       estadoFiltro=true;
       auxJugadores=jugadores.filtrar(n);
       tabla.setContenedor(auxJugadores);
       tabla.setChanged();
       refrescar();
    }
    
     public void borrar(int selectedRow) {
       if(estadoFiltro) 
           reestablecer();
       jugadores.eliminar(selectedRow);
       tabla.setChanged();
       refrescar();
    }
    
     public void agregar(Jugador p){
        if(estadoFiltro) 
           reestablecer();
        jugadores.agregar(p);
        tabla.setChanged();
        jugador=p;
        registrado=true;
        refrescar();
    }
    
     public void crearArchivoXML(){
         jugadores.guardarXml();
     }
     
     private void reestablecer(){
         tabla.setContenedor(jugadores);
         estadoFiltro=false;
     }
     
     public void leerXML(){
        File file = new File("jugadores.xml");
        Document documentoXML = UtilidadesXML.crearXMLDocumento(file);
        Node raiz = documentoXML.getDocumentElement();
        jugadores.leerXML(raiz);
        tabla.setChanged();
        refrescar();
     }

    public boolean isModoDatos() {
        return modoDatos;
    }

     public void activarModoDatos(){
         modoDatos=true;
         actualizar("modo Datos on");
     }
     
    public void activaModoJuego() {
        modoDatos=false;
        actualizar("Juego activo");
    }

    public boolean isRegistrado() {
        return registrado;
    }
    
    //-------------------atributos
    private Jugador jugador;
    

    private final  Piezas piezas;
    private int cantidadPiezas;
    private boolean aumentar;
    private boolean finalizo;
    
    private final TablaModel tabla;
    private final Jugadores  jugadores;
    private Jugadores  auxJugadores;
    private boolean estadoFiltro;
    private boolean modoDatos;
    private boolean registrado;
    boolean temp;
    URL url2 = this.getClass().getResource("../audios/g.wav");
    private final AudioClip audio1 = Applet.newAudioClip(url2);
    
    URL url3 = this.getClass().getResource("../audios/m.wav");
    private final AudioClip audio2 = Applet.newAudioClip(url3);
    
    
 }
