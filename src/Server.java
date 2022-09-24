
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    Chat chat;
    ServerSocket server_socket;
    Socket client_socket;
    String addres;
    ObjectInputStream in;
    ObjectOutputStream out;
    dstore temp;
    Sender sender;
    Receiver receiver;
    
    public Server(int port) throws IOException, ClassNotFoundException {
        server_socket = new ServerSocket(port);
        System.out.println("Waiting for a client to connect ...  ");
        while (true) {
            accept_conn();
        }
    }
    private void accept_conn() throws IOException, ClassNotFoundException {
        client_socket = server_socket.accept();
        addres =  (client_socket.getInetAddress()).getHostAddress();
         in = new ObjectInputStream(client_socket.getInputStream());
         out = new ObjectOutputStream(client_socket.getOutputStream());
        temp = (dstore) (in.readObject());
        System.out.println(addres + " : " + temp.save.text);

        if (temp.save.text.contains("Hey! Want to chat?")) {
            chat = new Chat(client_socket, in, out, "Server");
            temp.save.text = "Sure. Let us begin. My public keys are : " + chat.my_pub + " and " + chat.my_mod;
            out.writeObject(temp);
            System.out.println("Me : " + temp.save.text);
            sender = new Sender(chat, "Me : ");
            receiver = new Receiver(chat, "Client : ", "");
            sender.start();
            receiver.start();
        } 
        else {
            System.out.println("Connection Lost... Sorry!!!. ");
            client_socket.close();
        }
    }


}
