import java.util.*;

public class LRUpageReplacement {
    public static void main(String[] args) {
        int[] referenceString = {1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5};
        int frameCount = 3;

        LinkedList<Integer> frameList = new LinkedList<>();
        int pageFaults = 0;

        for (int page : referenceString) {
            if (!frameList.contains(page)) {
                if (frameList.size() == frameCount) {
                    frameList.removeFirst();
                }
                frameList.add(page);
                pageFaults++;
            } else {
                frameList.removeFirstOccurrence(page);
                frameList.add(page);
            }
        }

        System.out.println("LRU Page Replacement Algorithm");
        System.out.println("Page Faults: " + pageFaults);
    }
}
