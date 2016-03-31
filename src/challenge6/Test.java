package challenge6;

public class Test {

	public Test() {
		int[] test = toIntArray("GET / HTTP/1.1\r\n");
		for (int i : test) {
			System.out.println(Integer.toHexString(i));
		}
		
	}
	
	public int[] toIntArray(String toSend) {
		byte[] bytes = toSend.getBytes();
		Byte[] toRead = new Byte[bytes.length];
		for (int i = 0; i < bytes.length; i++) {
			toRead[i] = bytes[i];
		}
		int[] result = new int[bytes.length];
		for (int i = 0; i < bytes.length; i++) {
			result[i] = toRead[i].intValue();
		}
		return result;
	}
	
	public static void main(String[] args) {
		Tcp tcp = new Tcp("896472986");
		tcp.showPacket();
		
	}
}
