package challenge6;

class MyTcpHandler extends TcpHandler {
	public static void main(String[] args) {
		new MyTcpHandler();
	}

    private static final int ACK_FLAG = 0x10;
    private static final int SYN_FLAG = 0x02;
    private static final int FIN_FLAG = 0x01;

	public MyTcpHandler() {
        super();
		boolean done = false;

        // Init
        Ipv6Packet ipv6Packet = new Ipv6Packet();
        TcpPacket tcpPacket = new TcpPacket(0, 0, 0, 0, 0, 0, 0, 0, SYN_FLAG);
        ipv6Packet.setPayloadLength(20);
        tcpPacket.setOffset(0x50);

        this.sendData(PacketFactory.toIntArray(ipv6Packet.toIntArray(), tcpPacket.toIntArray()));

        int[] rxpkt = this.receiveData(500);

		while (!done) {
			// check for reception of a packet, but wait at most 500 ms:
			rxpkt = this.receiveData(500);
			if (rxpkt.length==0) {
				// nothing has been received yet
				continue;
			}

			// something has been received
			int len=rxpkt.length;

			// print the received bytes:
			int i;
			System.out.print("Received " + len + " bytes: ");
			for (i=0;i<len;i++) {
                System.out.print(rxpkt[i] + " ");
            }
            done = true;
            System.out.println("");
        }

        done = false;


        // ACK SYN/ACK.
        ipv6Packet = new Ipv6Packet();
        tcpPacket = new TcpPacket(0, 0, 0, 1, rxpkt[44], rxpkt[45], rxpkt[46], rxpkt[47] + 1, ACK_FLAG);
        ipv6Packet.setPayloadLength(20);
        tcpPacket.setOffset(0x50);

        this.sendData(PacketFactory.toIntArray(ipv6Packet.toIntArray(), tcpPacket.toIntArray()));

        // Send HTTP GET
        ipv6Packet = new Ipv6Packet();
        tcpPacket = new TcpPacket(0, 0, 0, 1, 0, 0, 0, 0, 0x00);
        HttpPacket httpPacket = new HttpPacket();
        ipv6Packet.setPayloadLength(122);
        tcpPacket.setOffset(0x50);

        this.sendData(PacketFactory.toIntArray(PacketFactory.toIntArray(ipv6Packet.toIntArray(), tcpPacket.toIntArray()), httpPacket.toIntArray()));

        while (!done) {
            rxpkt = this.receiveData(500);
            int len=rxpkt.length;
            if (len == 0) {
                continue;
            }

            // print the received bytes:
            int i;
            System.out.print("Received " + len + " bytes: ");
            for (i=0;i<len;i++) {
                System.out.print(rxpkt[i] + " ");
            }
            done = true;
        }

        done = false;

        while (!(rxpkt.length > 400)) {
            rxpkt = this.receiveData(500);
            int len=rxpkt.length;
            if (len == 0) {
                continue;
            }

            // print the received bytes:
            int i;
            System.out.print("Received " + len + " bytes: ");
            for (i=0;i<len;i++) {
                System.out.print(rxpkt[i] + " ");
            }
        }

        System.out.println("");
        done = false;

        // ACK HTTP OK.
        if (rxpkt.length > 400) {
            ipv6Packet = new Ipv6Packet();

            long ackNum = rxpkt[44];
            ackNum = ackNum << 8;
            ackNum += rxpkt[45];
            ackNum = ackNum << 8;
            ackNum += rxpkt[46];
            ackNum = ackNum << 8;
            ackNum += rxpkt[47] + 512;
            String ackString = Long.toHexString(ackNum);

            int ack1 = Integer.parseInt(ackString.substring(0,1), 16);
            int ack2 = Integer.parseInt(ackString.substring(2,4), 16);
            int ack3 = Integer.parseInt(ackString.substring(4,6), 16);
            int ack4 = Integer.parseInt(ackString.substring(6), 16);

            tcpPacket = new TcpPacket(0, 0, 0, 103, ack1, ack2, ack3, ack4, ACK_FLAG);
            ipv6Packet.setPayloadLength(20);
            tcpPacket.setOffset(0x50);

            this.sendData(PacketFactory.toIntArray(ipv6Packet.toIntArray(), tcpPacket.toIntArray()));

            while (!done) {
                rxpkt = this.receiveData(500);
                int len=rxpkt.length;
                if (len == 0) {
                    continue;
                }

                // print the received bytes:
                int i;
                System.out.print("Received " + len + " bytes: ");
                for (i=0;i<len;i++) {
                    System.out.print(rxpkt[i] + " ");
                }
                done = true;
                System.out.println("");
            }
        }

        // Send FIN packet.
        /*ipv6Packet = new Ipv6Packet();
        tcpPacket = new TcpPacket(0, 0, 0, 103, 0, 0, 0, 0, FIN_FLAG);
        ipv6Packet.setPayloadLength(20);
        tcpPacket.setOffset(0x50);

        this.sendData(PacketFactory.toIntArray(ipv6Packet.toIntArray(), tcpPacket.toIntArray()));

        rxpkt = this.receiveData(500);

        while (!done) {
            int len=rxpkt.length;

            // print the received bytes:
            int i;
            System.out.print("Received " + len + " bytes: ");
            for (i=0;i<len;i++) {
                System.out.print(rxpkt[i] + " ");
            }
            done = true;
            System.out.println("");
        }*/

	}
}