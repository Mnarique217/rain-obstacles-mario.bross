
package juego.vista;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import juego.control.Control;
import juego.modelo.Modelo;

/**
 *
 * @author xxxx
 */
public class Vista extends JFrame implements Observer ,Runnable,ActionListener{


    private final Control control;
    private PanelImagen fondo;
    private  Thread hilo;
    private JPanel pnlInferior;
    private JLabel lblUpdate;
    private JLabel lbPuntaje;
    private JLabel lbPuntos;
    private JButton btnLanzar;
    private JButton btnReiniciar;
    private int velocidad;
    private final Timer t;
    private JButton btnCorazon;
    private JButton btnDatos;
    URL url1 = this.getClass().getResource("../audios/mario.wav");
    private final AudioClip audio = Applet.newAudioClip(url1);
    boolean seguir;

    

    public Vista(Control control) {

        seguir=true;
        this.control=control;
        t=new Timer(5000, this);
        t.start();
        velocidad=800;
        configInial();
        agregarComponentes();
        agregar();
        iniciar();
    }

    private void mostrar(){
        this.setVisible(true);
    }
    
    private  void iniciar(){
        mostrar();
        
    }
    
    private void configInial(){
        this.setSize(900,700);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); 
    }

    private void agregarComponentes() {
        
         btnCorazon=new JButton("3");
         Container C=getContentPane();
         fondo=new PanelImagen();
         pnlInferior=new JPanel(new FlowLayout(FlowLayout.LEFT));
         
         btnLanzar   =new JButton("Lanzar");
         btnReiniciar=new JButton("Reiniciar");
         lblUpdate=new JLabel("Loading...");
         lblUpdate.setForeground(Color.white);
         btnCorazon.setIcon(new ImageIcon(getClass().getResource("../icons/corazon.png")));
         btnCorazon.setForeground(Color.white);
         btnCorazon.setContentAreaFilled(false);
         btnCorazon.setBorder(null);
         lbPuntaje=new JLabel("Puntaje: ");
         lbPuntos=new JLabel();
         lbPuntaje.setForeground(Color.WHITE);
         lbPuntos.setForeground(Color.WHITE);
         Insets i=new Insets(0,0,0,0);
         btnLanzar.setMargin(i);
         btnReiniciar.setMargin(i);
         pnlInferior.add(btnLanzar);
         pnlInferior.add(btnReiniciar);
         pnlInferior.add(lblUpdate);
         pnlInferior.add(btnCorazon);
         pnlInferior.add(lbPuntaje);
         pnlInferior.add(lbPuntos);

         btnDatos=new JButton("add Jugador" );
         btnDatos.setMargin(i);
         JPanel pnl=new JPanel(new BorderLayout());
         JPanel pnl2=new JPanel(new FlowLayout());
         pnl2.add(btnDatos);
         
         pnl.add(pnlInferior,BorderLayout.LINE_START);
         pnl.add(pnl2,BorderLayout.LINE_END);
         
         pnl.setBackground(Color.black);
         pnl2.setBackground(Color.black);
         pnlInferior.setBackground(Color.black);
         addListerner();
         C.add(pnl,BorderLayout.PAGE_END);
         C.add(fondo);

    }
    
    public void addListerner(){
    
     
            
        btnLanzar.addActionListener((ActionEvent e) -> {
             
             audio.play();
             hilo=new Thread(this);
             hilo.start();
        });
    
        btnReiniciar.addActionListener((ActionEvent e) -> {
           audio.stop();
           reiniciar();
        });

         btnDatos.addActionListener((ActionEvent e) -> {
           control.activarModoDatos();
        });
    }
    
    private void reiniciar(){
    
        this.dentenerHilo();
           control.reiniciarMarcador();
           velocidad=800;
           fondo.reiniciar();
           fondo.repaint();
    }
    @Override
    public void run() {
        
        while(hilo==Thread.currentThread() ){
              control.mover();
              control.revisar();
              fondo.repaint();
              setFocus();
            try {
                
                hilo.sleep(velocidad);
            } catch (InterruptedException ex) {
            }

        }
    }
    
    @Override
    public boolean isFocusable() {
        return true;
    }
    
     private void setFocus(){
     this.requestFocus();
    }
    
    private void agregar() {
        this.setFocusable(true);
        this.control.addObserver(this);
        this. addKeyListener(new Teclado());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         
        if(velocidad-50>0)
            velocidad-=50;
    }

    private class Teclado extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            
            if(e.getKeyCode()== KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_DOWN)
             control.mover(e);
            else
                JOptionPane.showMessageDialog(null, "? ->"+e.getKeyChar());
            
             fondo.repaint();      
        }
    }

   private  void dentenerHilo() {
        hilo=null;
    }
    
    @Override
    public void update(Observable o, Object arg) {
       Modelo m=(Modelo )o;
      
       if(m.isModoDatos())
           this.setEnabled(false);
       else
        this.setEnabled(true);
       
       btnCorazon.setText(" " +String.valueOf(m.getJugador().getCantVidas()));
       
                if(m.isFinalizo())
                 {
                     audio.stop();
                     hilo=null;
                     fondo.setCantidadPiezas( m.getCantidadPiezas()); 
                     lblUpdate.setText(("  "));
                 }

                if(m.isAumentar()){
                   fondo.setCantidadPiezas( m.getCantidadPiezas()); }

                if(arg instanceof Integer){
                    this.lbPuntos.setText(String.valueOf(arg));
                }


                if(arg instanceof String)
                    lblUpdate.setText((String )arg);
          
       

                fondo.jugadorActual(m.getJugador());
                fondo.piezasActuales(m.getPiezas());
                setFocus();
    
                
    }
     
   
}

 