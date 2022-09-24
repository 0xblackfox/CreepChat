import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Chat {
	String type;
	String his_pub;
	String his_mod;
	String my_pub;
	String my_mod;
	RSA rsa;
	ObjectInputStream in;
    ObjectOutputStream out;
    BufferedReader br;
    Socket socket;
    int count1=0;
    int count2=0;
    public Chat(Socket socket,ObjectInputStream in,ObjectOutputStream out, String type) {
    	rsa = new RSA();
    	my_pub = rsa.getPublicKey();
    	my_mod = rsa.getModulus();
        this.type = type;
    	this.socket = socket;
    	this.out = out;
    	this.in = in;
        br = new BufferedReader(new InputStreamReader(System.in));
        //Scanner input = new Scanner(System.in);
       
    }
    
    public void send(dstore inputString) throws EOFException, SocketException {
    
    	if (count1==0||count1==1){
    		//System.out.println("send-NULL");
 
    	}
    	else
    	inputString.save.text = rsa.encrypt(inputString.save.text,his_mod,his_pub);
        try {
            out.writeObject(inputString);
            count1+=1;
        } catch (IOException e) {
        	//System.out.println("Exception Occurs");
            e.printStackTrace();
        }
    }
    
    public dstore receive() throws IOException, ClassNotFoundException, SocketException, EOFException {
        dstore temp = (dstore) in.readObject();
        
        if(count2==0||count2==1){
        	//System.out.println("receive-NULL");
        	
        }
        else{
        	if (type.equalsIgnoreCase("Client")){
        		System.out.println("Server: ");
        	}
        	else {
        		System.out.println("Client: ");
        	}
        	System.out.println("Encrypted Message: "+temp.save.text);
        	temp.save.text = rsa.decrypt(temp.save.text);
	        System.out.println("Decrypted Message: "+temp.save.text);
	 
	        
        }
        count2+=1;
        return temp;
    }
    
    public void end_conn() {
        try {
            socket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
