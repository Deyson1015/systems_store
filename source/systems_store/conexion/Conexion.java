package systems_store.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	private static final String URL = "jdbc:mysql://localhost:3306/system_store";
	private static final String USER = "root";
	private static final String PASS = "";
	
   static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
        } catch (ClassNotFoundException e) {
            System.err.println("No se encontró el driver de MySQL: " + e.getMessage());
        }
    }
   
	public static Connection getConexion() {
		
		try {
			return DriverManager.getConnection(URL, USER, PASS);
			
		} catch (SQLException e) {
			System.out.println("Error de conexion" + e.getMessage());
			return null;
		}
	}
}
	 