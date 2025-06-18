package systems_store.dao.java;

// Importación de clases necesarias para trabajar con la base de datos y colecciones
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Importación de las clases del modelo y la conexión
import systems_store.mundo.java.Producto;
import systems_store.mundo.java.Categoria;
import systems_store.conexion.java.Conexion;

// Clase que se encarga de acceder a la base de datos para realizar operaciones CRUD con productos
public class ProductoDAO {

    /**
     * Agrega un nuevo producto a la base de datos.
     * @param producto Objeto Producto que se va a guardar.
     * @return true si se insertó correctamente, false si hubo un error.
     */
    public boolean agregarProducto(Producto producto) {
        String sql = "INSERT INTO productos(nombre, descripcion, cantidad, precio, fecha_ingreso, foto, categoria_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConexion();  // Obtener la conexión
             PreparedStatement stmt = conn.prepareStatement(sql)) {  // Preparar sentencia SQL

            // Asignar los valores a los parámetros del SQL
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setInt(3, producto.getCantidad());
            stmt.setDouble(4, producto.getPrecio());
            stmt.setString(5, producto.getFechaIngreso());
            stmt.setString(6, producto.getFoto());
            stmt.setInt(7, producto.getCategoria().getId());

            // Ejecutar el INSERT y devolver si se insertó al menos una fila
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al agregar producto: " + e.getMessage());
            return false;
        }
    }

}