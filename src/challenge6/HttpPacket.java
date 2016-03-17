package challenge6;

public class HttpPacket {

    public int[] toIntArray() {
        int[] packet = new int[102];

        // GET /s1745506 HTTP 1.1 (47 45 54 20 2f 73 31 37 34 35 35 30 36 20 48 54 54 50 2f 31 2e 31 5c 72 5c 6e)
        packet[0] = 0x47;
        packet[1] = 0x45;
        packet[2] = 0x54;
        packet[3] = 0x20;
        packet[4] = 0x2f;
        packet[5] = 0x73;
        packet[6] = 0x31;
        packet[7] = 0x37;
        packet[8] = 0x34;
        packet[9] = 0x35;
        packet[10] = 0x35;
        packet[11] = 0x30;
        packet[12] = 0x36;
        packet[13] = 0x20;
        packet[14] = 0x48;
        packet[15] = 0x54;
        packet[16] = 0x54;
        packet[17] = 0x50;
        packet[18] = 0x2f;
        packet[19] = 0x31;
        packet[20] = 0x2e;
        packet[21] = 0x31;
        packet[22] = 0x0d;
        packet[23] = 0x0a;

        // HOST
        packet[24] = 0x48;
        packet[25] = 0x6f;
        packet[26] = 0x73;
        packet[27] = 0x74;
        packet[28] = 0x3a;
        packet[29] = 0x20;
        packet[30] = 0x5b;
        packet[31] = 0x32;
        packet[32] = 0x30;
        packet[33] = 0x30;
        packet[34] = 0x31;
        packet[35] = 0x3a;
        packet[36] = 0x36;
        packet[37] = 0x37;
        packet[38] = 0x63;
        packet[39] = 0x3a;
        packet[40] = 0x32;
        packet[41] = 0x35;
        packet[42] = 0x36;
        packet[43] = 0x34;
        packet[44] = 0x3a;
        packet[45] = 0x61;
        packet[46] = 0x31;
        packet[47] = 0x37;
        packet[48] = 0x30;
        packet[49] = 0x3a;
        packet[50] = 0x32;
        packet[51] = 0x30;
        packet[52] = 0x34;
        packet[53] = 0x3a;
        packet[54] = 0x32;
        packet[55] = 0x33;
        packet[56] = 0x66;
        packet[57] = 0x66;
        packet[58] = 0x3a;
        packet[59] = 0x66;
        packet[60] = 0x65;
        packet[61] = 0x64;
        packet[62] = 0x65;
        packet[63] = 0x3a;
        packet[64] = 0x34;
        packet[65] = 0x62;
        packet[66] = 0x32;
        packet[67] = 0x63;
        packet[68] = 0x5d;
        packet[69] = 0x3a;
        packet[70] = 0x37;
        packet[71] = 0x37;
        packet[72] = 0x31;
        packet[73] = 0x31;
        packet[74] = 0x0d;
        packet[75] = 0x0a;

        // CONNECTION
        packet[76] = 0x43;
        packet[77] = 0x6f;
        packet[78] = 0x6e;
        packet[79] = 0x6e;
        packet[80] = 0x65;
        packet[81] = 0x63;
        packet[82] = 0x74;
        packet[83] = 0x69;
        packet[84] = 0x6f;
        packet[85] = 0x6e;
        packet[86] = 0x3a;
        packet[87] = 0x20;
        packet[88] = 0x6b;
        packet[89] = 0x65;
        packet[90] = 0x65;
        packet[91] = 0x70;
        packet[92] = 0x2d;
        packet[93] = 0x61;
        packet[94] = 0x6c;
        packet[95] = 0x69;
        packet[96] = 0x76;
        packet[97] = 0x65;
        packet[98] = 0x0d;
        packet[99] = 0x0a;

        // /r/n
        packet[100] = 0x0d;
        packet[101] = 0x0a;
        return packet;
    }

}
