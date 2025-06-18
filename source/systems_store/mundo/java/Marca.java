package systems_store.mundo.java;


public class Marca{
	private int id;
	private String tipoMarca, descripcion;
	
	public Marca( int id, String tipoMarca, String descripcion) {
		this.id = id;
		this.tipoMarca = tipoMarca;
		this.descripcion = descripcion;
	}
	
	public int getId() { return id;}
	public String getTipoMarca() { return tipoMarca;}
	public String getDescripcion() { return descripcion;}
	
}