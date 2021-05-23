package service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import dao.MySqlDAO;
import model.CalculoIMC;
import model.ErrorGeneral;
import model.Usuario;
import utiles.Utiles;

//Esta clase sirve de capa entre el origen de datos y la capa de controlador

public class Service {

	private static Service instance = null;
	private MySqlDAO dao;
	 
	private Service() {
		dao = new MySqlDAO();
	}
	
    public static Service getInstance() {
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }
    
    //Recupera un usuario de la BD
    public Usuario getUsuario(String username) throws SQLException, Exception {
    	return dao.getUsuario(username);
    }
    
    //Valida que el usuario y password recibido sean correctos
  	public boolean validarUsuario(String usuario, String password) throws SQLException, Exception {
  		//Obtengo el password del mapa
  		Usuario u = this.getUsuario(usuario);
  		if(u == null) {
  			//El usuario no existe
  			return false;
  		} else if(!u.password.equals(password)) {
  			//El password no coincide con el almacenado
  			return false;
  		}
  		return true;
  	}
  	
  	//Permite registrar en la aplicacion un nuevo usuario
  	public void registrarUsuario(Usuario u) throws SQLException, Exception {
  		dao.insertUsuario(u);
  	}
  	
  	//Permite registrar en la aplicacion un nuevo usuario
  	public List<Usuario> listarUsuarios() throws SQLException, Exception {
  		return dao.getUsuarios();
  	}
  	
  	/*
  	 * Realiza el calculo del IMC co los datos recibidos.
  	 * El índice de masa corporal (IMC) se calcula dividiendo los kilogramos de peso por el 
  	 * cuadrado de la estatura en metros (IMC = peso [kg]/ estatura [m2]).
  	 * Dado que la altura esta en cms, hay que multiplicarla por 0,01 para obtener en metros y elevarla al cuadrado.
  	 */
  	public CalculoIMC caluclarIMC(double peso, Usuario usuario) throws SQLException, ErrorGeneral, Exception  {
  		//Creamos el objeto CalculoIMC con los datos recibidos
  		CalculoIMC imc = new CalculoIMC();
  		imc.setUsuario(usuario.usuario);
  		imc.setPeso(peso);
  		imc.setEstatura(usuario.estatura);
  		imc.setFecha(Utiles.dateToString(new Date()));
  		
  		//Realizamos el calculo
  		double valor = imc.getPeso() / (imc.getEstatura() * imc.getEstatura() * 0.0001);
  		if(valor <= 0) {
  			ErrorGeneral e = new ErrorGeneral("Error en el calculo de IMC", "El IMC debe ser mayor a cero (0).");
  			throw e;
  		}
  		
  		imc.setImc(Utiles.redondear1Decimal(valor));
  		
  		//Insertamos el calculo en la base de datos
  		dao.insertCalculoIMC(imc); 
  		
  		//Actualizamos el valor del objeto en sesion con los datos del nuevo calculo guardado en BD
  		usuario.imc = imc.getImc();
  		
  		return imc;
  		
  	}
  	
  	/*
  	 * Obtiene de la base de datos el historico de calculos de un usuario.
  	 */
  	public List<CalculoIMC> getHistoricoCalculos(String usuario) throws SQLException, Exception{
  		return dao.getCalculosDeUsuario(usuario);  		
  	}
	
}
