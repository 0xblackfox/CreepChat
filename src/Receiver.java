
import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.sql.Timestamp;
import java.util.Date;


public class Receiver extends Thread {
    Chat chat;
    String server;
    String prompt;
    Timestamp time;
    
    public Receiver(Chat chat, String prompt, String server) {
        this.chat = chat;
        this.server = server;
        this.prompt = prompt;
    }
    
    public void run() { 
        dstore received;
        int count=0;
        String publicKey;
        String Modulus;
        time = new Timestamp((new Date()).getTime());
        try {
            while((received = chat.receive()) != null) {    
                if (count==0){
                	publicKey=received.save.text;
                	chat.his_pub = publicKey;
                	//System.out.println("his_pub : "+chat.his_pub);
                }
                else if (count==1){
                	Modulus=received.save.text;
                	chat.his_mod = Modulus;
                	//System.out.println("his_mod : "+chat.his_mod);
                	if (!(prompt.equalsIgnoreCase("Client : "))){
                		System.out.println("Connected to server: "+server);
                		System.out.println("Streams Established. Ready to chat ..");
                	}
                	else {
                		System.out.println("Keys Exchanged!");
                	}
                }
                else{           	
                	System.out.println("{{This maessage was sent at: "+received.time+"}}");
        	        System.out.println("[This message was received at:"+ time +"]");
	            }
            count+=1;
            }
        } catch (SocketException e) {
        	//System.out.println("Exception Occurs");
            if (prompt.equalsIgnoreCase("Client : ")){
            	System.out.println("Connection End with client!");
            	System.out.println("Waiitingfor a client to connect..");
            }            
        } 
        catch (EOFException e) {
        	if (prompt.equalsIgnoreCase("Client : ")){
            	System.out.println("Connection End with client!.");
            	System.out.println("Waiitingfor a client to connect..");
            }
        } 
        catch (IOException e) {
        	//System.out.println("Exception Occurs");
            e.printStackTrace();
        } 
        catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//System.out.println("Exception Occurs");
		}
    }
}
