/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author david
 */
public class CConexion {
   Connection conectar = null; 
   
    String usuario ="root";
    String contrasena ="roma123"; 
    String bd="sistema_ventaselec";
    String ip="localhost";
    String puerto="3306";
    
    String cadena = "jdbc:mysql://"+ip+":"+puerto+"/"+bd;
    
    public Connection estableceConexion () {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            
            conectar=DriverManager.getConnection (cadena, usuario, contrasena);
            //JOptionPane.showMessageDialog(null, "Se conectó correctamente a la base de datos ");//
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se conectó a la base de datos, error: "+e.toString());
        }
        
        return conectar;
    }
     
}
