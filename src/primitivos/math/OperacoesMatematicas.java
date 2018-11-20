package primitivos.math;

import java.util.Arrays;
import java.util.List;

import primitivos.grafico.CirculoGr;

public class OperacoesMatematicas {
	
    public static Ponto interseccaoRetas(Reta r0, Reta r1) {
    	
    	Ponto a0 = r0.getP1();
    	
    	Ponto b0 = r0.getP2();
    	
    	Ponto a1 = r1.getP1();
    	
    	Ponto b1 = r1.getP2();

    	double determinante = (b1.getX() - a1.getX()) * (b0.getY() - a0.getY()) - (b1.getY() - a1.getY()) * (b0.getX() - a0.getX());

        if (determinante == 0)
        	
            return null; // n�o h� intersec��o

        double s = ((b1.getX() - a1.getX()) * (a1.getY() - a0.getY()) - (b1.getY() - a1.getY()) * (a1.getX() - a0.getX())) / determinante;

        double x = a0.getX() + (b0.getX() - a0.getX()) * s;

        double y = a0.getY() + (b0.getY() - a0.getY()) * s;

        return new Ponto(x, y); // h� intersec��o
        
    }
    
    public static void interseccaoRetaCirculo(Reta r0, Circulo c0) {
    	
    	// TODO 
    	
    	/*
    	 * IDEIA:
    	 *  
    	 *  Para cada reta do retangulo de recorte: 
    	
    		* Identificar a intersec��o da reta e da circunfer�ncia
    		* Se h� dois pontos de intersec��o na reta corrente, logo a reta � secante a circunfer�ncia, 
    		* se enquadrando no seguinte tratamento: 
    			- Gerar um novo ponto definido por (cX, rY), sendo cX a coordenada x do ponto central da circunfer�ncia
    			e rY o eixo da abscissa Y da reta avaliada. 
    			- Identificar os dois arco tangente formado entre os pontos de intersec��o e o novo ponto central.
    			- Desenhar os arcos, preservando a borda determinada pela reta R.
    			   	
    		* Se h� somente um ponto de intersec��o, procurar o outro ponto de intersec��o da circunfer�ncia em rela��o a
    		* outras retas do ret�ngulo de recorte:
				- Se achou os dois pontos, adquirir o angulo formado pelos dois vetores com origem no ponto (cX, rY).
				- Desenhar o arco tangente
    	
    	*/ 
    	
		
   }
    
    public Ponto trasladar(Ponto p, int fatorX, int fatorY){
        
    	double x = p.getX() + fatorX; 
        
        double y = p.getY() + fatorY;
        
        return new Ponto((int) x,(int) y);
    }

    public Ponto escalar(Ponto p, Ponto q, double fatorX, double fatorY){
    	
    	double x = (fatorX*p.getX()) + (q.getX()*(1-fatorX));
    	
    	double y = (p.getY()*fatorY) + (q.getY()*(1-fatorY)); 
    	
        return new Ponto(x, y);

    }

    public Ponto rotacionar(Ponto p, Ponto q, double angulo) {
       
        double rad = Math.toRadians(angulo);
        
        double x = (p.getX()*Math.cos(rad)) - (p.getY() * Math.sin(rad)) + ((q.getX()*(1- Math.cos(rad)))+ (q.getY()* Math.sin(rad)));
        
        double y = (p.getX()*Math.sin(rad)) + (p.getY()*Math.cos(rad)) + ((q.getY()*(1-Math.cos(rad))) - ((q.getX()*Math.sin(rad))));
        
        return new Ponto(x, y);
        
        
        
        
    }


}
