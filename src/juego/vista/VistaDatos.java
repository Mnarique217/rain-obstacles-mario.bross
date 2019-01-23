 
package juego.vista;
import juego.control.Control;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.table.DefaultTableModel;
import juego.entidades.Jugador;
import juego.modelo.Modelo;


public final class VistaDatos extends JFrame implements Observer{

    public VistaDatos(Control c) {
      this.control=c;
      iniciar();
      control.addObserver(this);
    }

    public void iniciar(){
      configInicial();
      agregarComponentes(this);
     
    }
    
     private void mostrar(boolean b) {
         this.setVisible(b);
     }
   
    private void configInicial(){
       this.setSize(700,400);
       this.setLocationRelativeTo(null);
       this.setResizable(false);
       this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
       this.addWindowListener(new WindowAdapter() {
       
           @Override
           public void windowClosing(WindowEvent e) {
               mostrar(false);
               control.activarModoJuego();
           }
       });
    }
    
    private void agregarComponentes(Container c){
        panelPrincipalIniciar();
        panelIzquierdo();
        panelDerecho();
        c.add(pnlPrincipal);
    }

    private void panelIzquierdo(){
     pnlIzquierdo=new JPanel(new BorderLayout());
    
     pnlIzquierdo.setBorder(BorderFactory.createEtchedBorder());
     pnlIzAux=new JPanel(new GridBagLayout());
      pnlIzAux.setBorder(BorderFactory.createTitledBorder("agregar Jugador")); 
      
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets=new Insets(3,1,5,3);
        gbc.gridx = 0;//cols
        gbc.gridy = 0;//fils
       
        pnlIzAux.add(new JLabel("Nombre: "),gbc);
        gbc.gridx = 1;
        txtNombre=new JTextField(10);
        pnlIzAux.add(txtNombre,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        pnlIzAux.add(new JLabel("NickName: "),gbc);
        gbc.gridx = 1;
        gbc.gridwidth=4;
        txtNick=new JTextField(15);
        pnlIzAux.add(txtNick,gbc);
        gbc.gridwidth=1;
         
        gbc.gridx =0;
        gbc.gridy=3;

   
        gbc.gridx =0;
        gbc.gridy =4;
        btnGuardar=new JButton(new ImageIcon(getClass().getResource("../icons/guardar.png")));
        addActionListener(btnGuardar);
       btnGuardar .setText("guardar");
        btnGuardar.setActionCommand("GUARDAR");
        pnlIzAux.add(btnGuardar,gbc);
        gbc.gridx =1;
       
        btnCancel=new JButton(new ImageIcon(getClass().getResource("../icons/cancel.png")));
        btnCancel.setText("borrar");
        addActionListener(btnCancel);
        btnCancel.setActionCommand("BORRAR");
        pnlIzAux.add(btnCancel,gbc);
        
     pnlIzquierdo.add(pnlIzAux,BorderLayout.NORTH);
     pnlPrincipal.add(pnlIzquierdo,BorderLayout.LINE_START);
    }
    
    private void panelDerecho(){
     
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets=new Insets(3,1,5,3);
        gbc.gridx = 0;//cols
        gbc.gridy = 0;//fils
        
         pnlDerecho=new JPanel(new BorderLayout());
         pnlDerecAux=new JPanel(new GridBagLayout());
                 
         pnlDerecAux.setBorder(BorderFactory.createTitledBorder("Record Jugadores"));
         pnlDerecho.setBorder(BorderFactory.createEtchedBorder());
         
         model = new DefaultTableModel();
         model.setColumnIdentifiers(nombres);
         
         tabla=new JTable();
         tabla.setModel(control.tablaModel());
         scroll=new JScrollPane(tabla);
         tabla.setAutoCreateRowSorter(false); // ordenar las filas auto
         btnXML=new JButton(new ImageIcon(getClass().getResource("../icons/ex.png")));
         btnXML.setText("guardar xml");
         btnXML.setActionCommand("XML");
         addActionListener(btnXML);
         pnlDerecAux.add(btnXML,gbc);
         gbc.gridx=1;    
         
         btnLeerXML=new JButton(new ImageIcon(getClass().getResource("../icons/up.png")));
         btnLeerXML.setText("leer xml");
         btnLeerXML.setActionCommand("LeerXML");
         addActionListener(btnLeerXML);
         pnlDerecAux.add(btnLeerXML,gbc);

         gbc.gridx=4;
         btnFiltro=new JButton(new ImageIcon(getClass().getResource("../icons/iconoLupa.png")));
         btnFiltro.setActionCommand("FILTRO");
         addActionListener(btnFiltro);
         pnlDerecAux.add(btnFiltro,gbc);
         
         gbc.gridx=5;
         txtFiltro=new JTextField(10);
         pnlDerecAux.add(txtFiltro,gbc);
         
         pnlDerecho.add(pnlDerecAux,BorderLayout.NORTH);
         pnlDerecho.add(scroll,BorderLayout.CENTER);
         
         pnlPrincipal.add(pnlDerecho,BorderLayout.CENTER);
     }
     
    
    public void addActionListener(JButton boton){
        
        boton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                filtroAccion(e);
            }
        });
    }
    
    public void filtroAccion(ActionEvent e){
       
        switch(e.getActionCommand()){
            case "GUARDAR" :valoresNuevaFila();break;
            case "BORRAR"  :borrar();break; 
            case "FILTRO"  :filtrado();break;
            case "XML"     :xml();break;
            case "LeerXML" :leerxml();break;
            
        }
    }        
    private void leerxml() {
        btnLeerXML.setEnabled(false);
        control.leerXml();
    }
    public void filtrado(){
    
        if(txtFiltro.getText().isEmpty())
        {mensaje("Que Filtras?","completar informacion ", 0);return;}
        control.filtrar(txtFiltro.getText());
    }
    
    public void valoresNuevaFila(){
   
     if(txtNombre.getText().isEmpty() || txtNick.getText().isEmpty() ){
     mensaje("completar informacion ","Informacion Jugador ", 0);return;}
     
     btnGuardar.setEnabled(false);
            String c=this.txtNombre.getText();
            String d=this.txtNick.getText();
           
           Jugador p= new Jugador(c,d,0);
           control.guardar(p);
           txtNombre.setText("");
           txtNick.setText("");
    }
    
   private void xml() {
       control.crearXml();
    }
    public void borrar(){
        
        if(tabla.getSelectedRow()>=0)
            control.borrar(tabla.getSelectedRow());
            else
            mensaje("seleccion de fila", "eliminar fila", 0);
    }
    
    public void mensaje(String contenido, String titulo,int icono){
    JOptionPane.showMessageDialog(this, contenido, titulo, icono);
    }
    private void panelPrincipalIniciar(){
    pnlPrincipal=new JPanel(new BorderLayout());
    pnlPrincipal.setLayout(new BorderLayout());
    pnlPrincipal.setBorder(BorderFactory.createEtchedBorder());
    pnlPrincipal.setBorder(BorderFactory.createTitledBorder("Record Jugadores"));   
    }

   
   //----------------------       update    ------------------------------------
    @Override
    public void update(Observable modelo, Object arg) {
        
        Modelo m=(Modelo)modelo;
        
     if(m.isModoDatos()){
        mostrar(m.isModoDatos());
        tabla.repaint();    
        }  
    } 

    
  
       //atributos
   private Control control;
   private JPanel pnlPrincipal;
   private JPanel pnlIzquierdo;
   private JPanel pnlIzAux;
   
   private JScrollPane scroll;
   private JTable tabla;
   private DefaultTableModel model ;
   
   private  String [] nombres={"Nombre","NickName","Puntaje"};
   private JPanel pnlDerecho;
   private JPanel pnlDerecAux;
   
   private JButton btnGuardar;
   private JButton btnCancel;
   private JButton btnXML;
   private JButton btnLeerXML;
   
   public JTextField txtNombre;
   public JTextField txtNick;
   public JTextField txtFiltro;
   
   private JButton btnFiltro;

   
   
}
