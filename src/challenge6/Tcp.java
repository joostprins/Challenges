package challenge6;

public class Tcp {
	
	private int[] packet;
	private String message;
	
	public Tcp(int[] packet) {
		this.packet = packet;
		this.message = getString(packet);
	}
	
	public Tcp(String message) {
		this.message = message;
		this.packet = toIntArray(message);
	}
	
	private int[] toIntArray(String toSend) {
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
	
	private String getString(int[] packet) {
		String result = "";
		for (int i : packet) {
		}
		return result;
	}
	
	public void showPacket() {
		for (int i : packet) {
			System.out.println(Integer.toHexString(i));
		}
	}

}
