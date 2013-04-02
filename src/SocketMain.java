


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
		byte[] buffer = new byte[1024];
		
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
			messageStream.print("WELCOME");
			messageStream.flush();
			messageStream.print("INFO 9 5 F 5000");
			messageStream.flush();

			/* convert read bytes into a string 
			 * keep reading bytes from input stream until "READY" message is received*/
			String clientResponse = new String(buffer);
				socketInput.read(buffer,0,1024);
				clientResponse = new String(buffer);
		   
			messageStream.print("BEGIN");
			messageStream.flush();
			
			/*send and receive messages over the socket until the game is over*/
			while (!fg.isGameOver())
			{
				/*
				 * run AI on fg board and then send the final move made as text across the 
				 * 	socket to the client */
				/*MAKE MOVE FROM THE CLIENT*/
				buffer = new byte[1024];
				socketInput.read(buffer,0,1024);
				clientResponse= new String(buffer);
				System.out.println("client: " + clientResponse);
				long elapsedTime =0;
				if(clientResponse.startsWith("ILLEGAL"))
				{
					
					break;
				}
				if(clientResponse.startsWith("OK"))
				{
					long timeBegin = System.currentTimeMillis();
					socketInput.read(buffer,0,1024);
					clientResponse= new String(buffer);
					 elapsedTime = System.currentTimeMillis()-timeBegin;
				}
				if(elapsedTime > 5000)
				{
					messageStream.print("TIME EXPIRED\n");
					messageStream.print("LOSER\n");
					break;
				}
				/*ignores OK response from client*/
			
				if(clientResponse.startsWith("A") || clientResponse.startsWith("W") || clientResponse.startsWith("S") || clientResponse.startsWith("P"))
				{
					/*remove whitespace from command */
					Coordinate start = new Coordinate(Character.getNumericValue(clientResponse.charAt(2)), Character.getNumericValue(clientResponse.charAt(4)) );
					Coordinate end = new Coordinate(Character.getNumericValue(clientResponse.charAt(6)), Character.getNumericValue(clientResponse.charAt(8)) );
					FanoronaGameBoard.Move clientMove = fg.new Move(start,end);
					if(clientMove.isValid())
					{
					fg.move(clientMove);
					/*make successive capture moves designated by the client*/
					if(clientMove.isCapture())
					{
						for(int i=4;i<clientResponse.length();i++)
						{
							if(clientResponse.charAt(i)=='+')
							{
							 start = new Coordinate(Character.getNumericValue(clientResponse.charAt(i+2)), Character.getNumericValue(clientResponse.charAt(i+4))) ;
						     end = new Coordinate(Character.getNumericValue(clientResponse.charAt(i+6)), Character.getNumericValue(clientResponse.charAt(i+8)) );
							 clientMove = fg.new Move(start,end);
							 if(clientMove.isValid())
								{
								 fg.move(clientMove);
								}
							 
							}
						}
					}
					messageStream.print("OK\n");
					messageStream.flush();
					} else {
						messageStream.print("ILLEGAL\n");
						System.out.println(fg.toString());
						messageStream.flush();
						break;
					}
					
				}
				  /*MAKE MOVE FROM THE SERVER
				   * then:
				   * print the move to the client across the socket
				   * */
				FanoronaGameBoard.Move serverMove = actualAI(fg,2);
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
				} else if(serverMove.isSacrifice()){
					moveString=moveString + "S ";
				}
               
				moveString=moveString + serverMove.start.x.toString() + " ";
				moveString=moveString + serverMove.start.y.toString() + " ";
				moveString=moveString + serverMove.end.x.toString() + " ";
				moveString=moveString + serverMove.end.y.toString() + " ";
				messageStream.print(moveString);
				messageStream.flush();
			}

			server.close();
			System.out.println("GAME OVER\nWINNER: "+fg.getWinner().toString());
			System.out.println("connection terminated");

		} catch (IOException e) {
			System.out.println("Could not listen on port: 5000"); 
			System.exit(-1); 
			}
		
		
	}
	
	/*if this game is to act as the client, handle interactions using this function*/
	
	private static void clientInteractions(String host, int portNum)
	{
	  /*set up socket with address and port number
	   * read game information from server, set up initial gameboard
	   * send 'READY' to server
	   * parse string from server and make the appropriate move on the gameboard 
	   * make AI move and send it to the server */	
		byte[] buffer = new byte[1024];
		try{
			Socket gameSocket = new Socket(host,portNum);
			System.out.println("successfully connected to the socket!\n");
			InputStream socketInput = gameSocket.getInputStream();
			OutputStream socketOutput = gameSocket.getOutputStream();
			PrintStream messageStream = new PrintStream(socketOutput);
			
			/* get initial information from server */
			socketInput.read(buffer,0,1024);
			String serverResponse = new String(buffer);
			 serverResponse.replaceAll("\n","");
			
			/*ignore welcome statement from server */
			if(serverResponse.contains("WELCOME")|| serverResponse.contains("welcome")){
				socketInput.read(buffer,0,1024);
				serverResponse = new String(buffer);
				 serverResponse.replaceAll("\n","");
			}
			/*parse info statement and use it to set up game board */
			long timeLimit =0;
			int boardWidth=0;
			int boardHeight=0;
			String playerNumber="";
			if(serverResponse.startsWith("INFO") || serverResponse.startsWith("info"))
			{
			  serverResponse=serverResponse.substring(5);
			  serverResponse.replaceAll("\n","");
			  //System.out.println(serverResponse);
			  boardWidth = Integer.parseInt(serverResponse.substring(0,1));
			  boardHeight = Integer.parseInt(serverResponse.substring(2,3));
			
			  playerNumber= serverResponse.substring(4,5);
			  timeLimit = Integer.parseInt(serverResponse.substring(6,10));
			  
			}
			FanoronaGameBoard fg = new FanoronaGameBoard(boardWidth, boardHeight);
			messageStream.print("READY");
			messageStream.flush();
			socketInput.read(buffer,0,1024);
			serverResponse=new String(buffer);
			
			/*get move from the server, 
			 * apply it to the board 
			 * make move using AI
			 * send move information to the server */
			  
			while (!fg.isGameOver())
			{
				buffer = new byte[1024];
				if(playerNumber.equals("F")|| playerNumber.equals("W"))
				{
					/*play game as if it is first person to move*/
					FanoronaGameBoard.Move clientMove = actualAI(fg,2);
					fg.move(clientMove);
					/*generate string to send to server informing it of the move from the AI*/
					String moveString="";
					if(clientMove.isApproach())
					{
						moveString=moveString + "A ";
					} else if(clientMove.isWithdraw()){
						moveString=moveString + "W ";				
					} else if (clientMove.isPaika()) {
						moveString=moveString + "P ";
					} else if (clientMove.isSacrifice()){
						moveString=moveString + "S ";
					}
					moveString=moveString + clientMove.start.x.toString() + " ";
					moveString=moveString + clientMove.start.y.toString() + " ";
					moveString=moveString + clientMove.end.x.toString() + " ";
					moveString=moveString + clientMove.end.y.toString() + " ";
					messageStream.print(moveString);
					messageStream.flush();
					
					/*get move from server and apply it to the board*/

					socketInput.read(buffer,0,1024);
					serverResponse= new String(buffer);
					long elapsedTime =0;
					/*measure time between 'OK' response from server and the move from the server*/
					if(serverResponse.startsWith("OK"))
					{
						System.out.println("server: " + serverResponse);
						long timeBegin = System.currentTimeMillis();
						socketInput.read(buffer,0,1024);
						elapsedTime = System.currentTimeMillis()-timeBegin;
						serverResponse= new String(buffer);
						System.out.println("server: " + serverResponse);
						 
					}
					if(elapsedTime > timeLimit)
					{
						messageStream.print("TIME EXPIRED\n");
						messageStream.print("LOSER\n");
						break;
					}
					if(serverResponse.startsWith("A") || serverResponse.startsWith("W") || serverResponse.startsWith("S") || serverResponse.startsWith("P"))
					{
						/*remove whitespace from command */
					
						
						Coordinate start = new Coordinate(Character.getNumericValue(serverResponse.charAt(2)), Character.getNumericValue(serverResponse.charAt(4)) );
						Coordinate end = new Coordinate(Character.getNumericValue(serverResponse.charAt(6)), Character.getNumericValue(serverResponse.charAt(8)) );
						FanoronaGameBoard.Move serverMove = fg.new Move(start,end);
						System.out.println(serverMove.toString());
						if(serverMove.isValid())
						{
						fg.move(serverMove);
						/*make successive capture moves designated by the server*/
						if(serverMove.isCapture())
						{
							for(int i=4;i<serverResponse.length();i++)
							{
								if(serverResponse.charAt(i)=='+')
								{
								 start = new Coordinate(Character.getNumericValue(serverResponse.charAt(i+2)), Character.getNumericValue(serverResponse.charAt(i+4)) );
							     end = new Coordinate(Character.getNumericValue(serverResponse.charAt(i+6)), Character.getNumericValue(serverResponse.charAt(i+8)) );
							     serverMove = fg.new Move(start,end);
								 if(serverMove.isValid())
									{
									 fg.move(serverMove);
									}
								 
								}
							}
						}
						messageStream.print("OK\n");
						messageStream.flush();
						} else {
							messageStream.print("ILLEGAL\n");
							System.out.println(fg.toString());
							messageStream.flush();
							break;
						}
						
					}

					
				} else{
					/*play game as if it is the second person to move*/
					/*get move from server and apply it to the board*/
					socketInput.read(buffer,0,1024);
					serverResponse= new String(buffer);
					
					long elapsedTime =0;
					/*measure time between 'OK' response from server and the move from the server*/
					if(serverResponse.equals("OK"))
					{
						System.out.println("server: " + serverResponse);
						long timeBegin = System.currentTimeMillis();
						socketInput.read(buffer,0,1024);
						elapsedTime = System.currentTimeMillis()-timeBegin;
						serverResponse= new String(buffer);
						System.out.println("server: " + serverResponse); 
					}
					if(elapsedTime > timeLimit)
					{
						messageStream.print("TIME EXPIRED\n");
						messageStream.print("LOSER\n");
						messageStream.flush();
						break;
					}
					if(serverResponse.startsWith("A") ||serverResponse.startsWith("S")|| serverResponse.startsWith("W") || serverResponse.startsWith("P"))
					{
						/*remove whitespace from command */
						char [] command = serverResponse.toCharArray();
						String c= command.toString();
						c.replaceAll("\\s", "");
						command=c.toCharArray();
						
						Coordinate start = new Coordinate(Character.getNumericValue(command[2]), Character.getNumericValue(command[4]));
						Coordinate end = new Coordinate(Character.getNumericValue(command[6]), Character.getNumericValue(command[8]) );
						FanoronaGameBoard.Move serverMove = fg.new Move(start,end);
						if(serverMove.isValid())
						{
						fg.move(serverMove);
						/*make successive capture moves designated by the server*/
						if(serverMove.isCapture())
						{
							for(int i=4;i<command.length;i++)
							{
								if(command[i]=='+')
								{
								 start = new Coordinate(Character.getNumericValue(command[i+2]), Character.getNumericValue(command[i+3]) );
							     end = new Coordinate(Character.getNumericValue(command[i+4]), Character.getNumericValue(command[i+5]) );
							     serverMove = fg.new Move(start,end);
								 if(serverMove.isValid())
									{
									 fg.move(serverMove);
									}
								 
								}
							}
						}
						messageStream.print("OK\n");
						messageStream.flush();
						} else {
							messageStream.print("ILLEGAL\n");
							System.out.println(fg.toString());
							messageStream.flush();
							break;
						}
					}
					/*once server move is complete, make AI move*/
					FanoronaGameBoard.Move clientMove = actualAI(fg,2);
					fg.move(clientMove);
					/*generate string to send to server informing it of the move from the AI*/
					String moveString="";
					if(clientMove.isApproach())
					{
						moveString=moveString + "A ";
					} else if(clientMove.isWithdraw()){
						moveString=moveString + "W ";				
					} else if (clientMove.isPaika()) {
						moveString=moveString + "P ";
					} else if(clientMove.isSacrifice()){
						moveString=moveString + "S ";
					}
					moveString=moveString + clientMove.start.x.toString() + " ";
					moveString=moveString + clientMove.start.y.toString() + " ";
					moveString=moveString + clientMove.end.x.toString() + " ";
					moveString=moveString + clientMove.end.y.toString() + " ";
					messageStream.flush();
					
				}
				
			}
			System.out.println("GAME OVER\nWINNER: "+fg.getWinner().toString());
			gameSocket.close();
			
		}catch(UnknownHostException c){
			System.out.println("Could not get information about host"); 
			System.exit(-1); 
		}catch(IOException e)
		{
			System.out.println("Could not get IO connections for host"); 
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
       } else if(gameType.equals("client")|| gameType.equals("CLIENT")){
    	   String hostName="";
    	   int portNumber=0;
    	   System.out.println("Enter a host name: ");
    	   try{
    		   hostName=in.readLine();
        	   
    			} catch(IOException e){
    				System.out.println("Error, couldn't read from keyboard");
    			}
    	   System.out.println("\nEnter a port number: ");
    	   try{
    		   portNumber= Integer.parseInt(in.readLine());
        	   
    			} catch(IOException e){
    				System.out.println("Error, couldn't read from keyboard");
    			}
    	 
    	   SocketMain.clientInteractions(hostName, portNumber);
       }
	}

}
