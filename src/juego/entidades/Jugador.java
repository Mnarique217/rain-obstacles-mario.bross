
package juego.entidades;
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import juego.xml.UtilidadesXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public final class Jugador{

    Image imagenMario;
    String mario;
    int posMario_H;
    int posMario_V;
    int cantVidas;
    
 
    public Jugador(String n, String nick, int puntos) {
        this.nombre = n;
        this.nickName = nick;
        this.puntaje = puntos;
        iniciarJugador();
    }

    public Jugador() {
        
        this.nombre = "";
        this.nickName = "";
        this.puntaje =0;
        iniciarJugador();
    }

    public void iniciarJugador() {
       cantVidas=3;
       mario = "../icons/p.png";
       ImageIcon img = new ImageIcon(getClass().getResource(mario));
       imagenMario = img.getImage();
       posMario_H=400;
       posMario_V=474;
    }

    public int getPosH() {
        return posMario_H;
    }

    public void setPosH(int posH) {
        this.posMario_H = posH;
    }

    public int getPosV() {
        return posMario_V;
    }

    public void setPosV(int posV) {
        this.posMario_V = posV;
    }

    public Image getImagenMario(){
        return imagenMario;
    }

    public void moverMario(KeyEvent e){
        
         int key = e.getKeyCode();
        
        if(key == KeyEvent.VK_LEFT)
            if(posMario_H>0){
              posMario_H += -50;
              mario = "../icons/iz.png";
              ImageIcon img = new ImageIcon(getClass().getResource(mario));
              imagenMario = img.getImage();
            }

        if(key == KeyEvent.VK_RIGHT)
             if(posMario_H<800){
              posMario_H += +50;
              mario = "../icons/dr.png";
              ImageIcon img = new ImageIcon(getClass().getResource(mario));
              imagenMario = img.getImage();
             }
        
        if(key == KeyEvent.VK_DOWN){
              mario = "../icons/p.png";
              ImageIcon img = new ImageIcon(getClass().getResource(mario));
              imagenMario = img.getImage();
             }
    } 

    public int getCantVidas() {
        return cantVidas;
    }

    public void setCantVidas(int cantVidas) {
        if(this.cantVidas-cantVidas>0)
         this.cantVidas += cantVidas;
    }

    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String n) {
        this.nombre = n;
    }

    public String getNick() {
        return nickName;
    }

    public void setDescripcion(String nick) {
        this.nickName = nick;
    }

    public int  getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int p) {
        this.puntaje += p;
    }
    
    public Object[] fullContenido(){
        Object[] r = new Object[3];
        r[0] = getNombre();
        r[1] = getNick();
        r[2] = getPuntaje();
        return r;
    }
     
    public static String[] getATRIBUTOS() {
        return ATRIBUTOS;
    }
     
    public Node toXML(Document doc) {
        //Aquí se le da el nombre de la etiqueta
        Node r = doc.createElement(getNodeName());
        r.appendChild(UtilidadesXML.crearNodo(doc, "apellido", nombre));
        r.appendChild(UtilidadesXML.crearNodo(doc, "nombre", nickName));
        r.appendChild(UtilidadesXML.crearNodo(doc, "nacionalidad", String.valueOf(puntaje)));
        return r;
    }

    public void leerXML(Node nodo) {        
        NodeList arbolEtiquetas = nodo.getChildNodes(); 
        nombre = ((Element)arbolEtiquetas.item(0)).getTextContent();
        nickName = ((Element)arbolEtiquetas.item(1)).getTextContent();
        puntaje = Integer.parseInt(((Element)arbolEtiquetas.item(2)).getTextContent()  );
    }
    private String getNodeName() {
        return DESCRIPCION_XML;
    }

    String nombre;
    String nickName;
    int puntaje;
    private static final String[] ATRIBUTOS = {"Nombre", "NickName", "Puntos"};
    public static final String    DESCRIPCION_XML = "Jugador";
    
}
