package Advent2024;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DayFive extends Read {

    public static List<List<int[]>> splitFiles(List<String> lists) {
        int breakIndex = 0;
        List<int[]> pageNumbers = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i).trim().isEmpty()) {
                breakIndex = i + 1;
                break;
            } else {
                pageNumbers.add(convertStringArrayToIntArray(lists.get(i),"\\|"));


            }
        }

        List<int[]> updates = new ArrayList<>();
        for (int i = breakIndex; i < lists.size(); i++) {
            updates.add(convertStringArrayToIntArray(lists.get(i),"-"));


        }
        List<List<int[]>> list = new ArrayList<>();
        list.add(pageNumbers);
        list.add(updates);

        return list;
    }

    public static boolean pagesInUpdates(int[] updates, int[] pages){
        boolean first = false;
        boolean second = false;

        for(int update : updates){
            if(update == pages[0]){
                first = true;
            }
            if(update == pages[1]){
                second = true;
            }
        }
        return first && second;
    }

    public static List<int[]> getPages(int[] updates, List<int[]> pagesList){
        List<int[]> pages = new ArrayList<>();

        for(int[] page : pagesList){
            if(pagesInUpdates(updates, page)){
                pages.add(page);
            }
        }
        return pages;
    }

    public static int[] pageNumbers(int[] updates, int[] pages){
        int first = Integer.MAX_VALUE;
        int second = Integer.MIN_VALUE;

        for(int i = 0; i < updates.length; i++){
            if(updates[i] == pages[0]){
                first = i;
            }

            if(updates[i] == pages[1]){
                second = i;
            }
        }
        return new int[]{first, second};
    }

    public static boolean pagesAreSafe(int[] updates, int[] pages){
        int[] pageNumbers = pageNumbers(updates, pages);
        int first = pageNumbers[0];
        int second = pageNumbers[1];
        return first >= second;
    }

    public static boolean updatesAreSafe(int[] updates, List<int[]> pagesList){
        boolean safety = true;
        for(int[] page : pagesList){
            if (pagesAreSafe(updates, page)) {
                safety = false;
                break;
            }
        }
        return safety;
    }

    //part1
    public static int middleSum(List<int[]> updates,  List<int[]> pageUpdates){
        int middleSum = 0;
        for(int[] update : updates){
            if(updatesAreSafe(update, getPages(update, pageUpdates))){
                middleSum += update[update.length /2];
            }
        }
        return middleSum;
    }

    //part2
    public static void fixPageSafety(int[] updates, int first, int second){
        if(first > second && first != Integer.MAX_VALUE && second != Integer.MIN_VALUE){
            int temp = updates[first];
            updates[first] = updates[second];
            updates[second] = temp;
        }
    }

    public static void pagesAreSafeOrFix(int[] updates, int[] pages){
        int[] pageNumbers = pageNumbers(updates, pages);
        int first = pageNumbers[0];
        int second = pageNumbers[1];
        if(first > second && first != Integer.MAX_VALUE && second != Integer.MIN_VALUE) {
            fixPageSafety(updates, first, second);
        }

    }

    public static void updatesAreSafeOrFix(int[] updates, List<int[]> pagesList){
        for(int[] page : pagesList){
            if (pagesAreSafe(updates, page)) {
                pagesAreSafeOrFix(updates, page);
            }
        }
    }

    public static int middleSumForUnsafeUpdates(List<int[]> updates,  List<int[]> pageUpdates){
        List<int[]> unsafeUpdates = new ArrayList<>();
        for(int[] update : updates){
            if(!updatesAreSafe(update, getPages(update, pageUpdates))){
                unsafeUpdates.add(update);
            }
        }

        for(int[] update : unsafeUpdates){
            while(!updatesAreSafe(update, getPages(update, pageUpdates))) {
                updatesAreSafeOrFix(update, pageUpdates);
            }
        }
        return middleSum(unsafeUpdates, pageUpdates);
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = read("");
        List<List<int[]>> lists = splitFiles(lines);

        List<int[]> pageUpdates = lists.get(0);
        List<int[]> updates = lists.get(1);

        System.out.println(middleSum(updates, pageUpdates));
        System.out.println(middleSumForUnsafeUpdates(updates, pageUpdates));


    }
}
