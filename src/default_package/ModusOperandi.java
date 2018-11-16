package default_package;

public enum ModusOperandi {
	RETA("RETA"), CIRCULO("CIRCULO"), RETANGULO("RETANGULO"), POLIGONO("POLIGONO"), LIMPAR("LIMPAR"), 
	RECORTAR("RECORTAR"), APAGAR_SELECIONADO("APAGAR SELECIONADO"), NENHUM("NENHUM SELECIONADO"), 
	SELECIONAR("SELECIONAR"), ESCALA("ESCALA"), ROTACIONAR("ROTACIONAR"), TRASLADAR("TRASLADAR"), 
	LINHA_POLIGONAL("LINHA POLIGONAL");

	private final String toString;

	private ModusOperandi(String toString) {

		this.toString = toString;
	}

	public String toString() {

		return " " + toString.toLowerCase().substring(0, 1).toUpperCase() + toString.toString().toLowerCase().substring(1);

	}

}
