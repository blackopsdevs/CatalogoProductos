package com.comunidad.simplecatproducto;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        System.out.println("********************************");
        System.out.println("Iniciando sistema");
        System.out.println("********************************");
        try {
            String homeSistema = System.getProperty("user.dir");
            File directorio = new File(homeSistema + "/temp");
            Date fechaActual = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (!directorio.exists()) {
                if (directorio.mkdirs()) {
                    System.out.println("Directorio creado -> " + directorio);
                }
            }
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new SistemaMainFrame().setVisible(true);
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }
}
