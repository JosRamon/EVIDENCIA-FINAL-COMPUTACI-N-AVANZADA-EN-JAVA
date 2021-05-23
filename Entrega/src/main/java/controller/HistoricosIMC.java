package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dto.RespuestaJSON;
import model.CalculoIMC;
import model.ErrorGeneral;
import service.Service;


@WebServlet("/HistoricosIMC")
public class HistoricosIMC extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public HistoricosIMC() {
        super();
    }

    //Este servlet permite obtener la lista de historicos de calculos de IMC del usuario recibido por parametro y 
    //devolver esta lista como un JSon.
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RespuestaJSON respJson = new RespuestaJSON();
		
		try {
			//Tomamos los datos del usuario para poder obtener los historicos del mismo
			String usuario = (String)request.getParameter("usuario");
			if(usuario==null || "".equals(usuario)) {
				ErrorGeneral error = new ErrorGeneral("Error", "No se recibió el usuario.");
				throw error;
			}
			
			//Recuperamos los historicos
			List<CalculoIMC> historicos = Service.getInstance().getHistoricoCalculos(usuario);
			respJson.setColObjetos(historicos);
		
		} catch (ErrorGeneral e) {
			respJson.setExito(false);
			respJson.setLeyendaError(e.getMensaje());
		
		} catch (Exception e) {
			ErrorGeneral error = new ErrorGeneral("Exception", e.getMessage());
			respJson.setExito(false);
			respJson.setLeyendaError(error.getMensaje());
			
		}finally{
			Gson gson = new Gson();
			String json = gson.toJson(respJson);
			
			try {
				writeOutputJSON(response, json);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void writeOutputJSON(HttpServletResponse response, String json) throws Exception {
		//la codificacion ISO-8859 es para que no se cuelgue en IE y ademas no traiga caracteres raros
		response.setContentType("application/json; charset=iso-8859-1");
		ServletOutputStream output = response.getOutputStream();
		output.print(json);
		output.flush();
		output.close();
	}

}
