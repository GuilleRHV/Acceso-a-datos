import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import entrada.Teclado;

public class ActualizarDepartamento {

	// Actualiza un departamento, por código, de la base de datos personal.db
	public static void main(String[] args) {
		Connection conexion = null;
		try {
			int codigo = Teclado.leerEntero("¿Código? ");
			String nombre = Teclado.leerCadena("¿Nombre? ");
			String ubicacion = Teclado.leerCadena("¿Ubicación? ");
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
				System.out.println("No se ha encontrado ningún departamento con ese código.");
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
	
}
