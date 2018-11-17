package primitivos.grafico;

import default_package.ModusOperandi;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import primitivos.math.Circulo;
import primitivos.math.OperacoesMatematicas;
import primitivos.math.Ponto;

public class CirculoGr extends FormaGr {

	Circulo circulo;

	public CirculoGr(Ponto c, double r, Color cor) {

		setCirculo(new Circulo(c, r));

		setCor(cor);

		this.setOperacao(ModusOperandi.CIRCULO);

	}

	public void desenhar(GraphicsContext gc) {

		desenharCirculo((int) circulo.getCentro().getX(), (int) circulo.getCentro().getY(), (int) circulo.getRaio(),
				gc);

	}

	void desenharCirculo(int cx, int cy, int raio, GraphicsContext g) {

		if (raio != 0) {

			int x = 0;

			int y = raio;

			PontoGr p = new PontoGr(x, y, getCor());

			for (double alfa = 0; alfa <= 45; alfa = alfa + 0.1) {

				y = (int) (raio * Math.sin(alfa));

				x = (int) (raio * Math.cos(alfa));

				p = new PontoGr(cx + x, cy + y, getCor());

				p.desenharPonto(g);

				p = new PontoGr(cx + y, cy + x, getCor());

				p.desenharPonto(g);

				p = new PontoGr(cx + y, cy - x, getCor());

				p.desenharPonto(g);

				p = new PontoGr(cx + x, cy - y, getCor());

				p.desenharPonto(g);

				p = new PontoGr(cx - x, cy - y, getCor());

				p.desenharPonto(g);

				p = new PontoGr(cx - y, cy - x, getCor());

				p.desenharPonto(g);

				p = new PontoGr(cx - y, cy + x, getCor());

				p.desenharPonto(g);

				p = new PontoGr(cx - x, cy + y, getCor());

				p.desenharPonto(g);
			}
		}

	}

	@Override
	public boolean pontoNaForma(Ponto p, int margemErro) {

		double distancia = p.calcularDistancia(this.circulo.getCentro());

		return (int) Math.abs(this.circulo.getRaio()) <= (distancia + margemErro) ? true : false;

	}

	public Circulo getCirculo() {
		return circulo;
	}

	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
	}

	@Override
	public void rotacionar(Ponto q, double angulo) {
		
		OperacoesMatematicas op = new OperacoesMatematicas();
		
		this.circulo.setCentro(op.rotacionar(this.circulo.getCentro(), q, angulo));
		
	}

	@Override
	public void escalar(Ponto q, double fatorX, double fatorY ) {
		
		OperacoesMatematicas op = new OperacoesMatematicas();
		
		double raio = this.circulo.getRaio() * ((fatorX * fatorY)/2) ;
		
		Ponto centro = op.escalar(this.circulo.getCentro(), q, fatorX, fatorY);
		
		this.circulo =  new Circulo(centro, raio);
	}

	@Override
	public void trasladar(int distanciaX, int distanciaY) {
		
		OperacoesMatematicas op = new OperacoesMatematicas();
		
		this.circulo.setCentro(op.trasladar(this.circulo.getCentro(), distanciaX, distanciaY));
		
		
	}


}
