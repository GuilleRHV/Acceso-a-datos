import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import entrada.Teclado;

public class ConsultarDepartamentos2 {

	// Consulta los departamentos de la base de datos personal.db 
	// con la misma ubicación y ordenados por nombre de forma ascendente.
	public static List<Departamento> consultar(String ubicacion) 
	throws ClassNotFoundException, SQLException {
		Connection conexion = null;
		List<Departamento> listaDepartamentos = new ArrayList<Departamento>();
		try {
			Class.forName("org.sqlite.JDBC");
			conexion = DriverManager.getConnection("jdbc:sqlite:db\\personal.db");
			String sentenciaConsultar = "SELECT * FROM departamento " +
			                            "WHERE ubicacion = '" + ubicacion +
			                            "' ORDER BY nombre";
			Statement sentencia = conexion.createStatement();
			ResultSet resultados = sentencia.executeQuery(sentenciaConsultar);	
			while (resultados.next()) {
				Departamento departamento = 
					new Departamento(resultados.getInt("codigo"),
					                 resultados.getString("nombre"),
					                 resultados.getString("ubicacion"));
				listaDepartamentos.add(departamento);
			}
			resultados.close();
			sentencia.close();
		} 	
		finally {
			if (conexion != null) {
				conexion.close();
			}
		}
		return listaDepartamentos;
	}

	// Consulta los departamentos de la base de datos con la misma ubicación y
	// ordenados por nombre de forma ascendente.
	public static void main(String[] args) {
		try {
			String ubicacion = Teclado.leerCadena("¿Ubicación? ");
			List<Departamento> listaDepartamentos = consultar(ubicacion);
			if (listaDepartamentos.size() == 0) {
				System.out.println("No se ha encontrado ningún departamento con esa ubicación en la base de datos.");
			}
			else {
				for (Departamento departamento : listaDepartamentos) {
					System.out.println(departamento.toString());
				}
				System.out.println("Se han consultado " + 
				                   listaDepartamentos.size() + " departamentos de la base de datos.");
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
	}

}
