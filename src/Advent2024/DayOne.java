package Advent2024;

import java.io.IOException;
import java.util.*;

public class DayOne extends Read {

    public static List<Queue<Integer>> insert_into_heaps(List<String> lines) {
        List<Queue<Integer>> rows = new ArrayList<>();
        Queue<Integer> first = new PriorityQueue<>();
        Queue<Integer> second = new PriorityQueue<>();

        for (String line : lines) {
            String[] row = line.split("\\s+");
            first.add(Integer.parseInt(row[0]));
            second.add(Integer.parseInt(row[1]));
        }
        rows.add(first);
        rows.add(second);
        return rows;
    }

    //part1
    public static int distance(Queue<Integer> first, Queue<Integer> second) {
        int count = 0;
        while (!first.isEmpty() && !second.isEmpty()) {
            count += Math.abs(first.poll() - second.poll());
        }
        return count;
    }

    //part 2
    public static int track_counts(List<String> lines) {
        int count = 0;
        Map<Integer, Integer> map = new HashMap<>();

        List<Integer> first = new ArrayList<>();

        for (String line : lines) {
            String[] row = line.split("\\s+");
            first.add(Integer.parseInt(row[0]));
            int num = Integer.parseInt(row[1]);
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        for(int num : first){
            if(map.containsKey(num)){
                count += num * map.get(num);
            }
        }
        return count;
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = read("");
        List<Queue<Integer>> sl = insert_into_heaps(lines);

        //part 1
        System.out.println(distance(sl.get(0), sl.get(1)));

        //part 2
        System.out.println(track_counts(lines));

    }
}
