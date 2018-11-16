package tools.xml;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import primitivos.coordenadas.Conversor;
import primitivos.grafico.CirculoGr;
import primitivos.grafico.FormaGr;
import primitivos.grafico.PoligonoGr;
import primitivos.grafico.RetaGr;
import primitivos.grafico.RetanguloGr;
import primitivos.math.Ponto;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class FileWriter {

	public static void write(File file, List<FormaGr> formas, Pane canvas) {

		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element figuraElement = doc.createElement("Figura");
			doc.appendChild(figuraElement);

			for (FormaGr forma : formas) {

				if (forma instanceof RetaGr) {
					createReta(doc, figuraElement, (RetaGr) forma, canvas);
				} else if (forma instanceof CirculoGr) {
					createCirculo(doc, figuraElement, (CirculoGr) forma, canvas);
				} else if (forma instanceof RetanguloGr) {
					createRetangulo(doc, figuraElement, (RetanguloGr) forma, canvas);
				} else if (forma instanceof PoligonoGr) {
					createLinhaPoligonal(doc, figuraElement, (PoligonoGr) forma, canvas);
				}
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);

		} catch (Exception tfe) {
			tfe.printStackTrace();
		}
	}

	private static void createLinhaPoligonal(Document doc, Element figuraElement, PoligonoGr linhaPoligonal,
			Pane canvas) {

		String tagName;

		tagName = (linhaPoligonal.getPoligonoFechado() == true) ? "Poligono" : "LinhaPoligonal";

		Element linhaPoligonalElement = doc.createElement(tagName);
		figuraElement.appendChild(linhaPoligonalElement);

		for (Ponto p : linhaPoligonal.getPontos()) {
			linhaPoligonalElement.appendChild(createPonto(p, doc, canvas));
		}
		linhaPoligonalElement.appendChild(createCor(doc, linhaPoligonal.getCor()));
	}

	private static void createRetangulo(Document doc, Element figuraElement, RetanguloGr retangulo, Pane canvas) {
		Element retanguloElement = doc.createElement("Retangulo");
		figuraElement.appendChild(retanguloElement);

		retanguloElement.appendChild(createPonto(retangulo.getVertice1(), doc, canvas));
		retanguloElement.appendChild(createPonto(retangulo.getVertice2(), doc, canvas));

		retanguloElement.appendChild(createCor(doc, retangulo.getCor()));
	}

	private static void createCirculo(Document doc, Element figuraElement, CirculoGr circulo, Pane canvas) {
		Element circuloElement = doc.createElement("Circulo");
		figuraElement.appendChild(circuloElement);

		circuloElement.appendChild(createPonto(circulo.getCirculo().getCentro(), doc, canvas));
		Element raio = doc.createElement("Raio");
		raio.appendChild(doc.createTextNode(
				(Double.toString(Conversor.pixelToRelative(circulo.getCirculo().getRaio(), (int) canvas.getWidth())))));
		circuloElement.appendChild(raio);
		circuloElement.appendChild(createCor(doc, circulo.getCor()));
	}

	private static void createReta(Document doc, Element figuraElement, RetaGr reta, Pane canvas) {
		Element retaElement = doc.createElement("Reta");
		figuraElement.appendChild(retaElement);
		retaElement.appendChild(createPonto(reta.getReta().getP1(), doc, canvas));
		retaElement.appendChild(createPonto(reta.getReta().getP2(), doc, canvas));
		System.out.println(reta.getCor());
		retaElement.appendChild(createCor(doc, reta.getCor()));
	}

	private static Element createPonto(Ponto ponto, Document doc, Pane canvas) {
		Element dot = doc.createElement("Ponto");
		Element px = doc.createElement("x");
		Element py = doc.createElement("y");

		px.appendChild(
				doc.createTextNode(Double.toString(Conversor.pixelToRelative(ponto.getX(), (int) canvas.getWidth()))));

		py.appendChild(
				doc.createTextNode(Double.toString(Conversor.pixelToRelative(ponto.getY(), (int) canvas.getHeight()))));

		dot.appendChild(px);
		dot.appendChild(py);
		return dot;
	}

	private static Element createCor(Document doc, Color corForm) {
		Element cor = doc.createElement("Cor");
		Element r = doc.createElement("R");
		Element g = doc.createElement("G");
		Element b = doc.createElement("B");
		r.appendChild(doc.createTextNode(Integer.toString((int) corForm.getRed() * 255)));
		g.appendChild(doc.createTextNode(Integer.toString((int) corForm.getGreen() * 255)));
		b.appendChild(doc.createTextNode(Integer.toString((int) corForm.getBlue() * 255)));

		cor.appendChild(r);
		cor.appendChild(g);
		cor.appendChild(b);
		return cor;
	}

}
