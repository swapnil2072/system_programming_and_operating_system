import java.util.ArrayList;
import java.util.Arrays;
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

        for (int page : referenceString) {
            if (!frameList.contains(page)) {
                if (frameList.size() == frameCount) {
                    int indexToRemove = findOptimalIndex(referenceString, frameList);
                    frameList.set(indexToRemove, page);
                } else {
                    frameList.add(page);
                }
                pageFaults++;
            }
        }

        System.out.println("Optimal Page Replacement Algorithm");
        System.out.println("Page Faults: " + pageFaults);
    }

    private static int findOptimalIndex(int[] referenceString, ArrayList<Integer> frameList) {
        int farthest = -1;
        int index = -1;

        for (int i = 0; i < frameList.size(); i++) {
            int page = frameList.get(i);
            int farthestIndex = Arrays.asList(referenceString).indexOf(page);
            if (farthestIndex == -1) {
                return i;
            }
            if (farthestIndex > farthest) {
                farthest = farthestIndex;
                index = i;
            }
        }

        return index;
    }
}
