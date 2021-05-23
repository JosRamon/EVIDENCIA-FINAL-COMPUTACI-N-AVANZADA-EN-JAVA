package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.CalculoIMC;
import model.Usuario;
import utiles.Utiles;

public class MySqlDAO {

	private static String URL = "jdbc:mysql://localhost:3306/";
	private static String DB_NAME = "entrega";
	private static String USUARIO = "root";
	private static String PASSWORD = "";
	
	//Permite obtener una conexion a la base de datos
	private Connection getConnection() throws SQLException, Exception {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			String sURL = URL + DB_NAME;
			con = DriverManager.getConnection(sURL, USUARIO, PASSWORD);
			
			return con;
		} catch (SQLException e) {
			throw e;
		}
	}
	
	/* ******** USUARIO ******** */
	
	public Usuario getUsuario(String username) throws SQLException, Exception {
		//Declaramos las variables
		Usuario usuario = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			//Establecemos la conexion
			con = this.getConnection();
			stmt = con.prepareStatement("SELECT U.*, C.imc FROM usuario U LEFT OUTER JOIN calculo C "
					+ "ON U.usuario = C.usuario "
					+ "WHERE U.usuario = ? ORDER BY C.fecha DESC LIMIT 1");
			stmt.setString(1, username); //pasamos el usuario al primer parametro
			
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				usuario = new Usuario();
			    usuario.usuario = rs.getString("usuario");
			    usuario.nombre = rs.getString("nombre");
			    usuario.password = rs.getString("contraseña");
			    usuario.estatura = rs.getInt("estatura");
			    usuario.sexo = rs.getString("sexo");
			    usuario.edad = rs.getInt("edad");
			    usuario.imc = rs.getDouble("imc");
			}
			
			return usuario;
			
		} catch (SQLException sqle) { 
		  System.out.println("Error en la ejecución:" + sqle.getErrorCode() + " " + sqle.getMessage()); 
		  throw sqle;
		
		} finally {
			try {
				if (rs != null)
            		rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
				} catch (Throwable th) { }
		}
	}
	
	public void insertUsuario(Usuario usuario) throws SQLException, Exception {
		//Declaramos las variables
		Connection con = null;
		PreparedStatement stmt = null;
		
		try {
			//Establecemos la conexion
			con = this.getConnection();
			String sqlQuery = "INSERT INTO usuario (usuario, nombre, edad, sexo, estatura, contraseña) "
					+ "VALUES (?,?,?,?,?,?)";
			stmt = con.prepareStatement(sqlQuery);
			stmt.setString(1, usuario.usuario);
			stmt.setString(2, usuario.nombre);
			stmt.setInt(3, usuario.edad);
			stmt.setString(4, usuario.sexo);
			stmt.setInt(5, usuario.estatura);
			stmt.setString(6, usuario.password);
						
			int cant = stmt.executeUpdate();
			System.out.println("Cantidad de filas afectadas: " + cant);
			
		} catch (SQLException sqle) { 
		  System.out.println("Error en la ejecución:" + sqle.getErrorCode() + " " + sqle.getMessage()); 
		  throw sqle;
		
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
				} catch (Throwable th) { }
		}
	}
	
	public List<Usuario> getUsuarios() throws SQLException, Exception {
		//Declaramos las variables
		Usuario usuario = null;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Usuario> usuarios = new ArrayList<Usuario>(); //Lista de usuarios a devolver
		
		try {
			//Establecemos la conexion
			con = this.getConnection();
			stmt = con.prepareStatement("SELECT * FROM usuario");
			//Ejecutamos la consulta
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				usuario = new Usuario();
			    usuario.usuario = rs.getString("usuario");
			    usuario.nombre = rs.getString("nombre");
			    usuario.password = rs.getString("contraseña");
			    usuario.estatura = rs.getInt("estatura");
			    usuario.sexo = rs.getString("sexo");
			    usuario.edad = rs.getInt("edad");
			    usuario.imc = rs.getDouble("imc");
			    //Agregamos el usuario a la lista
			    usuarios.add(usuario);
			}
			
			return usuarios;
			
		} catch (SQLException sqle) { 
		  System.out.println("Error en la ejecución:" + sqle.getErrorCode() + " " + sqle.getMessage()); 
		  throw sqle;
		
		} finally {
			try {
				if (rs != null)
            		rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
				} catch (Throwable th) { }
		}
	}
	
	/* ******** IMC ******** */
	
	public void insertCalculoIMC(CalculoIMC imc) throws SQLException, Exception {
		//Declaramos las variables
		Connection con = null;
		PreparedStatement stmt = null;
		
		try {
			//Establecemos la conexion
			con = this.getConnection();
			String sqlQuery = "INSERT INTO calculo (usuario, fecha, peso, estatura, imc) "
					+ "VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE peso = ?, estatura = ?, imc = ?;";
			stmt = con.prepareStatement(sqlQuery);
			stmt.setString(1, imc.getUsuario()); //usuario
			java.sql.Date dateSQL = new java.sql.Date(Utiles.stringToDate(imc.getFecha()).getTime());
			stmt.setDate(2, dateSQL); //fecha
			stmt.setDouble(3, imc.getPeso()); //peso
			stmt.setInt(4, imc.getEstatura()); //estatura
			stmt.setDouble(5, imc.getImc()); //imc
			stmt.setDouble(6, imc.getPeso()); //peso
			stmt.setInt(7, imc.getEstatura()); //estatura
			stmt.setDouble(8, imc.getImc()); //imc
			
			int cant = stmt.executeUpdate();
			System.out.println("Cantidad de filas afectadas: " + cant);
			
		} catch (SQLException sqle) { 
		  System.out.println("Error en la ejecución:" + sqle.getErrorCode() + " " + sqle.getMessage()); 
		  throw sqle;
		
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
				} catch (Throwable th) { }
		}
	}
	
	public List<CalculoIMC> getCalculosDeUsuario(String usuario) throws SQLException, Exception {
		//Declaramos las variables
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<CalculoIMC> historicos = new ArrayList<CalculoIMC>(); //Lista de calculos
		
		try {
			//Establecemos la conexion
			con = this.getConnection();
			stmt = con.prepareStatement("SELECT * FROM calculo WHERE usuario = ? ORDER BY fecha DESC");
			stmt.setString(1, usuario); //usuario
			//Ejecutamos la consulta
			rs = stmt.executeQuery();
			
			CalculoIMC imc;
			while (rs.next()) {
				imc = new CalculoIMC();
			    imc.setUsuario(rs.getString("usuario"));
			    Date fecha = rs.getTimestamp("fecha");
			    imc.setFecha(Utiles.dateToString(fecha));
			    //imc.setFecha(String.valueOf(rs.getDate("fecha")));
			    imc.setPeso(rs.getDouble("peso"));
			    imc.setEstatura(rs.getInt("estatura"));
			    imc.setImc(rs.getDouble("imc"));
			    
			    //Agregamos el calculo a la lista
			    historicos.add(imc);
			}
			
			return historicos;
			
		} catch (SQLException sqle) { 
		  System.out.println("Error en la ejecución:" + sqle.getErrorCode() + " " + sqle.getMessage()); 
		  throw sqle;
		
		} finally {
			try {
				if (rs != null)
            		rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
				} catch (Throwable th) { }
		}
	}
	
}
