import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class creepchat {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Server server;
		Client client;
		BufferedReader br;
		String ans;
		System.out.print("server or client : ");
		 br = new BufferedReader(new InputStreamReader(System.in));
		 ans = br.readLine();
		 //System.out.println(ans);
		if (ans.equalsIgnoreCase("server")) {
			server = new Server(111);
		} else {
			client = new Client();
		}
	}

}
