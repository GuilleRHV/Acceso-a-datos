package ejercicio_biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
//import org.sqlite.SQLiteConfig;

import org.sqlite.SQLiteConfig;

import entrada.Teclado;

public class AccesoBiblioteca {
	final static String CONECT = "jdbc:sqlite:db\\biblioteca.db";

	public static void insertar() throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		try {
			int codigo = Teclado.leerEntero("�C�digo? ");
			String isbn = Teclado.leerCadena("�isbn? ");
			String titulo = Teclado.leerCadena("�titulo? ");
			String escritor = Teclado.leerCadena("�escritor? ");
			int anyo = Teclado.leerEntero("anyo?");
			double puntuacion = Teclado.leerReal("�puntuacion�n? ");
			Class.forName("org.sqlite.JDBC");
			conexion = DriverManager.getConnection(CONECT);
			String sentenciaInsertar = "INSERT INTO libro " + "VALUES (" + codigo + ", '" + isbn + "', '" + titulo
					+ "', '" + escritor + "', " + anyo + ", " + puntuacion + ")";

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

	public static List<Libro> consultarTodos() throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		List<Libro> listalibros = new ArrayList<Libro>();
		try {
			Class.forName("org.sqlite.JDBC");
			conexion = DriverManager.getConnection(CONECT);
			String sentenciaConsultar = "SELECT * FROM libro ";
			Statement sentencia = conexion.createStatement();
			ResultSet resultados = sentencia.executeQuery(sentenciaConsultar);
			while (resultados.next()) {
				Libro libro = new Libro(resultados.getInt("codigo"), resultados.getString("isbn"),
						resultados.getString("titulo"), resultados.getString("escritor"),
						resultados.getInt("anyo_publicacion"), resultados.getDouble("puntuacion"));
				listalibros.add(libro);
			}
			resultados.close();
			sentencia.close();
		} finally {
			if (conexion != null) {
				conexion.close();
			}
		}
		return listalibros;
	}

	public static void actualizar(int codigo) throws ClassNotFoundException, SQLException {
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
		} finally {

			if (conexion != null) {
				conexion.close();
			}

		}
	}

	public static Libro consultar(int codigo) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		Libro libro = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conexion = DriverManager.getConnection(CONECT);
			String sentenciaConsultar = "SELECT * FROM libro " + "WHERE codigo = '" + codigo + "' ";
			Statement sentencia = conexion.createStatement();
			ResultSet resultados = sentencia.executeQuery(sentenciaConsultar);
			while (resultados.next()) {
				libro = new Libro(resultados.getInt("codigo"), resultados.getString("isbn"),
						resultados.getString("titulo"), resultados.getString("escritor"),
						resultados.getInt("anyo_publicacion"), resultados.getDouble("puntuacion"));

			}
			resultados.close();
			sentencia.close();
		} finally {
			if (conexion != null) {
				conexion.close();
			}
		}
		return libro;
	}

	public static List<Libro> consultarporescritor(String escritor) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		Libro libro = null;
		List<Libro> lista = new ArrayList<Libro>();
		try {
			Class.forName("org.sqlite.JDBC");
			conexion = DriverManager.getConnection(CONECT);
			String sentenciaConsultar = "SELECT * FROM libro " + "WHERE escritor = '" + escritor
					+ "' order by puntuacion desc ";
			Statement sentencia = conexion.createStatement();
			ResultSet resultados = sentencia.executeQuery(sentenciaConsultar);
			while (resultados.next()) {
				libro = new Libro(resultados.getInt("codigo"), resultados.getString("isbn"),
						resultados.getString("titulo"), resultados.getString("escritor"),
						resultados.getInt("anyo_publicacion"), resultados.getDouble("puntuacion"));
				lista.add(libro);
			}
			resultados.close();
			sentencia.close();
		} finally {
			if (conexion != null) {
				conexion.close();
			}
		}
		return lista;
	}

	public static List<Libro> consultarnoprestados() throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		Libro libro = null;
		List<Libro> lista = new ArrayList<Libro>();
		try {
			Class.forName("org.sqlite.JDBC");
			conexion = DriverManager.getConnection(CONECT);
			String sentenciaConsultar = "select * from libro where codigo not in (select codigo_libro from prestamo GROUP by codigo_libro) ;";
			Statement sentencia = conexion.createStatement();
			ResultSet resultados = sentencia.executeQuery(sentenciaConsultar);
			while (resultados.next()) {
				libro = new Libro(resultados.getInt("codigo"), resultados.getString("isbn"),
						resultados.getString("titulo"), resultados.getString("escritor"),
						resultados.getInt("anyo_publicacion"), resultados.getDouble("puntuacion"));
				lista.add(libro);
			}
			resultados.close();
			sentencia.close();
		} finally {
			if (conexion != null) {
				conexion.close();
			}
		}
		return lista;
	}

//select * from libro where codigo not in (select codigo_libro from prestamo GROUP by codigo_libro) ;
	public static void eliminar(int codigo) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		try {

			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			config.enforceForeignKeys(true);
			conexion = DriverManager.getConnection(CONECT, config.toProperties());
			String sentenciaEliminar = "DELETE FROM libro " + "WHERE codigo = " + codigo;
			Statement sentencia = conexion.createStatement();
			int filasEliminadas = sentencia.executeUpdate(sentenciaEliminar);
			if (filasEliminadas == 0) {
				System.out.println("No se ha encontrado ning�n departamento con ese c�digo.");
			} else {
				System.out.println("Se ha eliminado un departamento de la base de datos.");
			}
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

	public static int actualizarvarios(List<Libro> lista) throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		int librosactualizados = 0;
		try {
			
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			config.enforceForeignKeys(true);
			conexion = DriverManager.getConnection(CONECT);
			conexion.setAutoCommit(false);
			String sentenciaactualizar = "update libro set puntuacion = puntuacion * ? where codigo = ?";
			
			for (Libro libro : lista) {
				PreparedStatement sentencia = conexion.prepareStatement(sentenciaactualizar);
				sentencia.setDouble(1, 3);
				sentencia.setDouble(2, libro.codigo);
				librosactualizados = librosactualizados + sentencia.executeUpdate();
			}
			conexion.commit();
		
		}catch(SQLException sqle) {
			if (conexion != null) {
				conexion.rollback();
				librosactualizados = 0;
			}
			throw sqle;
		}
		finally {
			
				if (conexion != null) {
					conexion.close();
				}
			
		}
		return librosactualizados;
	}

}
