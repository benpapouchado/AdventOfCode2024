package Advent2024;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

public class DayEleven extends Read {

    public static List<Long> blink_once(List<Long> stones) {
        List<Long> next = new ArrayList<>();

        for(long stone : stones) {
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

    public static int blink_n_times(int n, List<Long> stones){
        List<Long> current = new ArrayList<>(stones);
        for(int i = 0; i < n; i++){
            current = blink_once(current);
        }
        return current.size();
    }

    public static void main(String[] args) throws IOException {
        List<String> read = read("/Users/benjaminpapouchado/Documents/Projects/src/input.txt");
        List<Long> stones = convertStringArrayToLongList(read.get(0), "\\s+");

        //part 1
        System.out.println(blink_n_times(75, stones));
    }
}
