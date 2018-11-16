package tools.recorte;

import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;

import primitivos.grafico.RetaGr;

import primitivos.grafico.RetanguloGr;

import primitivos.math.Ponto;

public class RetanguloRecorte extends RetanguloGr  {

    public RetanguloRecorte(Ponto ponto_1, Ponto ponto_2, Color cor) {

        super(ponto_1, ponto_2, cor);

    }

    @Override
    public void desenhar(GraphicsContext gc) {
    	
        for (RetaGr r: super.calcularRetas()) {

            gc.setStroke(Color.BLACK);

            gc.setLineWidth(1);

            gc.setLineDashes(5);

            gc.strokeLine(r.getReta().getP1().getX(), r.getReta().getP1().gety(), r.getReta().getP2().getX(), r.getReta().getP2().gety());

        }

    }

}