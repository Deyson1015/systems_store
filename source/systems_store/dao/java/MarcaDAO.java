package systems_store.dao.java;

import systems_store.conexion.java.Conexion;
import systems_store.mundo.java.Marca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public  class MarcaDAO {
	
	public List<Marca> listar() {
		
		List<Marca> marcas = new ArrayList<>();
		String sql = "SELECT * FROM Marcas"; 
		
		try (Connection conn = Conexion.getConexion();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String tipoMarca = rs.getString("tipo_marca");
				Marca marca = new Marca(id, tipoMarca);
				marcas.add(marca);
			}
			
		} catch (SQLException e) {
			System.out.println("Error al listar categoría: " + e.getMessage());
		}
		return marcas;	
	}
}
