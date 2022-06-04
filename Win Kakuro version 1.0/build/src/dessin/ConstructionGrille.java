package dessin;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import application.LevelController;
import application.Main;
import application.MenuController;
import application.OptionsController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import jeu.CaseChiffre;
import jeu.CaseIndication;
import jeu.Grille;

public class ConstructionGrille {
	private final MenuController menu = new MenuController();
	private final LevelController controller;
	private final ConstructionPolygon polygone = new ConstructionPolygon();
	
	private final GraphicsDevice ECRAN_USER = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	private final int largeurStage = ECRAN_USER.getDisplayMode().getWidth();
	private final int hauteurStage = ECRAN_USER.getDisplayMode().getHeight();
	private double largeurScene = largeurStage/3;
	private double hauteurScene = hauteurStage/2;

	private Group root;
	private Stage stage;
	
	private final double caseX, caseY;
	private int indicLigne, indicColonne;

	private double progress;
	private double valeurDeProgression;
	private static Label timeLabel;
	private boolean solveActive = false;
	private int chronoSeconde;
	private int oneSeconde;
	private int nbCoup = 0;
	
	private static Button buttonRetour;
	private static Button buttonValidate;
	private static Button buttonSolve;
	private ProgressBar progressBar;
	private Label nbCoupLabel;
	
	/**
	 * Fonction tr�s importante, qui permet de cr�er la grille re�u en argument. Il 
	 * appel la fonction permettant de d�ssiner la grille. Cette fonction poss�de les 
	 * boutons permettant de jouer au jeu Kakuro (Valider, Solver).
	 * @param controller
	 * @param stage
	 * @throws IOException
	 */
	public ConstructionGrille(LevelController controller, Stage stage) throws IOException {
		valeurDeProgression = 1.0 / controller.getKakuro().nbCasesACompleter() ;
		this.stage = stage;
		this.controller = controller;
		if (OptionsController.isOptionSizeActive()) {
			largeurScene = OptionsController.getLargeurScreen();
			hauteurScene = OptionsController.getHauteurScreen();
		}
		else if (controller.getKakuro().getGrille().getDimX() <= 10 && 
				 controller.getKakuro().getGrille().getDimY() <= 10) {
			largeurScene = (largeurStage / 2);
			hauteurScene = (hauteurStage / 2);
		}
		else {
			largeurScene = (largeurStage / 1.2);
			hauteurScene = (hauteurStage / 1.2);
		}
		
		caseX = largeurScene / controller.getKakuro().getGrille().getDimX();
		caseY = (hauteurScene) / controller.getKakuro().getGrille().getDimY();
		
		
		root = new Group();
		String css = new File("resources/CSS/application.css").toURI().toString();
		Scene sceneGrille = new Scene(root, largeurScene, hauteurScene+caseY*2);
		stage.setScene(sceneGrille);
		stage.setTitle("KAKURO");
		sceneGrille.getStylesheets().add(css);
		
		buttonRetour = Main.getFXObjectConstructor().button(0,
				                                            0,
				                                            (int)caseX-10,
				                                            (int)caseY,
				                                            20,
				                                            Color.GRAY,
				                                            "Abandonner"
				                                            );
		
		buttonSolve = Main.getFXObjectConstructor().button(0,
				                                           hauteurScene + caseY,
				                                           (int)caseX,
				                                           (int)caseY,
				                                           20,
				                                           Color.RED,
				                                           "Solver"
				                                           );
		buttonValidate = Main.getFXObjectConstructor().button(largeurScene - caseX,
				                                              hauteurScene + caseY,
				                                              (int)caseX,
				                                              (int)caseY,
				                                              20,
				                                              Color.GREEN,
				                                              "Valider"
				                                              );
		
		timeLabel = Main.getFXObjectConstructor().label(caseX*1, hauteurScene + caseY*1, caseX*(controller.getKakuro().getGrille().getDimX()-2), caseY, "");
		nbCoupLabel = Main.getFXObjectConstructor().label(caseX*(controller.getKakuro().getGrille().getDimX()-1), 0, caseX, caseY, "Coups : " + nbCoup);
		
		progressBar = Main.getFXObjectConstructor().progressBar(
								       largeurScene / 4, 
									   caseY / 2 - (caseY / 2) / 2,
									   largeurScene / 2 -10,
									   caseY / 2
									   );
		progressBar.setProgress(progress);
		
		root.getChildren().addAll(getButtonRetour(),
				                  progressBar,
				                  getButtonSolve(),
				                  getButtonValidate(),
				                  getTimeLabel(),
				                  nbCoupLabel
				                  );
		dessineGrille(controller.getKakuro().getGrille());
		stage.centerOnScreen();
		stage.show();
		
		getButtonValidate().setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Action du bouton valider, la partie se termine avec une victoire si
			 * la partie est termin�e, dans le cas contraire l'action reste inactive.
			 */
			@Override
			public void handle(ActionEvent arg0) {
				try {
					if (controller.partieFinie(nbCoup)) {
						Main.getmP().stop();
						Parent root = FXMLLoader.load(getClass().getResource("/application/Victoire.fxml"));
						Scene sceneDefaite = new Scene(root);
						stage.setScene(sceneDefaite);
						stage.centerOnScreen();
						stage.show();
					} 
					else {
						
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		getButtonSolve().setOnAction(new EventHandler<ActionEvent>() {
			
			/**
			 * Action du bouton solver permettant de montrer la solution de la grille, 
			 * par cons�quent la partie se termine 5 secondes plus tard
			 * avec une d�faite.
			 */
			@Override
			public void handle(ActionEvent arg0) {
				buttonRetour.setDisable(true);
				buttonSolve.setDisable(true);
				buttonValidate.setDisable(true);
				
				if (LevelController.getTime() != null)
					LevelController.getTime().stop();
				
				Main.getmP().stop();
				Main.setmP(Main.getFXObjectConstructor().media("resources/Musics/DefaiteMusic.mp3"));
				Main.getmP().play();
				chronoSeconde = 5;
				
				solveActive = true;
				dessineGrille (controller.resoudreKakuro());
				
				Timeline time = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
					chronoSeconde--;
					getTimeLabel().setTextFill(Color.RED);
					getTimeLabel().setText(chronoSeconde + " secondes");
					if (chronoSeconde == 0) {
						Parent root;
						try {
							Main.getmP().stop();
							root = FXMLLoader.load(getClass().getResource("/application/Defaite.fxml"));
							Scene sceneDefaite = new Scene(root);
							stage.setScene(sceneDefaite);
							stage.centerOnScreen();
							stage.show();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}));
				time.setCycleCount(5);
				time.play();
			}
		});
		
		/**
		 * Action du bouton de retour qui permet de retourner � la s�lection de la grille.
		 */
		getButtonRetour().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub	
				try {
					Main.getmP().stop();
					Main.setmP(Main.getFXObjectConstructor().media("resources/Musics/MenuMusic.mp3"));
					Main.getmP().setVolume(MenuController.vol);
					menu.play(event);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Fonction qui dessine la grille re�u en argument en parcourant une
	 * boucle ligne, colonne et selon si c'est une case noire, indication,
	 * vide, il d�ssine en cons�quence. De m�me, il d�ssine les zones de textes tout
	 * en faisant attention au gestion d'erreur.
	 * @param grille
	 */
	public void dessineGrille (Grille grille) {
		int ligne, colonne;
		for (ligne=1; ligne < grille.getDimY()+1; ligne++) {
			for (colonne=0; colonne < grille.getDimX(); colonne++) {
				if (grille.getTable()[ligne-1][colonne].estNoire()) {
					Rectangle rectangle = polygone.rectangle(caseX * colonne,
							                                 caseY * ligne,
							                                 caseX, caseY,
							                                 Color.BLACK);
					root.getChildren().add(rectangle);
				}
				else if (grille.getTable()[ligne-1][colonne].estIndication()) {
					CaseIndication caseIndication = (CaseIndication) grille.getTable()[ligne-1][colonne];
					indicLigne = caseIndication.getIndicLigne();
					indicColonne = caseIndication.getIndicColonne();
					dessineIndication(ligne, colonne);
				}
				else {
					CaseChiffre caseChiffre = (CaseChiffre) grille.getTable()[ligne-1][colonne];
					if (!caseChiffre.estVide()) {
						Rectangle rectangle = polygone.rectangle(
								                      caseX * colonne,
								                      caseY * ligne,
								                      caseX,
								                      caseY,
								                      Color.WHITE
								                      );
						Text valeur = Main.getFXObjectConstructor().text(
								          caseX*colonne +(caseX/2)-5,
								          caseY*ligne+(caseY/2)+5,
								          24,
								          caseChiffre.getVal()
								          );
						root.getChildren().addAll(rectangle, valeur);
					}
					else {						
						Rectangle rectangle = polygone.rectangle(
								                      caseX * colonne,
								                      caseY * ligne,
								                      caseX, 
								                      caseY,
								                      Color.WHITE
								                      );
						root.getChildren().add(rectangle);
						
						if (!solveActive) {
							Text valeur = Main.getFXObjectConstructor().text(
									          caseX*colonne +(caseX/2)-5,
									          caseY*ligne+(caseY/2)+5,
									          24, 
									          30
									          );
							valeur.setVisible(false);
							valeur.setFill(Color.CHOCOLATE);
					
							TextField textField = Main.getFXObjectConstructor().textField(
									                  caseX*colonne,
									                  caseY*ligne,
									                  caseX, 
									                  caseY,
									                  caseX / 10
													  );
							
							root.getChildren().addAll(textField, valeur);
							textField.setOnAction(new EventHandler<ActionEvent> () {
								/**
								 * Lors du clique sur la zone, si la zone est editable et que 
								 * le nouveau coup est valide, la valeur est accept�e dans le cas
								 * contraire le fen�tre se d�place pour indiquer l'erreur.
								 */
								@Override
								public void handle(ActionEvent event) {
									if(textField.isEditable() == true) {
										if (controller.nouveauCoup(((int)(textField.getLayoutY()/caseY)-1),
												                  ((int)(textField.getLayoutX()/caseX)),
												                  Integer.valueOf(textField.getText()))) {
											valeur.setText(textField.getText());
											valeur.setVisible(true);
											textField.setText(null);
											textField.setEditable(false);
											progress = progress + valeurDeProgression;
											progressBar.setProgress(progress);
										}
										else {
											oneSeconde = 1;
											stage.setX(stage.getX() + 10);
											Timeline time = new Timeline(new KeyFrame(Duration.millis(100), e -> {
												oneSeconde--;
												if (oneSeconde == 0) {
													stage.setX(stage.getX() - 10);
												}
													
											}));
											time.setCycleCount(1);
											time.play();
										}
										
										nbCoup++;
										nbCoupLabel.setText("Coups : " + nbCoup);
									}	
									/**
									 * Action permettant d'effacer la saisie de la valeur. Touche 
									 * BACKSPACE.
									 */
									textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
										@Override
										public void handle(KeyEvent eventKey) {
											if (eventKey.getCode() == KeyCode.BACK_SPACE) {
												controller.annulerCoup(((int)(textField.getLayoutY()/caseY)-1),
													    ((int)(textField.getLayoutX()/caseX)));
												valeur.setVisible(false);
												textField.setEditable(true);
											}	
										}
										
									});
								}
							});
						}
					}
				}
			}
		}
	}
	/**
	 * Fonction qui dessine les indications selon le nombre 
	 * d'indication dans une case.
	 * @param ligne
	 * @param colonne
	 */
	public void dessineIndication(int ligne, int colonne) {
		if (indicLigne != 0 && indicColonne != 0) {
			Polygon triangleBlancHaut = polygone.triangle(
					                            caseX*colonne,
					                            caseY*ligne,
					                            caseX*(colonne+1),
					                            caseY*ligne,
					                            caseX*(colonne+1),
					                            caseY*(ligne+1),
					                            Color.WHITE
					                            );
			Polygon triangleBlancBas = polygone.triangle(
					                           caseX*colonne,
					                           caseY*ligne,
					                           caseX*colonne,
					                           caseY*(ligne+1),
					                           caseX*(colonne+1),
					                           caseY*(ligne+1),
					                           Color.WHITE
					                           );
			
			Text valeurBas = Main.getFXObjectConstructor().text(
					             caseX*(colonne+1) - 20,
					             caseY*ligne+20,
					             17,
					             indicLigne
					             );
			Text valeurHaut = Main.getFXObjectConstructor().text(
					              caseX*colonne + 20,
					              caseY*(ligne+1) - 20,
					              17,
					              indicColonne
					              );
			
			root.getChildren().addAll(triangleBlancHaut,
					                  triangleBlancBas,
					                  valeurBas,
					                  valeurHaut
					                  );
			
		}
		else if (indicLigne != 0 && indicColonne == 0) {
			Polygon triangleNoirBas = polygone.triangle(
					                          caseX*colonne,
					                          caseY*ligne,
					                          caseX*colonne,
					                          caseY*(ligne+1),
					                          caseX*(colonne+1),
					                          caseY*(ligne+1),
					                          Color.BLACK
					                          );
			Polygon triangleBlancHaut = polygone.triangle(
					                            caseX*colonne,
					                            caseY*ligne,
					                            caseX*(colonne+1),
					                            caseY*ligne,
					                            caseX*(colonne+1),
					                            caseY*(ligne+1),
					                            Color.WHITE
					                            );
			
			Text valeurHaut = Main.getFXObjectConstructor().text(
					              caseX*(colonne+1) - 20,
					              caseY*ligne+20,
					              17,
					              indicLigne
					              );
			
			root.getChildren().addAll(triangleNoirBas,
					                  triangleBlancHaut,
					                  valeurHaut
					                  );
		}
		else {
			Polygon triangleNoirHaut = polygone.triangle(
					                           caseX*colonne,
					                           caseY*ligne,
					                           caseX*(colonne+1),
					                           caseY*ligne,
					                           caseX*(colonne+1),
					                           caseY*(ligne+1),
					                           Color.BLACK
					                           );
			Polygon triangleBlancBas = polygone.triangle(
					                           caseX*colonne,
					                           caseY*ligne,
					                           caseX*colonne,
					                           caseY*(ligne+1),
					                           caseX*(colonne+1),
					                           caseY*(ligne+1),
					                           Color.WHITE
					                           );
			
			Text valeurBas = Main.getFXObjectConstructor().text(
					             caseX*colonne + 20,
					             caseY * (ligne+1) - 20,
					             17, 
					             indicColonne
					             );
			
			root.getChildren().addAll(triangleNoirHaut,
					                  triangleBlancBas,
					                  valeurBas
					                  );
		}
	}

	public static Label getTimeLabel() {
		return timeLabel;
	}

	public static Button getButtonRetour() {
		return buttonRetour;
	}

	public static Button getButtonSolve() {
		return buttonSolve;
	}

	public static Button getButtonValidate() {
		return buttonValidate;
	}
}
