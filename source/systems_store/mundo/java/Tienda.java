package systems_store.mundo.java;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import systems_store.mundo.java.Producto;


public class Tienda {
	
	// Ordenar los productos por precio de menor a mayor
	public static void ordenarPorPrecio(List<Producto> productos) {
		Collections.sort(productos, Comparator.comparingDouble(Producto::getPrecio));
	}
	
	// Ordenar por nombre alfabeticamente
	public static void ordenarPorNombre(List<Producto> productos) {
		Collections.sort(productos, Comparator.comparing(Producto::getNombre));
	}
	
	// Ordenar por cantidad de menor a mayor
	public static void ordenarPorCantidad(List<Producto> productos) {
		Collections.sort(productos, Comparator.comparingInt(Producto::getCantidad));
	}

	// Ordenar por fecha de ingreso del producto
	public static void ordenarPorFecha(List<Producto> productos) {
		Collections.sort(productos, Comparator.comparing(Producto::getFechaIngreso));
	}
	
}
