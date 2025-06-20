package systems_store.mundo.java;

public class Categoria {
    private int id;
    private String tipoProducto;
    
    public Categoria(int id, String tipoProducto) {
		this.id = id;
		this.tipoProducto = tipoProducto;
	}
    
	public int getId() { return id; }
    public void setId(int id) {this.id=id;}
    
    public String getTipoProducto() { return tipoProducto; }
    public void setTipoProducto(String tipoProducto ) {this.tipoProducto=tipoProducto;}
}
