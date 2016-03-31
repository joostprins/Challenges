package challenge7.wlanscanner.src.Location;
import challenge7.wlanscanner.src.Utils.MacRssiPair;
import challenge7.wlanscanner.src.Utils.Position;

/**
 * Interface for your LocationFinder
 * @author Bernd
 *
 */
public interface LocationFinder {
	
	public Position locate(MacRssiPair[] data);
	
}
