import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.imageio.ImageIO;
import java.io.*;
import javax.swing.*;
import java.util.List;



class Gui extends JComponent {
	
		static Board board;
		private BufferedImage image;
		public static FanoronaGameBoard gameBoard;
		private static FanoronaGameBoard.Player firstPlayer;
		private static FanoronaGameBoard.Player secondPlayer;
		private final JFrame window;
		private JPanel pnlButton;
		
    public Gui(){
			
			drawStartScreen();
			
		
				
			gameBoard = new FanoronaGameBoard();
			firstPlayer = gameBoard.getCurrentPlayer();
			secondPlayer = gameBoard.getWaitingPlayer();
			
			window = new JFrame();
			pnlButton = new JPanel(); // JPanel containing the board
			
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setSize(500, 500);
			window.getContentPane().add(this);
			window.setVisible(true);
			
			List<FanoronaGameBoard.Move> moves = gameBoard.getAllPossibleMoves();
			
			FanoronaGameBoard.Move capture_move = moves.get(1);
			
			
			System.out.println(capture_move);
			System.out.println(gameBoard.getNumberPiecesLeft(gameBoard.getWaitingPlayer()));
			System.out.println(gameBoard.getNumberPiecesLeft(gameBoard.getCurrentPlayer()));
		

			
			System.out.println(gameBoard.getNumberPiecesLeft(gameBoard.getWaitingPlayer()));
			System.out.println(gameBoard.getNumberPiecesLeft(gameBoard.getCurrentPlayer()));
		
    	
			
			JButton btnAddFlight = new JButton ("X");
			
			
		  //FlightInfo setbounds
			btnAddFlight.setBounds(440, 0, 50, 30);
		

			//JPanel bounds
			pnlButton.setBounds(800, 800, 200, 100);
			pnlButton.setLayout(null);
			
		
			
			btnAddFlight.addActionListener(new ActionListener() {
                 public void actionPerformed(ActionEvent e)
                 {
                     window.dispose();

                 }
								 
        });
			
			// Adding to JFrame
			pnlButton.add(btnAddFlight);
			pnlButton.setBackground(Color.WHITE);
			
			window.add(pnlButton);
			
			
      window.setSize(500, 500);
	
			window.setVisible(true);
			
    }
    
    public static void main(String[] argS){
    			
			
			FanoronaGameBoard board = new FanoronaGameBoard();
			
			Gui a = new Gui();
			
			
    }
    
    public void paintComponent(Graphics g) {
    	
			board = new Board(gameBoard, pnlButton,this);
			
			board.draw_board(g);
      
			board.display_pieces(g, gameBoard);
			
			
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
			int red  = gameBoard.getNumberPiecesLeft(firstPlayer);
			int black = gameBoard.getNumberPiecesLeft(secondPlayer);
			String numRed = Integer.toString(red);
			String numBlack = Integer.toString(black);
			
			
			g.drawString(numRed,40,400);
			g.drawString(numBlack,100,400);
		}
		
}
    
