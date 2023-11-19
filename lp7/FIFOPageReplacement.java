import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class FIFOPageReplacement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of page references: ");
        int numReferences = scanner.nextInt();

        int[] referenceString = new int[numReferences];

        System.out.println("Enter the page references:");

        for (int i = 0; i < numReferences; i++) {
            referenceString[i] = scanner.nextInt();
        }

        System.out.print("Enter the number of frames: ");
        int frameCount = scanner.nextInt();

        Queue<Integer> frameQueue = new LinkedList<>();
        int pageFaults = 0;
        int hits = 0;

        for (int page : referenceString) {
            if (!frameQueue.contains(page)) {
                if (frameQueue.size() == frameCount) {
                    frameQueue.poll();
                }
                frameQueue.add(page);
                pageFaults++;
            } else {
                hits++;
            }

            // Print frame content after each page replacement
            System.out.print("Frame Content: ");
            for (int frame : frameQueue) {
                System.out.print(frame + " ");
            }
            System.out.println();
        }

        System.out.println("\nFIFO Page Replacement Algorithm");
        System.out.println("Page Faults: " + pageFaults);
        System.out.println("Hits: " + hits);
    }
}
