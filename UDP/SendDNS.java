import java.net.*;
import java.io.*;
import java.util.*;
public class SendDNS {
	
	private String toSend;
	private MulticastSocket ds;
	private DatagramPacket dp;
	private InetAddress dst;
	private int port;

	public SendDNS(String port){
		try{
			this.port = Integer.parseInt(port);
			ds = new MulticastSocket();
			dst = InetAddress.getByName("224.0.0.1");
			ds.joinGroup(dst);

		} catch ( Exception e ){
			e.printStackTrace();
		}
		this.beginToSend();
		ds.close();
	}

	
	
	public byte[] translate(String str){
		byte[] label = new byte[str.length()];
		int cpt = 1, pos = 0;
		while(cpt<str.length()){
			if(str.charAt(cpt) == '.'){
				label[pos] = cpt - pos - 1;
				pos = cpt;
			}else{
				label[cpt] = str.charAt(cpt);
			}
			cpt++;
		}
		label[pos] = cpt-pos;
		return label;
	}
Q5
	public void request(Byte[] data, InetAddress add, int port){
		DatagramPacket d = new DatagramPacket(data, data.length()-1, add, port);
		DatagramSocket s = new DatagramSocket();
		s.sent(d);
	}
	
	public String createDNSrequest(String domain){
		Byte[] toto = null;
	}

	public static void main(String args[]){
		new SendUDP(args[0]);
	}
}
