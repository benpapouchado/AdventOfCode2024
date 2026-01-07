package Advent2024;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    public static int calculate_safety_factor(List<int[]> positions_and_velocities, int height, int width, int n){
        for (int[] row : positions_and_velocities) {
            move_robot(row, height, width, n);
        }

        return product_of_quadrant_counts(height, width, positions_and_velocities);
    }


    public static void main(String[] args) throws IOException {
        List<String> read = read("");

        List<int[]> positions_and_velocities = extract_positions(read);
        int n = 100;
        int height = 103;
        int width = 101;

        int safety_factor = calculate_safety_factor(positions_and_velocities, height, width, n);
        System.out.println(safety_factor);


    }
}
