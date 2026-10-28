/**
 * Application tracking network restoration status and statistics.
 */
public class NetworkRestoration {
    private final int totalNodes;
    private final Partition partition;
    private int processedLinks;
    private int maxClusterSize;
    private long totalConnectedPairs;
    private int firstFullyConnected;

    public NetworkRestoration(int n, Partition partition) {
        this.totalNodes = n;
        this.partition = partition;
        this.processedLinks = 0;
        this.maxClusterSize = (n > 0) ? 1 : 0;
        this.totalConnectedPairs = 0;
        
        // Si n == 1, le réseau est déjà entièrement connecté à l'étape 0
        if (n == 1) {
            this.firstFullyConnected = 0;
        } else {
            this.firstFullyConnected = -1;
        }
    }

    public boolean restoreLink(int u, int v) {
        processedLinks++;

        int rootU = partition.find(u);
        int rootV = partition.find(v);

        // Si déjà connectés, les statistiques ne changent pas
        if (rootU == rootV) {
            return false;
        }

        long sizeU = partition.size(u);
        long sizeV = partition.size(v);

        // Mettre à jour le nombre de paires communicantes :
        // Les nouvelles paires formées correspondent au produit des tailles des deux groupes fussionnés
        totalConnectedPairs += (sizeU * sizeV);

        // Exécuter l'union dans le TDA Partition
        partition.union(u, v);

        // Mettre à jour la taille du plus grand groupe
        int newSize = partition.size(u);
        if (newSize > maxClusterSize) {
            maxClusterSize = newSize;
        }

        // Vérifier si le réseau est entièrement connecté pour la première fois
        if (firstFullyConnected == -1 && partition.count() == 1) {
            firstFullyConnected = processedLinks;
        }

        return true;
    }

    public int numberOfClusters() {
        return partition.count();
    }

    public int largestClusterSize() {
        return maxClusterSize;
    }

    public long connectedPairs() {
        return totalConnectedPairs;
    }

    public int firstFullyConnectedStep() {
        return firstFullyConnected;
    }

    public int linksProcessed() {
        return processedLinks;
    }
}