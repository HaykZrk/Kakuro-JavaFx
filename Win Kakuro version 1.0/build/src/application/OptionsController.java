package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;

public class OptionsController implements Initializable {
	
	@FXML
	private RadioButton buttonDesactiveSonPassageSouris;
	
	@FXML 
	private ChoiceBox <String> sizeScreenBox;
	
	private static boolean optionSizeActive;
	private static double largeurScreen;
	private static double hauteurScreen;
	private static boolean optionDesactiveSonPassageSouris = false;
	
	private String[] screenSize = {
	         "800 x 600",
	         "1280 x 720",
	         "1600 x 900",
	         "DEFAULT"
	         };

	/**
	 * Fonction d'initialisation qui initialise le niveau par d�faut : Facile � activer 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (buttonDesactiveSonPassageSouris == null) {}
		else {
			buttonDesactiveSonPassageSouris.setSelected(optionDesactiveSonPassageSouris);
		}
		sizeScreenBox.getItems().addAll(screenSize);
		sizeScreenBox.setOnAction(this::changeScreen);
	}
	
	/**
	 * Propose une liste de r�solution d'�cran. Attention, il faut 
	 * que la taille sois inf�rieur � la taille de votre �cran. La 
	 * taille par d�faut est conseill�e.
	 * @param event
	 */
	public void changeScreen(ActionEvent event) {
		optionSizeActive = true;
		if (sizeScreenBox.getValue() == "1280 x 720") {
			largeurScreen = 1280;
			hauteurScreen = 720;
		}
		else if (sizeScreenBox.getValue() == "800 x 600") {
			largeurScreen = 800;
			hauteurScreen = 600;
		}
		else if (sizeScreenBox.getValue() == "1600 x 900") {
			largeurScreen = 1600;
			hauteurScreen = 900;
		}
		else {
			optionSizeActive = false;
		}
	}
	
	/**
	 * Permet la d�sactivation du son du passage de la souris 
	 * sur les boutons.
	 * @param event
	 */
	public void desactiveSonPassageSouris(ActionEvent event) {
		if (optionDesactiveSonPassageSouris == false)
			optionDesactiveSonPassageSouris = true;
		else 
			optionDesactiveSonPassageSouris = false;
	}
	
	/**
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void retour(ActionEvent event) throws IOException {
		MenuController menu = new MenuController();
		menu.retour(event);
	}
	
	/**
	 * Activation du son lors du passage de la souris vers un bouton.
	 * @param event
	 */
	public void onButtonSong(MouseEvent event) {
		if (!OptionsController.isOptionDesactiveSonPassageSouris()) {
			MediaPlayer song = Main.getFXObjectConstructor().media("resources/Musics/OnMouseButtonSound.mp3");
			song.play();
		}
	}

	public static boolean isOptionSizeActive() {
		return optionSizeActive;
	}

	public static double getHauteurScreen() {
		return hauteurScreen;
	}

	public static double getLargeurScreen() {
		return largeurScreen;
	}

	public static boolean isOptionDesactiveSonPassageSouris() {
		return optionDesactiveSonPassageSouris;
	}
}
