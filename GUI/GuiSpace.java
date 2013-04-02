import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.*;
import java.awt.Color;


// The GUI class for the representation of a space  

public class GuiSpace extends JButton {
	
	int x_location; // x location on board
	int y_location; // y location on board
	int color; // 0 for Black piece, 1 for white piece, 2 for blank
	Graphics graph; // The graphics class the board is declared on
	boolean containsWhite;
	boolean containsBlack;
	
	public GuiSpace (int x, int y, int c, Graphics g) {
		
		graph=g;
		color=c;
		x_location=x;
		y_location=y;
		
		this.setBounds(x,y,30,30);
		this.setBackground(Color.LIGHT_GRAY);
	
		
		// Define which color image to use for the piece
		if(color == 0) {
				// Read in the proper image file

					this.setIcon(new javax.swing.ImageIcon(getClass().getResource("black.png")));
					containsBlack= true;
					containsWhite = false;
      
			}
		
		if (color == 1) {
			    
					this.setIcon(new javax.swing.ImageIcon(getClass().getResource("white.png")));
					containsBlack= false;
					containsWhite = true;
					
		}
		
		else {
					containsBlack= false;
					containsWhite = false;
		}
		
		
	}
	
	// Display the board in the Graphics Graph
	public void display() {
		
		
	}
	
}
