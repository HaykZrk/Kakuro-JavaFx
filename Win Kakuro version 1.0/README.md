# KAKURO PROJECT

## Compilation / Exécution

- Solution 1 : Exécution du fichier jar via la commande. 
	### java --module-path "javafx-sdk-18\lib" --add-modules javafx.controls,javafx.fxml --add-modules javafx.controls,javafx.media -jar Kakuro.jar

- Solution 2 : Exécuter le fichier exécutable. (Min JRE : 10, Max JRE : 16)

- Solution 3 (conseillée) : Lancer le fichier "launch.vbs" qui lance le fichier jar automatiquement.

- Solution 4 : Installer le jeu sur votre machine avec KakuroSetup.exe. (Min JRE : 10, Max JRE : 16)

## Résolution bug

### Problème de dimension ? 
#### Mettre la mise à l'echelle et disposition dans les paramètres Windows à 100% pour eviter les débordements graphiques.