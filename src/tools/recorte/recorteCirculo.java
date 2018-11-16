package tools.recorte;

import java.util.List;
import primitivos.grafico.CirculoGr;
import primitivos.grafico.RetaGr;

public class recorteCirculo {

	CirculoGr circulo;
	
	RetanguloRecorte recorte;
	
	recorteCirculo(RetanguloRecorte r){
		this.recorte = r;
	}
	
	public void recortarCirculo(CirculoGr c){
		
		List<RetaGr> retas = recorte.calcularRetas();

		
	}
}
