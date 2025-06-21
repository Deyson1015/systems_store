package systems_store.mundo.java;

import java.util.Comparator;
import java.util.List;

public class Tienda {
	
	
	public static Producto buscarProductoPorNombre(List<Producto> productos, String pNombre) {
		int izquierda = 0;
    	int derecha = productos.size() -1;
    	
    	while (izquierda <= derecha) {
    		int medio = izquierda + (izquierda - derecha) / 2;
    		Producto productoMedio = productos.get(medio);
    		int comparacion = productoMedio.getNombre().compareToIgnoreCase(pNombre);
    		
    		if (comparacion == 0) {
	            return productoMedio;
	        } else if (comparacion < 0) {
	            izquierda = medio + 1;
	        } else {
	            derecha = medio - 1;
	        }
    	}
    	
    	return null;
	}
	// Ordenar los productos por precio de menor a mayor
	public static void ordenarPorPrecio(List<Producto> productos) {
		productos.sort(Comparator.comparingDouble(Producto::getPrecio));
	}
	
	// Ordenar por nombre alfabeticamente
	public static void ordenarPorNombre(List<Producto> productos) {
		productos.sort(Comparator.comparing(Producto::getNombre));
	}
	
	// Ordenar por cantidad de menor a mayor
	public static void ordenarPorCantidad(List<Producto> productos) {
		productos.sort(Comparator.comparingInt(Producto::getCantidad));
	}

	// Ordenar por fecha de ingreso del producto
	public static void ordenarPorFecha(List<Producto> productos) {
		productos.sort(Comparator.comparing(Producto::getFechaIngreso));
	}
	
	public static void ordenar(List<Producto> productos, String criterio) {
		switch (criterio.toLowerCase()) {
		case "precio":
			ordenarPorPrecio(productos);
			break;
		case "nombre":
			ordenarPorNombre(productos);
			break;
		case "cantidad":
			 ordenarPorCantidad(productos);
			 break;
		case "fecha":
			ordenarPorFecha(productos);
			break;
		default:
			System.out.println("Criterio de orden no reconocido" + criterio);
		
		}
	}
	
}
