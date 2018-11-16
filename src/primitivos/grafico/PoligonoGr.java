package primitivos.grafico;

import java.util.ArrayList;
import java.util.List;
import default_package.ModusOperandi;
import javafx.scene.paint.Color;
import primitivos.math.OperacoesMatematicas;
import primitivos.math.Ponto;
import javafx.scene.canvas.GraphicsContext;

public class PoligonoGr extends FormaGr {

	private List<Ponto> pontos;

	private boolean poligonoFechado;

	public PoligonoGr(List<Ponto> pontos) {

		setPontos(pontos);

		setPoligonoFechado(false);

		setOperacao(ModusOperandi.POLIGONO);

	}

	public PoligonoGr(List<Ponto> pontos, Color cor) {

		setPontos(pontos);

		setCor(cor);

		setPoligonoFechado(false);

		setOperacao(ModusOperandi.POLIGONO);

	}

	public PoligonoGr(List<Ponto> pontos, Color cor, boolean fechado) {

		setPontos(pontos);

		setCor(cor);

		setPoligonoFechado(fechado);

		setOperacao(ModusOperandi.POLIGONO);

	}

	public PoligonoGr(Color cor) {

		setPontos(new ArrayList<Ponto>());

		setCor(cor);

		setOperacao(ModusOperandi.POLIGONO);

	}

	public List<Ponto> getPontos() {

		return pontos;

	}

	public void setPontos(List<Ponto> pontos) {

		this.pontos = pontos;

	}

	public void adicionarPonto(Ponto ponto) {

		this.pontos.add(ponto);

	}

	public boolean getPoligonoFechado() {

		return this.poligonoFechado;

	}

	public void setPoligonoFechado(boolean fechado) {

		this.poligonoFechado = fechado;

	}

	public List<RetaGr> calcularRetas() {

		List<RetaGr> r = new ArrayList<RetaGr>();

		for (int i = 0; i <= getPontos().size() - 2; i++) {

			r.add(new RetaGr(this.getPontos().get(i), this.getPontos().get(i + 1), this.getCor()));

		}

		if (this.poligonoFechado) {

			r.add(new RetaGr(this.getPontos().get(getPontos().size() - 1), this.getPontos().get(0), this.getCor()));
		}

		return r;

	}

	@Override
	public void desenhar(GraphicsContext gc) {

		for (RetaGr r : calcularRetas()) {

			r.desenhar(gc);

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

	@Override
	public void rotacionar(Ponto q, double angulo) {

		OperacoesMatematicas op = new OperacoesMatematicas();

		List<Ponto> novosPontos = new ArrayList<Ponto>();

		for (Ponto p : this.getPontos()) {

			novosPontos.add(op.rotacionar(p, q, angulo));
		}

		setPontos(new ArrayList<Ponto>(novosPontos));

	}

	@Override
	public void escalar(double fatorEscala) {

		OperacoesMatematicas op = new OperacoesMatematicas();

		List<Ponto> novosPontos = new ArrayList<Ponto>();

		for (Ponto p : this.getPontos()) {

			novosPontos.add(op.escalar(p, fatorEscala));
		}

		setPontos(new ArrayList<Ponto>(novosPontos));

	}

	@Override
	public void trasladar(int distanciaX, int distanciaY) {

		OperacoesMatematicas op = new OperacoesMatematicas();

		List<Ponto> novosPontos = new ArrayList<Ponto>();

		for (Ponto p : this.getPontos()) {

			novosPontos.add(op.trasladar(p, distanciaX, distanciaY));
		}

		setPontos(new ArrayList<Ponto>(novosPontos));

	}

}