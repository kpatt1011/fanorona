

import static java.lang.System.*;
import java.net.*;
import java.io.*;



public class Socket {

	private FanoronaGameBoard masterGameBoard;
	
	public void establishServerConnection()
	{
		try { 
			ServerSocket server = new ServerSocket(5000);
			} catch (IOException e) {
			System.out.println("Could not listen on port: 5000"); 
			System.exit(-1); 
			}
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
		
			

	}

}
