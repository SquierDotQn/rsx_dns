import java.net.*;
import java.io.*;
import java.util.*;
public class SendDNS {
	
	private byte[] toSend;
	private MulticastSocket ds;
	private DatagramPacket dp;
	private InetAddress dst;
	private int port;
	
	public static int portLocal = 16725;
	
	public SendDNS(/*String port*/){
		try{
			byte [] answer = new byte [512];
			this.port = 53 /*Integer.parseInt(port)*/;
			dst = InetAddress.getByName("172.18.12.9");
			this.request(this.createDNSrequest());
			dp = new DatagramPacket(answer, answer.length, dst, 53);
			ds.receive(dp);

			System.out.println("paquet reçu de :" + dp.getAddress() + 
					", port : "+ dp.getPort() +
					", taille : " + dp.getLength());

			byte [] data = dp.getData();
			int pos = 0;
		
			int nbAnswer = data[6]*256 + data[7],
			    type     = 0; 
			if(nbAnswer == 0){
				System.out.println("Pas de réponse de la requête. Fermeture du programme");
				return;
			}	
			pos = 12;
			while(data[pos] != 0){
				pos += data[pos]+1; 
			}
			type = data[pos+1]*256 + data[pos+2]; // On cherche le type
			pos += 5;
		
			/* Tant que le type Réponse courant ne correspond pas au type Réponse recherché */
			while(data[pos+2]*256 + data[pos+3] != type){
				/* Lecture d'un champ réponse pour accéder au champ Réponse suivant */
				pos += 2+2+2+4;
			
				/* Lecture du champ RDLenght */
				pos += 2+data[pos]*256 + data[pos+1];
				nbAnswer--;
			
			}
		
			pos += 2+2+2+4; /* champ réponse */
		
			if(data[pos]*256 + data[pos+1] == 4){
				pos += 2;
			
				int [] address = new int [4];
			
				for(int i=0;i<4;i++){
					if(data[pos+i] < 0)
						address[i] = data[pos+i] + 256;
					else
						address[i] = data[pos+i];
				
				}
				 
				System.out.println("Address : " + address[0] + "." + address[1] + "." + address[2] + "." + address[3]);
			}


		} catch ( Exception e ){
			e.printStackTrace();
		}


		ds.close();
	}

	
	/*
	public byte[] translateAddress(String str){
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
*/
	public static byte[] createDNSrequest(){
		byte [] id = {0x00, 0x45},
			param = {0x00, 0x00},
			QDCount = {0x00, 0x01},
			ANCount = {0x00, 0x00},
			NSCount = {0x00, 0x00},
			ARCount = {0x00, 0x00},
			label= {0x03, 0x77, 0x77, 0x77, 0x04, 0x6c, 0x69, 0x66, 0x6c, 0x02, 0x66, 0x72},
			endLabel = {0x00},
			type = {0x00, 0x01},
			classDNS = {0x00, 0x01};
		
		byte [] concat = new byte[29];
		System.arraycopy(id, 0, concat, 0, 2);
		System.arraycopy(param, 0, concat, 2, 2);
		System.arraycopy(QDCount, 0, concat, 4, 2);
		System.arraycopy(ANCount, 0, concat, 6, 2);
		System.arraycopy(NSCount, 0, concat, 8, 2);
		System.arraycopy(ARCount, 0, concat, 10, 2);
		System.arraycopy(label, 0, concat, 12, 12);
		System.arraycopy(endLabel, 0, concat, 24, 1);
		System.arraycopy(type, 0, concat, 25, 2);
		System.arraycopy(classDNS, 0, concat, 27, 2);
		

		return concat;

	}

	public void request(byte[] data) throws Exception{
		DatagramPacket dp = new DatagramPacket(data, (data.length-1), this.dst, this.port);
		DatagramSocket ds = new DatagramSocket(portLocal);
		ds.send(dp);
	}
	
	public static void main(String args[]){
		new SendDNS(/*args[0]*/);
	}
}
