package tools.recorte;

import primitivos.grafico.PontoGr;
import primitivos.grafico.RetaGr;
import primitivos.grafico.RetanguloGr;

public class CohenSutherland {
	 
	 private int INSCRITO = 0;  // 0000
	 
	 private int ESQUERDA = 1;  // 0001
	 
	 private int DIREITA = 2;   // 0010
	 
	 private int ABAIXO = 4;    // 0100
	 
	 private int ACIMA = 8;     // 1000
	 
	 private Double min_x, min_y, max_x, max_y;
	 
	 public CohenSutherland(RetanguloGr r) {

		 this.min_x = Double.min(r.getVertice1().getX(), r.getVertice2().getX());
		 
		 this.max_x = Double.max(r.getVertice1().getX(), r.getVertice2().getX());
		 
		 this.min_y = Double.min(r.getVertice1().getY(), r.getVertice2().getY());
		 
		 this.max_y = Double.max(r.getVertice1().getY(), r.getVertice2().getY());
		 
	 }
	 
	 
	 public int codificarVertice(Double x, Double y) {
		 
		 int code = x < min_x ? ESQUERDA : x > max_x ? DIREITA : INSCRITO;
		 
		 if (y < min_y) code |= ABAIXO; 
		 
		 else if (y > max_y) code |= ACIMA; 
		     
		 return code; 
		
	 }
	 
	public RetaGr recortar(RetaGr r) {

		double p1x = r.getReta().getP1().getX();

		double p1y = r.getReta().getP1().gety();

		double p2x = r.getReta().getP2().getx();

		double p2y = r.getReta().getP2().gety();

		double qx = 0d;

		double qy = 0d;

		boolean vertical = p1x == p2x;

		double slope = vertical ? 0d : (p2y - p1y) / (p2x - p1x);

		int c1 = codificarVertice(p1x, p1y);

		int c2 = codificarVertice(p2x, p2y);

		while (c1 != INSCRITO || c2 != INSCRITO) {

			if ((c1 & c2) != INSCRITO) {

				return null;

			}

			int c = c1 == INSCRITO ? c2 : c1;

			if ((c & ESQUERDA) != INSCRITO) {

				qx = min_x;

				qy = (qx - p1x) * slope + p1y;

			} else if ((c & DIREITA) != INSCRITO) {

				qx = max_x;

				qy = (qx - p1x) * slope + p1y;

			} else if ((c & ABAIXO) != INSCRITO) {

				qy = min_y;

				qx = vertical ? p1x : (qy - p1y) / slope + p1x;

			}

			else if ((c & ACIMA) != INSCRITO) {

				qy = max_y;

				qx = vertical ? p1x : (qy - p1y) / slope + p1x;

			}

			if (c == c1) {

				p1x = qx;

				p1y = qy;

				c1 = codificarVertice(p1x, p1y);

			} else {

				p2x = qx;

				p2y = qy;

				c2 = codificarVertice(p2x, p2y);
			}
		}

		return new RetaGr(new PontoGr((int) p1x, (int) p1y), new PontoGr((int) p2x, (int) p2y), r.getCor());

	} 

}

	 
	  
	 
	 
	 


