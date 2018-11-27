package default_package;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import primitivos.grafico.CirculoGr;
import primitivos.grafico.FormaGr;
import primitivos.grafico.PoligonoGr;
import primitivos.grafico.PontoGr;
import primitivos.grafico.RetaGr;
import primitivos.grafico.RetanguloGr;
import primitivos.math.OperacoesMatematicas;
import primitivos.math.Ponto;
import tools.recorte.CohenSutherland;
import tools.recorte.RetanguloRecorte;

public class Tela {

	ModusOperandi operacao;

	Color cor;

	Color corFormaSelecionada;

	List<FormaGr> formas;

	Ponto _p1, _p2, oldP2;

	RetaGr _reta;

	RetanguloGr _retangulo;

	CirculoGr _circulo;

	PoligonoGr _poligono;

	PoligonoGr _linhaPoligonal;

	RetanguloRecorte _recorte;

	FormaGr formaSelecionada;

	boolean pReferencia = false;

	PontoGr pontoReferencia = null;

	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

	public Tela() {

		this.formas = new ArrayList<FormaGr>();

		this._poligono = new PoligonoGr(new ArrayList<Ponto>(), this.cor);

		this._linhaPoligonal = new PoligonoGr(new ArrayList<Ponto>(), this.cor);

	}

	public void desenhar(GraphicsContext gc, MouseEvent e) {

		if (operacao != ModusOperandi.POLIGONO & _poligono.getPontos().size() > 1) {

			_poligono.setPoligonoFechado(true);

			_poligono.adicionarPonto(_poligono.getPontos().get(0));

			_poligono.desenhar(gc);

			formas.add(new PoligonoGr(new ArrayList<Ponto>(_poligono.getPontos()), this.cor));

			_poligono.setPontos(new ArrayList<Ponto>());

			_poligono.setPoligonoFechado(false);

			_poligono.getPontos().clear();

			_p1 = null;

			_p2 = null;

			oldP2 = null;

		}

		if (operacao != ModusOperandi.LINHA_POLIGONAL & _linhaPoligonal.getPontos().size() > 1) {

			_linhaPoligonal.desenhar(gc);

			formas.add(new PoligonoGr(new ArrayList<Ponto>(_linhaPoligonal.getPontos()), this.cor));

			_linhaPoligonal.setPontos(new ArrayList<Ponto>());

			_linhaPoligonal.setPoligonoFechado(false);

			_linhaPoligonal.getPontos().clear();

			_p1 = null;

			_p2 = null;

			oldP2 = null;

		}

		if (e.isSecondaryButtonDown() == true) {

			if (operacao == ModusOperandi.POLIGONO & _poligono.getPontos().size() > 1) {

				_poligono.adicionarPonto(_poligono.getPontos().get(0));

				_poligono.desenhar(gc);

				formas.add(new PoligonoGr(new ArrayList<Ponto>(_poligono.getPontos()), this.cor, true));

				_poligono.setPontos(new ArrayList<Ponto>());

				_poligono.setPoligonoFechado(false);

				_poligono.getPontos().clear();

				_p1 = null;

				_p2 = null;

				oldP2 = null;

			}

			else if (operacao == ModusOperandi.LINHA_POLIGONAL & _linhaPoligonal.getPontos().size() > 1) {

				_linhaPoligonal.desenhar(gc);

				formas.add(new PoligonoGr(new ArrayList<Ponto>(_linhaPoligonal.getPontos()), this.cor, false));

				_linhaPoligonal.setPontos(new ArrayList<Ponto>());

				_linhaPoligonal.setPoligonoFechado(false);

				_linhaPoligonal.getPontos().clear();

				_p1 = null;

				_p2 = null;

				oldP2 = null;

			}
		} else {

			switch (e.getEventType().getName()) {

			case "MOUSE_CLICKED":

				mouseClicked(gc, e);

				break;

			case "MOUSE_PRESSED":

				mousePressed(gc, e);

				break;

			case "MOUSE_DRAGGED":

				mouseDragged(gc, e);

				break;

			case "MOUSE_RELEASED":

				mouseReleased(gc, e);

				break;

			}

			if (_poligono.getPontos().size() >= 1 && operacao != ModusOperandi.POLIGONO) {

				_poligono.setPontos(new ArrayList<Ponto>());

			}

		}

	}

	public void apagarTela(GraphicsContext gc) {

		gc.clearRect(0, 0, screen.getWidth(), screen.getHeight());

	}

	public void resetarTela(GraphicsContext gc) {

		this.formas = new ArrayList<FormaGr>();

		this._poligono = new PoligonoGr(new ArrayList<Ponto>(), this.cor);

		this._linhaPoligonal = new PoligonoGr(new ArrayList<Ponto>(), this.cor);

		this.pReferencia = false;

		apagarTela(gc);

	}

	public void redesenhar(GraphicsContext gc) {

		for (FormaGr f : formas) {

			f.desenhar(gc);

		}

	}

	public void apagarSelecionado(GraphicsContext gc) {

		if (this.formaSelecionada != null) {

			formas.remove(formaSelecionada);

			apagarTela(gc);

			redesenhar(gc);

		}

	}

	public void escalarSelecionado(GraphicsContext gc, Double fatorX, Double fatorY) {

		formaSelecionada.escalar(pontoReferencia, fatorX, fatorY);

		apagarTela(gc);

		redesenhar(gc);

	}

	public void trasladarSelecionado(GraphicsContext gc, int distanciaX, int distanciaY) {

		formaSelecionada.trasladar(distanciaX, distanciaY);

		apagarTela(gc);

		redesenhar(gc);

	}

	public void recortar(GraphicsContext gc, RetanguloGr area) {

		apagarTela(gc);

		CohenSutherland recorteReta = new CohenSutherland(area);

		List<FormaGr> exclusao = new ArrayList<FormaGr>();

		List<FormaGr> novasFormas = new ArrayList<FormaGr>();

		for (int i = 0; i < formas.size(); i++) {

			if (formas.get(i) instanceof RetaGr) {

				if (recorteReta.recortar((RetaGr) formas.get(i)) != null) {

					formas.set(i, recorteReta.recortar((RetaGr) formas.get(i)));

				} else {

					formas.set(i, new RetaGr(new Ponto(0, 0), new Ponto(0, 0), Color.WHITE));

				}
			}

			if (formas.get(i) instanceof PoligonoGr) {

				PoligonoGr pl = (PoligonoGr) formas.get(i);

				if (pl.getPoligonoFechado() == true) {

					tools.recorte.WeilerAthertonPolygon recorte = new tools.recorte.WeilerAthertonPolygon(area);

					if (recorte.recortar((PoligonoGr) formas.get(i)) != null) {

						exclusao.add((PoligonoGr) formas.get(i));

						for (PoligonoGr polig : recorte.recortar((PoligonoGr) formas.get(i))) {

							novasFormas.add(polig);

						}

					} else {

						formas.set(i, new PoligonoGr(new ArrayList<Ponto>(), Color.WHITE));

					}
				} else {

					List<Ponto> pontos = new ArrayList<Ponto>();

					for (RetaGr rt : pl.calcularRetas()) {

						if (recorteReta.recortar(rt) != null) {

							RetaGr newReta = recorteReta.recortar(rt);

							pontos.add(newReta.getReta().getP1());

							pontos.add(newReta.getReta().getP2());

						}

					}

					formas.set(i, new PoligonoGr(new ArrayList<Ponto>(pontos), pl.getCor()));

				}

			}

			if (formas.get(i) instanceof CirculoGr) {
				for (RetaGr r : area.calcularRetas()) {

					CirculoGr c0 = (CirculoGr) formas.get(i);

					List<Ponto> op = OperacoesMatematicas.interseccaoRetaCirculo(r.getReta(), c0.getCirculo());

					for (Ponto p : op) {
						System.out.println(p);
						PontoGr pNovo = new PontoGr((int) p.getX(), (int) p.getY(), Color.RED, 10);

						pNovo.desenharPonto(gc);
					}
				}
			}

		}

		for (FormaGr excl : exclusao) {

			formas.remove(excl);

		}

		for (FormaGr x : novasFormas) {

			formas.add(x);

		}
	}

	public void rotacionarSelecionado(GraphicsContext gc, double angulo) {

		formaSelecionada.rotacionar(pontoReferencia, angulo);

		apagarTela(gc);

		redesenhar(gc);

	}

	private void mousePressed(GraphicsContext gc, MouseEvent e) {

		if (operacao == ModusOperandi.POLIGONO & _poligono.getPontos().size() >= 1) {

			_poligono.desenhar(gc);

		} else if (operacao == ModusOperandi.LINHA_POLIGONAL & _linhaPoligonal.getPontos().size() >= 1) {

			_linhaPoligonal.desenhar(gc);
		} else {

			_p1 = new PontoGr((int) e.getX(), (int) e.getY());

			if (operacao == ModusOperandi.POLIGONO) {

				_poligono = new PoligonoGr(this.cor);

				_poligono.adicionarPonto(_p1);

			} else if (operacao == ModusOperandi.LINHA_POLIGONAL) {

				_linhaPoligonal = new PoligonoGr(this.cor);

				_linhaPoligonal.adicionarPonto(_p1);

			}

		}
		if (operacao == ModusOperandi.SELECIONAR) {

			Ponto p = new Ponto(e.getX(), e.getY());

			boolean selecionado = false;

			for (FormaGr f : formas) {

				selecionado |= f.pontoNaForma(p, 20);

				if (selecionado == true) {

					if (formaSelecionada != null) {

						formaSelecionada.setCor(this.corFormaSelecionada);

						formaSelecionada = f;

						corFormaSelecionada = f.getCor();

						formaSelecionada.setCor(Color.RED);

					} else {

						formaSelecionada = f;

						corFormaSelecionada = f.getCor();

						formaSelecionada.setCor(Color.RED);

					}

					break;

				}

			}

		}

	}

	private void mouseReleased(GraphicsContext gc, MouseEvent e) {

		if (_p2 != null) {

			switch (operacao) {

			case RETA:

				_reta = new RetaGr(_p1, _p2, this.cor);

				_reta.desenhar(gc);

				formas.add(new RetaGr(_p1, _p2, this.cor));

				break;

			case CIRCULO:

				Ponto centro = _circulo.getCirculo().getCentro();

				double raio = _circulo.getCirculo().getRaio();

				formas.add(new CirculoGr(centro, raio, this.cor));

				break;

			case LIMPAR:

				break;

			case POLIGONO:

				if (_poligono.getPoligonoFechado() == false) {

					_poligono.adicionarPonto(_p2);

					_poligono.desenhar(gc);

				}

				break;

			case LINHA_POLIGONAL:

				if (_linhaPoligonal.getPoligonoFechado() == false) {

					_linhaPoligonal.adicionarPonto(_p2);

					_linhaPoligonal.desenhar(gc);

				}

				break;

			case RETANGULO:

				formas.add(new RetanguloGr(_retangulo.getVertice1(), _retangulo.getVertice2(), this.cor));

				break;

			case RECORTAR:

				apagarTela(gc);

				recortar(gc, _recorte);

				break;

			}

			redesenhar(gc);

		}

		if (operacao != ModusOperandi.POLIGONO || operacao != ModusOperandi.LINHA_POLIGONAL) {

			_p1 = null;

			_p2 = null;

			oldP2 = null;

		} else {

			oldP2 = null;

		}

	}

	private void mouseClicked(GraphicsContext gc, MouseEvent e) {

		if (operacao == ModusOperandi.POLIGONO && _poligono.getPontos().size() >= 1) {

			_p2 = new PontoGr((int) e.getX(), (int) e.getY());

			_poligono.adicionarPonto(_p2);

			_poligono.desenhar(gc);

		}

		if (operacao == ModusOperandi.LINHA_POLIGONAL && _linhaPoligonal.getPontos().size() >= 1) {

			_p2 = new PontoGr((int) e.getX(), (int) e.getY());

			_linhaPoligonal.adicionarPonto(_p2);

			_linhaPoligonal.desenhar(gc);

		}

		if (operacao == ModusOperandi.ROTACIONAR || operacao == ModusOperandi.ESCALA) {

			apagarTela(gc);

			pontoReferencia = new PontoGr((int) e.getX(), (int) e.getY(), Color.BLACK, "P'");

			pontoReferencia.desenharPonto(gc);

			redesenhar(gc);

			pReferencia = true;

		}

	}

	private void mouseDragged(GraphicsContext gc, MouseEvent e) {

		if (oldP2 == null) {

			oldP2 = new PontoGr((int) e.getX(), (int) e.getY());

			apagarTela(gc);

			redesenhar(gc);

			_p2 = new PontoGr((int) e.getX(), (int) e.getY());

			switch (operacao) {

			case CIRCULO:

				_circulo = new CirculoGr(_p1, _p2.calcularDistancia(_p1), this.cor);

				_circulo.desenhar(gc);

				break;

			case POLIGONO:

				_poligono.setCor(this.cor);

				Ponto p = _poligono.getPontos().get(_poligono.getPontos().size() - 1);

				RetaGr reta = new RetaGr(p, _p2, this.cor);

				reta.desenhar(gc);

				_poligono.desenhar(gc);

				break;

			case LINHA_POLIGONAL:

				_linhaPoligonal.setCor(this.cor);

				Ponto pLp = _linhaPoligonal.getPontos().get(_linhaPoligonal.getPontos().size() - 1);

				RetaGr retalp = new RetaGr(pLp, _p2, this.cor);

				retalp.desenhar(gc);

				_linhaPoligonal.desenhar(gc);

				break;

			case RETANGULO:

				_retangulo = new RetanguloGr(_p1, _p2, this.cor);

				_retangulo.desenhar(gc);

				break;

			case RECORTAR:

				_recorte = new RetanguloRecorte(_p1, _p2, this.cor);

				_recorte.desenhar(gc);

				break;

			case RETA:

				_reta = new RetaGr(_p1, _p2, this.cor);

				_reta.desenhar(gc);

				break;

			}

		}

		oldP2 = null;
	}

	public ModusOperandi getOperacao() {
		return operacao;
	}

	public Color getCor() {
		return cor;
	}

	public void setOperacao(ModusOperandi operacao) {
		this.operacao = operacao;
	}

	public void setCor(Color cor) {
		this.cor = cor;
	}

	public void setForma(List<FormaGr> f) {

		this.formas = f;

	}

	public List<FormaGr> getFormas() {

		return this.formas;

	}

}
