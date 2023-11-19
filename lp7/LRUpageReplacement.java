import java.util.*;

public class LRUpageReplacement {
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

        LinkedList<Integer> frameList = new LinkedList<>();
        int pageFaults = 0;
        int hits = 0;

        for (int page : referenceString) {
            if (!frameList.contains(page)) {
                if (frameList.size() == frameCount) {
                    frameList.removeFirst();
                }
                frameList.add(page);
                pageFaults++;

                // Print frame content after each page fault
                System.out.print("Frame Content: ");
                for (int frame : frameList) {
                    System.out.print(frame + " ");
                }
                System.out.println();
            } else {
                frameList.removeFirstOccurrence(page);
                frameList.add(page);
                hits++;
            }
        }

        System.out.println("\nLRU Page Replacement Algorithm");
        System.out.println("Page Faults: " + pageFaults);
        System.out.println("Hits: " + hits);
    }
}
