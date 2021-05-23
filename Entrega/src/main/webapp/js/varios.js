//Funcion que permite que solo se ingresen numeros y algunos caracteres especiales si el campo es de tipo numerico
function esNumericoPlus(e) {
	//devuelve true si es numero o tab, enter, backspace, delete, flecha izquierda o flecha derecha. (incluye "-", "." y ",")
	var tecla = (e.keyCode) ? e.keyCode : e.which;
	return  (((tecla >= 44) && (tecla <= 57)) 
  			|| (tecla == 8) 
  			|| (tecla == 9) || (tecla == 13) 
  			|| (tecla == 37) || (tecla == 39));

}