package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ErrorGeneral;
import model.Usuario;
import service.Service;


@WebServlet("/RegistrarUsuario")
public class RegistrarUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RegistrarUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//Tomamos los datos del formulario de registro (inputs) para actualizar el Usuario, para poner en la session
			String nombre = (String)request.getParameter("nombre");
			String sexo = (String)request.getParameter("sexo");
			int edad = Integer.parseInt((String)request.getParameter("edad"));
			int estatura = Integer.parseInt((String)request.getParameter("estatura"));
			
			//Creamos el objeto Usuario y cargmos los datos recibidos del formulario
			Usuario usuario = new Usuario();
			usuario.usuario = (String)request.getParameter("usuario");
			usuario.password = (String)request.getParameter("password");
			usuario.nombre = nombre;
			usuario.edad = edad;
			usuario.estatura = estatura;
			usuario.sexo = sexo;
			
			//Realizamos el registro del nuevo usuario	
			Service.getInstance().registrarUsuario(usuario);
			
			//Actualiazmos los datos del usuario registrado en memoria con los datos que se cargaron.
			request.getSession().setAttribute("usuario", usuario);
			
			//Redirigimos a la JSP de bienvenida
			request.getRequestDispatcher("jsp/welcome.jsp").forward(request, response);
		
		} catch (ErrorGeneral e) {
			request.setAttribute("error", e);
			//Redirigimos a la JSP de fallo o error
			request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
			
		} catch (Exception e) {
			model.ErrorGeneral error = new ErrorGeneral("Exception", e.getMessage());
			request.setAttribute("error", error);
			
			//Redirigimos a la JSP de fallo o error
			request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
