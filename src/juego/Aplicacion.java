
/*

INTEGRANTES:
116830603
Merlyn Prendas Barboza.
114370830
Manrique Arrieta Alfaro.

PROFESORA:
Jennifer Fuentes.

*/



package juego;

import juego.control.Control;
import juego.vista.Vista;
import juego.vista.VistaDatos;


/**
 *
 * @author xxxx
 */

public class Aplicacion {
   public static int CANT_PIEZAS=8; 
   
    public static void main (String []args){
     Control c=new Control();
     Vista v=new Vista(c);
     VistaDatos vs=new VistaDatos(c);
    }
   
}