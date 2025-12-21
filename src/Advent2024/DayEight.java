package Advent2024;

import java.io.IOException;
import java.util.*;

public class DayEight extends Read {

    public static Map<Character, List<String>> scanBoard(char[][] board){
        Map<Character, List<String>> antennas = new HashMap<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {

                if (board[i][j] != '.') {
                    String location = i + "-" + j;
                    if(antennas.containsKey(board[i][j])){
                        antennas.get(board[i][j]).add(location);
                    } else {
                        List<String> locations = new ArrayList<>();
                        locations.add(location);
                        antennas.put(board[i][j], locations);
                    }
                }
            }
        }
        return antennas;
    }

    public static int[][] calculateAntiNodeLocations(int[] first_location, int[] second_Location){
        int[] anti_node1 = new int[2];
        int[] anti_node2 = new int[2];
        int dx = second_Location[0] - first_location[0];
        int dy = second_Location[1] - first_location[1];

        anti_node1[0] = first_location[0] - dx;
        anti_node1[1] = first_location[1] - dy;

        anti_node2[0] = second_Location[0] + dx;
        anti_node2[1] = second_Location[1] + dy;
        return new int[][]{anti_node1, anti_node2};
    }

    public static int[][] calculateAntiNodeLocations(List<String> locations){
        int[] first_location = convertStringArrayToIntArray(locations.get(0), "-");
        int[] second_location = convertStringArrayToIntArray(locations.get(1), "-");
        return calculateAntiNodeLocations(first_location, second_location);
    }

    public static List<List<String>> generatePairs(List<String> locations) {
        List<List<String>> pairs = new ArrayList<>();

        for (int i = 0; i < locations.size(); i++) {
            for (int j = i + 1; j < locations.size(); j++) {
                List<String> temp = new ArrayList<>();
                temp.add(locations.get(i));
                temp.add(locations.get(j));
                pairs.add(temp);
            }
        }
        return pairs;
    }

    //part 1
    public static int anti_node_count(char[][] board){
        Map<Character, List<String>> antennas = scanBoard(board);

        Set<String> uniqueAntinodes = new HashSet<>();
        int rows = board.length;
        int cols = board[0].length;

        for (Map.Entry<Character, List<String>> entry : antennas.entrySet()) {
            List<List<String>> pairs = generatePairs(entry.getValue());

            for (List<String> pair : pairs) {
                int[][] antinodes = calculateAntiNodeLocations(pair);

                for (int[] node : antinodes) {
                    int r = node[0];
                    int c = node[1];

                    if (r < 0 || r >= rows || c < 0 || c >= cols) continue;

                    uniqueAntinodes.add(r + "," + c);
                }
            }
        }
        return uniqueAntinodes.size();
    }

    //part 2
    private static int gcd(int a, int b) {
        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    public static List<int[]> findAllAntiNodes(int[] first, int[] second, int rows, int cols) {
        int dx = second[1] - first[1];
        int dy = second[0] - first[0];

        int g = gcd(Math.abs(dx), Math.abs(dy));
        dx /= g;
        dy /= g;

        List<int[]> list = new ArrayList<>();

        int r = first[0] - dy;
        int c = first[1] - dx;

        while (r >= 0 && r < rows && c >= 0 && c < cols) {
            list.add(new int[]{r, c});
            r = r - dy;
            c = c - dx;
        }

        r = first[0] + dy;
        c = first[1] + dx;

        while (r >= 0 && r < rows && c >= 0 && c < cols) {
            list.add(new int[]{r, c});
            r = r + dy;
            c = c + dx;
        }

        return list;
    }

    public static List<int[]> findAllAntiNodes(List<String> pair, int rows, int cols) {
        int[] first = convertStringArrayToIntArray(pair.get(0), "-");
        int[] second = convertStringArrayToIntArray(pair.get(1), "-");
        return findAllAntiNodes(first, second, rows, cols);
    }

    public static int all_anti_nodes_count(char[][] board) {
        Map<Character, List<String>> antennas = scanBoard(board);

        Set<String> uniqueAntinodes = new HashSet<>();
        int rows = board.length;
        int cols = board[0].length;

        for (Map.Entry<Character, List<String>> entry : antennas.entrySet()) {
            List<String> locations = entry.getValue();

            if (locations.size() >= 2) {
                uniqueAntinodes.addAll(locations);
            }

            List<List<String>> pairs = generatePairs(locations);
            for (List<String> pair : pairs) {

                List<int[]> antinodes = findAllAntiNodes(pair, rows, cols);

                for (int[] node : antinodes) {
                    int r = node[0];
                    int c = node[1];

                    if (r < 0 || r >= rows || c < 0 || c >= cols) continue;
                    uniqueAntinodes.add(r + "-" + c);
                }
            }
        }

        return uniqueAntinodes.size();
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = read("");
        char[][] board = convertGrid(lines);
        System.out.println(anti_node_count(board));
        System.out.println(all_anti_nodes_count(board));

    }
}
