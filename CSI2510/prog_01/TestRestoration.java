import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/** Provided testing program. No algorithm needs to be added here. */
public class TestRestoration {
    public static void main(String[] args) throws IOException {
        if (args.length != 2 ||
                !(args[0].equals("array") || args[0].equals("list"))) {
            System.err.println("Usage: java TestRestoration <array|list> <inputfile>");
            return;
        }

        int n;
        int[][] links;
        try (Scanner input = new Scanner(new File(args[1]), "UTF-8")) {
            n = input.nextInt();
            int m = input.nextInt();
            if (n < 1 || m < 0) {
                throw new IllegalArgumentException("Require n >= 1 and m >= 0");
            }
            links = new int[m][2];
            for (int i = 0; i < m; i++) {
                int u = input.nextInt();
                int v = input.nextInt();
                if (u < 0 || u >= n || v < 0 || v >= n) {
                    throw new IllegalArgumentException("Invalid computer ID at link " + (i + 1));
                }
                links[i][0] = u;
                links[i][1] = v;
            }
            if (input.hasNext()) {
                throw new IllegalArgumentException("Unexpected data after the declared links");
            }
        }

        Partition partition = args[0].equals("array")
                ? new ArrayPartition(n) : new ListPartition(n);
        NetworkRestoration network = new NetworkRestoration(n, partition);
        System.out.println("step clusters largest connectedPairs");
        for (int[] link : links) {
            network.restoreLink(link[0], link[1]);
            System.out.println(network.linksProcessed() + " " + network.numberOfClusters()
                    + " " + network.largestClusterSize() + " " + network.connectedPairs());
        }
        System.out.println("First fully connected step: " + network.firstFullyConnectedStep());
        System.out.println("Element visits: " + partition.elementVisits());
    }
}
