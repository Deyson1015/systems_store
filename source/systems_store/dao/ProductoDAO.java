package systems_store.dao;

// Importación de clases necesarias para trabajar con la base de datos y colecciones
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import systems_store.conexion.Conexion;
import systems_store.mundo.Marca;
import systems_store.mundo.Producto;

// Clase que se encarga de acceder a la base de datos para realizar operaciones CRUD con productos
public class ProductoDAO {

    /**
     * Agrega un nuevo producto a la base de datos.
     * @param producto Objeto Producto que se va a guardar.
     * @return true si se insertó correctamente, false si hubo un error.
     */
    public boolean agregarProducto(Producto producto) {
        String sql = "INSERT INTO productos(nombre, id_marca, descripcion, cantidad, precio, fecha_ingreso, foto) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConexion();  // Obtener la conexión
             PreparedStatement stmt = conn.prepareStatement(sql)) {  // Preparar sentencia SQL

            // Asignar los valores a los parámetros del SQL
            stmt.setString(1, producto.getNombre());
            stmt.setInt(2, producto.getMarca().getId());
            stmt.setString(3, producto.getDescripcion());
            stmt.setInt(4, producto.getCantidad());
            stmt.setDouble(5, producto.getPrecio());
            stmt.setDate(6, producto.getFechaIngreso()); 
            stmt.setString(7, producto.getFoto());
           
            

            // Ejecutar el INSERT y devolver si se insertó al menos una fila
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al agregar producto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza un producto existente en la base de datos.
     * @param producto Objeto Producto con los datos actualizados.
     * @return true si se actualizó correctamente, false si hubo un error.
     */
    public boolean actualizarProducto(Producto producto) {
        String sql = "UPDATE productos SET nombre=?, id_marca=?, descripcion=?, cantidad=?, precio=?, fecha_ingreso=?, foto=? WHERE id=?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Asignar valores a los parámetros
            stmt.setString(1, producto.getNombre());
            stmt.setInt(2, producto.getMarca().getId());
            stmt.setString(3, producto.getDescripcion());
            stmt.setInt(4, producto.getCantidad());
            stmt.setDouble(5, producto.getPrecio());
            stmt.setDate(6, producto.getFechaIngreso());
            stmt.setString(7, producto.getFoto());
            stmt.setInt(8, producto.getId());

            // Ejecutar el UPDATE
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un producto de la base de datos según su ID.
     * @param id Identificador del producto.
     * @return true si se eliminó, false si falló.
     */
    public boolean eliminarProducto(int id) {
        String sql = "DELETE FROM productos WHERE id=?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todos los productos de la base de datos.
     * @return Lista de productos encontrados.
     */
    public List<Producto> listarProductos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT p.nombre, p.precio, p.cantidad, p.foto, m.nombre AS nombre_marca " +
        	"FROM productos AS p " +
        	"JOIN marcas AS m ON p.id_marca = m.id"

;
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Iterar sobre los resultados y construir objetos Producto
            while (rs.next()) {
            	  Marca m = new Marca(
                		0,
                		rs.getString("nombre_marca"),
                		""
                   );
               
            	  Producto p = new Producto(
                      	0,
                      	rs.getString("nombre"),
                      	"",
                      	rs.getInt("cantidad"),
                      	rs.getDouble("precio"),
                      	null,
                      	rs.getString("foto"),
                      	m
                  );
                
                // Agregar 0el producto a la lista
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Obtiene un producto de la base de datos por su ID.
     * @param id Identificador del producto.
     * @return Objeto Producto si se encontró, o null si no existe.
     */
    public Producto obtenerProductoPorId(int id) {
        String sql = "SELECT p.id, p.nombre, p.descripcion, p.precio, p.cantidad, p.fecha_ingreso, p.foto, " +
                "m.id AS id_marca, m.nombre AS nombre_marca, m.descripcion AS descripcion_marca " +
                "FROM productos AS p " + 
                "JOIN marcas AS m ON p.id_marca = m.id " + 
                "WHERE p.id=?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
            	  Marca m = new Marca(
                  		rs.getInt("id"),
                  		rs.getString("nombre"),
                  		rs.getString("descripcion")
                  );
            	  
                Producto p = new Producto(
                	rs.getInt("id"),
                	rs.getString("nombre"),
                	rs.getString("descripcion"),
                	rs.getInt("cantidad"),
                	rs.getDouble("precio"),
                	rs.getDate("fecha_ingreso"),
                	rs.getString("foto"),
                	m
                );

                return p;
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener producto: " + e.getMessage());
        }
        return null;
    }
}
