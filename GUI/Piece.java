import java.awt.*;
import javax.swing.*;

// The GUI class for the representation of a piece 

public class Piece {
	
	int x_location; // x location on board
	int y_location; // y location on board
	int color; // 0 for Black piece, 1 for white piece
	Graphics graph; // The graphics class the board is declared on
	
	public Piece (int x, int y, int c, Graphics g) {
		graph=g;
		color=c;
		x_location=x;
		y_location=y;
	}
	
	public void display() {
		
		graph.drawOval(x_location, y_location, 20, 20); // Draw the piece on the board
		
		// Color the piece black if necessary
		if(color == 0) {
			graph.fillOval(x_location, y_location, 20, 20);
		}
	}
	
}
