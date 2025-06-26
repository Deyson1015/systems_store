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
	
 // Método que mapea el criterio a un comparador
    private static Comparator<Producto> obtenerComparadorPorCriterio(String criterio) {
        switch (criterio) {
            case "precio":
                return Comparator.comparingDouble(Producto::getPrecio);
            case "nombre":
                return Comparator.comparing(Producto::getNombre);
            case "cantidad":
                return Comparator.comparingInt(Producto::getCantidad);
            case "fecha":
                return Comparator.comparing(Producto::getFechaIngreso);
            default:
                throw new IllegalArgumentException("Criterio de orden no reconocido: " + criterio);
        }
    }
	
    // Método general de ordenamiento
    public static void ordenar(List<Producto> productos, String criterio, boolean descendente) {
        Comparator<Producto> comparator = obtenerComparadorPorCriterio(criterio);
        ordenar(productos, comparator, descendente);
    }
   
    
}

