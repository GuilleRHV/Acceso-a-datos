package ejercicio;

public class Departamento {

	private int codigo;
	private String nombre;
	private String ubicacion;
	
	public Departamento(int codigo, String nombre, String ubicacion) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.ubicacion = ubicacion;
	}

	@Override
	public String toString() {
		return 
			"Departamento [C�digo = " + this.codigo + 
			", Nombre = " + this.nombre + 
			", Ubicaci�n = " + this.ubicacion + 
			"]";
	}
	
}
