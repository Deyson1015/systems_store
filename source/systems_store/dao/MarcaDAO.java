package systems_store.dao;

import systems_store.conexion.Conexion;
import systems_store.mundo.Marca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public  class MarcaDAO {
	
	public List<Marca> listar() {
		
		List<Marca> marcas = new ArrayList<>();
		String sql = "SELECT id, nombre FROM marcas"; 
		
		try (Connection conn = Conexion.getConexion();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
				ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				
				Marca m = new Marca(
						rs.getInt("id"),
						rs.getString("nombre")
					);
				marcas.add(m);
			}
			
		} catch (SQLException e) {
			System.out.println("Error al listar marcas: " + e.getMessage());
		}
		return marcas;	
	}
	
	public Marca obtenerMarcaPorNombre(String nombreMarca) {
	    String sql = "SELECT * FROM marcas WHERE nombre = ?";
	    try (Connection conn = Conexion.getConexion();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setString(1, nombreMarca);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            return new Marca(
	                rs.getInt("id"),
	                rs.getString("nombre")
	            );
	        }
	    } catch (SQLException e) {
	        System.out.println("Error al obtener marca: " + e.getMessage());
	    }
	    return null; // Retorna null si no encuentra la marca
	}
	
}
