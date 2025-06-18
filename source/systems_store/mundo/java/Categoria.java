package systems_store.mundo.java;

public class Categoria {
    private int id;
    private String tipoProducto;
    private String descripcion;
    


    public Categoria() {
  
    }

    public int getId() { return id; }
    public void setId(int id) {this.id=id;}
    
    public String getTipoProducto() { return tipoProducto; }
    public void setTipoProducto(String tipoProducto ) {this.tipoProducto=tipoProducto;}
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) {this.descripcion=descripcion;}
}
