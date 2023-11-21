package ejercicio_biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entrada.Teclado;

public class Main {

	// Consulta los departamentos de la base de datos con la misma ubicaci�n y
	// ordenados por nombre de forma ascendente.
	public static void main(String[] args) {
		try {
			int opcion = 0;
			int codigo;
			AccesoBiblioteca ab = new AccesoBiblioteca();
			do {
				System.out.println(menu());
				opcion = Teclado.leerEntero("Introduce opcion");
				switch (opcion) {
				case 0:
					System.out.println("Fin del programa");
					break;
				case 1:
					ab.insertar();
					break;
				case 2:
					codigo = Teclado.leerEntero("Introduce codigo del empleado que quieres actualizar");
					Libro lib = ab.consultar(codigo);
					if (lib != null) {
						ab.eliminar(codigo);
					} else {
						System.out.println("No existe ningun libro con este codigo");
					}
					break;
				case 3:

					// Leer todos los libros
					List<Libro> libros = ab.consultarTodos();
					for (Libro libro : libros) {
						System.out.println(libro.toString());
					}
					break;
				case 4:
					String nombreescritor = Teclado.leerCadena("Introduce nombre del escritor que quieres buscar");
					List<Libro> lista = ab.consultarporescritor(nombreescritor);
					if (lista.size() > 0) {
						for (Libro libro : lista) {
							System.out.println(libro.toString());
						}
					} else {
						System.out.println("No hay libro escritos por " + nombreescritor);
					}
					break;
				case 5:
					List<Libro> list = ab.consultarnoprestados();
					if (list.size() > 0) {
						for (Libro libro : list) {
							System.out.println(libro.toString());
						}
					} else {
						System.out.println("No hay libros no prestados");
					}

					break;
				case 6:

					int actualizados = ab.actualizarvarios(ab.consultarTodos());
					if (actualizados > 0) {
						System.out.println("Se han actualizado " + actualizados + " libros");
					} else {
						System.out.println("No se ha actualizado ningun libro");
					}
					break;
				default:
					System.out.println("Opcion invalida");
					break;
				}
			} while (opcion != 0);
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Error al cargar el conector de SQLite: " + cnfe.getMessage());
			cnfe.printStackTrace();
		} catch (SQLException sqle) {
			System.out.println("Error de SQL: " + sqle.getMessage());
			sqle.printStackTrace();
		}
	}

	public static String menu() {
		String cadena = "0) Salir del programa.\n1) Insertar un libro en la base de datos.\r\n"
				+ "2) Eliminar un libro, por código, de la base de datos..\n3)Consultar todos los libros de la base de datos.\n4) Consultar varios libros, por escritor, de la base de datos, ordenados por puntuación\r\n"
				+ "decendente.\n5) Consultar los libros no prestados de la base de datos.\n6) Consultar los libros devueltos, en una fecha, de la base de datos.";
		return cadena;
	}

}
