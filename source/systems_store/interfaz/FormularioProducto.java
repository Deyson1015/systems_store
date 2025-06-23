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

public class FormularioProducto extends JDialog implements ActionListener {

    private final static String AGREGAR = "Agregar";
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

        // Campos
        panelInfo1.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelInfo1.add(txtNombre);

        panelInfo1.add(new JLabel("Marca:"));
        cmbMarca = new JComboBox<>();
        cargarMarcas();
        panelInfo1.add(cmbMarca);

        panelInfo1.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextField();
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

        // Agregar panel de info al centro
        add(panelInfo1, BorderLayout.CENTER);

        // Botones
        btnAgregar = new JButton(AGREGAR);
        btnAgregar.addActionListener(this);
        btnAgregar.setActionCommand(AGREGAR);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(btnAgregar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

        // Cargar datos si se va a editar
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
        } else if (comando.equals(AGREGAR)) {
            String nombre = txtNombre.getText();
            String marcaSeleccionada = (String) cmbMarca.getSelectedItem();
            String descripcion = txtDescripcion.getText();
            String cantidadStr = txtCantidad.getText();
            String precioStr = txtPrecio.getText();
            String rutaFoto = txtFoto.getText();

            if (txtFecha.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una fecha válida.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Date fechaIngreso = new Date(txtFecha.getDate().getTime());

            if (!nombre.isEmpty() && !descripcion.isEmpty() && !cantidadStr.isEmpty() && !precioStr.isEmpty() && !rutaFoto.isEmpty()){
                try {
                    int cantidad = Integer.parseInt(cantidadStr);
                    double precio = Double.parseDouble(precioStr);
                    Marca marca = new MarcaDAO().obtenerMarcaPorNombre(marcaSeleccionada);

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
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Error en los campos numéricos. ", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
