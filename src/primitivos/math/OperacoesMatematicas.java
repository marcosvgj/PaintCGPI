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
    
    public static void interseccaoRetaCirculo(Reta r0, Circulo c0) {
    	
    	// TODO 
		
   }
    
    public Ponto trasladar(Ponto p, int fatorX, int fatorY){
        
    	double x = p.getX() + fatorX; 
        
        double y = p.getY() + fatorY;
        
        return new Ponto((int) x,(int) y);
    }

    public Ponto escalar(Ponto p, double fatorDeEscala){
    	
    	double x = p.getX() * fatorDeEscala;
        
        double y = p.getY() * fatorDeEscala;
        
        return new Ponto((int) x,(int) y);

    }

    public Ponto rotacionar(Ponto p, Ponto q, double angulo) {
        
        //Rotação em relação ao ponto central da viewport
    	
        //transladar -p atÃ© a origem
        Ponto p_new = trasladar(p, (int)(-1*q.getX()), (int)(-1*q.getY()));

        //rotacionar angulo
        double rad = Math.toRadians(angulo);
        
        double old_x = p_new.getX();
        
        double old_y = p_new.getY();
        
        double x = old_x * Math.cos(rad) - old_y * Math.sin(rad);
        
        double y = old_x * Math.sin(rad) + old_y * Math.cos(rad);
        

        //desfazer a translacao

        return trasladar(new Ponto(x,y), (int)(q.getX()), (int)(q.getY()));
        
    }


}
