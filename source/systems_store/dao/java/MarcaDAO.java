package systems_store.dao.java;

import systems_store.conexion.java.Conexion;
import systems_store.mundo.java.Marca;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public  class MarcaDAO {
	
	public List<Marca> listar() {
		
		List<Marca> marcas = new ArrayList<>();
		String sql = "SELECT * FROM marcas ORDER BY nombre"; 
		
		try (Connection conn = Conexion.getConexion();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				String descripcion = rs.getString("descripcion");
				Marca m = new Marca(id, nombre, descripcion);
				marcas.add(m);
			}
			
		} catch (SQLException e) {
			System.out.println("Error al listar marcas: " + e.getMessage());
		}
		return marcas;	
	}
}
