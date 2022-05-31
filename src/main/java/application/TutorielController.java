package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

public class TutorielController {
	
	@FXML
	private AnchorPane scene;
	
	@FXML
	private Group avatarSpeak;
	
	@FXML
	private TextArea textArea;
	
	@FXML
	private Line line;
	
	@FXML 
	private Button ok;
	
	@FXML
	private Button retour;
	
	@FXML
	private ImageView grilleVide;
	
	@FXML
	private ImageView grilleResolu;
	
	@FXML
	private ImageView grilleLigneExemple;
	
	private String[] regleJeu = {"Il y a trois regles à connaître pour jouer.",
			
								"On ne peut remplir une case qu'avec un chiffre"
								+ " entre 1 et 9.", 
								
								"Dans un bloc on ne trouve jamais deux fois le "
								+ "meme chiffre.",
								
								"La somme des chiffres du bloc doit etre egale "
								+ "au nombre inscrit dans la case noire correspondante."};
	private static int compteur;
	
	/**
	 * Fonction qui quitte le tutoriel et retourne au menu
	 * principal.
	 * @param event
	 * @throws IOException
	 */
	public void quitterTutoriel(ActionEvent event) throws IOException {
		MenuController menu = new MenuController();
		Main.getmP().stop();
		Main.setmP(Main.getFXObjectConstructor().media("resources/Musics/MenuMusic.mp3"));
		Main.getmP().setVolume(MenuController.vol);
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
	 * Changement de couleur du fond lorsque la souris rentre
	 * dans la zone.
	 * @param event
	 */
	public void mouseOnTextZone(MouseEvent event) {
		line.setStroke(Paint.valueOf("WHITE"));
		scene.setBackground(Background.fill(Paint.valueOf("GREY")));
	}
	
	/**
	 * Changement de couleur de fond lorsque la souris quitte la zone.
	 * @param event
	 */
	public void mouseOutTextZone(MouseEvent event) {
		line.setStroke(Paint.valueOf("BLACK"));
		scene.setBackground(Background.fill(Paint.valueOf("WHITE")));
	}
	
	/**
	 * Le premier ok à appuyer, celle-ci change d'action.
	 * @param event
	 */
	public void ok1(ActionEvent event) {
		compteur = 0;
		retour.setVisible(true);
		retour.setOnAction(EventHandler -> depart(event));
		grilleVide.setVisible(true);
		avatarSpeak.setLayoutX(100);
		avatarSpeak.setLayoutY(150);
		textArea.setText("Voici un exemple de grille Kakuro !");
		ok.setOnAction(EventHandler -> ok2(event, regleJeu[compteur]));
	}
	
	/**
	 * La structure de départ au niveau de l'animation.
	 * @param event
	 */
	public void depart(ActionEvent event) {
		retour.setVisible(false);
		grilleVide.setVisible(false);
		grilleLigneExemple.setVisible(false);
		grilleResolu.setVisible(false);
		avatarSpeak.setLayoutX(216);
		avatarSpeak.setLayoutY(151);
		textArea.setText("こんにちは !! Bienvenue au tutoriel de KAKURO !!");
		ok.setOnAction(EventHandler -> ok1(event));
	}
	
	/**
	 * Le deuxième ok à appuyer, celle-ci change d'action.
	 * @param event
	 * @param text
	 */
	public void ok2(ActionEvent event, String text) {
		grilleVide.setVisible(true);
		grilleResolu.setVisible(false);
		grilleLigneExemple.setVisible(false);
		if (compteur == 0 ) {
			retour.setOnAction(EventHandler -> ok1(event));
		} else {
			retour.setOnAction(EventHandler -> ok2(event, regleJeu[compteur = compteur-2]));
		}
		compteur++;
		textArea.setText(text);
		ok.setOnAction(EventHandler -> ok2(event, regleJeu[compteur]));
		if (compteur == regleJeu.length) {
			ok.setOnAction(EventHandler -> ok3(event));
		}
	}
	
	/**
	 * Le troisième ok à appuyer, celle-ci change d'action.
	 * @param event
	 */
	public void ok3(ActionEvent event) {
		textArea.setText("Exemple : L'indication 8 est verifiee avec les deux "
				         +"cases blanches 7 + 1 = 8. N'oublier pas la "
				         + "verification pour la colonne !");
		grilleVide.setVisible(false);
		grilleResolu.setVisible(true);
		grilleLigneExemple.setVisible(true);
		grilleResolu.setLayoutX(326);
		grilleResolu.setLayoutY(-140);
		grilleLigneExemple.setLayoutX(301);
		grilleLigneExemple.setLayoutY(82);
		retour.setOnAction(EventHandler -> ok2(event, regleJeu[--compteur]));
		ok.setOnAction(EventHandler -> okFin(event));
	}
	
	/**
	 * Le dernier ok à appuyer, celle-ci se dirige vers le départ.
	 * @param event
	 */
	public void okFin(ActionEvent event) {
		textArea.setText("Revoir le tutoriel ?");
		grilleVide.setVisible(true);
		grilleLigneExemple.setVisible(false);
		grilleResolu.setLayoutX(400);
		grilleResolu.setLayoutY(35);
		grilleVide.setLayoutX(400);
		grilleVide.setLayoutY(-120);
		retour.setOnAction(EventHandler -> ok3(event));
		ok.setOnAction(EventHandler -> depart(event));
	}
	
}
