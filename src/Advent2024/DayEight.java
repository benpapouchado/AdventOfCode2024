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
        int[] first_location = convertStringArrayToIntArray(locations.get(0),"-");
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

    public static List<int[]> findAllAntiNodes(int[] first_location, int[] second_Location){
        int dx = second_Location[0] - first_location[0];
        int dy = second_Location[1] - first_location[1];



        List<int[]> list_of_nodes = new ArrayList<>();
        return list_of_nodes;
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = read("");

        char[][] board = convertGrid(lines);

        System.out.println(anti_node_count(board));

    }
}
