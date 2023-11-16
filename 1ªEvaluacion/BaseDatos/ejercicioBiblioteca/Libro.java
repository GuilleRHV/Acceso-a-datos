package ejercicioBiblioteca;

public class Libro {
	/* código: número entero, no nulo, clave primaria.
	 - isbn: cadena de texto, no nulo, único.
	 - título: cadena de texto, no nulo.
	 - escritor: cadena de texto, no nulo.
	 - año_publicación: número entero, no nulo.
	 - puntuación: número real, no nulo.*/
	int codigo;
	String isbn;
	String titulo;
	String escritor;
	int anyo_publicacion;
	double puntuacion;
	public Libro(int codigo, String isbn, String titulo, String escritor, int anyo_publicacion, double puntuacion) {
		super();
		this.codigo = codigo;
		this.isbn = isbn;
		this.titulo = titulo;
		this.escritor = escritor;
		this.anyo_publicacion = anyo_publicacion;
		this.puntuacion = puntuacion;
	}
	@Override
	public String toString() {
		return "Libro [codigo=" + codigo + ", isbn=" + isbn + ", titulo=" + titulo + ", escritor=" + escritor
				+ ", anyo_publicacion=" + anyo_publicacion + ", puntuacion=" + puntuacion + "]";
	}
	
	 
	 
}
