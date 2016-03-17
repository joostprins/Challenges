package challenge6;

class MyTcpHandler extends TcpHandler {
	public static void main(String[] args) {
		new MyTcpHandler();
	}

    private static final String ipAddr = "2001:067c:2564:a156:7277:81ff:fe01:83f1";
    private static final int ACK_FLAG = 0x10;
    private static final int SYN_FLAG = 0x02;
    private static final int FIN_FLAG = 0x01;

	public MyTcpHandler() {
        super();

		boolean done = false;

        /*
		int[] txpkt = new int[60];

        // IPv6

		txpkt[0] = 0x60;	// Version + Traffic Class
		txpkt[1] = 0x00;    // Traffic Class + Flow Label
        txpkt[2] = 0x00;    // Flow Label
        txpkt[3] = 0x00;    // Flow Label
        txpkt[4] = 0x00;    // Payload Length
        txpkt[5] = 20;      // Payload Length
        txpkt[6] = 0xfd;    // Next Header
        txpkt[7] = 0x10;    // Hop Limit

        // Source Address (2001:067c:2564:a156:7277:81ff:fe01:83f1)
        txpkt[8]  = 0x20;
        txpkt[9]  = 0x01;
        txpkt[10] = 0x06;
        txpkt[11] = 0x7c;
        txpkt[12] = 0x25;
        txpkt[13] = 0x64;
        txpkt[14] = 0xa1;
        txpkt[15] = 0x56;
        txpkt[16] = 0x72;
        txpkt[17] = 0x77;
        txpkt[18] = 0x81;
        txpkt[19] = 0xff;
        txpkt[20] = 0xfe;
        txpkt[21] = 0x01;
        txpkt[22] = 0x83;
        txpkt[23] = 0xf1;

        // Destination Address (2001:67c:2564:a170:204:23ff:fede:4b2c)
        txpkt[24] = 0x20;
        txpkt[25] = 0x01;
        txpkt[26] = 0x06;
        txpkt[27] = 0x7c;
        txpkt[28] = 0x25;
        txpkt[29] = 0x64;
        txpkt[30] = 0xa1;
        txpkt[31] = 0x70;
        txpkt[32] = 0x02;
        txpkt[33] = 0x04;
        txpkt[34] = 0x23;
        txpkt[35] = 0xff;
        txpkt[36] = 0xfe;
        txpkt[37] = 0xde;
        txpkt[38] = 0x4b;
        txpkt[39] = 0x2c;

		// TCP

        txpkt[40] = 0x04;	// Source Port
        txpkt[41] = 0xd2;   // Source Port
        txpkt[42] = Integer.parseInt(String.format("%04X", 7711).substring(0,2), 16);
        txpkt[43] = Integer.parseInt(String.format("%04X", 7711).substring(2), 16);
        txpkt[44] = 0x00;   // Sequence Number
        txpkt[45] = 0x00;   // Sequence Number
        txpkt[46] = 0x00;   // Sequence Number
        txpkt[47] = 0x00;   // Sequence Number
        txpkt[48] = 0x00;   // Acknowledgement
        txpkt[49] = 0x00;   // Acknowledgement
        txpkt[50] = 0x00;   // Acknowledgement
        txpkt[51] = 0x00;   // Acknowledgement
        txpkt[52] = 0x50;   // Data Offset  + Reserved + NS
        txpkt[53] = 0x02;   // Flags (SYN)
        txpkt[54] = 0x04;   // Window Size
        txpkt[55] = 0x00;   // Window Size
        txpkt[56] = 0x00;   // Checksum
        txpkt[57] = 0x00;   // Checksum
        txpkt[58] = 0x00;   // Urgent Pointer
        txpkt[59] = 0x00;   // Urgent Pointer

        this.sendData(txpkt);*/

        // Send SYN packet.
        Ipv6Packet ipv6Packet = new Ipv6Packet();
        TcpPacket tcpPacket = new TcpPacket(0, 0, 0, 0, 0, 0, 0, 0, SYN_FLAG);
        ipv6Packet.setPayloadLength(20);
        tcpPacket.setOffset(0x50);

        this.sendData(PacketFactory.toIntArray(ipv6Packet, tcpPacket));

        int[] rxpkt = this.receiveData(500);

        // Ack the SYN/ACK packet.
        ipv6Packet = new Ipv6Packet();
        tcpPacket = new TcpPacket(0, 0, 0, 1, rxpkt[44], rxpkt[45], rxpkt[46], rxpkt[47] + 1, ACK_FLAG);
        ipv6Packet.setPayloadLength(20);
        tcpPacket.setOffset(0x50);

        this.sendData(PacketFactory.toIntArray(ipv6Packet, tcpPacket));

        rxpkt = this.receiveData(500);

        // Send the GET request.
        ipv6Packet = new Ipv6Packet();
        tcpPacket = new TcpPacket(0, 0, 0, 1, rxpkt[44], rxpkt[45], rxpkt[46], rxpkt[47] + 1, ACK_FLAG);
        ipv6Packet.setPayloadLength(20);
        tcpPacket.setOffset(0x50);

        this.sendData(PacketFactory.toIntArray(ipv6Packet, tcpPacket));

        rxpkt = this.receiveData(500);

        // Send FIN packet.
        ipv6Packet = new Ipv6Packet();
        tcpPacket = new TcpPacket(0, 0, 0, 2, 0, 0, 0, 0, FIN_FLAG);
        ipv6Packet.setPayloadLength(20);
        tcpPacket.setOffset(0x50);

        this.sendData(PacketFactory.toIntArray(ipv6Packet, tcpPacket));

        rxpkt = this.receiveData(500);

	}
}