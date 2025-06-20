package systems_store.mundo.java;


public class Marca{
	private int id;
	private String tipoMarca;
	
	public Marca( int id, String tipoMarca) {
		this.id = id;
		this.tipoMarca = tipoMarca;
	}

	public int getId() {return id;}
	public void setId(int id) {this.id = id;}

	public String getTipoMarca() {return tipoMarca;}
	public void setTipoMarca(String tipoMarca) {this.tipoMarca = tipoMarca;}
}