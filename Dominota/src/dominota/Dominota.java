/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominota;

import controlador.Operaciones;
import javax.sound.midi.VoiceStatus;

/**
 *
 * @author Yuleidy
 */
public class Dominota {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System. out. println("Hola mundo");
        //Operaciones operacion= new Operaciones();
        //operacion.AgregarUsuario();
        new vista.NuevoJuagador().setVisible(true);
    }
    
}
