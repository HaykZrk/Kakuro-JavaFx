package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dessin.ConstructionGrille;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import jeu.Classement;
import jeu.Coup;
import jeu.Grille;
import jeu.Kakuro;
import jeu.Solveur;

public class LevelController implements Initializable {
	
	@FXML
	Button level1Button;
	
	@FXML 
	Button level2Button;
	
	@FXML 
	Button level3Button;
	
	@FXML
	RadioButton easy;
	
	@FXML
	RadioButton medium;
	
	@FXML 
	RadioButton hard;
	
	@FXML 
	private TextField playerNameField;
	
	private String playerName;
	private Stage stageLevelController;
	
	private final String LEVEL1 = "resources/Levels/Level1.txt";
	private final String LEVEL2 = "resources/Levels/Level2.txt";
	private final String LEVEL3 = "resources/Levels/Level3.txt";
	
	private static Timeline time;
	private int chronoSeconde;
	private static boolean difficultyActive = false;
	private Kakuro kakuro;
	@SuppressWarnings("unused")
	private ConstructionGrille vueGrille;
	private Classement classement;
	private static int classementWhatLevel = 0;
	
	/**
	 * Fonction d'initialisation qui initialise la difficult�e par d�faut : Facile
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (easy != null)
			easy.setSelected(true);
		difficultyActive = false;
	}
	/**
	 * Fonction qui lance le jeu et d�terminer quel bouton a �t� actionner 
	 * entres les trois, chaque bouton repr�sente un niveau et lance la cr�ation de la 
	 * grille selon le niveau.
	 * @param event
	 * @throws IOException
	 */
	public void jeu(ActionEvent event) throws IOException {
		playerName = playerNameField.getText();
		Main.getmP().stop();
		stageLevelController = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		if (level1Button.isArmed()) {
			setClassementWhatLevel(1);
			if (difficultyActive) {
				getTime().play();
				Main.setmP(Main.getFXObjectConstructor().media("resources/Musics/MediumHardDiffucultyTimeMusic.mp3"));
				Main.getmP().setVolume(MenuController.vol);
				Main.getmP().setAutoPlay(true);
				Main.getmP().setCycleCount(10);
			}
			kakuro = new Kakuro(LEVEL1);
			vueGrille = new ConstructionGrille(this, stageLevelController);
			classement = new Classement("resources/Classement/ClassementLevel1.txt");
		}
		else if (level2Button.isArmed()) {
			setClassementWhatLevel(2);
			if (difficultyActive) {
				getTime().play();
				Main.setmP(Main.getFXObjectConstructor().media("resources/Musics/MediumHardDiffucultyTimeMusic.mp3"));
				Main.getmP().setVolume(MenuController.vol);
				Main.getmP().setAutoPlay(true);
				Main.getmP().setCycleCount(10);
			}
			kakuro = new Kakuro(LEVEL2);
			vueGrille = new ConstructionGrille(this, stageLevelController);
			classement = new Classement("resources/Classement/ClassementLevel2.txt");
		}
		else if (level3Button.isArmed()) {
			setClassementWhatLevel(3);
			if (difficultyActive) {
				getTime().play();;
				Main.setmP(Main.getFXObjectConstructor().media("resources/Musics/MediumHardDiffucultyTimeMusic.mp3"));
				Main.getmP().setVolume(MenuController.vol);
				Main.getmP().setAutoPlay(true);
				Main.getmP().setCycleCount(10);
			}
			kakuro = new Kakuro(LEVEL3);
			vueGrille = new ConstructionGrille(this, stageLevelController);
			classement = new Classement("resources/Classement/ClassementLevel3.txt");
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
	 * Met le niveau par d�faut : Facile.
	 * @param event
	 */
	public void defaultDifficulty(ActionEvent event) {
		difficultyActive = false;
	}
	
	/**
	 * Activation du niveau normal, le chronom�tre s'initialise � 300 secondes.
	 * A la fin de celle-ci, la partie se termine avec une d�faite. 
	 * @param event
	 */
	public void mediumDifficulty(ActionEvent event) {
		chronoSeconde = 300;
		time = new Timeline(new KeyFrame(Duration.seconds(1), e ->{
			chronoSeconde--;
			ConstructionGrille.getTimeLabel().setText(chronoSeconde + " secondes");
			if (chronoSeconde == 0) {
				Main.getmP().stop();
				Main.setmP(Main.getFXObjectConstructor().media("resources/Musics/DefaiteMusic.mp3"));
				Main.getmP().play();
				Main.getmP().setVolume(MenuController.vol);
				try {
					Parent root = FXMLLoader.load(getClass().getResource("Defaite.fxml"));
					Scene sceneDefaite = new Scene(root);
					stageLevelController.setScene(sceneDefaite);
					stageLevelController.centerOnScreen();
					stageLevelController.show();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}));
		getTime().setCycleCount(300);
		setDifficultyActive(true);
	}
	/**
	 * Activation du niveau normal, le chronom�tre s'initialise � 180 secondes.
	 * A la fin de celle-ci, la partie se termine avec une d�faite. 
	 * @param event
	 */
	public void hardDifficulty(ActionEvent event) {
		chronoSeconde = 180;
		time = new Timeline(new KeyFrame(Duration.seconds(1), e ->{
			chronoSeconde--;
			ConstructionGrille.getTimeLabel().setText(chronoSeconde + " secondes");
			if (chronoSeconde == 0) {
				Main.getmP().stop();
				Main.setmP(Main.getFXObjectConstructor().media("resources/Musics/DefaiteMusic.mp3"));
				Main.getmP().play();
				Main.getmP().setVolume(MenuController.vol);
				try {
					Parent root = FXMLLoader.load(getClass().getResource("Defaite.fxml"));
					Scene sceneDefaite = new Scene(root);
					stageLevelController.setScene(sceneDefaite);
					stageLevelController.centerOnScreen();
					stageLevelController.show();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}));
		getTime().setCycleCount(180);
		setDifficultyActive(true);
	}
	/**
	 * Retourne � la s�lection de la grille.
	 * @param event
	 * @throws IOException
	 */
	public void choixGrille(ActionEvent event) throws IOException {
		classementWhatLevel = 0;
		ConstructionGrille.getButtonRetour().setDisable(false);
		ConstructionGrille.getButtonSolve().setDisable(false);
		ConstructionGrille.getButtonValidate().setDisable(false);
		MenuController menu = new MenuController();
		Main.setmP(Main.getFXObjectConstructor().media("resources/Musics/MenuMusic.mp3"));
		Main.getmP().setVolume(MenuController.vol);
		menu.play(event);
	}
	
	public static Timeline getTime() {
		return time;
	}

	public boolean nouveauCoup(int x, int y, int val) {
		Coup c = new Coup(x, y, val);
		return getKakuro().jouerCoup(c);
	}
	
	public Grille resoudreKakuro() {
		Solveur solveur = new Solveur(kakuro);
		return solveur.resoudre();
		
	}

	public Kakuro getKakuro() {
		return kakuro;
	}

	public static void setDifficultyActive(boolean difficultyActive) {
		LevelController.difficultyActive = difficultyActive;
	}

    public boolean partieFinie(int nbCoup) throws IOException { 
        if (kakuro.partieFinie()) {
        	int totalCase = kakuro.nbCasesACompleter();
        	int score = totalCase - (nbCoup - totalCase);
        	if (score < 0) score = 0;
            classement.nouveauScore(playerName, score);
            classement.miseAJourClassement();
            return true;
        }
        return false;
    }

	public static int getClassementWhatLevel() {
		return classementWhatLevel;
	}

	public static void setClassementWhatLevel(int classementWhatLevel) {
		LevelController.classementWhatLevel = classementWhatLevel;
	}
	
    public void annulerCoup(int x, int y) {
        kakuro.getGrille().setCase(new Coup(x, y, 0));
    }
}
