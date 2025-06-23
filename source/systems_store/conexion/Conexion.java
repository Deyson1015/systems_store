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
            // Cargar el driver JDBC de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver no encontrado: " + e.getMessage());
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
	 