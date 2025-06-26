package systems_store.interfaz;

import systems_store.dao.ProductoDAO;
import systems_store.dao.MarcaDAO;
import systems_store.mundo.Marca;
import systems_store.mundo.Producto;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JDateChooser;
import java.sql.Date;
import java.util.List;

/**
 * Formulario para agregar o editar un producto.
 */


public class FormularioProducto extends JDialog implements ActionListener {
    private final static String GUARDAR = "Guardar";
    private final static String SELECCIONAR = "Seleccionar";
    
    private InterfazTienda principal;
    private JTextField txtNombre, txtDescripcion, txtCantidad, txtPrecio, txtFoto;
    private JDateChooser txtFecha;
    private JComboBox<String> cmbMarca;
    private Producto productoExistente;
    private JButton btnAgregar, btnCancelar, btnSeleccionar;
    
    public FormularioProducto(InterfazTienda principal, Producto productoExistente) {
        this.principal = principal;
        this.productoExistente = productoExistente;

        setLayout(new BorderLayout());
        setSize(450, 350);
        setModal(true);
        setLocationRelativeTo(null);
        setTitle("Formulario de producto");

        JPanel panelInfo1 = new JPanel();
        panelInfo1.setLayout(new GridLayout(8, 2, 5, 5));
        panelInfo1.setBorder(new EmptyBorder(10, 10, 10, 10));

        panelInfo1.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelInfo1.add(txtNombre);

        panelInfo1.add(new JLabel("Marca:"));
        cmbMarca = new JComboBox<>();
        cargarMarcas();
        panelInfo1.add(cmbMarca);

        panelInfo1.add(new JLabel("Descripción (Opcional):"));
        txtDescripcion = new JTextField();
        txtDescripcion.setToolTipText("Este campo puede dejarse vacío si no aplica");
        panelInfo1.add(txtDescripcion);

        panelInfo1.add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField();
        panelInfo1.add(txtCantidad);

        panelInfo1.add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        panelInfo1.add(txtPrecio);

        panelInfo1.add(new JLabel("Fecha ingreso:"));
        txtFecha = new JDateChooser();
        panelInfo1.add(txtFecha);
        
        panelInfo1.add(new JLabel("Foto:"));
        JPanel panelAuxiliar = new JPanel(new GridLayout(1, 2));
        txtFoto = new JTextField();
        txtFoto.setEditable(false);
        btnSeleccionar = new JButton("Seleccionar");
        btnSeleccionar.addActionListener(this);
        btnSeleccionar.setActionCommand(SELECCIONAR);
        panelAuxiliar.add(txtFoto);
        panelAuxiliar.add(btnSeleccionar);
        panelInfo1.add(panelAuxiliar);

        add(panelInfo1, BorderLayout.CENTER);

        btnAgregar = new JButton(GUARDAR);
        btnAgregar.addActionListener(this);
        btnAgregar.setActionCommand(GUARDAR);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(btnAgregar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        if (productoExistente != null) {
            txtNombre.setText(productoExistente.getNombre());
            txtDescripcion.setText(productoExistente.getDescripcion());
            txtCantidad.setText(String.valueOf(productoExistente.getCantidad()));
            txtPrecio.setText(String.valueOf(productoExistente.getPrecio()));
            txtFecha.setDate(productoExistente.getFechaIngreso());
            txtFoto.setText(productoExistente.getFoto());
            cmbMarca.setSelectedItem(productoExistente.getMarca().getNombre());
        }

        setVisible(true);
    }

    private void cargarMarcas() {
        MarcaDAO marcaDAO = new MarcaDAO();
        List<Marca> marcas = marcaDAO.listar();
        for (Marca marca : marcas) {
            cmbMarca.addItem(marca.getNombre());
        }
    }

    public void actionPerformed(ActionEvent pEvento) {
        String comando = pEvento.getActionCommand();

        if (comando.equals(SELECCIONAR)) {
            JFileChooser jf = new JFileChooser("./data/imagenes");
            jf.setDialogTitle("Seleccionar archivo");
            jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jf.setVisible(true);

            if (jf.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                String nombre = jf.getSelectedFile().getName();
                String[] formatosPermitidos = {".jpg", ".png"};
                boolean formatoValido = false;

                for (String ext : formatosPermitidos) {
                    if (nombre.endsWith(ext)) {
                        formatoValido = true;
                        break;
                    }
                }

                if (formatoValido) {
                    txtFoto.setText(nombre);
                } else {
                    JOptionPane.showMessageDialog(this, "Debe seleccionar un archivo en formato .jpg.", "Seleccionar imagen", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (comando.equals(GUARDAR)) {
            if (productoExistente == null) {
                agregarProducto();
            } else {
                actualizarProducto();
            }
        }
    }

    private void agregarProducto() {
        String nombre = txtNombre.getText().trim();
        String marcaSeleccionada = (String) cmbMarca.getSelectedItem();
        String descripcion = txtDescripcion.getText().trim();
        String cantidadStr = txtCantidad.getText().trim();
        String precioStr = txtPrecio.getText().trim();
        String rutaFoto = txtFoto.getText().trim();

        if (nombre.isEmpty() || marcaSeleccionada == null || cantidadStr.isEmpty() || precioStr.isEmpty() || rutaFoto.isEmpty() || txtFecha.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!nombre.matches("^[a-zA-Z0-9\\sáéíóúÁÉÍÓÚñÑ]+$")) {
            JOptionPane.showMessageDialog(this, "El nombre solo debe contener letras, números y espacios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int cantidad;
        Double precio;
        try {
            cantidad = Integer.parseInt(cantidadStr);
            precio = Double.parseDouble(precioStr);

            if (cantidad < 0 || precio < 0) {
                JOptionPane.showMessageDialog(this, "La cantidad y el precio deben ser números positivos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cantidad y precio deben ser valores numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date fechaIngreso = new Date(txtFecha.getDate().getTime());

        Date fechaActual = new Date(System.currentTimeMillis());
        if (fechaIngreso.after(fechaActual)) {
            JOptionPane.showMessageDialog(this, "La fecha no puede ser mayor a la fecha actual.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Marca marca = new MarcaDAO().obtenerMarcaPorNombre(marcaSeleccionada);

        Producto p = new Producto(
                0,  // ID se asignará automáticamente al agregar
                nombre,
                descripcion.isEmpty() ? null : descripcion,
                cantidad,
                precio,
                fechaIngreso,
                rutaFoto,
                marca
        );

        ProductoDAO dao = new ProductoDAO();
        boolean exito = dao.agregarProducto(p);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Producto guardado correctamente.");
            principal.cargarProductosDesdeBD();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el producto.");
        }
    }

    private void actualizarProducto() {
        String nombre = txtNombre.getText().trim();
        String marcaSeleccionada = (String) cmbMarca.getSelectedItem();
        String descripcion = txtDescripcion.getText().trim();
        String cantidadStr = txtCantidad.getText().trim();
        String precioStr = txtPrecio.getText().trim();
        String rutaFoto = txtFoto.getText().trim();

        if (nombre.isEmpty() || marcaSeleccionada == null || cantidadStr.isEmpty() || precioStr.isEmpty() || rutaFoto.isEmpty() || txtFecha.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int cantidad;
        Double precio;
        try {
            cantidad = Integer.parseInt(cantidadStr);
            precio = Double.parseDouble(precioStr);

            if (cantidad < 0 || precio < 0) {
                JOptionPane.showMessageDialog(this, "La cantidad y el precio deben ser números positivos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cantidad y precio deben ser valores numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date fechaIngreso = new Date(txtFecha.getDate().getTime());

        Date fechaActual = new Date(System.currentTimeMillis());
        if (fechaIngreso.after(fechaActual)) {
            JOptionPane.showMessageDialog(this, "La fecha no puede ser mayor a la fecha actual.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Marca marca = new MarcaDAO().obtenerMarcaPorNombre(marcaSeleccionada);

        Producto p = new Producto(
                productoExistente.getId(),
                nombre,
                descripcion.isEmpty() ? null : descripcion,
                cantidad,
                precio,
                fechaIngreso,
                rutaFoto,
                marca
        );

        ProductoDAO dao = new ProductoDAO();
        boolean exito = dao.actualizarProducto(p);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.");
            principal.cargarProductosDesdeBD();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar el producto.");
        }
    }
}
