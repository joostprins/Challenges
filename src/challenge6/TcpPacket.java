package challenge6;

public class TcpPacket {

    private int seq1;
    private int seq2;
    private int seq3;
    private int seq4;
    private int ack1;
    private int ack2;
    private int ack3;
    private int ack4;
    private int flags;
    private int offset;

    public TcpPacket(int seq1, int seq2, int seq3, int seq4, int ack1, int ack2, int ack3, int ack4, int flags) {
        this.seq1 = seq1;
        this.seq2 = seq2;
        this.seq3 = seq3;
        this.seq4 = seq4;
        this.ack1 = ack1;
        this.ack2 = ack2;
        this.ack3 = ack3;
        this.ack4 = ack4;
        this.flags = flags;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int[] toIntArray() {
        int[] packet = new int[20];

        packet[0] = 0x25;	// Source Port
        packet[1] = 0xb5;   // Source Port
        packet[2] = 0x1e;   // Destination Port
        packet[3] = 0x1f;   // Destination Port
        packet[4] = seq1;   // Sequence Number
        packet[5] = seq2;   // Sequence Number
        packet[6] = seq3;   // Sequence Number
        packet[7] = seq4;   // Sequence Number
        packet[8] = ack1;   // Acknowledgement
        packet[9] = ack2;   // Acknowledgement
        packet[10] = ack3;   // Acknowledgement
        packet[11] = ack4;   // Acknowledgement
        packet[12] = offset;   // Data Offset  + Reserved + NS
        packet[13] = flags;   // Flags (SYN)
        packet[14] = 0x04;   // Window Size
        packet[15] = 0x00;   // Window Size
        packet[16] = 0x00;   // Checksum
        packet[17] = 0x00;   // Checksum
        packet[18] = 0x00;   // Urgent Pointer
        packet[19] = 0x00;   // Urgent Pointer

        return packet;
    }

}
