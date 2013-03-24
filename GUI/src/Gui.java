import java.awt.*;
import java.util.*;




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
    	Board a = new Board(g);
    	a.display_pieces(g);
    	
    	
    }
    }
    
