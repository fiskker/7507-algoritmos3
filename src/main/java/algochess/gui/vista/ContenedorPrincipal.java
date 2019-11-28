package algochess.gui.vista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.event.ActionEvent;
import algochess.gui.vista.VistaTablero;
import algochess.engine.facciones.Faccion;
import algochess.engine.juego.Juego;

public class ContenedorPrincipal extends HBox {

	Stage stage;
	int aceptados = 0;
	Juego juego;
	VBox boxIzquierdo;
	VBox boxDerecho;

	public ContenedorPrincipal(Stage stage, Juego juego) {
		super();
		this.stage = stage;
		this.juego = juego;

		setAlignment(Pos.CENTER);
		setSpacing(50);
		refrescar();
	}

	public void refrescar() {
		this.getChildren().clear();
		armarColumnaIzquierda();
		armarColumnaDerecha();
		VistaTablero tableroVista = new VistaTablero(juego, this);
		this.getChildren().addAll(boxIzquierdo, tableroVista.getPaneTablero(), boxDerecho);
	}

	private void armarColumnaDerecha() {
		boxDerecho = new VBox(50);
		boxDerecho.setAlignment(Pos.CENTER);
		Label lblNombre = new Label(juego.getTurno().getJugadorActual().getNombre());
		lblNombre.setFont(Font.font("Amble CN", FontWeight.BOLD, 24));
		boxDerecho.getChildren().add(lblNombre);
		Label lbl1 = new Label("Disponible:");
		lbl1.setFont(Font.font("Amble CN", FontWeight.BOLD, 24));
		boxDerecho.getChildren().add(lbl1);
		Label lblMonto = new Label("$" + juego.getTurno().getJugadorActual().getDinero().toString());
		lblMonto.setFont(Font.font("Amble CN", FontWeight.BOLD, 24));
		boxDerecho.getChildren().add(lblMonto);
		
		String color;
		if (juego.getTurno().getFaccionActual() == Faccion.ALIADOS)
			color = "LIGHTPINK";
		else
			color = "AQUAMARINE";

		boxDerecho.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 8;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 8;" + "-fx-border-color: " + color + ";");

	}

	private void armarColumnaIzquierda() {
		boxIzquierdo = new VBox(20);
		boxIzquierdo.setAlignment(Pos.CENTER_LEFT);
		String color;
		if (juego.getTurno().getFaccionActual() == Faccion.ALIADOS)
			color = "LIGHTPINK";
		else
			color = "AQUAMARINE";

		boxIzquierdo.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 8;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 8;" + "-fx-border-color: " + color + ";");

		String[] imagePaths = new String[] { "images/SHOP_ICON.png", "images/CaballoNeutro.png",
				"images/SoldadoNeutro.png", "images/CuranderoNeutro.png", "images/CatapultaNeutro.png" };

		for (String path : imagePaths) {
			Image image = new Image(path);
			ImageView imageView = new ImageView();
			imageView.setImage(image);
			imageView.setFitWidth(100);
			imageView.setPreserveRatio(true);
			imageView.setSmooth(true);
			imageView.setCache(true);
			ToggleButton toggleButton = new ToggleButton();
			toggleButton.setGraphic(imageView);
			toggleButton.setPadding(new Insets(-1, -1, -1, -1));
			toggleButton.setOnAction((ActionEvent e) -> {
				// TODO sacar if
				if (path == imagePaths[1]) { // jinete
					juego.seleccionarJinete();
				} else if (path == imagePaths[2]) { // soldado
					juego.seleccionarSodado();
				} else if (path == imagePaths[3]) { // curandero
					juego.seleccionarCurandero();
				} else if (path == imagePaths[4]) { // catapulta
					juego.seleccionarCatapulta();
				}

			});
			boxIzquierdo.getChildren().add(toggleButton);
		}

	}

	private void setBorder(Pane pane) {
		pane.setStyle("-fx-padding: 2;-fx-border-style: solid inside;-fx-border-width: 5;"
				+ "-fx-border-insets: 2;-fx-border-radius: 1;-fx-border-color:DARKBLUE;");
	}
}