

import static java.lang.System.*;
import java.net.*;
import java.net.Socket;
import java.io.*;





public class SocketMain {


	private static final int socketNumber=5000;
	
	private static void serverInteractions()
	{
		/*run through entire game in this function, operating as the server for the game*/
		/**/
		/*parse string from input*/
		byte[] buffer = new byte[255];
		//char[] bufferToString = new char[255];
		try { 
			ServerSocket server = new ServerSocket(socketNumber);
			System.out.println("listening to port 5000...");
			Socket s = server.accept();
			InputStream socketInput = s.getInputStream();
			OutputStream socketOutput = s.getOutputStream();
			PrintStream messageStream = new PrintStream(socketOutput);
			/*send welcome message to the client 
			 * default board dimensions will be used so default board
			 * constructor is used for server model*/
			FanoronaGameBoard fg = new FanoronaGameBoard();
			messageStream.print("WELCOME\n");
			messageStream.flush();
			messageStream.print("INFO 9 5 B 5000\n");
			messageStream.flush();
			messageStream.print("F\n"); //client always goes first
			messageStream.flush();
			/* convert read bytes into a string 
			 * keep reading bytes from input stream until "READY" message is received*/
			String clientResponse = new String(buffer);
			while(!clientResponse.equals("READY")||!clientResponse.equals("ready"))
			{
				socketInput.read(buffer,0,6);
				clientResponse = new String(buffer);
			}
			
			/*send and receive messages over the socket until the game is over*/
			while (!fg.isGameOver())
			{
				/*
				 * run AI on fg board and then send the final move made as text across the 
				 * 	socket to the client */
				/*MAKE MOVE FROM THE CLIENT*/
				socketInput.read(buffer,0,255);
				clientResponse= new String(buffer);
				if(clientResponse.startsWith("A") || clientResponse.startsWith("W") || clientResponse.startsWith("P"))
				{
					/*remove whitespace from command */
					char [] command = clientResponse.toCharArray();
					String c= command.toString();
					c.replaceAll("//s", "");
					command=c.toCharArray();
					
					Coordinate start = new Coordinate(Character.getNumericValue(command[1]), Character.getNumericValue(command[2]) );
					Coordinate end = new Coordinate(Character.getNumericValue(command[3]), Character.getNumericValue(command[4]) );
					FanoronaGameBoard.Move clientMove = fg.new Move(start,end);
					if(clientMove.isValid())
					{
					fg.move(clientMove);
					/*make successive capture moves designated by the client*/
					if(clientMove.isCapture())
					{
						for(int i=4;i<command.length;i++)
						{
							if(command[i]=='+')
							{
							 start = new Coordinate(Character.getNumericValue(command[i+2]), Character.getNumericValue(command[i+3]) );
						     end = new Coordinate(Character.getNumericValue(command[i+4]), Character.getNumericValue(command[i+5]) );
							 clientMove = fg.new Move(start,end);
							 if(clientMove.isValid())
								{
								 fg.move(clientMove);
								}
							 
							}
						}
					}
					} else {
						messageStream.print("ILLEGAL\n");
						messageStream.print("LOSER\n");
						break;
					}
					
				}
				  /*MAKE MOVE FROM THE SERVER
				   * then:
				   * print the move to the client across the socket
				   * */
				FanoronaGameBoard.Move serverMove = actualAI(fg,3);
				fg.move(serverMove);
				/*generate string to send to client informing it of the move from the AI*/
				String moveString="";
				if(serverMove.isApproach())
				{
					moveString=moveString + "A ";
				} else if(serverMove.isWithdraw()){
					moveString=moveString + "W ";				
				} else if (serverMove.isPaika()) {
					moveString=moveString + "P ";
				}
               
				moveString=moveString + serverMove.start.x.toString() + " ";
				moveString=moveString + serverMove.start.y.toString() + " ";
				moveString=moveString + serverMove.end.x.toString() + " ";
				moveString=moveString + serverMove.end.x.toString() + " ";

			}

			server.close();
			System.out.println("connection terminated");

		} catch (IOException e) {
			System.out.println("Could not listen on port: 5000"); 
			System.exit(-1); 
			}
		
		
	}
	public static FanoronaGameBoard.Move actualAI(FanoronaGameBoard fgb, int depth)
	{
		
			MaxNode testRoot = new MaxNode (fgb);
			MinimaxTree testTree = new MinimaxTree (testRoot, depth);
            return testTree.getIdealMove();
	}
	

	public static void main(String[] args) {
		String gameType="";
		InputStreamReader inputStream= new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(inputStream);
		System.out.println("Welcome to Fanorona!\nPlease designate this game" +
				" to be either a client or a server\n");
		try{
		gameType=in.readLine();
		} catch(IOException e){
			System.out.println("Error, couldn't read keyboard");
		}
		
      /*interactions for socket */			
       if(gameType.equals("server"))
       {
   		SocketMain.serverInteractions();
       }
	}

}
