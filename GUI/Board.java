import java.awt.*;
import java.util.*;


// The GUI class for the representation of a board

public class Board {
	

	ArrayList<Piece> pieces; // All of the pieces currently on the board
	ArrayList<Space> spaces; // All the spaces on the board
	int remainingRed;
	int remainingBlack;
	
	// Constructs a new board with the starting piece layout
	// Defines all the spaces on the board and puts the pieces in the proper spaces
	 public Board(Graphics g) {
		
     pieces = new ArrayList<Piece> (64);
     spaces = new ArrayList<Space> (64);
		 remainingRed=23;
		 remainingBlack=23;
	  	int x_location =20;
    	int y_location = 20;
    	
    	for(int i=0; i < 5; i++) {
    		
    		x_location = 20; // Change the location back to the location of the first column
    		
    		 
    		for(int j=0; j < 9; j++) {
    			
    			// Draw lines connecting each space horizontally
    			if (j != 8) {
    				g.drawLine(x_location, y_location + 10 , x_location + 55 , y_location + 10);
    			}
    			
    			// Draw lines connecting each space vertically
    			if(i != 4) {
    				g.drawLine(x_location + 10, y_location, x_location + 10 , y_location + 55);
    			}
    			
    			// Draw lines from top left to bottom right in even rows.
    			if (((j==0 || j==2 || j==4 || j == 6 ) && (i== 0 || i == 2)) ) {
    				g.drawLine(x_location + 5, y_location + 5, x_location + 55 , y_location + 55);
    			}
    			
    			// Draws lines from top left to bottom right in odd rows
    			
    			if (((i == 1 || i == 3) && (j== 1 || j == 3 || j ==5 || j ==7 )) ){
    				g.drawLine(x_location + 5, y_location + 5, x_location + 55 , y_location + 55);
    			}
    			
    			// Draw lines from top right to bottom left in even rows.
    			if (((j==2 || j==4 || j == 6 || j == 8) && (i== 0 || i == 2)) ) {
    				g.drawLine(x_location + 10, y_location + 5, x_location - 45 , y_location + 65);
    			}
    			
    			
    			// Draws lines from top right to bottom left in odd rows
    			
    			if (((i == 1 || i == 3) && (j== 1 || j == 3 || j ==5 || j ==7 )) ){
    				g.drawLine(x_location + 10, y_location + 5, x_location - 45 , y_location + 65);
    			}
    			
    			
    			
    			// Draw lines from right to left in odd rows
    			Space s = new Space(x_location,y_location);
    			spaces.add(s); // Add the new space to the arraylist of spaces
    			
    			x_location += 50;
    		}// End of j for loop
    		
    		
    		y_location += 50;
    		
    	} // End of i for loop
    	
    	
     }// End of board constructor 
	 
	 void display_pieces(Graphics g) {
	 
	 
		 
		// Display Pieces on board in starting arrangment
    	 for(int i= 0; i < spaces.size(); i++) {
    		 int x = spaces.get(i).get_x();
    		 int y= spaces.get(i).get_y();
    		 
    		 if(i <= 18 || i == 20 || i== 23 || i ==25) {
    			Piece a = new Piece(x,y,0,g);
         		a.display();
         		pieces.add(a); // Add piece to array list of pieces
    		 }
    		 
    		 if(i == 17 || i == 19 || i == 21 || i >= 24) {
    			Piece a = new Piece(x,y,1,g);
          		a.display();
          		pieces.add(a); // Add piece to array list of pieces
    		 }
    	 }
	 }
		
		// Returns the number of pieces remaining for a given color. 0 for black, 1 for red, returns -1 if there is an error
		int getPiecesRemaining(int color) {
			if(color == 0) {
				return remainingRed; 
			}
			
			if (color == 1) {
				return remainingBlack;
			}
			
			else {
				return -1;
			}
			
		}
	 
	 }
	 
	
	

