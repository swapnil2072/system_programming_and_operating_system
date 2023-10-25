public class OptimalPageReplacement {
    public static void main(String[] args) {
        int[] referenceString = {1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5};
        int frameCount = 3;

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
