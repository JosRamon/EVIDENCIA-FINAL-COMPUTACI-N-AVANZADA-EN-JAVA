package model;


/**
 * Esta clase permite registrar los datos de un error que se produzca para informar en pantalla.
 * 
 */
public class ErrorGeneral extends Exception {

	private String titulo;
	private String mensaje;
	
	//Constructor con los parametros necesarios
	public ErrorGeneral(String t, String m) {
		titulo = t;
		mensaje = m;
	}
	
	//Constructor sin parametros. Cargamos un string vacio en cada propiedad.
	public ErrorGeneral() {
		titulo = "";
		mensaje = "";
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
	
}
