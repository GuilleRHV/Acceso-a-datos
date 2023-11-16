package ejercicio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.sqlite.SQLiteConfig;

import entrada.Teclado;

public class Main {

	

	// Consulta los departamentos de la base de datos con la misma ubicaci�n y
	// ordenados por nombre de forma ascendente.
	public static void main(String[] args) {
		AccesoDepartamento ad = new AccesoDepartamento();
		try {
			int opcion = 0;
			int codigo;
			do {
				System.out.println(menu());
				opcion = Teclado.leerEntero("Introduce opcion");
				switch (opcion) {
				case 0:
					System.out.println("Fin del programa");
					break;
				case 1:
					ad.insertar();
					break;
				case 2:
					List<Departamento> listadepartamentos = consultarTodos();
					for (Departamento departamento : listadepartamentos) {
						System.out.println(departamento.toString());
					}
					break;
				case 3:
					
					codigo = Teclado.leerEntero("Introduce codigo");
					List<Departamento> listadep = consultar(codigo);
					for (Departamento departamento : listadep) {
						System.out.println(departamento.toString());
					}
					break;
				case 4:
					actualizar();
					break;
				case 5:
					codigo = Teclado.leerEntero("Introduce codigo que eliminar");
					eliminar(codigo);
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


	
	public static List<Departamento> consultarTodos() throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		List<Departamento> listaDepartamentos = new ArrayList<Departamento>();
		try {
			Class.forName("org.sqlite.JDBC");
			conexion = DriverManager.getConnection("jdbc:sqlite:db\\personal.db");
			String sentenciaConsultar = "SELECT * FROM departamento ";
			Statement sentencia = conexion.createStatement();
			ResultSet resultados = sentencia.executeQuery(sentenciaConsultar);
			while (resultados.next()) {
				Departamento departamento = new Departamento(resultados.getInt("codigo"),
						resultados.getString("nombre"), resultados.getString("ubicacion"));
				listaDepartamentos.add(departamento);
			}
			resultados.close();
			sentencia.close();
		} finally {
			if (conexion != null) {
				conexion.close();
			}
		}
		return listaDepartamentos;
	}

	public static List<Departamento> consultar(int codigo) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		List<Departamento> listaDepartamentos = new ArrayList<Departamento>();
		try {
			Class.forName("org.sqlite.JDBC");
			conexion = DriverManager.getConnection("jdbc:sqlite:db\\personal.db");
			String sentenciaConsultar = "SELECT * FROM departamento " + "WHERE codigo = '" + codigo
					+ "' ORDER BY nombre";
			Statement sentencia = conexion.createStatement();
			ResultSet resultados = sentencia.executeQuery(sentenciaConsultar);
			while (resultados.next()) {
				Departamento departamento = new Departamento(resultados.getInt("codigo"),
						resultados.getString("nombre"), resultados.getString("ubicacion"));
				listaDepartamentos.add(departamento);
			}
			resultados.close();
			sentencia.close();
		} finally {
			if (conexion != null) {
				conexion.close();
			}
		}
		return listaDepartamentos;
	}
	
	public static void actualizar() {
		Connection conexion = null;
		try {
			int codigo = Teclado.leerEntero("�C�digo? ");
			String nombre = Teclado.leerCadena("�Nombre? ");
			String ubicacion = Teclado.leerCadena("�Ubicaci�n? ");
			Class.forName("org.sqlite.JDBC");
			conexion = DriverManager.getConnection("jdbc:sqlite:db\\personal.db");
			System.out.println("Conectado");
			String sentenciaActualizar = "UPDATE departamento " +
			                             "SET nombre = '" + nombre + 
			                             "', ubicacion = '" + ubicacion + "' " +
			                             "WHERE codigo = " + codigo;
			Statement sentencia = conexion.createStatement();
			int filasActualizadas = sentencia.executeUpdate(sentenciaActualizar);
			if (filasActualizadas == 0) {
				System.out.println("No se ha encontrado ning�n departamento con ese c�digo.");
			}
			else {
				System.out.println("Se ha actualizado un departamento de la base de datos.");
			}
		} 
		catch (ClassNotFoundException cnfe) {
			System.out.println("Error al cargar el conector de SQLite: " + cnfe.getMessage());
			cnfe.printStackTrace();
		} 
		catch (SQLException sqle) {
			System.out.println("Error de SQL: " + sqle.getMessage());
			sqle.printStackTrace();
		}
		finally {
			try {
				if (conexion != null) {
					conexion.close();
				}
			} 
			catch (SQLException sqle) {
				System.out.println("Error al cerrar la base de datos: " + sqle.getMessage());
				sqle.printStackTrace();
			}
		}
	}
	
	
	
	public static void eliminar(int codigo) {
		Connection conexion = null;
		try {
			
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();  
	        config.enforceForeignKeys(true); 
			conexion = DriverManager.getConnection("jdbc:sqlite:db\\personal.db", 
			                                       config.toProperties());
			System.out.println("Conectado");
			String sentenciaEliminar = "DELETE FROM departamento " +
			                           "WHERE codigo = " + codigo;
			Statement sentencia = conexion.createStatement();
			int filasEliminadas = sentencia.executeUpdate(sentenciaEliminar);
			if (filasEliminadas == 0) {
				System.out.println("No se ha encontrado ning�n departamento con ese c�digo.");
			}
			else {
				System.out.println("Se ha eliminado un departamento de la base de datos.");
			}
		} 
		catch (ClassNotFoundException cnfe) {
			System.out.println("Error al cargar el conector de SQLite: " + cnfe.getMessage());
			cnfe.printStackTrace();
		} 
		catch (SQLException sqle) {
			System.out.println("Error de SQL: " + sqle.getMessage());
			sqle.printStackTrace();
		}
		finally {
			try {
				if (conexion != null) {
					conexion.close();
				}
			} 
			catch (SQLException sqle) {
				System.out.println("Error al cerrar la base de datos: " + sqle.getMessage());
				sqle.printStackTrace();
			}
		}
	}
	public static String menu() {
		String cadena = "0) Salir del programa.\n1) Insertar un departamento en la base de datos\n2) Consultar todos los departamentos de la base de datos.\n3) Consultar un departamento, por código, de la base de datos\n4) Actualizar un departamento, por código, de la base de datos.\r\n"
				+ "5) Eliminar un departamento, por código, de la base de datos.";
		return cadena;
	}

}
