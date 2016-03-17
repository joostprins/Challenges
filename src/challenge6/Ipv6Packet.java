package challenge6;

public class Ipv6Packet {

    private int payloadLength;

    public Ipv6Packet() {
        this.payloadLength = 0;
    }

    public void setPayloadLength(int length) {
        payloadLength = length;
    }

    public int[] toIntArray() {
        int[] packet = new int[40];

        packet[0] = 0x60;
        packet[1] = 0x00;
        packet[2] = 0x00;
        packet[3] = 0x00;
        packet[4] = 0x00;           // Payload length
        packet[5] = payloadLength;  // Payload length
        packet[6] = 0xfd;
        packet[7] = 0x10;
        packet[8]  = 0x20;
        packet[9]  = 0x01;
        packet[10] = 0x06;
        packet[11] = 0x7c;
        packet[12] = 0x25;
        packet[13] = 0x64;
        packet[14] = 0xa1;
        packet[15] = 0x56;
        packet[16] = 0x72;
        packet[17] = 0x77;
        packet[18] = 0x81;
        packet[19] = 0xff;
        packet[20] = 0xfe;
        packet[21] = 0x01;
        packet[22] = 0x83;
        packet[23] = 0xf1;
        packet[24] = 0x20;
        packet[25] = 0x01;
        packet[26] = 0x06;
        packet[27] = 0x7c;
        packet[28] = 0x25;
        packet[29] = 0x64;
        packet[30] = 0xa1;
        packet[31] = 0x70;
        packet[32] = 0x02;
        packet[33] = 0x04;
        packet[34] = 0x23;
        packet[35] = 0xff;
        packet[36] = 0xfe;
        packet[37] = 0xde;
        packet[38] = 0x4b;
        packet[39] = 0x2c;

        return packet;
    }

}
