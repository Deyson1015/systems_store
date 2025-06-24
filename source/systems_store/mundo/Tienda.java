package systems_store.mundo;

import java.util.Comparator;
import java.util.List;

public class Tienda {
	
	   // Método general de ordenamiento que acepta un comparador y el orden
    private static <T> void ordenar(List<T> lista, Comparator<T> comparator, boolean descendente) {
        if (descendente) {
            lista.sort(comparator.reversed());
        } else {
            lista.sort(comparator);
        }
    }
	
    /**
     * Estos metodos utilizan el metodo general para ordenar ya sea de forma descendente o ascendente
     */

	public static void ordenarPorPrecio(List<Producto> productos, boolean descendente) {
		ordenar(productos, Comparator.comparingDouble(Producto::getPrecio), descendente);
	}

	public static void ordenarPorNombre(List<Producto> productos, boolean descendente) {
		ordenar(productos, Comparator.comparing(Producto::getNombre), descendente);
	}
	
	public static void ordenarPorCantidad(List<Producto> productos, boolean descendente) {
		ordenar(productos, Comparator.comparingInt(Producto::getCantidad), descendente);
	}

	public static void ordenarPorFecha(List<Producto> productos, boolean descendente) {
		ordenar(productos, Comparator.comparing(Producto::getFechaIngreso), descendente);
	}
	
    public static void ordenar(List<Producto> productos, String criterio, boolean descendente) {
        switch (criterio) {
            case "precio":
                ordenarPorPrecio(productos, descendente);
                break;
            case "nombre":
                ordenarPorNombre(productos, descendente);
                break;
            case "cantidad":
                ordenarPorCantidad(productos, descendente);
                break;
            case "fecha":
                ordenarPorFecha(productos, descendente);
                break;
            default:
                System.out.println("Criterio de orden no reconocido: " + criterio);
        }	
    }
}

