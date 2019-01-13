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

		if (y < min_y)
			code |= outside;

		else if (y > max_y)
			code |= outside;

		return code;
	}

	public Ponto calcularIntersec(RetanguloGr recorte, Reta r) {

		Ponto p = null;

		for (RetaGr rt : recorte.calcularRetas()) {

			if (OperacoesMatematicas.interseccaoRetas(rt.getReta(), r) != null) {

				p = OperacoesMatematicas.interseccaoRetas(rt.getReta(), r);

				break;

			}

		}

		return p;
	}

	public List<PoligonoGr> recortar(PoligonoGr p) {

		List<PoligonoGr> poligonosResultantes = new ArrayList<PoligonoGr>();

		List<Ponto> pontos = new ArrayList<Ponto>();

		int inout;

		int inoutNext;

		Ponto p_corrente;

		Ponto p_proximo;

		for (int i = 0; i < p.getPontos().size() - 2; i++) {

			p_corrente = p.getPontos().get(i);

			p_proximo = p.getPontos().get(i + 1);

			inout = verificarBorda(p_corrente);

			inoutNext = verificarBorda(p_proximo);

			if (inout == 1 && inoutNext == 0) {

				Ponto pontoNovo;

				pontoNovo = this.calcularIntersec(area_recorte, new Reta(p_corrente, p_proximo));

				if (pontoNovo != null) {

					pontos.add(pontoNovo);

				}

			}

			else if (inout == 0 && inoutNext == 1) {

				Ponto pontoNovo;

				pontoNovo = this.calcularIntersec(area_recorte, new Reta(p_corrente, p_proximo));

				pontos.add(p_corrente);

				pontos.add(pontoNovo);

				pontos.add(pontos.get(0));

				poligonosResultantes.add(new PoligonoGr(new ArrayList<Ponto>(pontos), p.getCor()));

				poligonosResultantes.get(poligonosResultantes.size() - 1).setPoligonoFechado(true);

				pontos = new ArrayList<Ponto>();

			}

			else if (inout == 0 && inoutNext == 0) {

				for (RetaGr rt : area_recorte.calcularRetas()) {

					if (OperacoesMatematicas.interseccaoRetas(rt.getReta(), new Reta(p_corrente, p_proximo)) != null) {

						pontos.add(
								OperacoesMatematicas.interseccaoRetas(rt.getReta(), new Reta(p_corrente, p_proximo)));

						break;

					}

				}

			}

		}

		if (p.getPoligonoFechado() == true) {

			p_corrente = p.getPontos().get(p.getPontos().size() - 1);

			p_proximo = p.getPontos().get(0);

			for (RetaGr rt : area_recorte.calcularRetas()) {

				if (OperacoesMatematicas.interseccaoRetas(rt.getReta(), new Reta(p_corrente, p_proximo)) != null) {

					pontos.add(OperacoesMatematicas.interseccaoRetas(rt.getReta(), new Reta(p_corrente, p_proximo)));

					break;

				}

			}

			if (pontos.size() >= 1) {

				poligonosResultantes.add(new PoligonoGr(new ArrayList<Ponto>(pontos), p.getCor()));

			}

		}

		return poligonosResultantes;

	}

}