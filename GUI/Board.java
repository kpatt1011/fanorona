import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.List;

// The GUI class for the representation of a board

public class Board {
	

	ArrayList<Piece> pieces; // All of the pieces currently on the board
	ArrayList<Space> spaces; // All the spaces on the board
	int remainingRed; 
	int remainingBlack;
	public JPanel pnlButton;
	public FanoronaGameBoard gameBoard; // The FanoronaGameBoard class associated with the board
	
	private List<FanoronaGameBoard.Move> moves;
	public Gui passedGui; // The gui object passed from the Gui class
	
	// Constructs a new board with the starting piece layout
	// Defines all the spaces on the board and puts the pieces in the proper spaces
	 public Board(FanoronaGameBoard gb, JPanel pan, Gui gu) {
		
		 pnlButton = pan;
     pieces = new ArrayList<Piece> (64);
     spaces = new ArrayList<Space> (64);
		 passedGui = gu;
		
		 remainingRed=23; // Red pieces left on the board
		 remainingBlack=23; // Black Pieces left on the board
	  
			
				
     }// End of board constructor 
	 
	 
	 public void draw_board(Graphics g) {
			 int x_location = 50; // Top left corner of the board, x
			 int y_location = 50; // Top left corner of the board, y
    	
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
    	
	 }
	 
	 public void display_pieces(final Graphics g, final FanoronaGameBoard gb) {
	 
		moves = gb.getAllPossibleMoves(); // Put all the posible moves for the player into a list
	 
			
	 
	 // Draw the pieces on the board.
	 for(int i= 0; i < gb.BOARD_LENGTH; i++  ) {
				
				for(int j = 0; j < gb.BOARD_WIDTH; j++) {
						
						// Add Jbutton to every space location, so the player can press them
					
		
				Point temp = gb.getPointAt(j,i); // Get the point at the give x (width) and y (length)
						
						final GuiSpace spaceButton;
						switch (temp.getState()) {
							
							case isOccupiedByBlack: 
											System.out.println("Printed a Black");
											spaceButton = new GuiSpace((j * 50) + 15, (i * 50) + 40 ,0,g);
											pnlButton.add(spaceButton); // Add button to the panel
										break;
										
							case isOccupiedByWhite: 
											System.out.println("Printed a White");
											spaceButton = new GuiSpace((j * 50) + 15, (i * 50) + 40 ,1,g);
											pnlButton.add(spaceButton); // Add button to the panel
										break;
							default: 
											System.out.println("Printed a silly space");
											spaceButton = new GuiSpace((j * 50) + 15, (i * 50) + 40 ,2,g);
											pnlButton.add(spaceButton); // Add button to the panel
										break;
											
						}
					
					spaceButton.addActionListener(new ActionListener() {          
							public void actionPerformed(ActionEvent e) {
									System.out.println(spaceButton.x_location + "  "+ spaceButton.y_location);
									
									  
									
										for(int i = 0; i < moves.size(); i++ ) {
												Coordinate test = new Coordinate (spaceButton.x_location, spaceButton.y_location);
												
												// If move exists in list of possible moves then exectute it
												if (( spaceButton.x_location == ((moves.get(i).end.x) * 50) + 15) && ( spaceButton.y_location == ((moves.get(i).end.y) * 50) + 40)) {
														System.out.println(moves.get(i));
														gb.move(moves.get(i)); // Execute move on gameBoard
														//display_pieces(g,gb);
														passedGui.repaint();
														
														break;
												}
											
										}		
										
										
																
							} // actionPerformed
					}); 
					
					
						
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
	 
			// Creates a list of avalible moves on the board to make
			
	 }
	 
	
	

