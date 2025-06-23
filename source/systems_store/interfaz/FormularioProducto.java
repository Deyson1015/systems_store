package systems_store.interfaz;

import systems_store.dao.ProductoDAO;
import systems_store.mundo.Marca;
import systems_store.mundo.Producto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.Date;

public class FormularioProducto extends JDialog {

    private JTextField txtNombre, txtDescripcion, txtCantidad, txtPrecio, txtFecha, txtFoto;
    private JComboBox<String> cmbMarca;
    private JButton btnGuardar;
    private Producto productoExistente;
    private InterfazTienda principal;

    public FormularioProducto(JFrame padre, InterfazTienda principal, Producto productoExistente) {
        super(padre, "Formulario de Producto", true);
        this.productoExistente = productoExistente;
        this.principal = principal;

        setSize(400, 400);
        setLocationRelativeTo(padre);
        setLayout(new GridLayout(9, 2, 5, 5));

        add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel("Descripción:"));
        txtDescripcion = new JTextField();
        add(txtDescripcion);

        add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField();
        add(txtCantidad);

        add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        add(txtPrecio);

        add(new JLabel("Fecha ingreso (yyyy-mm-dd):"));
        txtFecha = new JTextField();
        add(txtFecha);

        add(new JLabel("Ruta Imagen:"));
        txtFoto = new JTextField();
        add(txtFoto);

        add(new JLabel("Marca:"));
        cmbMarca = new JComboBox<>(new String[]{"Samsung", "Apple", "Xiaomi", "Oppo", "Vivo", "Honor", "Huawei"});
        add(cmbMarca);

        btnGuardar = new JButton("Guardar");
        add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        add(btnCancelar);

        if (productoExistente != null) {
            txtNombre.setText(productoExistente.getNombre());
            txtDescripcion.setText(productoExistente.getDescripcion());
            txtCantidad.setText(String.valueOf(productoExistente.getCantidad()));
            txtPrecio.setText(String.valueOf(productoExistente.getPrecio()));
            txtFecha.setText(String.valueOf(productoExistente.getFechaIngreso()));
            txtFoto.setText(productoExistente.getFoto());
            cmbMarca.setSelectedItem(productoExistente.getMarca().getNombre());
        }

        btnGuardar.addActionListener(e -> guardarProducto());
        btnCancelar.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void guardarProducto() {
        try {
            String nombre = txtNombre.getText();
            String descripcion = txtDescripcion.getText();
            int cantidad = Integer.parseInt(txtCantidad.getText());
            double precio = Double.parseDouble(txtPrecio.getText());
            Date fechaIngreso = Date.valueOf(txtFecha.getText());
            String rutaFoto = txtFoto.getText();
            String marcaSeleccionada = (String) cmbMarca.getSelectedItem();

            Marca marca = new Marca(1, marcaSeleccionada, "Simulado");

            Producto p = new Producto(
                productoExistente != null ? productoExistente.getId() : 0,
                nombre,
                descripcion,
                cantidad,
                precio,
                fechaIngreso,
                rutaFoto,
                marca
            );

            ProductoDAO dao = new ProductoDAO();
            boolean exito = (productoExistente == null) ? dao.agregarProducto(p) : dao.actualizarProducto(p);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Producto guardado correctamente");
                principal.cargarProductosDesdeBD();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el producto");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
