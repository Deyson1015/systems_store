package systems_store.mundo.java;

public class Categoria {
    private int id;
    private String tipoProducto;
    private String descripcion;

    public Categoria(int id, String tipoProducto, String descripcion) {
        this.id = id;
        this.tipoProducto = tipoProducto;
        this.descripcion = descripcion;
    }

    public int getId() { return id; }
    public String getTipoProducto() { return tipoProducto; }
    public String getDescripcion() { return descripcion; }
}
