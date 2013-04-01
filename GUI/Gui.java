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
		private  static FanoronaGameBoard gameBoard;
		private static FanoronaGameBoard.Player firstPlayer;
		private static FanoronaGameBoard.Player secondPlayer;
		
    public Gui(FanoronaGameBoard gb){
			
			gameBoard = gb;
			firstPlayer = gameBoard.getCurrentPlayer();
			secondPlayer = gameBoard.getWaitingPlayer();
			drawStartScreen();
			
	
			
    }
    

    public static void main(String[] argS){
    			
			final JFrame window = new JFrame();
			FanoronaGameBoard board = new FanoronaGameBoard();
			
			List<FanoronaGameBoard.Move> moves = board.getAllPossibleMoves();
			
			JPanel pnlButton = new JPanel(); // JPanel containing the board
			
			
			FanoronaGameBoard.Move capture_move = moves.get(1);
			
			// Loop to create a buttons for all the possible moves
			for(int i = 0; i < moves.size(); i++ ) {
					
					int x = ((moves.get(i).end.x) * 50) + 15;
					int y = ((moves.get(i).end.y) * 50) + 40;
					
					JButton possibleMove = new JButton("");
					possibleMove.setBounds(x,y,20,20);
					pnlButton.add(possibleMove);			  

					if(moves.get(i).isCapture()) {
						
						capture_move = moves.get(i);
					}
			}
			
			System.out.println(capture_move);
			System.out.println(board.getNumberPiecesLeft(board.getWaitingPlayer()));
			System.out.println(board.getNumberPiecesLeft(board.getCurrentPlayer()));
		
		  FanoronaGameBoard.MoveResult a = board.movePiece(capture_move);
			
			System.out.println(board.getNumberPiecesLeft(board.getWaitingPlayer()));
			System.out.println(board.getNumberPiecesLeft(board.getCurrentPlayer()));
		
			
			
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setSize(500, 500);
			window.getContentPane().add(new Gui(board));
			window.setVisible(true);
    	
			
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
			pnlButton.validate();
			pnlButton.repaint();
				
				
     
    }
    
    public void paint(Graphics g) {
    	
			board = new Board(g, gameBoard);
      board.display_pieces(g);
			
			
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
    
