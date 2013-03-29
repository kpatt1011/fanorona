

import static java.lang.System.*;
import java.net.*;
import java.io.*;



public class Socket {

	private FanoronaGameBoard masterGameBoard;
	
	private static void serverInteractions(ServerSocket s)
	{
		
	}

	public static void main(String[] args) {
		String gameType="";
		InputStreamReader inputStream= new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(inputStream);
		out.println("Welcome to Fanorona!\nPlease designate this game" +
				" to be either a client or a server\n");
		try{
		gameType=in.readLine();
		} catch(IOException e){
			System.out.println("Error, couldn't read keyboard");
		}
		
      /*interactions for socket */			
       if(gameType.equals("server"))
       {
   		try { 
			ServerSocket server = new ServerSocket(5000);
			System.out.println("listening to port 5000...");
			Socket.serverInteractions(server);
			server.close();
			System.out.println("connection terminated");
			} catch (IOException e) {
			System.out.println("Could not listen on port: 5000"); 
			System.exit(-1); 
			}
   		
   		
       }
	}

}
