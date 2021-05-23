package utiles;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utiles {

	
	/**
     * Convierte un Date a String.
     */
    public static String dateToString(Date pFecha) {
		if(pFecha == null) return "";
		
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "AR"));  
        String strDate = formatter.format(pFecha); 
        return strDate;
    }

    
    /**
     * Convierte un Sstring en un Date
     */
    public static Date stringToDate(String pFecha) throws ParseException  {
    	DateFormat format = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "AR"));
    	Date date = format.parse(pFecha);
    	return date;
    }
    
    public static double redondear1Decimal(double valor) {
    	DecimalFormat df = new DecimalFormat("#.0");
    	String strValor = df.format(valor).replace(",", ".");
        return Double.parseDouble(strValor);
    }
	
}
