package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class MenuController implements Initializable {
	
	@FXML
	private Slider volumeSlider;
	

	private Stage stageMenuController;
	private Scene scene;
	private Parent root;
	
	public static double vol = 0.5;
	
	/**
	 * Fonction d'initialisation qui initialise le slider qui 
	 * permet de changer de volume. A chaque changement, on change la valeur de
	 * l'attribut vol qui correspond au nouveau volume du m�dia.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (volumeSlider != null) {
			if (Main.getmP() == null) {}
			else {
				getVolumeSlider().setValue(Main.getmP().getVolume() * 100);
			}
			
			getVolumeSlider().valueProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
					Main.getmP().setVolume(getVolumeSlider().getValue() * 0.01);
					vol = volumeSlider.getValue() * 0.01;
				}
			});
		}
	}
	
	/**
	 * Fontion qui permet de se diriger vers la s�lection de la grille.
	 * @param event
	 * @throws IOException
	 */
	public void play(ActionEvent event) throws IOException {
		if (LevelController.getTime() != null)
			LevelController.getTime().stop();
		Main.getmP().play();
		String css = new File("resources/CSS/application.css").toURI().toString();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ChoixGrille.fxml"));
		root = loader.load();
		stageMenuController = (Stage) ((Node)event.getSource()).getScene().getWindow();
		scene = new Scene (root);
		scene.getStylesheets().add(css);
		stageMenuController.setScene(scene);
		stageMenuController.setTitle(Main.getTitre());
		stageMenuController.show();
	}
	
	/**
	 * Fonction qui permet le retour au menu principal.
	 * @param event
	 * @throws IOException
	 */
	public void retour(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		String css = new File("resources/CSS/application.css").toURI().toString();
		stageMenuController = (Stage) ((Node)event.getSource()).getScene().getWindow();
		scene = new Scene (root);
		scene.getStylesheets().add(css);
		stageMenuController.setScene(scene);
		stageMenuController.setTitle(Main.getTitre());
		stageMenuController.show();
	}
	
	/**
	 * Fonction qui se dirige vers les options du jeu.
	 * @param event
	 * @throws IOException
	 */
	public void options(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("Options.fxml"));
		String css = new File("resources/CSS/application.css").toURI().toString();
		stageMenuController = (Stage) ((Node)event.getSource()).getScene().getWindow();
		scene = new Scene (root);
		scene.getStylesheets().add(css);
		stageMenuController.setScene(scene);
		stageMenuController.show();
	}
	
	/**
	 * Fonction qui se dirige vers le tutoriel.
	 * @param event
	 * @throws IOException
	 */
	public void tutoriel(ActionEvent event) throws IOException {
		Main.getmP().stop();
		Main.setmP(Main.getFXObjectConstructor().media("resources/Musics/TutorielMusic.mp3"));
		Main.getmP().setVolume(getVolumeSlider().getValue());
		Main.getmP().play();
		root = FXMLLoader.load(getClass().getResource("Tutoriel.fxml"));
		String css = new File("resources/CSS/application.css").toURI().toString();
		stageMenuController = (Stage) ((Node)event.getSource()).getScene().getWindow();
		scene = new Scene (root);
		scene.getStylesheets().add(css);
		stageMenuController.setScene(scene);
		stageMenuController.show();
	}
	
	/***
	 * Fonction qui se dirige vers le classement.
	 * @param event
	 * @throws IOException
	 */
	public void classement(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("Classement.fxml"));
		String css = new File("resources/CSS/application.css").toURI().toString();
		stageMenuController = (Stage) ((Node)event.getSource()).getScene().getWindow();
		scene = new Scene (root);
		scene.getStylesheets().add(css);
		stageMenuController.setScene(scene);
		stageMenuController.show();
	}
	
	/**
	 * Activation du son lors du passage de la souris vers un bouton.
	 * @param event
	 */
	public void onButtonSong(MouseEvent event) {
		if (!OptionsController.isOptionDesactiveSonPassageSouris()) {
			MediaPlayer sound = Main.getFXObjectConstructor().media("resources/Musics/OnMouseButtonSound.mp3");
			sound.play();
		}
	}

	public Slider getVolumeSlider() {
		return volumeSlider;
	}
}