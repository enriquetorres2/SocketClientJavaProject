import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class APITester {
	public static void main(String[] args) {
		ClientAPI cl = new ClientAPI("localhost",6789);
		new Thread(cl).start();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			try {
				if(br.ready()) {
					String keyboardInput = br.readLine();
				}
				if(cl.isMessage()) {
					System.out.println(cl.readMessage());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}