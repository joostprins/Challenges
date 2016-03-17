package challenge5.protocol;

import challenge5.client.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RoutingProtocol implements IRoutingProtocol {
    private LinkLayer linkLayer;
    private ConcurrentHashMap<Integer, BasicRoute> forwardingTable = new ConcurrentHashMap<>();
    private ArrayList<Integer> neighbours = new ArrayList<>();
    private DataTable dataTable = new DataTable(6);
    private DataTable updateTable = new DataTable(3);
    private static final int REVERSE_POISON_VALUE = -1;

    private static final boolean ROUTE_POISONING_SPLIT_HORIZON_ENABLED = false; // Currently does not work properly, tests 3 to 5 will fail.

    @Override
    public void init(LinkLayer linkLayer) {
        for (int i = 0; i < dataTable.getNColumns(); i++) {
            dataTable.addRow(new Integer[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE});
        }
        this.linkLayer = linkLayer;

        dataTable.set(this.linkLayer.getOwnAddress() - 1, this.linkLayer.getOwnAddress() - 1, 0);
        addToUpdateTable(this.linkLayer.getOwnAddress(), 0, this.linkLayer.getOwnAddress());
    }


    @Override
    public void tick(Packet[] packets) {
        if (ROUTE_POISONING_SPLIT_HORIZON_ENABLED) {
            checkBrokenLinks(packets);
        }

        for (Packet packet : packets) {
            int neighbour = packet.getSourceAddress();
            int linkCost = this.linkLayer.getLinkCost(neighbour);
            DataTable neighbourUpdateTable = packet.getData();

            checkUpdates(neighbour, linkCost, neighbourUpdateTable);
        }

        findFastestRoutes();

        Packet pkt = new Packet(this.linkLayer.getOwnAddress(), 0, updateTable);
        this.linkLayer.transmit(pkt);
        updateTable = new DataTable(3);
    }

    public void checkUpdates(int neighbour, int linkCost, DataTable updateTable) {
        if (updateTable.getNRows() == 0) {
            return;
        }

        for (int i = 0; i < updateTable.getNRows(); i++) {
            int node = updateTable.get(i, 0);
            int cost = updateTable.get(i, 1) + linkCost;
            int nextHop = updateTable.get(i, 2);

            if (ROUTE_POISONING_SPLIT_HORIZON_ENABLED) {
                if (this.linkLayer.getOwnAddress() != nextHop) {
                    if ((cost - linkCost) == REVERSE_POISON_VALUE) {
                        dataTable.set(node - 1, neighbour - 1, REVERSE_POISON_VALUE);
                        findFastestRoutes();
                        addToUpdateTable(node, REVERSE_POISON_VALUE, forwardingTable.get(node).getNextHop());
                    }

                    if ((dataTable.get(node - 1, neighbour - 1) > cost)) {
                        dataTable.set(node - 1, neighbour - 1, cost);
                        findFastestRoutes();
                        addToUpdateTable(node, cost, forwardingTable.get(node).getNextHop());
                    }
                }
            } else {
                if ((dataTable.get(node - 1, neighbour - 1) > cost)) {
                    dataTable.set(node - 1, neighbour - 1, cost);
                    addToUpdateTable(node, cost, forwardingTable.get(node).getNextHop());
                }
            }
        }
    }

    public void findFastestRoutes() {
        for (int i = 0; i < dataTable.getNRows(); i++) {
            int index = 0;
            int lowestValue = dataTable.getRow(i)[0];
            int lowestIndex = 0;

            for (int value : dataTable.getRow(i)) {
                if (value < lowestValue) {
                    lowestValue = value;
                    lowestIndex = index;
                }
                index++;
            }

            forwardingTable.put(i + 1, new BasicRoute(i + 1, lowestIndex + 1, lowestValue));

        }

    }

    public void addToUpdateTable(int destination, int value, int nextHop) {
        updateTable.addRow(new Integer[]{destination, value, nextHop});
    }

    public void checkBrokenLinks(Packet[] packets) {
        if (neighbours.size() == 0) {
            for (Packet packet : packets) {
                neighbours.add(packet.getSourceAddress());
            }
        } else if (packets.length > neighbours.size()) {
            neighbours.clear();
            for (Packet packet : packets) {
                neighbours.add(packet.getSourceAddress());
            }
        } else if (packets.length < neighbours.size()) {
            for (Packet packet : packets) {
                neighbours.remove(packet.getDestinationAddress());
                reversePoison(neighbours);
            }
        }
    }

    public void reversePoison(List<Integer> nodes) {
        for (Integer node : nodes) {
            for (int i = 0; i < dataTable.getNColumns(); i++) {
                dataTable.set(i, node - 1, REVERSE_POISON_VALUE);
                findFastestRoutes();
                addToUpdateTable(node, REVERSE_POISON_VALUE, forwardingTable.get(node).getNextHop());
            }
        }
    }

    @Override
    public ConcurrentHashMap<Integer, BasicRoute> getForwardingTable() {
        return this.forwardingTable;
    }
}