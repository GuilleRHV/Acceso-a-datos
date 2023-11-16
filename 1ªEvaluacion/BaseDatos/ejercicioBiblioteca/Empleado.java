package ejercicioBiblioteca;

public class Empleado {

	private int codigo;
	private String nombre;
	private String fechaAlta;
	private double salario;
	private int codigoDepartamento;

	public Empleado(int codigo, String nombre, String fechaAlta, double salario, int codigoDepartamento) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.fechaAlta = fechaAlta;
		this.salario = salario;
		this.codigoDepartamento = codigoDepartamento;
	}

	@Override
	public String toString() {
		return "Empleado [codigo=" + codigo + ", nombre=" + nombre + ", fechaAlta=" + fechaAlta + ", salario=" + salario
				+ ", codigoDepartamento=" + codigoDepartamento + "]";
	}

}
