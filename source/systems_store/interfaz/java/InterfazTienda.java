package systems_store.interfaz.java;

import systems_store.dao.java.ProductoDAO;
import systems_store.mundo.java.Producto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Comparator;
import java.util.List;

public class InterfazTienda extends JFrame {

    private JPanel panelProductos;
    private JTextField txtBuscar;
    private ProductoDAO productoDAO;
    private List<Producto> listaProductos;

    public InterfazTienda() {
        setTitle("Systems Store");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        productoDAO = new ProductoDAO();

        construirEncabezado();
        construirPanelProductos();

        cargarProductosDesdeBD();

        setVisible(true);
    }

    private void construirEncabezado() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(25, 118, 210));
        header.setBorder(new EmptyBorder(10, 15, 10, 15));

        JLabel lblTitulo = new JLabel(" Systems Store");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);

        txtBuscar = new JTextField("Buscar productos...");
        txtBuscar.setPreferredSize(new Dimension(200, 30));

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> new FormularioProducto(this, this, null)); 

        JButton btnOrdenar = new JButton("Ordenar");
        btnOrdenar.addActionListener(e -> mostrarOpcionesOrdenamiento());

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        acciones.setOpaque(false);
        acciones.add(txtBuscar);
        acciones.add(btnOrdenar);
        acciones.add(btnAgregar);

        header.add(lblTitulo, BorderLayout.WEST);
        header.add(acciones, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);
    }

    private void construirPanelProductos() {
        panelProductos = new JPanel();
        panelProductos.setLayout(new GridLayout(0, 3, 20, 20));
        panelProductos.setBorder(new EmptyBorder(20, 20, 20, 20));

        JScrollPane scroll = new JScrollPane(panelProductos);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }

    public void cargarProductosDesdeBD() {
        listaProductos = productoDAO.listarProductos();
        mostrarProductos(listaProductos);
    }

    private void mostrarProductos(List<Producto> lista) {
        panelProductos.removeAll();
        for (Producto p : lista) {
            cargarProducto(p);
        }
        panelProductos.revalidate();
        panelProductos.repaint();
    }

    public void cargarProducto(Producto producto) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        card.setBackground(Color.WHITE);

        JLabel lblImagen;
        File imgFile = new File(producto.getFoto());
        if (imgFile.exists()) {
            ImageIcon icon = new ImageIcon(producto.getFoto());
            Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            lblImagen = new JLabel(new ImageIcon(img));
        } else {
            lblImagen = new JLabel("Sin imagen", SwingConstants.CENTER);
        }

        JLabel lblNombre = new JLabel(producto.getNombre());
        JLabel lblPrecio = new JLabel("$" + producto.getPrecio());

        JButton btnVerMas = new JButton(" Ver más");
        btnVerMas.addActionListener(e -> mostrarOpciones(producto));

        JPanel info = new JPanel(new GridLayout(0, 1));
        info.setBorder(new EmptyBorder(5, 5, 5, 5));
        info.add(lblNombre);
        info.add(lblPrecio);
        info.add(btnVerMas);

        card.add(lblImagen, BorderLayout.CENTER);
        card.add(info, BorderLayout.SOUTH);

        panelProductos.add(card);
    }

    private void mostrarOpciones(Producto producto) {
        JPopupMenu opciones = new JPopupMenu();
        JMenuItem eliminar = new JMenuItem("Eliminar");
        JMenuItem actualizar = new JMenuItem("Actualizar");

        eliminar.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Seguro que deseas eliminar el producto \"" + producto.getNombre() + "\"?",
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean exito = productoDAO.eliminarProducto(producto.getId());
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Producto eliminado con éxito.");
                    cargarProductosDesdeBD();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el producto.");
                }
            }
        });

        actualizar.addActionListener(e -> {
            new FormularioProducto(this, this, producto);
        }); 

        opciones.add(actualizar);
        opciones.add(eliminar);
        opciones.show(this, getWidth() / 2, getHeight() / 2);
    }

    private void mostrarOpcionesOrdenamiento() {
        String[] opciones = {"Nombre (A-Z)", "Nombre (Z-A)", "Precio (menor a mayor)", "Precio (mayor a menor)"};
        String seleccion = (String) JOptionPane.showInputDialog(this,
                "Ordenar productos por:",
                "Ordenar",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]);

        if (seleccion != null) {
            switch (seleccion) {
                case "Nombre (A-Z)":
                    listaProductos.sort(Comparator.comparing(Producto::getNombre));
                    break;
                case "Nombre (Z-A)":
                    listaProductos.sort(Comparator.comparing(Producto::getNombre).reversed());
                    break;
                case "Precio (menor a mayor)":
                    listaProductos.sort(Comparator.comparingDouble(Producto::getPrecio));
                    break;
                case "Precio (mayor a menor)":
                    listaProductos.sort(Comparator.comparingDouble(Producto::getPrecio).reversed());
                    break;
            }
            mostrarProductos(listaProductos);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InterfazTienda::new);
    }
} 
