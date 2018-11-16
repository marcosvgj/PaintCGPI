package primitivos.grafico;
import primitivos.math.EquacaoReta;
import primitivos.math.OperacoesMatematicas;
import primitivos.math.Ponto;
import primitivos.math.Reta;
import default_package.ModusOperandi;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class RetaGr extends FormaGr {
	
	Reta reta;
	
	EquacaoReta equacao;
	
	public RetaGr(Ponto p1, Ponto p2, Color cor) {

		this.reta = new Reta(p1, p2);
		
		this.cor = cor;
		
		this.equacao = new EquacaoReta(this.reta);
		
		setOperacao(ModusOperandi.RETA);
	}

	@Override
    public void desenhar(GraphicsContext g) {
    	
    	double b = equacao.calculaB();
        
        double m = equacao.calculaM();
        
        if(Math.abs(this.reta.getP2().getY()-this.reta.getP1().getY())>Math.abs(this.reta.getP2().getX()-this.reta.getP1().getX())){
        	
            if(this.reta.getP1().getY()>this.reta.getP2().getY()){

                if (this.reta.getP1().getX()==this.reta.getP2().getX()){
                    PontoGr ponto=new PontoGr((int)this.reta.getP1().getX(),(int)this.reta.getP1().getY(),this.getCor());
                    ponto.desenharPonto(g);
                    for(double y=this.reta.getP2().getY();y<this.reta.getP1().getY();++y){
                        ponto=new PontoGr((int)(this.reta.getP1().getX()),(int)y,this.getCor());
                        ponto.desenharPonto(g);
                    }
                }
                else{
                    PontoGr ponto=new PontoGr((int)this.reta.getP2().getX(),(int)this.reta.getP2().getY(),this.getCor());
                    ponto.desenharPonto(g);
                    for(double y=this.reta.getP2().getY();y<this.reta.getP1().getY();++y){
                        ponto=new PontoGr((int)((y-b)/m),(int)y,this.getCor());
                        ponto.desenharPonto(g);
                    }
                }
            }else if(this.reta.getP1().getX()==this.reta.getP2().getX()){

                PontoGr ponto=new PontoGr((int)this.reta.getP1().getX(),(int)this.reta.getP1().getY(),this.getCor());
                ponto.desenharPonto(g);
                for(double y=this.reta.getP1().getY();y<this.reta.getP2().getY();++y){
                    ponto=new PontoGr((int)(this.reta.getP1().getX()),(int)y,this.getCor());
                    ponto.desenharPonto(g);
                }
            }else{

                PontoGr ponto=new PontoGr((int)this.reta.getP1().getX(),(int)this.reta.getP1().getY(),this.getCor());
                ponto.desenharPonto(g);
                for(double y=this.reta.getP1().getY();y<this.reta.getP2().getY();++y){
                    ponto=new PontoGr((int)((y-b)/m),(int)y,this.getCor());
                    ponto.desenharPonto(g);
                }

            }

        }else{

        	if(this.reta.getP1().getX()>this.reta.getP2().getX()){

                PontoGr ponto=new PontoGr((int)this.reta.getP2().getX(),(int)this.reta.getP2().getY(),this.getCor());
                ponto.desenharPonto(g);
                for(double x=this.reta.getP2().getX();x<this.reta.getP1().getX();++x){
                    ponto=new PontoGr((int)x,(int)(b+m*x),this.getCor());
                    ponto.desenharPonto(g);
                }
                
            }else if(this.reta.getP1().getX()==this.reta.getP2().getX()){

                PontoGr ponto=new PontoGr((int)this.reta.getP1().getX(),(int)this.reta.getP1().getY(),this.getCor());
                ponto.desenharPonto(g);
                for(double x=this.reta.getP1().getX();x<this.reta.getP2().getX();++x){
                    ponto=new PontoGr((int)(this.reta.getP1().getX()),(int)(b+m*x),this.getCor());
                    ponto.desenharPonto(g);
                }
                
            }else{

                PontoGr ponto=new PontoGr((int)this.reta.getP1().getX(),(int)this.reta.getP1().getY(),this.getCor());
                ponto.desenharPonto(g);
                for(double x=this.reta.getP1().getX();x<this.reta.getP2().getX();++x){
                    ponto=new PontoGr((int)x,(int)(b+m*x),this.getCor());
                    ponto.desenharPonto(g);
                }
            }
        }

    }

    @Override
	public boolean pontoNaForma(Ponto vertice, int margemErro) {
		
		double equacao = this.equacao.equacaoReta(vertice.getX(), vertice.getY());
			
		return Math.abs(equacao) <= margemErro ? true : false;
			
		}

	public Reta getReta() {
		return reta;
	}

	public EquacaoReta getEquacao() {
		return equacao;
	}

	public void setReta(Reta reta) {
		this.reta = reta;
	}

	public void setEquacao(EquacaoReta equacao) {
		this.equacao = equacao;
	}

	@Override
	public void rotacionar(Ponto q, double angulo) {
	    	
		OperacoesMatematicas op = new OperacoesMatematicas();
		
		Ponto p1 = op.rotacionar(this.reta.getP1(), q, angulo);
		
		Ponto p2 = op.rotacionar(this.reta.getP2(), q, angulo);
		
		this.reta = new Reta(p1, p2);
		
		this.equacao = new EquacaoReta(reta);
		
		
	}

	@Override
	public void escalar(double fatorEscala) {
		
		OperacoesMatematicas op = new OperacoesMatematicas();
		
		Ponto p1 = op.escalar(this.reta.getP1(), fatorEscala);
		
		Ponto p2 = op.escalar(this.reta.getP2(), fatorEscala);
		
		this.reta = new Reta(p1, p2);
		
		this.equacao = new EquacaoReta(reta);
		
	}

	@Override
	public void trasladar(int distanciaX, int distanciaY) {
			
		OperacoesMatematicas op = new OperacoesMatematicas();
		
		Ponto p1 = op.trasladar(this.reta.getP1(), distanciaX, distanciaY);
		
		Ponto p2 = op.trasladar(this.reta.getP2(), distanciaX, distanciaY);
		
		this.reta = new Reta(p1, p2);
		
		this.equacao = new EquacaoReta(reta);
		
	}
		
	}


