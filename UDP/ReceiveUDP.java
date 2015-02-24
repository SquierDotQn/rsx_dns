import java.net.*;
import java.io.*;

public class ReceiveUDP {
	
	private String received;
	private DatagramSocket ds;
	private DatagramPacket dp;

	public ReceiveUDP(InetAddress addr, int port){
		try{
			ds = new DatagramSocket(port, addr);
			dp = new DatagramPacket(new byte[1024], 1024);
		}catch(Exception e){
			e.printStackTrace();
		}
		this.beginToReceive();
		ds.close();
	}

	/* Listen to a single message sent to its socket */
	protected void beginToReceive(){
		try{
			ds.receive(dp);
		}catch(Exception e){
			e.printStackTrace();
		}
		/*this.afficheInfos(dp);//Not used*/
		this.affiche(dp);
	}

	/* Prints the informations about the sender */
	protected void afficheInfos(DatagramPacket dp){
		System.out.println("Paquet reçu de :" + dp.getAddress() + "\n" +
				   "port "+		dp.getPort()+ "\n" +
				   "taille  "+		dp.getLength());
	}
	
	/* Prints the message */
	protected void affiche(DatagramPacket dp){
		System.out.println("Message : "+ new String(dp.getData()));
		
	}
	
	public static void main(String args[]){
		if(args.length < 2){
			System.out.println("bad usage, should be ReceiveUDP addr port");
			return;
		}
		try{
			new ReceiveUDP(InetAddress.getByName(args[0]),Integer.parseInt(args[1]));
		} catch (UnknownHostException e){
			System.err.println("hostname is unknown");
		}
	}
}
