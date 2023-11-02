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

        for (int page : referenceString) {
            if (!frameQueue.contains(page)) {
                if (frameQueue.size() == frameCount) {
                    frameQueue.poll();
                }
                frameQueue.add(page);
                pageFaults++;
            }
        }

        System.out.println("FIFO Page Replacement Algorithm");
        System.out.println("Page Faults: " + pageFaults);
    }
}
