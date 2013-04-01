

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
				
				
			}

			server.close();
			System.out.println("connection terminated");

		} catch (IOException e) {
			System.out.println("Could not listen on port: 5000"); 
			System.exit(-1); 
			}
		
		
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
