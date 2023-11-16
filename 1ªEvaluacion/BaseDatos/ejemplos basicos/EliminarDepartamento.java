import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.sqlite.SQLiteConfig;
import entrada.Teclado;

public class EliminarDepartamento {

	// Elimina un departamento, por código, de la base de datos personal.db
	public static void main(String[] args) {
		Connection conexion = null;
		try {
			int codigo = Teclado.leerEntero("¿Código? ");
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
				System.out.println("No se ha encontrado ningún departamento con ese código.");
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

}
