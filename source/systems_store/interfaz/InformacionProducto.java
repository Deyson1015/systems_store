package systems_store.interfaz;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * Panel con la información detallada de un producto.
 */
public class InformacionProducto extends JPanel {

    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private JTextField txtCantidad;
    private JTextField txtPrecio;
    private JTextField txtFechaIngreso;
    private JTextField txtMarca;

    private JLabel lblImagen;

    public InformacionProducto() {
        setBorder(new TitledBorder(" Información del producto "));

        JPanel panelAuxiliar = new JPanel();
        panelAuxiliar.setLayout(new GridBagLayout());
        setLayout(new BorderLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.insets = new Insets(5, 3, 5, 3);

        gbc.gridy = 0;
        gbc.gridx = 0;
        panelAuxiliar.add(new JLabel(" Nombre: "), gbc);
        gbc.gridy++;
        panelAuxiliar.add(new JLabel(" Descripción: "), gbc);
        gbc.gridy++;
        panelAuxiliar.add(new JLabel(" Marca: "), gbc);
        gbc.gridy++;
        panelAuxiliar.add(new JLabel(" Cantidad: "), gbc);
        gbc.gridy++;
        panelAuxiliar.add(new JLabel(" Precio: "), gbc);
        gbc.gridy++;
        panelAuxiliar.add(new JLabel(" Fecha de ingreso: "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        txtNombre = new JTextField();
        txtNombre.setEditable(false);
        panelAuxiliar.add(txtNombre, gbc);
        
        gbc.gridy++;
        txtDescripcion = new JTextField();
        txtDescripcion.setEditable(false);
        panelAuxiliar.add(txtDescripcion, gbc);

        gbc.gridy++;
        txtMarca = new JTextField();
        txtMarca.setEditable(false);
        panelAuxiliar.add(txtMarca, gbc);
        
        gbc.gridy++;
        txtCantidad = new JTextField();
        txtCantidad.setEditable(false);
        panelAuxiliar.add(txtCantidad, gbc);

        gbc.gridy++;
        txtPrecio = new JTextField();
        txtPrecio.setEditable(false);
        panelAuxiliar.add(txtPrecio, gbc);

        gbc.gridy++;
        txtFechaIngreso = new JTextField();
        txtFechaIngreso.setEditable(false);
        panelAuxiliar.add(txtFechaIngreso, gbc);

        add(panelAuxiliar, BorderLayout.CENTER);

        lblImagen = new JLabel();
        add(lblImagen, BorderLayout.EAST);
    }

    /**
     * Actualiza la información del producto.
     */
    public void actualizarInformacion(String nombre, String descripcion, int cantidad, double precio,
                                      String fechaIngreso, String marca, String imagenRuta) {
        txtNombre.setText(nombre);
        txtMarca.setText(marca);
        txtDescripcion.setText(descripcion);
        txtCantidad.setText(String.valueOf(cantidad));
        txtPrecio.setText(String.valueOf(precio));
        txtFechaIngreso.setText(fechaIngreso);

        String rutaCompleta = "./data/imagenes/" + imagenRuta;
        ImageIcon icono = new ImageIcon(rutaCompleta);

        Image imagen = icono.getImage().getScaledInstance(180, 250, Image.SCALE_SMOOTH);
        lblImagen.setIcon(new ImageIcon(imagen));
    }
}
