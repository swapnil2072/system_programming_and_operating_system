import java.util.ArrayList;
import java.util.Scanner;

public class OptimalPageReplacement {
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

        ArrayList<Integer> frameList = new ArrayList<>();
        int pageFaults = 0;
        int hits = 0;

        for (int i = 0; i < numReferences; i++) {
            int page = referenceString[i];
            if (!frameList.contains(page)) {
                if (frameList.size() == frameCount) {
                    int indexToRemove = findOptimalIndex(referenceString, frameList, i);
                    frameList.set(indexToRemove, page);
                } else {
                    frameList.add(page);
                }
                pageFaults++;
            } else {
                hits++;
            }

            // Print frame content after each page replacement
            System.out.print("Frame Content: ");
            for (int frame : frameList) {
                System.out.print(frame + " ");
            }
            System.out.println();
        }

        System.out.println("Optimal Page Replacement Algorithm");
        System.out.println("Page Faults: " + pageFaults);
        System.out.println("Hits: " + hits);
    }

    private static int findOptimalIndex(int[] referenceString, ArrayList<Integer> frameList, int currentIndex) {
        int indexToRemove = -1;
        int farthest = -1;

        for (int i = 0; i < frameList.size(); i++) {
            int frame = frameList.get(i);
            int farthestIndex = Integer.MAX_VALUE;

            for (int j = currentIndex + 1; j < referenceString.length; j++) {
                if (referenceString[j] == frame) {
                    farthestIndex = j;
                    break;
                }
            }

            if (farthestIndex > farthest) {
                farthest = farthestIndex;
                indexToRemove = i;
            }
        }

        return indexToRemove;
    }
}
