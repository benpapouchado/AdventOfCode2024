package Advent2024;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayThirteen extends Read {

    public static double[] extract_numbers(String line) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(line);
        double[] array = new double[2];
        int idx = 0;

        while (matcher.find()) {
            array[idx] = Double.parseDouble(matcher.group());
            idx++;
        }
        return array;
    }

    public static List<double[][]> extract_matrices(List<String> lines) {
        List<double[][]> matrices = new ArrayList<>();

        for (int i = 0; i < lines.size(); i += 4) {

            double[][] move_x = new double[][]{extract_numbers(lines.get(i)), extract_numbers(lines.get(i + 1))};
            double[][] move_y = new double[][]{{extract_numbers(lines.get(i + 2))[0]}, {extract_numbers(lines.get(i + 2))[1]}};

            double temp = move_x[0][1];
            move_x[0][1] = move_x[1][0];
            move_x[1][0] = temp;

            if (!matrices.contains(move_x)) {
                matrices.add(move_x);
            }
            if (!matrices.contains(move_y)) {
                matrices.add(move_y);
            }
        }
        return matrices;
    }

    public static double[][] inverse(double[][] matrix) {
        double a = matrix[0][0];
        double b = matrix[0][1];
        double c = matrix[1][0];
        double d = matrix[1][1];

        return new double[][]{
                {d, -b},
                {-c, a}
        };
    }

    public static double determinant(double[][] matrix) {
        double a = matrix[0][0];
        double b = matrix[0][1];
        double c = matrix[1][0];
        double d = matrix[1][1];

        return a * d - b * c;
    }

    public static double[][] calculate_moves(double[][] target, double[][] instructions) {
        double[][] inverse = inverse(instructions);
        double determinant = determinant(instructions);

        return new double[][]{
                {(inverse[0][0] * target[0][0] + inverse[0][1] * target[1][0]) / determinant},
                {(inverse[1][0] * target[0][0] + inverse[1][1] * target[1][0]) / determinant}
        };
    }

    public static boolean moves_are_possible(double[][] moves) {
        for (double[] row : moves) {
            double move = row[0];
            return move % 1 == 0.0;
        }
        return false;
    }

    public static int determine_tokens(List<double[][]> matrices) {
        int tokens = 0;

        for (int i = 0; i < matrices.size(); i += 2) {
            double[][] target = matrices.get(i + 1);
            double[][] instructions = matrices.get(i);

            double[][] calculated_moves = calculate_moves(target, instructions);

            if (moves_are_possible(calculated_moves)) {
                tokens += calculate_tokens(calculated_moves);
            }
        }
        return tokens;
    }

    //part 1 - thank you A level Further maths
    public static double calculate_tokens(double[][] matrix) {
        double button_a = matrix[0][0];
        double button_b = matrix[1][0];

        return 3 * button_a + button_b;
    }

    //part 2
    //had to look for hints here but wowwwww this is simpler
    public static long[] cramers_rule(double[][] target, double[][] instructions){
        long[][] Ax =  new long[][]{
               {(long) target[0][0] + 10000000000000L, (long) instructions[0][1]},
                {(long) target[1][0] + 10000000000000L, (long) instructions[1][1]}
        };

        long[][] Ay = new long[][]{
                {(long) instructions[0][0], (long) target[0][0] + 10000000000000L},
                {(long) instructions[1][0], (long) target[1][0] + 10000000000000L}
        };

        long det_A = (long) determinant(instructions);
        long det_Ax = determinant_as_long(Ax);
        long det_Ay = determinant_as_long(Ay);

        long A = det_Ax/det_A;
        long B = det_Ay/det_A;
        return new long[]{A, B};
    }

    public static long determinant_as_long(long[][] matrix) {
        long a = matrix[0][0];
        long b = matrix[0][1];
        long c = matrix[1][0];
        long d = matrix[1][1];

        return a * d - b * c;
    }

    public static long calculate_tokens_as_long(long[] matrix) {
        long button_a = matrix[0];
        long button_b = matrix[1];

        return 3L * button_a + button_b;
    }

    public static boolean moves_are_possible_as_long(double[][] instructions, long[] moves, double[][] target){
        long button_A = (long) instructions[0][0] * moves[0] + (long) instructions[0][1] * moves[1] - 10000000000000L;
        long button_B = (long) instructions[1][0] * moves[0] + (long) instructions[1][1] * moves[1] - 10000000000000L;

        return button_A == (long) target[0][0] && button_B == (long) target[1][0];
    }

    public static long calculate_moves_as_long(List<double[][]> matrices){
        long tokens = 0;

        for (int i = 0; i < matrices.size(); i += 2) {
            double[][] target = matrices.get(i + 1);
            double[][] instructions = matrices.get(i);

            long[] calculated_moves = cramers_rule(target, instructions);

            if (moves_are_possible_as_long(instructions, calculated_moves, target)) {
                tokens += calculate_tokens_as_long(calculated_moves);
            }
        }
        return tokens;
    }

    public static void main(String[] args) throws IOException {
        List<String> read = read("");

        List<double[][]> lines = extract_matrices(read);

        //part 1
        int tokens = determine_tokens(lines);
        System.out.println(tokens);

        //part2
        long tokens_as_long = calculate_moves_as_long(lines);
        System.out.println(tokens_as_long);

    }
}

