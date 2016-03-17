package challenge6;

public class PacketFactory {

    private Ipv6Packet ipv6Packet;
    private TcpPacket tcpPacket;
	
	public PacketFactory(Ipv6Packet ipv6Packet, TcpPacket tcpPacket) {
        this.ipv6Packet = ipv6Packet;
        this.tcpPacket = tcpPacket;
	}
	
	public static int[] toIntArray(int[] firstPacket, int[] secondPacket) {
        int[] packet = new int[firstPacket.length + secondPacket.length];

        System.arraycopy(firstPacket, 0, packet, 0, firstPacket.length);
        System.arraycopy(secondPacket, 0, packet, firstPacket.length, secondPacket.length);

        return packet;
	}
}
