

package juego.vista;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import juego.entidades.*;



public class PanelImagen extends JPanel   {
     
    public PanelImagen( ) { 
       size=1;
    }

    void reiniciar() {
        size=1;
    }

    
   @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        dibujarImagenes(g);
    }

    public void dibujarImagenes(Graphics g) {
        imagen = new ImageIcon(new ImageIcon(getClass().getResource("../icons/cs.png")).getImage());
        g.drawImage(imagen.getImage(), 0, 0, getWidth(), getHeight(), null);
        g.drawImage(jugador.getImagenMario(), jugador.getPosH(),jugador.getPosV(), 125, 125, this);
        
        for(int i=0; i<size; i++){
            Image img=piezas.obtenerPieza(i).getImagen();
            int posHor  =piezas.obtenerPieza(i).getPosH();
            int posVert =piezas.obtenerPieza(i).getPosV();
            int tamaH=piezas.obtenerPieza(i).getTamaH();
            int tamaV=piezas.obtenerPieza(i).getTamaV();
            g.drawImage(img, posHor,posVert, tamaH, tamaV, this);
        }
    }

    void piezaActual(Piezas piezas) {
        this.piezas=piezas;
    }

    void marioActual(Jugador jugador) {
        this.jugador=jugador;
    }
    
    void jugadorActual(Jugador jugador) {
        this.jugador=jugador;
    }

    void piezasActuales(Piezas piezas) {
        this.piezas=piezas;
    }

    public void setSize(int size) {
        this.size = size;
    }

    void setCantidadPiezas(int cantidadPiezas) {
        size=cantidadPiezas;
    }
    ///atributos
     private  ImageIcon imagen;
     private  Piezas piezas;
     private  Jugador jugador;
     private int size;

    }
