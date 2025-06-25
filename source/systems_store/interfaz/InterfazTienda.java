package systems_store.interfaz;

import systems_store.dao.ProductoDAO;
import systems_store.mundo.Producto;
import systems_store.mundo.Tienda;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;

public class InterfazTienda extends JFrame {

	

    // Panel donde se muestran los productos
    private JPanel panelProductos;
    
    // Acceso a la base de datos
    private ProductoDAO productoDAO;
    

    // Lista de productos cargada desde la base de datos
    private List<Producto> listaProductos;

    
    /**
     * Interfaz principal de la aplicación Systems Store.
     * Muestra los productos como tarjetas, permite agregarlos, editarlos, eliminarlos y ordenarlos.
     */
    public InterfazTienda() {
        setTitle("Systems Store");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);//Cierre la aplicación
        setLocationRelativeTo(null);//Centra la ventana 
        setLayout(new BorderLayout());

        productoDAO = new ProductoDAO();//DAO para manejar productos

        construirEncabezado();
        construirPanelProductos();

        cargarProductosDesdeBD();

        setVisible(true); // Muestra la ventana
    }

    

    /**
     * Crea el encabezado (barra superior) con título y botones.
     */
    private void construirEncabezado() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(25, 118, 210));
        header.setBorder(new EmptyBorder(10, 15, 10, 15));

        JLabel lblTitulo = new JLabel(" Phone shop");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);

        
        // Botón para agregar un nuevo producto
        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> new FormularioProducto( this, null)); 

        

        // Botón para ordenar productos
        JButton btnOrdenar = new JButton("Ordenar");
        btnOrdenar.addActionListener(e -> mostrarOpcionesOrdenamiento());

        
        // Panel para los botones (alineado a la derecha)
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        acciones.setOpaque(false);
        acciones.add(btnOrdenar);
        acciones.add(btnAgregar);

        header.add(lblTitulo, BorderLayout.WEST);
        header.add(acciones, BorderLayout.EAST);

        add(header, BorderLayout.NORTH); // Agrega encabezado arriba
    }

    
    /**
     * Crea el área donde se mostrarán los productos.
     */
    private void construirPanelProductos() {
        panelProductos = new JPanel();
        panelProductos.setLayout(new GridBagLayout());
        panelProductos.setBorder(new EmptyBorder(20, 20, 20, 20));

        JScrollPane scroll = new JScrollPane(panelProductos);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }

    
    /**
     * Carga productos desde la base de datos y los muestra en pantalla.
     */
    public void cargarProductosDesdeBD() {
        listaProductos = productoDAO.listarProductos();
        mostrarProductos(listaProductos);
    }

    

    /**
     * Muestra las tarjetas de productos en el panel.
     */
    private void mostrarProductos(List<Producto> lista) {
    	 panelProductos.removeAll();// Limpia productos actuales
    	    
    	 if (lista.isEmpty()) {
    	        JLabel mensaje = new JLabel("No hay productos para mostrar.");
    	        mensaje.setFont(new Font("Segoe UI", Font.BOLD, 18));
    	        mensaje.setForeground(Color.GRAY);
    	        panelProductos.setLayout(new GridBagLayout()); // Centrar mensaje
    	        panelProductos.add(mensaje);
    	    } else {
    	        panelProductos.setLayout(new GridBagLayout());
    	        GridBagConstraints gbc = new GridBagConstraints();
    	        gbc.insets = new Insets(10, 10, 10, 10);
    	        gbc.anchor = GridBagConstraints.NORTHWEST;

    	        int col = 0;
    	        int row = 0;

    	        for (Producto p : lista) {
    	            JPanel tarjeta = crearTarjetaProducto(p);
    	            gbc.gridx = col;
    	            gbc.gridy = row;
    	            panelProductos.add(tarjeta, gbc);

    	            col++;
    	            if (col == 5) {
    	                col = 0;
    	                row++;
    	            }
    	        }
    	    }

    	    panelProductos.revalidate();
    	    panelProductos.repaint();
    }

    
    /**
     * Crea una tarjeta visual con la información del producto.
     */
    private JPanel crearTarjetaProducto(Producto producto) {
        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(160, 280));
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        card.setBackground(Color.WHITE);

        // Panel de imagen
        JPanel panelImagen = new JPanel();
        panelImagen.setPreferredSize(new Dimension(160, 160));
        panelImagen.setBackground(Color.WHITE);
        JLabel lblImagen;

        String rutaImagen = "data/imagenes/" + producto.getFoto();
        File imgFile = new File(rutaImagen);
        if (imgFile.exists()) {
            ImageIcon icon = new ImageIcon(rutaImagen);
            Image img = icon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
            lblImagen = new JLabel(new ImageIcon(img));
        } else {
            lblImagen = new JLabel("Sin imagen", SwingConstants.CENTER);
            lblImagen.setPreferredSize(new Dimension(140, 140));
        }

        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        panelImagen.add(lblImagen);
        
       
        // Información textual del producto
        JLabel lblNombre = new JLabel("Nombre: " + producto.getNombre());
        JLabel lblMarca = new JLabel("Marca: " + producto.getMarca().getNombre());
        JLabel lblPrecio = new JLabel("Precio: $ " + producto.getPrecio());

        
     // Botón "Ver más" para mostrar detalles en otra ventana
        JButton btnVerMas = new JButton("Ver más");
        btnVerMas.addActionListener(e -> {
	        InformacionProducto informacionProductoPanel = new InformacionProducto();
	        informacionProductoPanel.actualizarInformacion(
	                producto.getNombre(),
	                producto.getDescripcion(),
	                producto.getCantidad(),
	                producto.getPrecio(),
	                String.valueOf(producto.getFechaIngreso()),
	                producto.getMarca().getNombre(),
	                producto.getFoto()
	        );
	        // Crear un JDialog para mostrar la información en una ventana 
	        JDialog dialog = new JDialog();
	        dialog.setTitle("Información del Producto");
	        dialog.setSize(500, 350);
	        dialog.setLocationRelativeTo(null);
	        dialog.setModal(true);
	        dialog.add(informacionProductoPanel);
	        dialog.setVisible(true);
        });
        
        
        // Botón de opciones (actualizar, eliminar)
        JButton btnOpciones = new JButton("Opciones");
        btnOpciones.addActionListener(e -> mostrarOpciones(producto));

        
        // Panel para texto + botones
        JPanel info = new JPanel(new GridLayout(0, 1));
        info.setBorder(new EmptyBorder(2, 5, 2, 5));
        info.add(lblNombre);
        info.add(lblMarca);
        info.add(lblPrecio);
        info.add(btnVerMas);
        info.add(btnOpciones);

        card.add(panelImagen, BorderLayout.NORTH);
        card.add(info, BorderLayout.CENTER);

        return card;
    }



    
    /**
     * Muestra un menú de opciones (actualizar o eliminar producto).
     */
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
                    cargarProductosDesdeBD();// Recargar lista
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el producto.");
                }
            }
        });

        actualizar.addActionListener(e -> {
            new FormularioProducto(this, producto);
        }); 

        opciones.add(actualizar);
        opciones.add(eliminar);
        
        // Muestra el menú en el centro de la ventana
        opciones.show(this, getWidth() / 2, getHeight() / 2);
    }

    
    /**
     * Muestra opciones de ordenamiento al usuario.
     */
    private void mostrarOpcionesOrdenamiento() {
        String[] opciones = {
        		"Nombre",
				"Precio",
				"Cantidad",
				"Fecha de ingreso "};
        String seleccion = (String) JOptionPane.showInputDialog(this,
                "Ordenar productos por:",
                "Ordenar",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]);

        if (seleccion != null) {
        	
        	Object[] options = {"Descendente", "Ascendente"}; 
            int confirm = JOptionPane.showOptionDialog(this,
                    "¿Cómo quieres ordenar?", 
                    "Confirmar orden",
                    JOptionPane.DEFAULT_OPTION, 
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options, // Opciones personalizadas
                    options[1]); // 
           

            boolean descendente = (confirm == 0);
            
            String criterio = "";
            if (seleccion.contains("Nombre")) {
                criterio = "nombre";
            } else if (seleccion.contains("Precio")) {
                criterio = "precio";
            } else if (seleccion.contains("Cantidad")) {
                criterio = "cantidad";
            } else if (seleccion.contains("Fecha")) {
                criterio = "fecha";
            }

            // Llamar al método de ordenamiento de Tienda
          Tienda.ordenar(listaProductos, criterio, descendente);
          
          mostrarProductos(listaProductos);
          
        } else {
            JOptionPane.showMessageDialog(this, "No se seleccionó ninguna opción.");
        }
    }

    
    /**
     * Método principal para la aplicación.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(InterfazTienda::new);
    }
} 
