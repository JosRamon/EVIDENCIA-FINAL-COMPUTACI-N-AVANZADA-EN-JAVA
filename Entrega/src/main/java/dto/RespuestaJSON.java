package dto;

import java.util.List;

public class RespuestaJSON {

	private Boolean exito = true;
	private String leyendaError = ""; //Solo se carga en el caso de retornarse un error
	private Object objeto = null;
	@SuppressWarnings("rawtypes")
	private List colObjetos = null;
	
	
	@SuppressWarnings("rawtypes")
	public List getColObjetos() {
		return colObjetos;
	}
	
	@SuppressWarnings("rawtypes")
	public void setColObjetos(List colObjetos) {
		this.colObjetos = colObjetos;
	}

	public String getLeyendaError() {
		return leyendaError;
	}

	public void setLeyendaError(String leyendaError) {
		this.leyendaError = leyendaError;
	}

	public Object getObjeto() {
		return objeto;
	}

	public void setObjeto(Object objeto) {
		this.objeto = objeto;
	}
	
	public int getCantidadDeObjetos(){
		return this.getColObjetos()==null?0:this.getColObjetos().size();
	}

	public Boolean getExito() {
		return exito;
	}

	public void setExito(Boolean exito) {
		this.exito = exito;
	}
	
	
}
