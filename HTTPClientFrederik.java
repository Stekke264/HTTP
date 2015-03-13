// Import necessary IO and NETwork libraries.
import java.io.*;
import java.net.*;
import java.util.stream.Stream;

public class HTTPClient {

	// Create the socket for connection.
	public static Socket clientSocket;

	// Startup the client with the parameters provided by the invoker.
	// Parameters are: HTTPCommand, host, port, HTTPversion
	public static void main(String[] argv) throws Exception{

		// Port number the user wants to use to connect.
		int portNumber = Integer.parseInt(argv[2]);
		
		// Host the user wants to connect to.
		String host = argv[1];
		
		//Check the HTTPVersion
		String HTTPVersion;
		switch(argv[3]){
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

		//Try connecting to the host.
		try{
			clientSocket = new Socket(host, portNumber);
		}
		catch(UnknownHostException e){
			System.err.println("Can't find host: " + host);
			System.exit(1);
		}
		System.out.println("Connected");

		// Create a buffer to store the data from the server
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		// Create an outputstream to send data to the server
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

		// Create a buffer to read data from the user
		//BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
		//System.out.print("FROM CONSOLE: ");


		// Determine which command should be send to the server.
		String commandToServer;
		switch (argv[0]){
		case "GET":
			commandToServer = "GET /index.html " + HTTPVersion + "\r\n\r\n";
			break;
		case "HEAD":
			commandToServer = "HEAD /index.html " + HTTPVersion + "\r\n\r\n";;
			break;
		case "PUT":
			commandToServer = "PUT /index.html " + HTTPVersion + "\r\n\r\n";;
			break;
		case "POST":
			commandToServer = "POST /index.html " + HTTPVersion + "\r\n\r\n";;
			break;
		default:
			commandToServer = "INVALLID COMMAND";
		}
		
		System.out.println(commandToServer);

		outToServer.writeBytes(commandToServer);

		// Read text from the console and write it to the server. 
		//String sentence = inFromUser.readLine();
		//outToServer.writeBytes(sentence + '\n');
		
		// Write the page to a text file.
		PrintWriter writer = new PrintWriter(host + ".txt", "UTF-8");

		// Display info from server on the screen
		String line;
		while((line = inFromServer.readLine()) != null){
			writer.write(line);
			System.out.println("FROM SERVER: " + line);
		}
		
		writer.close();
		clientSocket.close();
		System.out.println("Connection Closed");

	}

}
