import java.io.*;
import java.net.*;

public class SimpleClient {
	public static void main(String[] args) {

		String hostname = "localhost";
		int port = 6789;


		Socket clientSocket = null;
		DataOutputStream os = null;

		try {
			clientSocket = new Socket(hostname,port);
			os = new DataOutputStream(clientSocket.getOutputStream());
		}catch (Exception e) {
			e.printStackTrace();
		}
		if (clientSocket == null || os == null) {
			System.out.println("NULL POINTER(S)");
			return;
		}
		try {
			while (true) {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				String keyboardInput = br.readLine();
				os.writeBytes(keyboardInput+"\n");
				
				if (keyboardInput.equals("exit")) break;
			}
			os.close();
			clientSocket.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
