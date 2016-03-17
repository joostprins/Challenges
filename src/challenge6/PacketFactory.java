package challenge6;

public class PacketFactory {

    private Ipv6Packet ipv6Packet;
    private TcpPacket tcpPacket;
	
	public PacketFactory(Ipv6Packet ipv6Packet, TcpPacket tcpPacket) {
        this.ipv6Packet = ipv6Packet;
        this.tcpPacket = tcpPacket;
	}
	
	public static int[] toIntArray(Ipv6Packet ipv6Packet, TcpPacket tcpPacket) {
        int[] ipv6PacketArray = ipv6Packet.toIntArray();
        int[] tcpPacketarray = tcpPacket.toIntArray();

        int[] packet = new int[ipv6PacketArray.length + tcpPacketarray.length];

        System.arraycopy(ipv6PacketArray, 0, packet, 0, ipv6PacketArray.length);
        System.arraycopy(tcpPacketarray, 0, packet, ipv6PacketArray.length, tcpPacketarray.length);

        return packet;
	}
}
