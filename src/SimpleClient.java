import java.io.*;
import java.net.*;

public class SimpleClient {
	public static void main(String[] args) {

		String hostname = "54.213.91.121";
		int port = 6789;


		Socket clientSocket = null;
		DataOutputStream os = null;
		BufferedReader is = null;

		try {
			clientSocket = new Socket(hostname,port);
			os = new DataOutputStream(clientSocket.getOutputStream());
			is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		}catch (Exception e) {
			e.printStackTrace();
		}
		if (clientSocket == null || os == null || is == null) {
			System.out.println("NULL POINTER(S)");
			return;
		}
		try {
			System.out.println("Connected to server");
			while (true) {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				if(br.ready()) {
					String keyboardInput = br.readLine();
					os.writeBytes(keyboardInput+"\n");
				
					if (keyboardInput.equals("exit")) break;
				}
				if(is.ready()) {
					System.out.println(is.readLine());
					
				}
			}
			os.close();
			clientSocket.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
