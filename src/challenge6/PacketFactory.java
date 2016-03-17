package challenge6;

public class PacketFactory {
	
	private String sourceAddr;
	private String destAddr;
	private int sourcePort;
	private int destPort;
	
	public PacketFactory(String source, String destination, int sourcePort, int destPort) {
		this.sourceAddr = source;
		this.destAddr = destination;
		this.sourcePort = sourcePort;
		this.destPort = destPort;
	}
	
	public int[] ipv6Packet() {
		return null;
		
	}
}
