import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.EventObject;

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

	public synchronized boolean isMessage() {
		//this.notify();
		return messageAvailable;
	}
	public synchronized void sendMessage(String str) {
		this.outMessage = str;
		this.send = true;
		this.notify();
	}
	public synchronized String readMessage() {
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
			synchronized(this) {
				while (true) {
					this.wait();
					System.out.println("woke up");
					if(this.send) {
						//String keyboardInput = br.readLine();
						this.os.writeBytes(outMessage+"\n");
						this.send = false;
						//if (keyboardInput.equals("exit")) break;
						if (outMessage.equals("exit")) break;
						this.outMessage = "";
					}
					if(is.ready()) {
						//System.out.println(is.readLine());
						this.inMessage = is.readLine();
						this.messageAvailable = true;
					}
				}
			}
			this.os.close();
			this.clientSocket.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	class MessageEvent extends EventObject {
		public MessageEvent(Object source) {
			super(source);
		}
	}

	interface MessageListener
	{
		public String messageRecieved(String message);
	}
	
}
