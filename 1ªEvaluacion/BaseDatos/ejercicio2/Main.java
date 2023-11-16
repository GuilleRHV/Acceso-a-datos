package ejercicio2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.sqlite.SQLiteConfig;

import ejercicio.AccesoDepartamento;
import entrada.Teclado;

public class Main {

	// Consulta los departamentos de la base de datos con la misma ubicaci�n y
	// ordenados por nombre de forma ascendente.
	public static void main(String[] args) {
		try {
			int opcion = 0;
			int codigo;
			AccesoEmpleado ae = new AccesoEmpleado();
			do {
				System.out.println(menu());
				opcion = Teclado.leerEntero("Introduce opcion");
				switch (opcion) {
				case 0:
					System.out.println("Fin del programa");
					break;
				case 1:
					ae.insertar();
					break;
				case 2:
					List<Empleado> listadepartamentos = ae.consultarTodos();
					for (Empleado departamento : listadepartamentos) {
						System.out.println(departamento.toString());
					}
					break;
				case 3:

					codigo = Teclado.leerEntero("Introduce codigo");
					Empleado emp = ae.consultar(codigo);
					if (emp != null) {
						System.out.println(emp.toString());
					} else {
						System.out.println("No hay ningun empleado con el codigo introducido");
					}
					break;
				case 4:
					codigo = Teclado.leerEntero("Introduce codigo del empleado que quieres actualizar");
					Empleado empl = ae.consultar(codigo);
					if (empl != null) {
						ae.actualizar(codigo);
					} else {
						System.out.println("No existe ningun empleado con ese id");
					}

					break;
				case 5:
					codigo = Teclado.leerEntero("Introduce codigo que eliminar");
					Empleado emple = ae.consultar(codigo);
					if (emple != null) {
						ae.eliminar(codigo);
					} else {
						System.out.println("No existe ningun empleado con ese id");
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
		String cadena = "0) Salir del programa.\n1) Insertar un departamento en la base de datos\n2) Consultar todos los departamentos de la base de datos.\n3) Consultar un departamento, por código, de la base de datos\n4) Actualizar un departamento, por código, de la base de datos.\r\n"
				+ "5) Eliminar un departamento, por código, de la base de datos.";
		return cadena;
	}

}
