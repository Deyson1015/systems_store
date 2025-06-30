package systems_store.mundo;
/**
 * Clase que representa un producto almacenado en la tienda.<br> 
 */
import java.sql.Date;

public class Producto {
	private int id, cantidad;
	private double precio;
	private Date fechaIngreso;
	private String nombre, descripcion, foto;
	private Marca marca;
	
	public Producto(int id, String pNombre,  String pDescripcion, int pCantidad, double pPrecio, Date pFechaIngreso,
			String pFoto, Marca pMarca) {
		this.id = id;
		this.nombre = pNombre != null ? pNombre.toUpperCase() : "";;
		this.descripcion = pDescripcion != null ? pDescripcion.toUpperCase() : "";
		this.cantidad = pCantidad;
		this.precio = pPrecio;
		this.fechaIngreso = pFechaIngreso;
		this.foto = pFoto;
		this.marca = pMarca;
	}
	
	public int getId() { return id;}
	public void setId(int id) { this.id = id;}
	
	public String getNombre() { return nombre;}
	public void setNombre(String pNombre) { this.nombre = pNombre;}
	
	public String getDescripcion() { return descripcion;}
	public void setDescripcion(String pDescripcion) { this.descripcion = pDescripcion;}
	
	public int getCantidad() { return cantidad;}
	public void setCantidad(int pCantidad) { this.cantidad = pCantidad;}
	
	public double getPrecio() { return precio;}
	public void setPrecio(double pPrecio) { this.precio = pPrecio;}
	
	public Date getFechaIngreso() { return fechaIngreso;}
	public void setFechaIngreso( Date pFechaIngreso) {this.fechaIngreso = pFechaIngreso;}
	
	public String getFoto() { return foto;}
	public void setFoto(String pFoto) { this.foto = pFoto;}
	 	 	
	public Marca getMarca() { return marca;}
	public void setMarca( Marca pMarca) { this.marca = pMarca;}
		
}
	
	
	

