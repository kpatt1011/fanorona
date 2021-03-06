import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;



// The GUI class for the representation of a piece 

public class Piece {
	
	int x_location; // x location on board
	int y_location; // y location on board
	int color; // 0 for Black piece, 1 for white piece
	Graphics graph; // The graphics class the board is declared on
	private BufferedImage image; // Image to represent the piece on the board
	private JButton spaceButton;
	
	public Piece (int x, int y, int c, Graphics g, JButton butt) {
		graph=g;
		color=c;
		x_location=x;
		y_location=y;
		spaceButton=butt;
		
		// Define which color image to use for the piece
		if(color == 0) {
				// Read in the proper image file

					spaceButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("black.png")));

      
			}
		
		else {
			    
					spaceButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("white.png")));

		}
		
	}
	
	// Display the board in the Graphics Graph
	public void display() {
		
		//graph.drawImage(image,x_location,y_location,null); // This should add a black
		
	}
	
}
