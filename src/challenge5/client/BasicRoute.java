package challenge5.client;

/**
 * Basic implementation of AbstractRoute.
 * @author Jaco
 * @version 09-03-2015
 */
public class BasicRoute extends AbstractRoute {
	
	private int cost;
	
	public BasicRoute(int destination, int nextHop, int linkCost) {
		super.destination = destination;
		super.nextHop = nextHop;
		this.cost = linkCost;
	}
	
	public int getDestination() {
		return super.destination;
	}
	
	public int getNextHop() {
		return super.nextHop;
	}
	
	public int getLinkCost() {
		return this.cost;
	}
	
	public void setNextHop(int nextHop) {
		super.nextHop = nextHop;
	}
	
	public void setLinkCost(int linkCost) {
		this.cost = linkCost;
	}
}
