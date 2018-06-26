import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientAPI implements Runnable {

	private String hostname;
	private int port;
	private Socket clientSocket;
	private DataOutputStream os;
	private BufferedReader is;
	private boolean messageAvailable;
	private String inMessage;
	private String outMessage;
	private boolean send;

	public ClientAPI(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
		this.clientSocket = null;
		this.os = null;
		this.is = null;
		this.messageAvailable = false;
		this.inMessage = "";
		this.outMessage = "";
		this.send = false;
	}
	
	public boolean isMessage() {
		return messageAvailable;
	}
	public void sendMessage(String str) {
		this.outMessage = str;
		this.send = true;
	}
	public String readMessage() {
		this.messageAvailable = false;
		return inMessage;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			clientSocket = new Socket(hostname,port);
			os = new DataOutputStream(clientSocket.getOutputStream());
			is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		}catch (Exception e) {
			e.printStackTrace();
		}
		if (clientSocket == null || os == null || is == null) {
			System.err.println("NULL POINTER(S)");
			return;
		}
		try {
			System.out.println("Connected to server");
			while (true) {
				if(this.send) {
					//String keyboardInput = br.readLine();
					this.os.writeBytes(outMessage+"\n");
					this.outMessage = "";
					this.send = false;
					//if (keyboardInput.equals("exit")) break;
					if (outMessage.equals("exit")) break;
				}
				if(is.ready()) {
					//System.out.println(is.readLine());
					this.inMessage = is.readLine();
					this.messageAvailable = true;
				}
			}
			this.os.close();
			this.clientSocket.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
