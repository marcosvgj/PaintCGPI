package primitivos.math;

public class EquacaoReta {
	
	Ponto p1, p2;
	
	Reta reta;
	
	public EquacaoReta(Reta r){
		
		this.reta = r;
		
		this.p1 = r.getP1();
		
		this.p2 = r.getP2();
		
	}
	
	public EquacaoReta(Ponto p1, Ponto p2){
		
		this.reta = new Reta(p1, p2);

	}
	
	public double calculaM(){
        
        double m = (p2.getY()-p1.getY())/(p2.getX()-p1.getX());
        
        return m;
    }
    
    public double calculaB(){
        
        double m = calculaM();
        
        double b = p2.getY()-(m*p2.getX());
        
        return b;
        
    }
	
	public double equacaoReta(double x, double y){
		
		double m = calculaM();
		
		double b = calculaB();
		
		return m*x + b - y;
		
	}
	
	

}
