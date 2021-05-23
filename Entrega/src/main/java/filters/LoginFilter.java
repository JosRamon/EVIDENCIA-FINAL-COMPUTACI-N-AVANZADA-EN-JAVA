package filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ErrorGeneral;

//Este filtro validara que haya un usuario en la sesion para poder acceder a las demas pantallas y servlets
//Se excluyen aquellos que se pueden ejecutar sin tener un usuario logueado.

@WebFilter(filterName="LoginFilter", urlPatterns="/*")
public class LoginFilter implements Filter {
	FilterConfig config;
	private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
	        Arrays.asList("", "/Login", "/jsp/login.jsp", "/jsp/register.jsp", "/RegistrarUsuario", "/jsp/error.jsp")));
	
    public LoginFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)throws ServletException, IOException {
		 
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession(false);
		
		try{
			
			String urlsolicitada = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
			//Evitamos revisar los archivos .js .css .png .jpg y .gif
			if(urlsolicitada.matches(".*(css|jpg|png|gif|js)")){
			    chain.doFilter(request, response);
			    return;
			}
			
			boolean permitePath = ALLOWED_PATHS.contains(urlsolicitada);
			boolean hayUsuario = (session != null && session.getAttribute("usuario") != null);
			
			 if (hayUsuario || permitePath) {
				 chain.doFilter(req, resp);
				 
		     } else {
		    	 response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
		     }
		
		} catch (Throwable th) {
			//Creamos un objeto Error para poder informar de la situacion
			model.ErrorGeneral error = new ErrorGeneral("Error en el filtro de login", th.getMessage());
			request.setAttribute("error", error);
			
			//Redirigimos a la JSP de fallo o error
			request.getRequestDispatcher("jsp/error.jsp").forward(request, (HttpServletResponse)resp);						

		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// Código de inicialización del filtro
        this.config = config;
	}

}
