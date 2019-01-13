package primitivos.grafico;
import default_package.ModusOperandi;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import primitivos.math.Ponto;

public abstract class FormaGr {

	protected Color cor;

	private ModusOperandi operacao;

	public abstract boolean pontoNaForma(Ponto p, int margemErro);
	
	public abstract void rotacionar(Ponto q, double angulo);
	 
	public abstract void escalar(Ponto q, double fatorX, double fatorY);
	
	public abstract void trasladar(int distanciaX, int distanciaY);
	
	public abstract void desenhar(GraphicsContext gc);
	
	public Color getCor() {

		return this.cor;

	}

	public void setCor(Color cor) {

		this.cor = cor;

	}

	public ModusOperandi getOperacao() {
		
		return this.operacao;
	}

	public void setOperacao(ModusOperandi operacao) {
		
		this.operacao = operacao;
	}

}
