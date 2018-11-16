package tools.recorte;

import java.util.ArrayList;
import java.util.List;
import primitivos.grafico.PoligonoGr;
import primitivos.grafico.RetaGr;
import primitivos.grafico.RetanguloGr;
import primitivos.math.OperacoesMatematicas;
import primitivos.math.Ponto;
import primitivos.math.Reta;

public class WeilerAthertonPolygon {

    private RetanguloGr area_recorte;

    private Double min_x, min_y, max_x, max_y;

    private final int outside = 1;

    private final int inside = 0;
    
    private final int INSCRITO = 0;  // 0000
	 
	private final int ESQUERDA = 1;  // 0001
	 
	private final int DIREITA = 2;   // 0010
	 
	private final int ABAIXO = 4;    // 0100
	 
	private final int ACIMA = 8;     // 1000

    public WeilerAthertonPolygon(RetanguloGr r) {

        this.min_x = Double.min(r.getVertice1().getX(), r.getVertice2().getX());

        this.max_x = Double.max(r.getVertice1().getX(), r.getVertice2().getX());

        this.min_y = Double.min(r.getVertice1().getY(), r.getVertice2().getY());

        this.max_y = Double.max(r.getVertice1().getY(), r.getVertice2().getY());
        
        this.area_recorte = new RetanguloGr(new Ponto(min_x, max_y), new Ponto(max_x, min_y), r.getCor());        

    }

    int verificarBorda(Ponto p) {

        Double x = p.getX();

        Double y = p.getY();

        int code = x < min_x ? outside : x > max_x ? outside : inside;

        if (y < min_y) code |= outside;

        else if (y > max_y) code |= outside;

        return code;
    }
    
    int verificarLado(Ponto p) {
		 
    	 double x = p.getX();
    			 
    	 double y = p.getY();
    	 
		 int code = x < min_x ? ESQUERDA : x > max_x ? DIREITA : INSCRITO;
		 
		 if (y < min_y) code |= ABAIXO; 
		 
		 else if (y > max_y) code |= ACIMA; 
		     
		 return code; 
		
	 }
    
    public Ponto calcularIntersec(RetanguloGr recorte, Reta r) {
    	
    	Ponto p = null;
    	
    	for(RetaGr rt : recorte.calcularRetas()) {
    		
    		if(OperacoesMatematicas.interseccaoRetas(rt.getReta(), r) != null) {
    			
    			p =  OperacoesMatematicas.interseccaoRetas(rt.getReta(), r);
    			
    			break;
    			
    		}
    		
    	}
    	
    	return p;
    }
 
    public List < PoligonoGr > recortar(PoligonoGr p) {

        List < PoligonoGr > poligonosResultantes = new ArrayList < PoligonoGr > ();

        List < List < Ponto > > pontos = new ArrayList < List< Ponto > > ();
        
        pontos.add(new ArrayList<Ponto>());
        
        int indice = 0;
        
        int inout;
        
        int inoutNext;
        
        Ponto p_corrente;
        
        Ponto p_proximo;

        for (int i = 0; i < p.getPontos().size() - 2; i++) {

        	p_corrente = p.getPontos().get(i);
        	
        	p_proximo = p.getPontos().get(i+1);
        	
        	inout = verificarBorda(p_corrente);
        	
        	inoutNext = verificarBorda(p_proximo);
        	
        	if(inout == 1 && inoutNext == 0) {
        		
        		Ponto pontoNovo;
        		
        		switch (verificarLado(p_corrente)) {
        		
        			case ESQUERDA:
        				
        				pontoNovo = this.calcularIntersec(area_recorte, new Reta(p_corrente, p_proximo));
        				
        				pontos.get(indice).add(pontoNovo);
        				
        				break;
        				
        			case DIREITA:
        				
        				pontoNovo = this.calcularIntersec(area_recorte, new Reta(p_corrente, p_proximo));
        				
        				pontos.get(indice).add(pontoNovo);
        				
        				break;
        				
        			case ABAIXO:
        				
        				pontoNovo = this.calcularIntersec(area_recorte, new Reta(p_corrente, p_proximo));
        				
        				pontos.get(indice).add(pontoNovo);
        				
        				break;
        			case ACIMA:
        				
        				pontoNovo = this.calcularIntersec(area_recorte, new Reta(p_corrente, p_proximo));
        				
        				pontos.get(indice).add(pontoNovo);
        				
        				break;
        		}
        		
        	}
        	
        	else if(inout == 0 && inoutNext == 1) {
        		
        		pontos.add(new ArrayList<Ponto>());
        		
        		Ponto pontoNovo;
        		
        		PoligonoGr novoPoligono;
        		
        		switch (verificarLado(p_proximo)) {
        		
    			case ESQUERDA:
    				
    				pontoNovo = this.calcularIntersec(area_recorte, new Reta(p_corrente, p_proximo));
    				
    				pontos.get(indice).add(p_corrente);
    				
    				pontos.get(indice).add(pontoNovo);
    				
    				pontos.get(indice).add(pontos.get(indice).get(0));
    				
    				novoPoligono = new PoligonoGr(pontos.get(indice));
    				
    				novoPoligono.setPoligonoFechado(true);
    				
    				poligonosResultantes.add(new PoligonoGr(novoPoligono.getPontos(), p.getCor()));
    				
    				indice ++ ;
    				
    				break;
    				
    			case DIREITA:
    				
    				pontoNovo = this.calcularIntersec(area_recorte, new Reta(p_corrente, p_proximo));
    				
    				pontos.get(indice).add(p_corrente);
    				
    				pontos.get(indice).add(pontoNovo);
    				
    				pontos.get(indice).add(pontos.get(indice).get(0));
    				
    				novoPoligono = new PoligonoGr(pontos.get(indice));
    				
    				novoPoligono.setPoligonoFechado(true);
    				
    				poligonosResultantes.add(new PoligonoGr(novoPoligono.getPontos(), p.getCor()));

    				indice++;
    				
    				break;
    				
    			case ABAIXO:
    				
    				pontoNovo = this.calcularIntersec(area_recorte, new Reta(p_corrente, p_proximo));
    				
    				pontos.get(indice).add(p_corrente);
    				
    				pontos.get(indice).add(pontoNovo);
    				
    				pontos.get(indice).add(pontos.get(indice).get(0));
    				
    				novoPoligono = new PoligonoGr(pontos.get(indice));
    				
    				novoPoligono.setPoligonoFechado(true);
    				
    				poligonosResultantes.add(new PoligonoGr(novoPoligono.getPontos(), p.getCor()));

    				indice++;
    				
    				break;
    			case ACIMA:
    				
    				pontoNovo = this.calcularIntersec(area_recorte, new Reta(p_corrente, p_proximo));
    				
    				pontos.get(indice).add(p_corrente);
    				
    				pontos.get(indice).add(pontoNovo);
    				
    				pontos.get(indice).add(pontos.get(indice).get(0));
    				
    				novoPoligono = new PoligonoGr(pontos.get(indice));
    				
    				novoPoligono.setPoligonoFechado(true);

    				indice++;
    				
    				break;
        		}
        		
        	}
        	
        	else if(inout == 0 && inoutNext == 0) {
        		
        		pontos.get(indice).add(p_corrente);
        		
        	}

        }

        return poligonosResultantes;
        
    }
    
    
    	

}