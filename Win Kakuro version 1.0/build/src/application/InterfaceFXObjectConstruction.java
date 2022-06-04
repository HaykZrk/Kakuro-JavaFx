
package application;

import java.io.File;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Constructeur ou Usine de construction d'objets graphique personnalis�.
 *
 */
public class InterfaceFXObjectConstruction {
	/**
	 * Retourne un m�dia avec le fichier mp3 assign�.
	 * @param soundMenu
	 * @return
	 */
	public MediaPlayer media (String soundMenu) {
		Media media = new Media(new File(soundMenu).toURI().toString());
		MediaPlayer mP = new MediaPlayer(media);
		mP.setAutoPlay(true);
		return mP;
	}
	
	/**
	 * Retourne un texte selon les coordonn�es, taille de la police et valeur.
	 * @param X
	 * @param Y
	 * @param fontSize
	 * @param valeur
	 * @return
	 */
	public Text text (double X, double Y, double fontSize, int valeur) {
		Text donneeText = new Text();
		donneeText.setText(String.valueOf(valeur));
		donneeText.setFont(new Font ("Bauhaus 93", fontSize));
		donneeText.setX(X);
		donneeText.setY(Y);
		return donneeText;
	}
	
	/**
	 * Retourne une progresse barre selon les coordonn�es, largeur et hauteur.
	 * @param X
	 * @param Y
	 * @param width
	 * @param height
	 * @return
	 */
	public ProgressBar progressBar (double X, double Y, double width, double height) {
		ProgressBar progressBar = new ProgressBar ();
		progressBar.setLayoutX(X);
		progressBar.setLayoutY(Y);
		progressBar.setPrefSize(width, height);
		progressBar.setStyle("-fx-accent: green;");
		return progressBar;
	}
	
	/**
	 * Retourne une zone de texte selon les coordon�es, largeur, hauteur et taille de la police.
	 * @param X
	 * @param Y
	 * @param width
	 * @param height
	 * @param fontSize
	 * @return
	 */
	public TextField textField (double X, double Y, double width, 
			                    double height, double fontSize) {
		TextField textField = new TextField();
		textField.setLayoutX(X);
		textField.setLayoutY(Y);
		textField.setPrefSize(width, height);
		textField.setFont(new Font ("Bauhaus 93", fontSize));
		return textField;
	}
	/**
	 * Retourne un bouton selon les coordonn�es, largeur, hauteur, taille de la police,
	 * la couleur et le titre.
	 * @param X
	 * @param Y
	 * @param width
	 * @param height
	 * @param fontSize
	 * @param color
	 * @param title
	 * @return
	 */
	public Button button (double X, double Y, double width, double height, 
			              double fontSize, Color color, String title) {
		Button button = new Button(title);
		button.setLayoutX(X);
		button.setLayoutY(Y);
		button.setFont(new Font("Bauhaus 93", 20));
		button.setPrefSize(width, height);
		button.setBackground(Background.fill(color));
		return button;
	}
	
	/**
	 * Retourne un label selon les coordonn�es, largeur, hauteur et le titre.
	 * @param X
	 * @param Y
	 * @param width
	 * @param height
	 * @param title
	 * @return
	 */
	public Label label (double X, double Y, double width, double height, String title) {
		Label label = new Label();
		label.setLayoutX(X);
		label.setLayoutY(Y);
		label.setBackground(Background.fill(Color.GRAY));
		label.setAlignment(Pos.CENTER);
		label.setPrefSize(width, height);
		label.setText(title);
		label.setFont(new Font("Bauhaus 93", 20));
		return label;
	}

}
