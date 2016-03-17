package challenge6;

class MyTcpHandler extends TcpHandler {
	public static void main(String[] args) {
		new MyTcpHandler();
	}

    private static final String ipAddr = "2001:067c:2564:a156:7277:81ff:fe01:83f1";

	public MyTcpHandler() {
		super();

		boolean done = false;

		int[] txpkt = new int[60];

        // IPv6

		txpkt[0] = 0x60;	// Version + Traffic Class
		txpkt[1] = 0x00;    // Traffic Class + Flow Label
        txpkt[2] = 0x00;    // Flow Label
        txpkt[3] = 0x00;    // Flow Label
        txpkt[4] = 0x00;    // Payload Length
        txpkt[5] = 0x14;    // Payload Length
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
        txpkt[42] = 0x1e;   // Destination Port
        txpkt[43] = 0x1f;   // Destination Port
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

        this.sendData(txpkt);

		while (!done) {
			// check for reception of a packet, but wait at most 500 ms:
			int[] rxpkt = this.receiveData(500);
			if (rxpkt.length==0) {
				// nothing has been received yet
				System.out.println("Nothing...");
				continue;
			}

			// something has been received
			int len=rxpkt.length;

			// print the received bytes:
			int i;
			System.out.print("Received " + len + " bytes: ");
			for (i=0;i<len;i++) {
                if (rxpkt[52] == 96 && rxpkt[53] == 18) {
                    // SYN/ACK Flags set.
                }
                System.out.print(Integer.toHexString(rxpkt[i]) + " ");
            }
			System.out.println("");
		}   
	}
}