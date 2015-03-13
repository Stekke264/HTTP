import java.io.*;
import java.net.*;

public class HTTPClient {

	public static Socket clientSocket;

	public static void main(String[] args) throws Exception{

		//Port number the user wants to use to connect
		int portNumber = Integer.parseInt(args[2]);
		
		String HTTPVersion;
		switch(args[3]){
		case "1.0":
			HTTPVersion = "HTTP/1.0";
			break;
		case "1.1":
			HTTPVersion = "HTTP/1.1";
			break;
		case "HTTP/1.0":
			HTTPVersion = "HTTP/1.0";
			break;
		case "HTTP/1.1":
			HTTPVersion = "HTTP/1.1";
			break;
		default:
			HTTPVersion = "HTTP/1.1";
		}


		try{
			clientSocket = new Socket(args[1], portNumber);
		}
		catch(UnknownHostException e){
			System.err.println("Can't find host: " + args[1]);
			System.exit(1);
		}
		System.out.println("Connected");

		// Create a buffer to store the data from the server
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		// Create an outputstream to send data to the server
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

		// Create a buffer to read data from the user
//		BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
//		System.out.print("FROM CONSOLE: ");

		String command = args[0];
		String commandToServer;
		switch (command){
		//case "GET": commandToServer = "GET /index.html HTTP/1.1" + "\r\n" + "host: " + args[1];
		case "GET":
			commandToServer = "GET /index.html HTTP/1.0" +"\r\n";
			
			outToServer.writeBytes(commandToServer + '\n');

			String inputLine;
			while (((inputLine = inFromServer.readLine()) != null)){
				System.out.println(inputLine);
				}

			break;
		case "HEAD": 
			commandToServer = "HEAD";
			outToServer.writeBytes(commandToServer + '\n');
	
			while (((inputLine = inFromServer.readLine()) != null) && ((inFromServer.readLine().contains("\n\n")) == false)){
				System.out.println(inputLine);
				}
			
			break;
		case "PUT":
			commandToServer = "PUT";
			break;
		case "POST": 
			commandToServer = "POST";
			break;
		default:
			commandToServer = "INVALID COMMAND";
		}
		System.out.println(commandToServer);

		

		// Read text from the console and write it to the server. 
//		String sentence = inFromUser.readLine();
//		outToServer.writeBytes(sentence + '\n');




		clientSocket.close();

	}

}

