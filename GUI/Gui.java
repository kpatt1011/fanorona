import java.awt.*;
import java.util.*;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.*;



class Gui extends JFrame {
	
		static Board board;
		
    public Gui(){
		
			setBackground(Color.WHITE);
      setSize(500, 500);
				
				
					
			JPanel pnlButton = new JPanel();
		JButton btnAddFlight = new JButton ("Add Flight");
			
		   //FlightInfo setbounds
    btnAddFlight.setBounds(60, 400, 220, 30);

    //JPanel bounds
    pnlButton.setBounds(800, 800, 200, 100);
			pnlButton.setLayout(null);
			
			// Adding to JFrame
			pnlButton.add(btnAddFlight);
			add(pnlButton);
			
        drawStartScreen();
				
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setVisible(true);
			
    }
    

    public static void main(String[] argS){
    	Gui G = new Gui();
    	
				
			
				
				
			//add the canvas
        //add(G);
     
    }
    
    public void paintComponent(Graphics g) {
    //	board = new Board(g);
    //	board.display_pieces(g);
			drawScoreboard(g);
			
			
    }
		
		// Draws start screen. With rules and instructions
		public static void drawStartScreen() {
		
			JOptionPane pane = new JOptionPane();
				JDialog dialog = pane.createDialog( "Start Screen");
				pane.setMessage("Welcome to Fanorona!");
				
				
				dialog.show();
				
			}
		
		
		public static void drawScoreboard (Graphics g) {
		
			Font font = new Font("Serif",Font.BOLD, 24);
			g.setFont(font);
			g.drawString("Pieces Remaining",20,355);
			Font font2 = new Font("Serif",Font.PLAIN, 14);
			g.setFont(font2);
			g.drawString("Red",40,375);
			g.drawString("Black",100,375);
			
			// Display the Number of Pieces Remaining
			int red  = board.getPiecesRemaining(1);
			int black = board.getPiecesRemaining(0);
			String numRed = Integer.toString(red);
			String numBlack = Integer.toString(black);
			
			
			g.drawString(numRed,40,400);
			g.drawString(numBlack,100,400);
		}
		
    }
    
