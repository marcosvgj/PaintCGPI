package primitivos.grafico;

import java.util.ArrayList;
import java.util.List;
import default_package.ModusOperandi;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import primitivos.math.OperacoesMatematicas;
import primitivos.math.Ponto;

public class RetanguloGr extends FormaGr {

	private Ponto vertice1, vertice2, vertice3, vertice4;

	public RetanguloGr(Ponto vertice1, Ponto vertice2, Color cor) {

		this.vertice1 = vertice1;

		this.vertice2 = vertice2;

		this.vertice3 = new Ponto(this.getVertice1().getX(), this.getVertice2().getY());

		this.vertice4 = new Ponto(this.getVertice2().getX(), this.getVertice1().getY());

		setCor(cor);

		setOperacao(ModusOperandi.RETANGULO);

	}

	public List<RetaGr> calcularRetas() {

		ArrayList<RetaGr> retas = new ArrayList<>();

		retas.add(new RetaGr(vertice1, vertice3, this.cor));

		retas.add(new RetaGr(vertice1, vertice4, this.cor));

		retas.add(new RetaGr(vertice2, vertice3, this.cor));

		retas.add(new RetaGr(vertice2, vertice4, this.cor));

		return retas;
	}

	@Override
	public void desenhar(GraphicsContext gc) {

		for (RetaGr reta : calcularRetas()) {

			reta.desenhar(gc);

		}

	}

	@Override
	public boolean pontoNaForma(Ponto p, int margemErro) {

		boolean pontoNaForma = false;

		for (RetaGr reta : calcularRetas()) {

			pontoNaForma |= reta.pontoNaForma(p, margemErro);
		}
		return pontoNaForma;

	}

	public Ponto getVertice1() {
		return vertice1;
	}

	public Ponto getVertice2() {
		return vertice2;
	}

	public Ponto getVertice3() {
		return vertice3;
	}

	public Ponto getVertice4() {
		return vertice4;
	}

	public void setVertice1(Ponto vertice1) {
		this.vertice1 = vertice1;
	}

	public void setVertice2(Ponto vertice2) {
		this.vertice2 = vertice2;
	}

	public void setVertice3(Ponto vertice3) {
		this.vertice3 = vertice3;
	}

	public void setVertice4(Ponto vertice4) {
		this.vertice4 = vertice4;
	}

	@Override
	public void rotacionar(Ponto q, double angulo) {

		OperacoesMatematicas op = new OperacoesMatematicas();

		this.vertice1 = op.rotacionar(this.vertice1, q, angulo);

		this.vertice2 = op.rotacionar(this.vertice2, q, angulo);

		this.vertice3 = op.rotacionar(this.vertice3, q, angulo);

		this.vertice4 = op.rotacionar(this.vertice4, q, angulo);

	}

	@Override
	public void escalar(Ponto q, double fatorX, double fatorY ) {

		OperacoesMatematicas op = new OperacoesMatematicas();

		this.vertice1 = op.escalar(getVertice1(), q, fatorX, fatorY);

		this.vertice2 = op.escalar(getVertice2(), q, fatorX, fatorY);

		this.vertice3 = op.escalar(this.vertice3, q, fatorX, fatorY);

		this.vertice4 = op.escalar(this.vertice4, q, fatorX, fatorY);

	}

	@Override
	public void trasladar(int distanciaX, int distanciaY) {

		OperacoesMatematicas op = new OperacoesMatematicas();

		this.vertice1 = op.trasladar(getVertice1(), distanciaX, distanciaY);

		this.vertice2 = op.trasladar(getVertice2(), distanciaX, distanciaY);

		this.vertice3 = op.trasladar(this.vertice3, distanciaX, distanciaY);

		this.vertice4 = op.trasladar(this.vertice4, distanciaX, distanciaY);

	}

}
