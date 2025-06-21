package systems_store.interfaz.java;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import systems_store.dao.java.ProductoDAO;
import systems_store.mundo.java.Categoria;
import systems_store.mundo.java.Marca;
import systems_store.mundo.java.Producto;

public class AgregarProducto extends JDialog implements ActionListener {

    private static final String AGREGAR = "Agregar";
    private static final String SELECCIONAR = "Seleccionar";

    private InterfazSystems_Store principal;

    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private JTextField txtCantidad;
    private JTextField txtPrecio;
    private JTextField txtFechaIngreso;
    private JTextField txtImagen;
    private JTextField txtCategoriaId;
    private JTextField txtMarcaId;

    private JButton btnAgregar;
    private JButton btnSeleccionar;

    public AgregarProducto(InterfazSystems_Store pPrincipal) {
        principal = pPrincipal;
        setLayout(new BorderLayout());
        setSize(500, 400);
        setModal(true);
        setLocationRelativeTo(null);
        setTitle("Agregar Producto");

        JPanel panelInfo = new JPanel(new GridLayout(9, 2));
        panelInfo.setBorder(new EmptyBorder(10, 10, 10, 10));

        panelInfo.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelInfo.add(txtNombre);

        panelInfo.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextField();
        panelInfo.add(txtDescripcion);

        panelInfo.add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField();
        panelInfo.add(txtCantidad);

        panelInfo.add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        panelInfo.add(txtPrecio);

        panelInfo.add(new JLabel("Fecha Ingreso (yyyy-mm-dd):"));
        txtFechaIngreso = new JTextField();
        panelInfo.add(txtFechaIngreso);

        panelInfo.add(new JLabel("Imagen:"));
        JPanel panelImagen = new JPanel(new BorderLayout());
        txtImagen = new JTextField();
        txtImagen.setEditable(false);
        btnSeleccionar = new JButton("Seleccionar");
        btnSeleccionar.setActionCommand(SELECCIONAR);
        btnSeleccionar.addActionListener(this);
        panelImagen.add(txtImagen, BorderLayout.CENTER);
        panelImagen.add(btnSeleccionar, BorderLayout.EAST);
        panelInfo.add(panelImagen);

        panelInfo.add(new JLabel("ID Categoría:"));
        txtCategoriaId = new JTextField();
        panelInfo.add(txtCategoriaId);

        panelInfo.add(new JLabel("ID Marca:"));
        txtMarcaId = new JTextField();
        panelInfo.add(txtMarcaId);

        add(panelInfo, BorderLayout.CENTER);

        btnAgregar = new JButton("Agregar Producto");
        btnAgregar.setActionCommand(AGREGAR);
        btnAgregar.addActionListener(this);
        add(btnAgregar, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.equals(SELECCIONAR)) {
            JFileChooser fileChooser = new JFileChooser("./data/imagenes");
            fileChooser.setDialogTitle("Seleccionar imagen");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            int resultado = fileChooser.showOpenDialog(this);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                File archivo = fileChooser.getSelectedFile();
                if (archivo.getName().endsWith(".jpg")) {
                    txtImagen.setText(archivo.getAbsolutePath());
                } else {
                    JOptionPane.showMessageDialog(this, "Debe seleccionar una imagen .jpg", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (comando.equals(AGREGAR)) {
            try {
                String nombre = txtNombre.getText();
                String descripcion = txtDescripcion.getText();
                int cantidad = Integer.parseInt(txtCantidad.getText());
                double precio = Double.parseDouble(txtPrecio.getText());
                Date fecha = Date.valueOf(txtFechaIngreso.getText());
                String foto = txtImagen.getText();
                int categoriaId = Integer.parseInt(txtCategoriaId.getText());
                int marcaId = Integer.parseInt(txtMarcaId.getText());

                Producto producto = new Producto();
                producto.setNombre(nombre);
                producto.setDescripcion(descripcion);
                producto.setCantidad(cantidad);
                producto.setPrecio(precio);
                producto.setFechaIngreso(fecha);
                producto.setFoto(foto);
                producto.setCategoria(new Categoria(categoriaId, ""));
                producto.setMarca(new Marca(marcaId, ""));

                ProductoDAO dao = new ProductoDAO();
                boolean exito = dao.agregarProducto(producto);

                if (exito) {
                    JOptionPane.showMessageDialog(this, "Producto agregado correctamente.");
                    dispose();
                    principal.actualizarLista();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos o incompletos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
