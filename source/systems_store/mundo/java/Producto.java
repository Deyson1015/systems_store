package systems_store.mundo.java;
/**
 * Clase que representa un producto almacenado en la tienda.<br> 
 */
public class Producto {
	private int id, cantidad;
	private double precio;
	private String nombre, descripcion, fechaIngreso, foto;
	private Categoria categoria;
	
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
	
	public String getFechaIngreso() { return fechaIngreso;}
	public void setFechaIngreso( String fechaIngreso) {this.fechaIngreso = fechaIngreso;}
	
	public String getFoto() { return foto;}
	public void setFoto(String foto) { this.foto = foto;}
	
	public Categoria getCategoria() { return categoria;}
	public void setCategoria(Categoria categoria) { this.categoria = categoria; }
	 	 	
			
}
	
	
	

