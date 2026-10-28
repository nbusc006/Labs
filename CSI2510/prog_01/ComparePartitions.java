import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Locale;

/**
 * Provided program; No algorithm needs to be added here
 * Runs the same link sequences with both implementations and checks their results.
 * Use: java ComparePartitions comparison.csv
 * Times include only the link-restoration loop, excluding setup and output.
 * @author Bo Sun
 */
public class ComparePartitions {
    // Powers of two are required by the balanced link pattern below.
    private static final int[] SIZES = {512, 1024, 2048, 4096, 8192};
    // Warm-up runs let Java optimize frequently used code before the measured trials.
    private static final int WARMUPS = 2;
    private static final int TRIALS = 5;
    private static volatile long sink; // Make the computed results observable.

    // Stores the elapsed time, visit count, and final network statistics from one run.
    private static class Run {
        long nanoseconds;
        long visits;
        int clusters;
        int largest;
        long pairs;
        int firstFullStep;
        int processed;
    }

    // Stores one run's results (checked to agree across trials) and the median time.
    private static class Summary {
        Run result;
        long medianNanoseconds;
    }

    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.err.println("Usage: java ComparePartitions [output.csv]");
            return;
        }
        if (args.length == 1) {
            // Explicit UTF-8 file output; timing excludes opening/writing the file.
            try (PrintStream out = new PrintStream(
                    new FileOutputStream(args[0]), false, "UTF-8")) {
                runComparisons(out);
                if (out.checkError()) {
                    throw new IOException("Could not write all comparison results");
                }
            }
        } else {
            runComparisons(System.out);
        }
    }

    private static void runComparisons(PrintStream out) {
        // Each CSV row describes one implementation, network size, and link pattern.
        out.println("pattern,n,links,implementation,clusters,largest,connectedPairs,"
                + "firstFullyConnectedStep,elementVisits,medianMilliseconds");
        for (String pattern : new String[] {"growing", "balanced"}) {
            for (int n : SIZES) {
                // Both implementations receive exactly the same links in the same order.
                int[][] links = pattern.equals("growing") ? growing(n) : balanced(n);
                Summary array = measure("array", n, links);
                Summary list = measure("list", n, links);
                checkSameStatistics(array.result, list.result);
                print(out, pattern, n, links.length, "array", array);
                print(out, pattern, n, links.length, "list", list);
            }
        }
    }

    // Add one new element at a time to the cluster containing 0.
    private static int[][] growing(int n) {
        int[][] links = new int[n - 1][2];
        for (int v = 1; v < n; v++) {
            links[v - 1][0] = 0;
            links[v - 1][1] = v;
        }
        return links;
    }

    // Merge equal-sized clusters in rounds; n must be a power of two.
    private static int[][] balanced(int n) {
        int[][] links = new int[n - 1][2];
        int k = 0;
        // Each round merges adjacent clusters of size 1, then 2, then 4, and so on.
        for (int size = 1; size < n; size *= 2) {
            for (int first = 0; first < n; first += 2 * size) {
                links[k][0] = first;
                links[k][1] = first + size;
                k++;
            }
        }
        if (k != links.length) {
            throw new IllegalStateException("Unexpected number of generated links");
        }
        return links;
    }

    private static Summary measure(String implementation, int n, int[][] links) {
        // Check warm-up results, but do not include their times in the reported median.
        for (int i = 0; i < WARMUPS; i++) {
            checkExpected(run(implementation, n, links), n, links.length);
        }
        long[] times = new long[TRIALS];
        Summary summary = new Summary();
        for (int i = 0; i < TRIALS; i++) {
            Run result = run(implementation, n, links);
            checkExpected(result, n, links.length);
            // Repeating the same work should give the same statistics and visit count.
            if (summary.result != null) {
                checkSameStatistics(summary.result, result);
                if (summary.result.visits != result.visits) {
                    throw new IllegalStateException("Visit count changed between identical runs");
                }
            }
            summary.result = result;
            times[i] = result.nanoseconds;
        }
        // The middle of the five sorted times reduces the effect of unusually slow/fast runs.
        Arrays.sort(times);
        summary.medianNanoseconds = times[TRIALS / 2];
        return summary;
    }

    private static Run run(String implementation, int n, int[][] links) {
        // A fresh partition and application are created BEFORE timing begins.
        Partition partition = implementation.equals("array")
                ? new ArrayPartition(n) : new ListPartition(n);
        NetworkRestoration network = new NetworkRestoration(n, partition);
        // Start to record the time
        long start = System.nanoTime();
        for (int[] link : links) {
            network.restoreLink(link[0], link[1]);
        }
        long elapsed = System.nanoTime() - start;
        // No output or result checking occurs in the measured region.
        Run result = new Run();
        result.nanoseconds = elapsed;
        result.visits = partition.elementVisits();
        result.clusters = network.numberOfClusters();
        result.largest = network.largestClusterSize();
        result.pairs = network.connectedPairs();
        result.firstFullStep = network.firstFullyConnectedStep();
        result.processed = network.linksProcessed();
        sink = result.visits + result.pairs + result.clusters + result.largest
                + result.firstFullStep + result.processed;
        return result;
    }

    private static void checkExpected(Run r, int n, int m) {
        long expectedPairs = ((long) n * (n - 1)) / 2;
        if (r.clusters != 1 || r.largest != n || r.pairs != expectedPairs
                || r.firstFullStep != n - 1 || r.processed != m) {
            throw new IllegalStateException("Incorrect final network statistics for n=" + n);
        }
    }

    private static void checkSameStatistics(Run a, Run b) {
        // Network results must agree; timings and visit counts may differ by implementation.
        if (a.clusters != b.clusters || a.largest != b.largest || a.pairs != b.pairs
                || a.firstFullStep != b.firstFullStep || a.processed != b.processed) {
            throw new IllegalStateException("Application results disagree");
        }
    }

    private static void print(PrintStream out, String pattern, int n, int m, String implementation, Summary s) {
        Run r = s.result;
        // Convert nanoseconds to milliseconds; Locale.ROOT keeps the decimal point consistent.
        out.printf(Locale.ROOT, "%s,%d,%d,%s,%d,%d,%d,%d,%d,%.6f%n",
                pattern, n, m, implementation, r.clusters, r.largest, r.pairs,
                r.firstFullStep, r.visits, s.medianNanoseconds / 1_000_000.0);
    }
}
