package Advent2024;

import java.io.IOException;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayEleven extends Read {

    public static List<Long> blink_once(List<Long> stones) {
        List<Long> next = new ArrayList<>();

        for (long stone : stones) {
            String num_str = String.valueOf(stone);
            if (stone == 0) {
                next.add(1L);
            } else if (num_str.length() % 2 == 0 && stone > 0) {
                long num1 = Integer.parseInt(num_str.substring(0, num_str.length() / 2));
                long num2 = Integer.parseInt(num_str.substring(num_str.length() / 2));
                next.add(num1);
                next.add(num2);
            } else {
                next.add(stone * 2024);
            }
        }
        return next;
    }

    //causes out of heap memory exception
    public static int blink_n_times(int n, List<Long> stones) {
        List<Long> current = new ArrayList<>(stones);
        for (int i = 0; i < n; i++) {
            current = blink_once(current);
        }
        return current.size();
    }

    public static Map<Long, Long> fill_map(List<String> line) {
        Map<Long, Long> tracker = new HashMap<>();
        List<Long> stones = convertStringArrayToLongList(line.get(0), "\\s+");
        for (long stone : stones) {
            tracker.put(stone, tracker.getOrDefault(stone, 0L) + 1);
        }
        return tracker;
    }

    public static Map<Long, Long> blink_once(Map<Long, Long> stones){
        Map<Long, Long> tracker = new HashMap<>();
        for(Map.Entry<Long, Long> track : stones.entrySet()){

            long key = track.getKey();
            long val = track.getValue();
            String num_str = String.valueOf(key);

            if(key == 0){
                tracker.put(1L, val + tracker.getOrDefault(1L, 0L));
            }

            else if(num_str.length() % 2 == 0){
                int mid = num_str.length() / 2;
                long num1 = Long.parseLong(num_str.substring(0, mid));
                long num2 = Long.parseLong(num_str.substring(mid));

                tracker.put(num1, val + tracker.getOrDefault(num1, 0L));
                tracker.put(num2, val + tracker.getOrDefault(num2, 0L));
            } else {
                long new_key = key * 2024;
                tracker.put(new_key, val + tracker.getOrDefault(new_key, 0L));
            }
        }
        return tracker;
    }

    public static long blink_n_times(Map<Long, Long> tracker, int n){
        Map<Long, Long> stones = new HashMap<>(tracker);
        for (int i = 0; i < n; i++) {
            stones = blink_once(stones);
        }

        return stones.values().stream().reduce(0L, Long::sum);
    }

    public static void main(String[] args) throws IOException {
        List<String> read = read("");
        List<Long> stones = convertStringArrayToLongList(read.get(0), "\\s+");
        Map<Long, Long> tracker = fill_map(read);

        //part 1
        System.out.println(blink_n_times(tracker, 25));

        //part2
        System.out.println(blink_n_times(tracker, 75));
    }
}
