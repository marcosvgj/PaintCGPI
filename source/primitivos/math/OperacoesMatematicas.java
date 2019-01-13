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
        	
            return null; // não há intersecção

        double s = ((b1.getX() - a1.getX()) * (a1.getY() - a0.getY()) - (b1.getY() - a1.getY()) * (a1.getX() - a0.getX())) / determinante;

        double x = a0.getX() + (b0.getX() - a0.getX()) * s;

        double y = a0.getY() + (b0.getY() - a0.getY()) * s;

        return new Ponto(x, y); // há intersecção
        
    }
    
    public static List<Ponto> interseccaoRetaCirculo(Reta r0, Circulo c0) {
    	
    	// TODO 
    	
    	/*
    	 * IDEIA:
    	 *  
    	 *  Para cada reta do retangulo de recorte: 
    	
    		* Identificar a intersecção da reta e da circunferência
    		* 
    		* Se há dois pontos de intersecção na reta corrente, logo a reta é secante a circunferência, 
    		* se enquadrando no seguinte tratamento: 
    			- Gerar um novo ponto definido por (cX, rY), sendo cX a coordenada x do ponto central da circunferência
    			e rY o eixo da abscissa Y da reta avaliada. 
    			- Identificar os dois arco tangente formado entre os pontos de intersecção e o novo ponto central.
    			- Desenhar os arcos, preservando a borda determinada pela reta R.
    			   	
    		* Se há somente um ponto de intersecção, procurar o outro ponto de intersecção da circunferência em relação a
    		* outras retas do retângulo de recorte:
				- Se achou os dois pontos, adquirir o angulo formado pelos dois vetores com origem no ponto (cX, rY).
				- Desenhar o arco tangente
    	
    	*/ 
    	EquacaoReta equacao = new EquacaoReta(r0);
    	
    	System.out.printf("\n Reta: (%f, %f) - (%f, %f) \n", r0.getP1().getX(), r0.getP1().getY(),
    			r0.getP2().getX(), r0.getP2().getY());
    	
    	double m =  equacao.calculaM();
    	
    	double b =  equacao.calculaB();
    	
    	double formA = m + 1 ;
    	
    	double formB = (2*m*b) - (2*c0.getCentro().getX()) - (2*c0.getCentro().getY());
    	
    	double formC = Math.pow(b, 2) + (2*c0.getCentro().getY()*b) + Math.pow(c0.getCentro().getX(), 2) +
    			Math.pow(c0.getCentro().getY(), 2) - Math.pow(c0.getRaio(), 2);
    			
    	
    	double delta = Math.pow(formB, 2) - 4*formA*formC;
    	
    	System.out.printf("m: %f \n", m);
    	
    	System.out.printf("b: %f \n", b);
    	
    	System.out.printf("formA: %f \n", formA);
    	
    	System.out.printf("formB: %f \n", formB);
    	
    	System.out.printf("formC: %f \n", formC);
    	
    	System.out.println(" \n Fim iteracao \n" );
    	
    	if(delta < 0) {
    		
    			return Arrays.asList(new Ponto(0,0), new Ponto(0,0));
    			
    	}
    	

    	double x1 = ((-1*formB) + Math.sqrt(delta))/2*formA;
    	
    	double x2 = ((-1*formB) - Math.sqrt(delta))/2*formA;
    	
    	double y1 = m*x1+b;
    	
    	double y2 = m*x2+b;
    	
    	return Arrays.asList(new Ponto(x1, y1), new Ponto(x2, y2));
    	
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

