/** Singly-linked-list implementation of Partition ADT
 * Complete every TODO
 * Replace each throw new UnsupportedOperationException("TODO: ...") with your implementation.
 * The inner classes below are a SUGGESTED starting point, not a prescribed private layout. 
 * Use any helper methods you need,
 * but follow the method specifications and the handout’s time and space requirements.
 * @author Bo Sun
 */
public class ListPartition implements Partition {
    private Node[] nodes;
    private long visits;
    private int clusterCount;
    private static class Node {
        int element;
        Node next;
        Cluster owner;
        Node(int element) { this.element = element; }
    }

    private static class Cluster {
        int id;
        int size;
        Node head;
        Node tail;
        Cluster(int id) { this.id = id; }
    }

    // TODO: choose the private state needed by your design.

    public ListPartition(int n) {
        visits = 0;
        nodes = new Node[n];
        clusterCount = 0;
        for (int i = 0; i<n; i++) {
            Node node = new Node(i);
            nodes[i] = node;
            Cluster cluster = new Cluster(i);
            cluster.size = 1;
            cluster.head = node;
            cluster.tail = node;
            node.owner = cluster;
            node.next = null;
            clusterCount++;
        }
    }

    @Override
    public int find(int x) {
        return nodes[x].owner.id;
    }

    @Override
public boolean union(int x, int y) {
    int rootX = find(x);
    int rootY = find(y);
    if (rootX == rootY) {
        return false;
    }

    Cluster cX = nodes[x].owner;
    Cluster cY = nodes[y].owner;
    
    Cluster survivor, absorbed;
    if (cX.size > cY.size) {
        survivor = cX;
        absorbed = cY;
    } else if (cX.size < cY.size) {
        survivor = cY;
        absorbed = cX;
    } else {
        if (cX.id < cY.id) {
            survivor = cX;
            absorbed = cY;
        } else {
            survivor = cY;
            absorbed = cX;
        }
    }

    // Parcourir le groupe absorbé pour mettre à jour les 'owner' et compter les visites
    Node current = absorbed.head;
    while (current != null) {
        current.owner = survivor; // Mise à jour cruciale du propriétaire !
        current = current.next;
        visits++;
    }

    // Concaténer la liste chaînée de l'absorbé à la fin du survivant
    survivor.tail.next = absorbed.head;
    survivor.tail = absorbed.tail; // Le nouveau tail devient le tail de l'absorbé

    // Mettre à jour la taille et le nombre de groupes
    survivor.size += absorbed.size;
    clusterCount--;

    return true;
}
    @Override
    public int size(int x) {
        return nodes[x].owner.size;
    }

    @Override
    public int count() {
        return clusterCount;
    }

    @Override
    public long elementVisits() {
        return visits;
    }
}
