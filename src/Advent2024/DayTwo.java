package Advent2024;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DayTwo {

    public static List<List<Integer>> read(String file) throws IOException {
        List<List<Integer>> list = new ArrayList<>();
        FileReader fr = new FileReader(file);
        BufferedReader bf = new BufferedReader(fr);
        String line;
        while((line = bf.readLine()) != null){
            String[] s = line.split(" ");
            List<Integer> temp = new ArrayList<>();

            for(String x : s){
                temp.add(Integer.parseInt(x));
            }
            list.add(new ArrayList<>(temp));
        }
        return list;
    }

    public static boolean checkLine(List<Integer> row){
        Set<Integer> checker = new HashSet<>();
        for (int i = 1; i < row.size(); i++) {
            int diff = row.get(i) - row.get(i - 1);
            if(diff == 0 || Math.abs(diff) > 3){
                return false;
            }
            if(diff > 0){
                checker.add(1);
            } else {
                checker.add(0);
            }
        }
        return checker.size() == 1;
    }

    public static int countLines(List<List<Integer>> row){
        int count = 0;
        for(List<Integer> x : row) {

            if (makeSafe(x)) {
                count++;
            }
        }
        return count;
    }

    public static boolean makeSafe(List<Integer> row) {
        return isSafe(row) || canFixByRemovingOne(row);
    }

    private static boolean isSafe(List<Integer> row) {
        boolean increasing = false, decreasing = false;

        for (int i = 1; i < row.size(); i++) {
            int diff = row.get(i) - row.get(i - 1);

            if (diff == 0 || Math.abs(diff) > 3) return false;

            if (diff > 0) increasing = true;
            else decreasing = true;

            if (increasing && decreasing) return false;
        }
        return true;
    }

    private static boolean canFixByRemovingOne(List<Integer> row) {
        for (int i = 0; i < row.size(); i++) {
            List<Integer> copy = new ArrayList<>(row);
            copy.remove(i);

            if (isSafe(copy)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        List<List<Integer>> read = read("");
        System.out.println(countLines(read));
    }
}
