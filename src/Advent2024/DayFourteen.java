package Advent2024;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayFourteen extends Read {

    public static int[] extract_position(String line) {
        int[] position_and_velocity = new int[4];

        Pattern pattern = Pattern.compile("-?\\d+");
        Matcher matcher = pattern.matcher(line);

        int idx = 0;

        while (matcher.find()) {
            position_and_velocity[idx] = Integer.parseInt(matcher.group());
            idx++;
        }
        return position_and_velocity;
    }

    public static List<int[]> extract_positions(List<String> lines) {
        List<int[]> positions_and_velocities = new ArrayList<>();
        for (String line : lines) {
            int[] position_and_velocity = extract_position(line);
            positions_and_velocities.add(position_and_velocity);
        }
        return positions_and_velocities;
    }

    public static void move_robot(int[] pos_vel, int height, int width, int n) {
        int y = pos_vel[1];
        int x = pos_vel[0];

        int dy = pos_vel[3];
        int dx = pos_vel[2];

        x += dx * n;
        y += dy * n;

        //Hack I learned from leetcode practice. no loops here
        x = ((x % width) + width) % width;
        y = ((y % height) + height) % height;

        pos_vel[1] = y;
        pos_vel[0] = x;
    }

    public static boolean within_quadrant(int x0, int x1, int y0, int y1, int[] pos) {
        int x = pos[0];
        int y = pos[1];

        return (x0 <= x && x <= x1) && (y0 <= y && y <= y1);
    }

    public static int quadrant_counts(int height, int width, int[] pos) {
        int x = pos[0];
        int y = pos[1];

        int x_mid = (int) Math.rint((width - 1) / 2.);
        int y_mid = (int) Math.rint((height - 1) / 2.);

        if (x == x_mid || y == y_mid) {
            return 0;
        } else {

            int first = within_quadrant(0, x_mid, 0, y_mid, pos) ? 1 : 0;
            int second = within_quadrant(x_mid, width, 0, y_mid, pos) ? 2 : 0;
            int third = within_quadrant(0, x_mid, y_mid, height, pos) ? 3 : 0;
            int fourth = within_quadrant(x_mid, width, y_mid, height, pos) ? 4 : 0;

            return first + second + third + fourth;
        }
    }

    public static int product_of_quadrant_counts(int height, int width, List<int[]> rows) {
        int[] c = new int[4];
        for (int[] row : rows) {
            int i = quadrant_counts(height, width, row);
            if (i > 0) {
                c[i - 1]++;
            }
        }
        int product = 1;
        for (int x : c) {
            product *= x;
        }
        return product;
    }

    //part 1
    public static int calculate_safety_factor(List<int[]> positions_and_velocities, int height, int width, int n) {
        for (int[] row : positions_and_velocities) {
            move_robot(row, height, width, n);
        }
        return product_of_quadrant_counts(height, width, positions_and_velocities);
    }

    public static void print_robots(List<int[]> positions_and_velocities, int height, int width) {
        String[][] grid = new String[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = ".";
            }
        }

        for (int[] row : positions_and_velocities) {
            int i = row[1];
            int j = row[0];
            grid[i][j] = "#";
        }

        for (String[] row : grid) {
            System.out.println();
            for (String i : row) {
                System.out.print(i + "");
            }
        }
    }

    public static Map<Integer, List<int[]>> row_counts(List<int[]> positions_and_velocities) {
        Map<Integer, List<int[]>> row_count = new HashMap<>();
        for (int[] pos : positions_and_velocities) {
            List<int[]> row = row_count.getOrDefault(pos[1], new ArrayList<>());
            row.add(pos);
            row_count.put(pos[1], row);
        }
        return row_count;
    }

    public static int longest_consecutive_run(List<int[]> row) {
        row.sort(Comparator.comparingInt(a -> a[0]));

        int longest = 1;
        int current = 1;

        for (int i = 1; i < row.size(); i++) {
            if (row.get(i)[0] == row.get(i - 1)[0] + 1) {
                current++;
                longest = Math.max(longest, current);
            } else {
                current = 1;
            }
        }
        return longest;
    }

    public static int scan_for_consecutive_robots(Map<Integer, List<int[]>> row_count) {
        int max = 0;
        for (List<int[]> row : row_count.values()) {
            if (row.size() < 2) continue;
            max = Math.max(max, longest_consecutive_run(row));
        }
        return max;
    }

    //part 2
    public static int count_seconds(List<int[]> positions_and_velocities, int height, int width) {
        int n = 0;

        Map<Integer, List<int[]>> row_count;
        do {
            for (int[] row : positions_and_velocities) {
                move_robot(row, height, width, 1);
            }
            row_count = row_counts(positions_and_velocities);
            n++;
        } while (scan_for_consecutive_robots(row_count) < 12);

        return n;
    }

    public static void main(String[] args) throws IOException {
        List<String> read = read("/Users/benjaminpapouchado/Documents/Projects/src/input.txt");

        List<int[]> positions_and_velocities = extract_positions(read);
        int height = 103;
        int width = 101;

        //part1
        int safety_factor = calculate_safety_factor(positions_and_velocities, height, width, 100);
        System.out.println(safety_factor);

        //part2
        int seconds = count_seconds(positions_and_velocities, height, width);
        System.out.println(seconds);

        //displays christmas tree
        positions_and_velocities = extract_positions(read);
        for (int[] row : positions_and_velocities) {
            move_robot(row, height, width, seconds);
        }
        print_robots(positions_and_velocities, height, width);

    }
}
