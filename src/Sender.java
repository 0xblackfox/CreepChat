

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Scanner;

public class Sender extends Thread {
	Chat chat;
	dstore pkt;
	String check;

	public Sender(Chat chat, String check) {
		this.chat = chat;
		this.check = check;
	}

	public void run() {
		pkt = new dstore(new dstore(chat.my_pub));
		try {
			chat.send(pkt);
		} 
		catch (EOFException | SocketException e) {
			//System.out.println("Exception Occurs");
			e.printStackTrace();
		}
		pkt = new dstore(new dstore(chat.my_mod));
		try {
			chat.send(pkt);
		} 
		catch (EOFException | SocketException e) {
			//System.out.println("Exception Occurs");
			e.printStackTrace();
		}

		while (true) {
			String input="";
			try {
				input = chat.br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			/*
			 * if (input.equalsIgnoreCase("file")) { //chat.end_conn(); //return;
			 * FileInputStream fr; try { System.out.println("Enter File Name:"); Scanner in
			 * = new Scanner(System.in); String s = in.nextLine(); fr = new
			 * FileInputStream(s); byte[] b = new byte[20002]; fr.read(b, 0, b.length);
			 * 
			 * OutputStream os = chat.socket.getOutputStream(); String packet = os.write(b,
			 * 0, b.length; chat.send(packet); } catch (Exception e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 * 
			 * }
			 */

			if (input.equalsIgnoreCase("over")) {
				chat.end_conn();
				return;
			}
			pkt = new dstore(new dstore(input));
			try {
				chat.send(pkt);
			} 
			catch (EOFException | SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        System.out.println("{{ sent at: "+pkt.time+"}}\n");
		}
	}

}
