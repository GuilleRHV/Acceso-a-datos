package ejercicio2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.sqlite.SQLiteConfig;

import ejercicio2.Empleado;
import entrada.Teclado;

public class AccesoEmpleado {
	final static String CONECT = "jdbc:sqlite:db\\personal.db";

	public static void insertar() throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		try {
			int codigo = Teclado.leerEntero("�C�digo? ");
			String nombre = Teclado.leerCadena("�Nombre? ");
			String ubicac = Teclado.leerCadena("�Ubicaci�n? ");
			Class.forName("org.sqlite.JDBC");
			conexion = DriverManager.getConnection(CONECT);
			String sentenciaInsertar = "INSERT INTO departamento " + "VALUES (" + codigo + ", '" + nombre + "', '"
					+ ubicac + "')";

			Statement sentencia = conexion.createStatement();
			int resultados = sentencia.executeUpdate(sentenciaInsertar);
			System.out.println(resultados);
			sentencia.close();
		} finally {
			if (conexion != null) {
				conexion.close();
			}
		}

	}

	public static List<Empleado> consultarTodos() throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		List<Empleado> listaEmpleados = new ArrayList<Empleado>();
		try {
			Class.forName("org.sqlite.JDBC");
			conexion = DriverManager.getConnection(CONECT);
			String sentenciaConsultar = "SELECT * FROM empleado ";
			Statement sentencia = conexion.createStatement();
			ResultSet resultados = sentencia.executeQuery(sentenciaConsultar);
			while (resultados.next()) {
				Empleado empleado = new Empleado(resultados.getInt("codigo"), resultados.getString("nombre"),
						resultados.getString("fecha_alta"), resultados.getDouble("salario"),
						resultados.getInt("codigo_departamento"));
				listaEmpleados.add(empleado);
			}
			resultados.close();
			sentencia.close();
		} finally {
			if (conexion != null) {
				conexion.close();
			}
		}
		return listaEmpleados;
	}

	public static void actualizar(int codigo) {
		Connection conexion = null;
		try {
			int nuevocodigo = Teclado.leerEntero("�nuevo C�digo? ");
			String nombre = Teclado.leerCadena("�Nombre? ");
			String fecha = Teclado.leerCadena("�fecha? ");
			String salario = Teclado.leerCadena("�salario? ");
			int codigodepartamento = Teclado.leerEntero("�codigo departamento? ");
			Class.forName("org.sqlite.JDBC");
			conexion = DriverManager.getConnection(CONECT);
			String sentenciaActualizar = "UPDATE empleado " + "SET codigo = " + nuevocodigo + ", nombre = '" + nombre
					+ "', fecha_alta = '" + fecha + "', salario =" + salario + ", codigo_departamento = "
					+ codigodepartamento + "  " + "WHERE codigo = " + codigo;
			Statement sentencia = conexion.createStatement();
			int filasActualizadas = sentencia.executeUpdate(sentenciaActualizar);
			if (filasActualizadas == 0) {
				System.out.println("No se ha encontrado ning�n departamento con ese c�digo.");
			} else {
				System.out.println("Se ha actualizado un departamento de la base de datos.");
			}
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Error al cargar el conector de SQLite: " + cnfe.getMessage());
			cnfe.printStackTrace();
		} catch (SQLException sqle) {
			System.out.println("Error de SQL: " + sqle.getMessage());
			sqle.printStackTrace();
		} finally {
			try {
				if (conexion != null) {
					conexion.close();
				}
			} catch (SQLException sqle) {
				System.out.println("Error al cerrar la base de datos: " + sqle.getMessage());
				sqle.printStackTrace();
			}
		}
	}

	public static Empleado consultar(int codigo) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		Empleado empleado = null;
		List<Empleado> listaDepartamentos = new ArrayList<Empleado>();
		try {
			Class.forName("org.sqlite.JDBC");
			conexion = DriverManager.getConnection(CONECT);
			String sentenciaConsultar = "SELECT * FROM empleado " + "WHERE codigo = '" + codigo + "' ORDER BY nombre";
			Statement sentencia = conexion.createStatement();
			ResultSet resultados = sentencia.executeQuery(sentenciaConsultar);
			while (resultados.next()) {
				empleado = new Empleado(resultados.getInt("codigo"), resultados.getString("nombre"),
						resultados.getString("fecha_alta"), resultados.getDouble("salario"),
						resultados.getInt("codigo_departamento"));

			}
			resultados.close();
			sentencia.close();
		} finally {
			if (conexion != null) {
				conexion.close();
			}
		}
		return empleado;
	}

	public static void eliminar(int codigo) {
		Connection conexion = null;
		try {

			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			config.enforceForeignKeys(true);
			conexion = DriverManager.getConnection(CONECT, config.toProperties());
			String sentenciaEliminar = "DELETE FROM empleado " + "WHERE codigo = " + codigo;
			Statement sentencia = conexion.createStatement();
			int filasEliminadas = sentencia.executeUpdate(sentenciaEliminar);
			if (filasEliminadas == 0) {
				System.out.println("No se ha encontrado ning�n departamento con ese c�digo.");
			} else {
				System.out.println("Se ha eliminado un departamento de la base de datos.");
			}
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Error al cargar el conector de SQLite: " + cnfe.getMessage());
			cnfe.printStackTrace();
		} catch (SQLException sqle) {
			System.out.println("Error de SQL: " + sqle.getMessage());
			sqle.printStackTrace();
		} finally {
			try {
				if (conexion != null) {
					conexion.close();
				}
			} catch (SQLException sqle) {
				System.out.println("Error al cerrar la base de datos: " + sqle.getMessage());
				sqle.printStackTrace();
			}
		}
	}

}
