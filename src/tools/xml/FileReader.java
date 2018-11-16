package tools.xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;

import primitivos.coordenadas.Conversor;
import primitivos.grafico.CirculoGr;
import primitivos.grafico.FormaGr;
import primitivos.grafico.PoligonoGr;
import primitivos.grafico.RetaGr;
import primitivos.grafico.RetanguloGr;
import primitivos.math.Ponto;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

	private List<FormaGr> formas = new ArrayList<>();

	public boolean readFile(File file, Pane canvas) {
		try {

			File fXmlFile = file;

			if (Conversor.fileExt(fXmlFile.getAbsolutePath()).equals("xml")) {

				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
				buildRetas(doc.getElementsByTagName("Reta"), canvas);
				buildRetangulos(doc.getElementsByTagName("Retangulo"), canvas);
				buildCirculos(doc.getElementsByTagName("Circulo"), canvas);
				buildLinhasPoligonais(doc.getElementsByTagName("LinhaPoligonal"), false, canvas);
				buildLinhasPoligonais(doc.getElementsByTagName("Poligono"), true, canvas);

			} else {
				JOptionPane.showMessageDialog(null, "Por favor, Selecione um XML");
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<FormaGr> getFormas() {
		return formas;
	}

	private void buildLinhasPoligonais(NodeList nList, Boolean fechado, Pane canvas) {
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				List<Ponto> pontos = new ArrayList<Ponto>();

				Element elementCor = (Element) eElement.getElementsByTagName("Cor").item(0);
				String corR = elementCor.getElementsByTagName("R").item(0).getTextContent();
				String corG = elementCor.getElementsByTagName("G").item(0).getTextContent();
				String corB = elementCor.getElementsByTagName("B").item(0).getTextContent();

				NodeList nl = eElement.getElementsByTagName("Ponto");
				for (int i = 0; i < nl.getLength(); i++) {
					Element elementPonto = (Element) nl.item(i);

					String p1x = elementPonto.getElementsByTagName("x").item(0).getTextContent();
					String p1y = elementPonto.getElementsByTagName("y").item(0).getTextContent();
					pontos.add(new Ponto((double) Conversor.relativeToPixel(p1x, (int) canvas.getWidth()),
							(double) Conversor.relativeToPixel(p1y, (int) canvas.getHeight())));

				}

				Color cor = Color.color(Integer.parseInt(corR) / 255, Integer.parseInt(corG) / 255,
						Integer.parseInt(corB) / 255);
				formas.add(new PoligonoGr(pontos, cor, fechado));

			}
		}
	}

	private void buildRetas(NodeList nList, Pane canvas) {
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;

				Element elementCor = (Element) eElement.getElementsByTagName("Cor").item(0);
				String corR = elementCor.getElementsByTagName("R").item(0).getTextContent();
				String corG = elementCor.getElementsByTagName("G").item(0).getTextContent();
				String corB = elementCor.getElementsByTagName("B").item(0).getTextContent();

				Element elementPonto1 = (Element) eElement.getElementsByTagName("Ponto").item(0);
				String p1x = elementPonto1.getElementsByTagName("x").item(0).getTextContent();
				String p1y = elementPonto1.getElementsByTagName("y").item(0).getTextContent();

				Element elementPonto2 = (Element) eElement.getElementsByTagName("Ponto").item(1);
				String p2x = elementPonto2.getElementsByTagName("x").item(0).getTextContent();
				String p2y = elementPonto2.getElementsByTagName("y").item(0).getTextContent();

				createReta(p1x, p1y, p2x, p2y, corR, corG, corB, canvas);

			}
		}
	}

	private void buildRetangulos(NodeList nList, Pane canvas) {
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;

				Element elementCor = (Element) eElement.getElementsByTagName("Cor").item(0);
				String corR = elementCor.getElementsByTagName("R").item(0).getTextContent();
				String corG = elementCor.getElementsByTagName("G").item(0).getTextContent();
				String corB = elementCor.getElementsByTagName("B").item(0).getTextContent();

				Element elementPonto1 = (Element) eElement.getElementsByTagName("Ponto").item(0);
				String p1x = elementPonto1.getElementsByTagName("x").item(0).getTextContent();
				String p1y = elementPonto1.getElementsByTagName("y").item(0).getTextContent();

				Element elementPonto2 = (Element) eElement.getElementsByTagName("Ponto").item(1);
				String p2x = elementPonto2.getElementsByTagName("x").item(0).getTextContent();
				String p2y = elementPonto2.getElementsByTagName("y").item(0).getTextContent();

				createRetangulo(p1x, p1y, p2x, p2y, corR, corG, corB, canvas);
			}
		}
	}

	private void buildCirculos(NodeList nList, Pane canvas) {
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;

				Element elementCor = (Element) eElement.getElementsByTagName("Cor").item(0);
				String corR = elementCor.getElementsByTagName("R").item(0).getTextContent();
				String corG = elementCor.getElementsByTagName("G").item(0).getTextContent();
				String corB = elementCor.getElementsByTagName("B").item(0).getTextContent();

				Element elementPonto1 = (Element) eElement.getElementsByTagName("Ponto").item(0);
				String p1x = elementPonto1.getElementsByTagName("x").item(0).getTextContent();
				String p1y = elementPonto1.getElementsByTagName("y").item(0).getTextContent();

				String raio = eElement.getElementsByTagName("Raio").item(0).getTextContent();

				createCirculo(p1x, p1y, raio, corR, corG, corB, canvas);
			}
		}
	}

	private void createReta(String p1x, String p1y, String p2x, String p2y, String corR, String corG, String corB,
			Pane canvas) {

		int ponto1X = Conversor.relativeToPixel(p1x, (int) canvas.getWidth());
		int ponto1Y = Conversor.relativeToPixel(p1y, (int) canvas.getHeight());
		int ponto2X = Conversor.relativeToPixel(p2x, (int) canvas.getWidth());
		int ponto2Y = Conversor.relativeToPixel(p2y, (int) canvas.getHeight());

		Color cor = Color.color(Integer.parseInt(corR) / 255, Integer.parseInt(corG) / 255,
				Integer.parseInt(corB) / 255);

		formas.add(new RetaGr(new Ponto(ponto1X, ponto1Y), new Ponto(ponto2X, ponto2Y), cor));
	}

	private void createRetangulo(String p1x, String p1y, String p2x, String p2y, String corR, String corG, String corB,
			Pane canvas) {

		int ponto1X = Conversor.relativeToPixel(p1x, (int) canvas.getWidth());
		int ponto1Y = Conversor.relativeToPixel(p1y, (int) canvas.getHeight());
		int ponto2X = Conversor.relativeToPixel(p2x, (int) canvas.getWidth());
		int ponto2Y = Conversor.relativeToPixel(p2y, (int) canvas.getHeight());

		Color cor = Color.color(Integer.parseInt(corR) / 255, Integer.parseInt(corG) / 255,
				Integer.parseInt(corB) / 255);
		formas.add(new RetanguloGr(new Ponto(ponto1X, ponto1Y), new Ponto(ponto2X, ponto2Y), cor));
	}

	private void createCirculo(String p1x, String p1y, String raioStr, String corR, String corG, String corB,
			Pane canvas) {
		int ponto1X = Conversor.relativeToPixel(p1x, (int) canvas.getWidth());
		int ponto1Y = Conversor.relativeToPixel(p1y, (int) canvas.getHeight());
		int raio = (int) Conversor.relativeToPixel(raioStr, (int) canvas.getHeight());

		Color cor = Color.color(Integer.parseInt(corR) / 255, Integer.parseInt(corG) / 255,
				Integer.parseInt(corB) / 255);
		formas.add(new CirculoGr(new Ponto(ponto1X, ponto1Y), raio, cor));
	}
}
