
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Date;


public class Client {
    Chat chat;
    BufferedReader ans;
    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    dstore pkt;
    thread wait;
    Sender sender;
    Receiver receiver;
    Timestamp time;
    
    public Client() throws ClassNotFoundException, IOException {
    	ans = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter IP address of server : ");
        this.call_server(ans.readLine(), 111);
    }
    public void call_server(String host,int port) throws ClassNotFoundException, UnknownHostException, IOException {
     	System.out.println("Trying to connect to a server ... ");
     	//System.out.println(new Socket("127.0.0.1", 5555));
        socket = new Socket(host, port);
        System.out.println("Connected to server : "+host);
        
        out =  new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        System.out.println("Streams established. Ready for a chat ...\n");
        wait = new thread();
        chat = new Chat(socket, in, out,"Client"); 
        pkt = new dstore(new dstore("Hey! Want to chat? My public keys are: " + chat.my_pub+" and "+chat.my_mod));
        System.out.println("Me : "+pkt.save.text);
        System.out.println("<sent at: "+pkt.time+">\n");
        
        out.writeObject(pkt);
        wait.start();
        dstore reply = (dstore)(in.readObject());
         time = new Timestamp((new Date()).getTime());
        if ((reply.save.text).contains("Sure")) {
	        System.out.println(host+ " : " + reply.save.text);
	        System.out.println("{{Maessage sent at: "+pkt.time+"}}");
	        System.out.println("[Message received at:"+ time +"]");
	        sender = new Sender(chat, "Me : ");
	        receiver = new Receiver(chat, host+" : ", host);
	        sender.start();
	        receiver.start();
        }
        else {
            socket.close();
            System.out.println("Time Out!");
        }
 
        }
}
