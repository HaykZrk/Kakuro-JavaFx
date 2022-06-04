package dessin;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class ConstructionPolygon {
	/**
	 * Création d'un polygone triangle via 6 points et une couleur.
	 * @param p1X
	 * @param p1Y
	 * @param p2X
	 * @param p2Y
	 * @param p3X
	 * @param p3Y
	 * @param color
	 * @return
	 */
	public Polygon triangle(double p1X, double p1Y, double p2X, double p2Y, double p3X, double p3Y, Color color) {
		Polygon triangle = new Polygon();
		triangle.getPoints().setAll(
				p1X, p1Y,
				p2X, p2Y,
				p3X, p3Y
				);
		triangle.setFill(color);
		triangle.setStroke(Color.BLACK);
		return triangle;
	}
	
	/**
	 * Création d'un rectangle via les coordonées, largeur, hauteur et la couleur.
	 * @param X
	 * @param Y
	 * @param width
	 * @param height
	 * @param color
	 * @return
	 */
	public Rectangle rectangle (double X, double Y, double width, double height, Color color) {
		Rectangle rectangle = new Rectangle();
		rectangle.setX(X);
		rectangle.setY(Y);
		rectangle.setWidth(width);
		rectangle.setHeight(height);
		rectangle.setFill(color);
		rectangle.setStroke(color);
		rectangle.setStroke(Color.BLACK);
		return rectangle;
	}

}


