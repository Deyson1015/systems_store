package systems_store.mundo.java;
/**
 * Clase que representa un producto almacenado en la tienda.<br> 
 */
import java.sql.Date;

public class Producto {
	private int id, cantidad;
	private double precio;
	private Date fechaIngreso;
	private String nombre, descripcion, foto;
	private Categoria categoria;
	private Marca marca;
	
	public int getId() { return id;}
	public void setId(int id) { this.id = id;}
	
	public String getNombre() { return nombre;}
	public void setNombre(String nombre) { this.nombre = nombre;}
	
	public String getDescripcion() { return descripcion;}
	public void setDescripcion(String descripcion) { this.descripcion = descripcion;}
	
	public int getCantidad() { return cantidad;}
	public void setCantidad(int cantidad) { this.cantidad = cantidad;}
	
	public double getPrecio() { return precio;}
	public void setPrecio(double precio) { this.precio = precio;}
	
	public Date getFechaIngreso() { return fechaIngreso;}
	public void setFechaIngreso( Date fechaIngreso) {this.fechaIngreso = fechaIngreso;}
	
	public String getFoto() { return foto;}
	public void setFoto(String foto) { this.foto = foto;}
	
	public Categoria getCategoria() { return categoria;}
	public void setCategoria(Categoria categoria) { this.categoria = categoria; }
	 	 	
	public Marca getMarca() { return marca;}
	public void setMarca( Marca marca) { this.marca = marca;}
			
}
	
	
	

