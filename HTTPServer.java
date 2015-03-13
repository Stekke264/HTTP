import java.io.*;
import java.net.*;

public class HTTPServer {

	public static void main(String args[]) throws Exception{

		int portNumber = Integer.parseInt(args[0]);

		ServerSocket serverSocket = new ServerSocket(6789);


		// Wait for a connection to be made to the server socket. 
		while(true)
		{
			// Create	 a 'real' socket from the Server socket.
			Socket connectionSocket = serverSocket.accept();

			// Create inputstream (convenient data reader) to this host.
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader	(connectionSocket.getInputStream()));

			// Create outputstream (convenient data writer) to this host.
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			PrintWriter out = new PrintWriter(outToClient, true); 

			// Read text from the client, make it uppercase and write it back.
			String clientSentence = inFromClient.readLine();

			int i = clientSentence.indexOf(' ');
			String command =clientSentence.substring(0, i);
			String rest = clientSentence.substring(i+2);
			String basicPath = "C:\\Users\\Stijn\\workspace\\HTTPClient\\src\\";
			String path;
			File f;
			switch(command){
			case "GET":
				System.out.println("in GET");
				i = rest.indexOf(' ');
				System.out.println(rest);
				System.out.println(i);
				path =rest.substring(0, i);
				System.out.println(basicPath.concat(path));

				f = new File(basicPath.concat(path));
				System.out.println(f.isFile());
				if(f.isFile()){
					outToClient.writeBytes("200 OK"+"\n");
					BufferedReader in = new BufferedReader(new FileReader(f));
					String line = in.readLine();
					while(line != null)
					{
					  outToClient.writeBytes(line.concat("\n"));
					  line = in.readLine();
					}
					in.close();
				}
				else
					outToClient.writeBytes("404 NOT FOUND");
				break;
			case "HEAD":
				System.out.println("In HEAD");
				i = rest.indexOf(' ');
				path =rest.substring(0, i);
				f = new File(basicPath + path);
				if(f.isFile()){
					outToClient.writeBytes("200 OK");				
				}
				else
					outToClient.writeBytes("404 NOT FOUND");
				break;
			case "PUT":
				break;
			case "POST":
				break;
			default :
				
			}


		}
	}
}


