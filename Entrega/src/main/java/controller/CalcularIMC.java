package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CalculoIMC;
import model.ErrorGeneral;
import model.Usuario;
import service.Service;


@WebServlet("/CalcularIMC")
public class CalcularIMC extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public CalcularIMC() {
        super();
    }

    //Este servlet permite realizar un nuevo calculo de IMC para el usuario logueado, guardarlo en tabla y actualizar los datos en sesion
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {		
			//Tomamos los datos del formulario de Login (inputs) para crear nuestro objeto Usuario, para poner en la session
			String peso = (String)request.getParameter("peso");
			
			if(peso == null || "".equals(peso)) {
				//Creamos un objeto Error para poder informar de la situacion
				ErrorGeneral error = new ErrorGeneral("Usuario incorrecto", "Los datos ingresados para el login son incorrectos.");
				throw error;
				
			} else {
				Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
				CalculoIMC imc = Service.getInstance().caluclarIMC(Double.valueOf(peso), usuario);
				
				request.setAttribute("mensaje", "El calculo del IMC se genero satisfactoriamente. El IMC actual es " + imc.getImc());
				request.setAttribute("urlFwrd", "/Inicio");
				request.getRequestDispatcher("jsp/mensaje.jsp").forward(request, response);
			}
		
		} catch (ErrorGeneral e) {
			request.setAttribute("error", e);
			//Redirigimos a la JSP de fallo o error
			request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
			
		} catch (Exception e) {
			ErrorGeneral error = new ErrorGeneral("Exception", e.getMessage());
			request.setAttribute("error", error);
			//Redirigimos a la JSP de fallo o error
			request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
