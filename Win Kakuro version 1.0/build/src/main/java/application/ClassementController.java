package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import jeu.Classement;
import jeu.Score;

public class ClassementController implements Initializable{
	
	@FXML
	ListView<String> listClassementLevel1;
	
	@FXML
	ListView<String> listClassementLevel2;
	
	@FXML 
	ListView<String> listClassementLevel3;
	
	@FXML
	ListView<String> listClassementWhatLevel;
	
	@FXML 
	Label whatLevelLabel;
	

	private Classement classementLevel1;
	private Classement classementLevel2;
	private Classement classementLevel3;
	
	/**
	 * Fontion d'initialisation qui initialise les classements des 
	 * trois niveaux. Attention, si nous nous trouvons dans une partie 
	 * l'initialisation se fait uniqument pour le classement du niveau en cours
	 * afin de l'afficher lors de la victoire. Dans les cas o�, nous sommes
	 * dans le menu l'initialisation se fait pour les trois classements.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (LevelController.getClassementWhatLevel() == 0) {
			try {
				classementLevel1 = new Classement("resources/Classement/ClassementLevel1.txt");
				classementLevel2 = new Classement("resources/Classement/ClassementLevel2.txt");
				classementLevel3 = new Classement("resources/Classement/ClassementLevel3.txt");
				for(Score s : classementLevel1.getTableau()) {
					listClassementLevel1.getItems().add("Nom : " + s.getNom() + " | Score : " + String.valueOf(s.getValeur()));
				}	
				for(Score s : classementLevel2.getTableau()) {
					listClassementLevel2.getItems().add("Nom : " + s.getNom() + " | Score : " + String.valueOf(s.getValeur()));
				}
				for(Score s : classementLevel3.getTableau()) {
					listClassementLevel3.getItems().add("Nom : " + s.getNom() + " | Score : " + String.valueOf(s.getValeur()));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
		else if (listClassementWhatLevel != null) {
			if (LevelController.getClassementWhatLevel() == 1) {
				try {
					whatLevelLabel.setText("LEVEL 1");
					classementLevel1 = new Classement("resources/Classement/ClassementLevel1.txt");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(Score s : classementLevel1.getTableau()) {
					listClassementWhatLevel.getItems().add("Nom : " + s.getNom() + " | Score : " + String.valueOf(s.getValeur()));
				}
			}
			else if (LevelController.getClassementWhatLevel() == 2) {
				try {
					whatLevelLabel.setText("LEVEL 2");
					classementLevel2 = new Classement("resources/Classement/ClassementLevel2.txt");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(Score s : classementLevel2.getTableau()) {
					listClassementWhatLevel.getItems().add("Nom : " + s.getNom() + " | Score : " + String.valueOf(s.getValeur()));
				}	
			}
			else if (LevelController.getClassementWhatLevel() == 3) {
				try {
					whatLevelLabel.setText("LEVEL 3");
					classementLevel3 = new Classement("resources/Classement/ClassementLevel3.txt");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(Score s : classementLevel3.getTableau()) {
					listClassementWhatLevel.getItems().add("Nom : " + s.getNom() + " | Score : " + String.valueOf(s.getValeur()));
				}	
			}
			
		}
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
	/**
	 * Retourne � la s�lection de la grille
	 * @param event
	 * @throws IOException
	 */
	public void choixGrille(ActionEvent event) throws IOException {
		LevelController choixGrille = new LevelController();
		choixGrille.choixGrille(event);
	}
}
