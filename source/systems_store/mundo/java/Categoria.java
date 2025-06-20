package systems_store.mundo.java;

public class Categoria {
    private int id;
    private String nombre, descripcion;
    
    public Categoria(int id, String nombre, String descripcion ) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
    
	public int getId() { return id; }
    public void setId(int id) {this.id=id;}
    
    public String getTipoProducto() { return nombre; }
    public void setTipoProducto(String tipoProducto ) {this.nombre=nombre;}
    
    
}
