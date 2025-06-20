package systems_store.dao.java;

import systems_store.conexion.java.Conexion;
import systems_store.mundo.java.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public  class CategoriaDAO {
	
	public List<Categoria> listar() {
		
		List<Categoria> categorias = new ArrayList<>();
		String sql = "SELECT * FROM categorias"; 
		
		try (Connection conn = Conexion.getConexion();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String tipoProducto = rs.getString("tipo_producto");
				Categoria categoria = new Categoria(id, tipoProducto);
				categorias.add(categoria);
			}
			
		} catch (SQLException e) {
			System.out.println("Error al listar categoría: " + e.getMessage());
		}
		return categorias;	
	}
}
