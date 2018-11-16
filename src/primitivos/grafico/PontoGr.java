package primitivos.grafico;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import primitivos.math.Ponto;

public class PontoGr extends Ponto {
	
	Color corPonto = Color.BLACK;
	
	String nomePonto = ""; 
	
	Color corNomePonto = Color.BLACK;
	
	int diametro = 2;

	public PontoGr(int x, int y){
		
		super((double)x, (double)y);
		
	}

	public PontoGr(int x, int y, Color corPonto){
		
		this(x, y);
		
		setCorPonto(corPonto);	 
	}

	public PontoGr(int x, int y, Color corPonto, int diametro){
		
		this(x, y, corPonto);
		
		setDiametro(diametro);
	}

	public PontoGr(int x, int y, Color corPonto, String nomePonto){
		
		this(x, y, corPonto);
		
		setNomePonto(nomePonto);
		
	}

	public PontoGr(int x, int y, Color corPonto, Color corNomePonto, String nomePonto){

		this(x, y, corPonto);
		
		setCorNomePonto(corNomePonto);
		
		setNomePonto(nomePonto);
		
	}

	public PontoGr(int x, int y, Color corPonto, String nomePonto, int diametro){
		
		this(x, y, corPonto, diametro);
		
		setNomePonto(nomePonto);
		
	}
	
	public PontoGr(PontoGr pg, Color corPonto, int diametro){
		
		this((int)pg.getX(),(int)pg.getY(), corPonto, diametro);
		
	}

	public PontoGr(int diametro){
		
		this(0,0);
		
		setDiametro(diametro);
		
	}

	private Color getCorPonto() {
		
		return corPonto;
		
	}

	private String getNomePonto() {
		
		return nomePonto;
		
	}

	private Color getCorNomePonto() {
		
		return corNomePonto;
		
	}

	private int getDiametro() {
		
		return diametro;
		
	}

	private void setDiametro(int diametro) {
		
		this.diametro = diametro;
		
	}

	private void setCorPonto(Color corPonto){
		
		this.corPonto = corPonto;
		
	}
	private void setCorNomePonto(Color corNomePonto){
		
		this.corNomePonto = corNomePonto;
		
	}
	private void setNomePonto(String nomePonto){
		
		this.nomePonto = nomePonto;
		
	}

	public void desenharPonto(GraphicsContext g) {
		
		g.setFill(getCorPonto());
		
		g.fillOval((int)getX() -(getDiametro()/2), (int)getY() - (getDiametro()/2), getDiametro(), getDiametro());
		
		g.setFill(getCorNomePonto());
		
		g.strokeText(getNomePonto(), (int)getX() + getDiametro(), (int)getY());
		
	}

}
