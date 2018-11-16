package primitivos.math;

public class Circulo {
	
	Ponto centro;
	
	double raio;
	
	public Circulo(Ponto c, double r) {
		
		setCentro(c);
		
		setRaio(r);
		
	}

	public Ponto getCentro() {
		return centro;
	}

	public void setCentro(Ponto centro) {
		this.centro = centro;
	}

	public double getRaio() {
		return raio;
	}

	public void setRaio(double raio) {
		this.raio = raio;
	}


}
