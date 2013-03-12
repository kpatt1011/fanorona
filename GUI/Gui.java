import java.awt.*;

/* This very basic class simply creates a canvas to draw the game board on, 
 * and draws a game board with all black pieces.
 * 
 * Current code written by Keith Pattison
 */

class Gui extends Canvas {

    public Gui(){
        setSize(400, 400);
        setBackground(Color.white);
    }
    

    public static void main(String[] argS){
    	Gui G = new Gui();
    	
    	  //create a new frame to which we will add a canvas
        Frame aFrame = new Frame();
        aFrame.setSize(300, 300);
        
        //add the canvas
        aFrame.add(G);
        
        aFrame.setVisible(true);
    }
    
    public void paint(Graphics g) {
    	
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
    			
    			//g.drawOval(x_location, y_location, 20, 20);
    			//g.fillOval(x_location, y_location, 20, 20);
    			x_location += 50;
    		}
    		
    		
    		y_location += 50;
    		
    	}
    	
    }
    }
    
