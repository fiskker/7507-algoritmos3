package algochess.gui.vista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import algochess.gui.controller.BotonComenzarJuegoHandler;
import algochess.engine.juego.Juego;

public class ContenedorJugadores extends VBox {

    Stage stage;
    int aceptados = 0;

    public ContenedorJugadores(Stage stage, Juego juego) {

    	super();

    	this.stage = stage;
        this.setAlignment(Pos.CENTER);
    	this.setSpacing(50);

        HBox jugadorContainer = new HBox(20);
        jugadorContainer.setAlignment(Pos.CENTER);

        VBox jugadorUnoContainer = new VBox(20);
        VBox jugadorDosContainer = new VBox(20);
        jugadorUnoContainer.setAlignment(Pos.CENTER);
        jugadorDosContainer.setAlignment(Pos.CENTER);

        Image jugadorUnoBackground = new Image("images/BACKGROUND_PLAYER_BLUE_CHOOSE_NAME.png");
        ImageView jugadorUnoBackgroundView = new ImageView();
        jugadorUnoBackgroundView.setImage(jugadorUnoBackground);
        jugadorUnoBackgroundView.setFitWidth(100);
        jugadorUnoBackgroundView.setPreserveRatio(true);
        jugadorUnoBackgroundView.setSmooth(true);
        jugadorUnoBackgroundView.setCache(true);

        Image jugadorDosBackground = new Image("images/BACKGROUND_PLAYER_PINK_CHOOSE_NAME.png");
        ImageView jugadorDosBackgroundView = new ImageView();
        jugadorDosBackgroundView.setImage(jugadorDosBackground);
        jugadorDosBackgroundView.setFitWidth(100);
        jugadorDosBackgroundView.setPreserveRatio(true);
        jugadorDosBackgroundView.setSmooth(true);
        jugadorDosBackgroundView.setCache(true);

        TextField nombreJugador_1 = new TextField("Nombre jugador");
        TextField nombreJugador_2 = new TextField("Nombre jugador");

        Button botonAceptar = new Button();
        botonAceptar.setText("Aceptar");

        Scene proximaEscena = crearEscenaPrincipal(stage, juego);

        BotonComenzarJuegoHandler botonAceptarHandler = new BotonComenzarJuegoHandler(stage, proximaEscena, juego,
                                                        nombreJugador_1, nombreJugador_2);
        botonAceptar.setOnAction(botonAceptarHandler);

        jugadorUnoContainer.getChildren().addAll(jugadorUnoBackgroundView, nombreJugador_1);
        jugadorDosContainer.getChildren().addAll(jugadorDosBackgroundView, nombreJugador_2);

        jugadorContainer.getChildren().addAll(jugadorUnoContainer, jugadorDosContainer);

        this.getChildren().addAll(jugadorContainer, botonAceptar);
    }

    private Scene crearEscenaPrincipal(Stage stage, Juego juego) {
        ContenedorPrincipal contenedorPrincipal = new ContenedorPrincipal(stage, juego);
        Scene escenaPrincipal = new Scene(contenedorPrincipal, 1280, 720);

        return escenaPrincipal;
    }
}