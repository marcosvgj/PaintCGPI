package default_package;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javafx.application.*;
import javafx.event.EventHandler;
import javafx.stage.*;
import tools.xml.FileReader;
import tools.xml.FileWriter;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Gui extends Application {

	Canvas canvas = new Canvas();

	Pane wrapperPane = new Pane();

	GraphicsContext gc = canvas.getGraphicsContext2D();

	BorderPane scene_dispose = new BorderPane();

	Label tOperacao = new Label();

	Tela tela = new Tela();

	@Override
	public void start(Stage window) throws IOException {

		tela.setOperacao(ModusOperandi.NENHUM);
		
		tela.setCor(Color.BLACK);

		/* Declaração de Botoes e ferramentas do Javafx - Formas */

		Button btnReta = new Button();

		Button btnCirculo = new Button();

		Button btnRetangulo = new Button();

		Button btnPoligono = new Button();
		
		Button btnLinhaPoligonal = new Button();

		Button btnBorracha = new Button();

		Button btnSelecionar = new Button();

		Button btnApagarForma = new Button();

		Button btnImportar = new Button();

		Button btnEscala = new Button();

		Button btnTransladar = new Button();

		Button btnRotacao = new Button();

		Button btnSalvar = new Button();

		Button btnRecortar = new Button();

		ColorPicker cpSelecionarCor = new ColorPicker(Color.BLACK);
		
		cpSelecionarCor.getStyleClass().add("button"); 
		
		cpSelecionarCor.setPrefWidth(100);

		/* Estruturas de dados auxiliares - Formas */

		Map<String, Button> Botoes = new HashMap<String, Button>();

		Botoes.put("reta", btnReta);

		Botoes.put("circulo", btnCirculo);

		Botoes.put("retangulo", btnRetangulo);

		Botoes.put("poligono", btnPoligono);

		Botoes.put("borracha", btnBorracha);

		Botoes.put("selecionar", btnSelecionar);

		Botoes.put("apagar", btnApagarForma);

		Botoes.put("salvar", btnSalvar);

		Botoes.put("importar", btnImportar);

		Botoes.put("escala", btnEscala);

		Botoes.put("trasladar", btnTransladar);

		Botoes.put("rotacao", btnRotacao);

		Botoes.put("recortar", btnRecortar);
		
		Botoes.put("linhaPoligonal", btnLinhaPoligonal);

		/* Adição de icones p/ cada botão */

		for (String k : Botoes.keySet()) {

			ImageView image_view = new ImageView("gui/icones/" + k + ".png");

			image_view.setFitWidth(25);

			image_view.setFitHeight(25);

			Botoes.get(k).setGraphic(image_view);

		}

		Image ico = new Image("gui/icones/icone.png");

		window.getIcons().add(ico);
		
		btnSelecionar.setDisable(true);

		btnRecortar.setDisable(true);

		btnApagarForma.setDisable(true);
		
		btnEscala.setDisable(true);

		btnTransladar.setDisable(true);

		btnRotacao.setDisable(true);

		/* Conjunto de ferramentas */

		GridPane gridFormas = new GridPane();

		GridPane gridSelecao = new GridPane();

		GridPane gridXml = new GridPane();

		GridPane gridTransformacoes = new GridPane();

		GridPane gridApagar = new GridPane();

		gridFormas.add(btnReta, 0, 0);

		gridFormas.add(btnCirculo, 1, 0);

		gridFormas.add(btnRetangulo, 2, 0);

		gridFormas.add(btnPoligono, 3, 0);
		
		gridFormas.add(btnLinhaPoligonal, 4, 0);

		gridSelecao.add(btnSelecionar, 0, 0);

		gridSelecao.add(btnApagarForma, 1, 0);

		gridSelecao.add(btnRecortar, 2, 0);

		gridXml.add(btnSalvar, 0, 0);

		gridXml.add(btnImportar, 1, 0);

		gridTransformacoes.add(btnEscala, 0, 0);

		gridTransformacoes.add(btnTransladar, 1, 0);

		gridTransformacoes.add(btnRotacao, 2, 0);

		gridFormas.setHgap(5);

		gridFormas.setVgap(5);

		gridSelecao.setHgap(5);

		gridSelecao.setVgap(5);

		gridXml.setHgap(5);

		gridXml.setVgap(5);

		gridTransformacoes.setHgap(5);

		gridTransformacoes.setVgap(5);

		gridApagar.add(btnBorracha, 1, 0);

		gridApagar.setHgap(5);

		gridApagar.setVgap(5);

		/* Adicionando propriedades visuais - CSS */

		wrapperPane.setId("background");

		wrapperPane.getChildren().add(canvas);

		canvas.widthProperty().bind(wrapperPane.widthProperty());

		canvas.heightProperty().bind(wrapperPane.heightProperty());

		/* Definicão da barra de ferramentas */

		ToolBar conjuntoFerramentas = new ToolBar();

		/* Populando o conjunto de ferramentas criado */

		VBox conjuntoFormas = new VBox();
		conjuntoFormas.getChildren().addAll(new Label("Formas"), new Label(), gridFormas);

		VBox conjuntoCorEspessura = new VBox();
		conjuntoCorEspessura.getChildren().addAll(new Label("Cor"), new Label(), cpSelecionarCor);

		VBox conjuntoSelecao = new VBox();
		conjuntoSelecao.getChildren().addAll(new Label("Seleção e recorte"), new Label(), gridSelecao);

		VBox conjuntoXml = new VBox();
		conjuntoXml.getChildren().addAll(new Label("\t XML \t "), new Label(), gridXml);

		VBox conjuntoTransformacoes = new VBox();
		conjuntoTransformacoes.getChildren().addAll(new Label("Transformações Geométricas"), new Label(),
				gridTransformacoes);

		VBox conjuntoBorracha = new VBox();
		conjuntoBorracha.getChildren().addAll(new Label("Apagar tudo"), new Label(), gridApagar);

		conjuntoFerramentas.getItems().addAll(new Label(), conjuntoFormas, new Separator(), conjuntoCorEspessura,
				new Separator(), conjuntoSelecao, new Separator(), conjuntoXml, new Separator(), conjuntoTransformacoes,
				new Separator(), conjuntoBorracha, new Separator()

		);

		VBox conjuntoInferiorTela = new VBox();

		conjuntoInferiorTela.getChildren().addAll(conjuntoFerramentas, tOperacao);

		conjuntoFerramentas.setId("ferramentas");

		conjuntoInferiorTela.setId("ferramentas");

		/* Scene Creation */

		scene_dispose.setCenter(wrapperPane);

		/* Deixa o Canvas full screen */

		StackPane left = new StackPane();

		StackPane right = new StackPane();

		left.setPrefWidth(1);

		right.setPrefWidth(1);

		scene_dispose.setLeft(left);

		scene_dispose.setRight(right);

		scene_dispose.setBottom(conjuntoInferiorTela);

		Scene scene = new Scene(scene_dispose, 1200, 600);

		scene.getStylesheets().add("gui/css/Interface.css");

		window.setScene(scene);

		window.setTitle("Computacao Grafica - CGPI 2018");
		
		window.setMaximized(true);

		window.show();

		/* Defining button's behavior */

		EventHandler<MouseEvent> event_btnReta = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {

				tela.setOperacao(ModusOperandi.RETA);

				tOperacao.setText(ModusOperandi.RETA.toString());

				e.consume();

			}
		};

		EventHandler<MouseEvent> event_btnCirculo = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {

				tela.setOperacao(ModusOperandi.CIRCULO);

				tOperacao.setText(ModusOperandi.CIRCULO.toString());

				e.consume();

			}
		};

		EventHandler<MouseEvent> event_btnRetangulo = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {

				tela.setOperacao(ModusOperandi.RETANGULO);

				tOperacao.setText(ModusOperandi.RETANGULO.toString());

				e.consume();

			}
		};

		EventHandler<MouseEvent> event_btnPoligono = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {

				tela.setOperacao(ModusOperandi.POLIGONO);

				tOperacao.setText(ModusOperandi.POLIGONO.toString());

				e.consume();

			}
		};
		
		
		EventHandler<MouseEvent> event_btnLinhaPoligonal = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {

				tela.setOperacao(ModusOperandi.LINHA_POLIGONAL);

				tOperacao.setText(ModusOperandi.LINHA_POLIGONAL.toString());

				e.consume();

			}
		};

		EventHandler<MouseEvent> event_btnBorracha = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {

				tela.resetarTela(gc);

				tOperacao.setText(ModusOperandi.LIMPAR.toString());
				
				btnEscala.setDisable(true);

				btnTransladar.setDisable(true);

				btnRotacao.setDisable(true);
				
				btnSelecionar.setDisable(true);

				btnRecortar.setDisable(true);

				btnApagarForma.setDisable(true);

				e.consume();

			}
		};

		EventHandler<MouseEvent> event_btnRecortar = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {

				tela.setOperacao(ModusOperandi.RECORTAR);

				tOperacao.setText(ModusOperandi.RECORTAR.toString());

				e.consume();

			}
		};

		EventHandler<MouseEvent> eventBtnSelecionar = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {

				tela.setOperacao(ModusOperandi.SELECIONAR);

				tOperacao.setText(ModusOperandi.SELECIONAR.toString());
				
				btnEscala.setDisable(false);

				btnTransladar.setDisable(false);

				btnRotacao.setDisable(false);

				e.consume();
				
				

			}
		};

		EventHandler<MouseEvent> eventBtnApagarForma = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {

				tela.apagarSelecionado(gc);

				tOperacao.setText(ModusOperandi.APAGAR_SELECIONADO.toString());

				e.consume();

			}
		};

		EventHandler<MouseEvent> eventBtnEscalar = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {

				tela.setOperacao(ModusOperandi.ESCALA);

				tOperacao.setText(ModusOperandi.ESCALA.toString());

				TextInputDialog dialog = new TextInputDialog("1");

				dialog.setTitle("Transformações Geométricas");

				dialog.setHeaderText("Escala");

				ImageView image_view = new ImageView("gui/icones/escala.png");

				image_view.setFitWidth(25);

				image_view.setFitHeight(25);

				dialog.setGraphic(image_view);

				dialog.setContentText("Insira o fator escalar: ");

				try {

					Optional<String> result = dialog.showAndWait();

					if (result.get().matches("[0-9]{1,13}(\\.[0-9]*)?")) {

						tela.escalarSelecionado(gc, Integer.parseInt(result.get().trim()));

					} else {
						throw new Exception("Valor fora do padrão Double");
					}
				} catch (Exception er) {
					
					tOperacao.setText(" Erro: Valor Inválido");
					
				}

				e.consume();

			}
		};

		EventHandler<MouseEvent> eventBtnTrasladar = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {

				tela.setOperacao(ModusOperandi.TRASLADAR);

				TextInputDialog dialog = new TextInputDialog("0, 0");

				dialog.setTitle("Transformações Geométricas");

				dialog.setHeaderText("Trasladar");

				ImageView image_view = new ImageView("gui/icones/trasladar.png");

				image_view.setFitWidth(25);

				image_view.setFitHeight(25);

				dialog.setGraphic(image_view);

				dialog.setContentText("Insira o fator de re-posicionamento: (x, y)");

				Optional<String> result = dialog.showAndWait();

				if (result != null) {

					try {

						String[] split = result.get().split(",");

						if (split.length == 2) {

							String xStr = split[0].trim();

							String yStr = split[1].trim();

							int fatorX = Integer.parseInt(xStr);

							int fatorY = Integer.parseInt(yStr);

							tela.trasladarSelecionado(gc, fatorX, fatorY);

						} else {
							throw new Exception("Valor fora do formato 'x,y'");
						}
					} catch (Exception er) {
						
						tOperacao.setText(" \t Erro: Valor Inválido");
						
					}
				}

				e.consume();

			}
		};

		EventHandler<MouseEvent> eventBtnRotacionar = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
		
				if(tela.pRotacao == true) {

					TextInputDialog dialog = new TextInputDialog("0.0");
	
					dialog.setTitle("Transformações Geométricas");
	
					dialog.setHeaderText("Rotação");
	
					ImageView image_view = new ImageView("gui/icones/rotacao.png");
	
					image_view.setFitWidth(25);
	
					image_view.setFitHeight(25);
	
					dialog.setGraphic(image_view);
	
					dialog.setContentText("Ângulo da rotação: ");
	
					try {
						Optional<String> result = dialog.showAndWait();
	
						if (result.get().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
	
							tela.rotacionarSelecionado(gc, Double.parseDouble(result.get()));
	
						} else {
							throw new Exception("Valor fora do padrão Double");
						}
					} catch (Exception er) {
						tOperacao.setText(" Erro: Valor Inválido");
	
					}
	
					e.consume();
					
					tela.pRotacao = false;

				}
				else {
					
					Alert alert = new Alert(AlertType.INFORMATION);
					
					alert.setTitle("Rotação");
					
					alert.setHeaderText(null);
					
					if(tela.formaSelecionada == null) {
						
						alert.setContentText("Selecione uma forma para a transformação geométrica");
						
					}
					else {
						
						alert.setContentText("Declare o ponto de referência para a rotação!");
						
					}

					alert.showAndWait();
					
					tela.setOperacao(ModusOperandi.ROTACIONAR);
				}
			}
		};

		EventHandler<MouseEvent> event_rastrear_cursor = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {

				tela.desenhar(gc, e);

				String cursor = " \t Cursor (" + (int) e.getX() + ", " + (int) e.getY() + ")";

				tOperacao.setText(tela.getOperacao().toString() + cursor);

				e.consume();
				
				if(tela.formas.size() >= 1) {
					
					btnSelecionar.setDisable(false);

					btnRecortar.setDisable(false);

					btnApagarForma.setDisable(false);
				}

			}
		};

		btnSalvar.setOnAction(t -> {

			try {
                
                FileChooser fileChooser = new FileChooser();
                
                //Set extension filter for text files
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
                fileChooser.getExtensionFilters().add(extFilter);

                //Show save file dialog
                File file = fileChooser.showSaveDialog(window);

                if (file != null) {
                	
                	FileWriter.write(file, tela.getFormas(), wrapperPane);
                	
                }

            } catch (Exception e) {
                tOperacao.setText(" " + e.getMessage());
            }



		});

		btnImportar.setOnAction(t -> {
			
			tela.resetarTela(gc);
			
			tela.apagarTela(gc);
			
			final FileChooser fileChooser = new FileChooser();

	        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");

	        fileChooser.getExtensionFilters().add(extFilter);

	        File file = fileChooser.showOpenDialog(window);
	        
			FileReader fr = new FileReader();
			
			if(file != null) {
				
				boolean fullLoad = fr.readFile(file, wrapperPane);
				
				if(fullLoad) {
					
					tela.setForma(fr.getFormas());
					
					tela.redesenhar(gc);
					
				} else {
					
					tOperacao.setText(" Arquivo não pode ser lido");
				}
			}
		});

		cpSelecionarCor.setOnAction(t -> {

			tela.setCor(cpSelecionarCor.getValue());
		});

		btnReta.addEventFilter(MouseEvent.MOUSE_CLICKED, event_btnReta);

		btnCirculo.addEventFilter(MouseEvent.MOUSE_CLICKED, event_btnCirculo);

		btnRetangulo.addEventFilter(MouseEvent.MOUSE_CLICKED, event_btnRetangulo);

		btnPoligono.addEventFilter(MouseEvent.MOUSE_CLICKED, event_btnPoligono);

		btnBorracha.addEventFilter(MouseEvent.MOUSE_CLICKED, event_btnBorracha);

		btnBorracha.addEventFilter(MouseEvent.MOUSE_CLICKED, event_btnBorracha);

		btnRecortar.addEventFilter(MouseEvent.MOUSE_CLICKED, event_btnRecortar);

		btnApagarForma.addEventFilter(MouseEvent.MOUSE_CLICKED, eventBtnApagarForma);

		btnSelecionar.addEventFilter(MouseEvent.MOUSE_CLICKED, eventBtnSelecionar);

		btnEscala.addEventFilter(MouseEvent.MOUSE_CLICKED, eventBtnEscalar);

		btnTransladar.addEventFilter(MouseEvent.MOUSE_CLICKED, eventBtnTrasladar);

		btnRotacao.addEventFilter(MouseEvent.MOUSE_CLICKED, eventBtnRotacionar);
		
		btnLinhaPoligonal.addEventFilter(MouseEvent.MOUSE_CLICKED, event_btnLinhaPoligonal);

		wrapperPane.addEventFilter(MouseEvent.ANY, event_rastrear_cursor);
	}

}