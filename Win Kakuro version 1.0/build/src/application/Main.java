package application;
	
import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Main extends Application {
	private static final String TITRE = "Launcher Game";
	private final Image imageStage = new Image("file:resources/Images/MenuKakuroGridImage.png");
	private final String titleSoundMenu = "resources/Musics/MenuMusic.mp3";
	private static MediaPlayer mP;
	private static InterfaceFXObjectConstruction FXObjectConstructor = new InterfaceFXObjectConstruction();
	
	/**
	 * Lance le menu principal.
	 */
	@Override
	public void start(Stage stageMenu) throws Exception {
			Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
			Scene sceneMenu = new Scene(root);
			
			setmP(getFXObjectConstructor().media(titleSoundMenu));
			mP.setVolume(0.5);
	
			String css = new File("resources/CSS/application.css").toURI().toString();
			sceneMenu.getStylesheets().add(css);
			
			stageMenu.getIcons().add(imageStage);
			stageMenu.setResizable(false);
			stageMenu.setTitle(getTitre());
			stageMenu.setScene(sceneMenu);
			stageMenu.show();
	}
	/**
	 * Lance la fonction start.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			launch(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static MediaPlayer getmP() {
		return mP;
	}

	public static InterfaceFXObjectConstruction getFXObjectConstructor() {
		return FXObjectConstructor;
	}

	public static String getTitre() {
		return TITRE;
	}

	public static void setmP(MediaPlayer mP) {
		Main.mP = mP;
	}

}
