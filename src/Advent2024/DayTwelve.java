package Advent2024;

import java.io.IOException;
import java.util.*;

public class DayTwelve extends Read {

    public static char[][] convert_board(List<String> list) {
        char[][] board = new char[list.size()][list.get(0).length()];

        for (int i = 0; i < list.size(); i++) {
            board[i] = list.get(i).toCharArray();
        }
        return board;
    }

    public static int[] perimeter_and_area(char[][] garden, int r, int c) {
        int n = garden.length;
        int m = garden[0].length;
        boolean[][] visited = new boolean[n][m];
        int[] directions = {-1, 0, 1, 0, -1};
        Queue<int[]> queue = new LinkedList<>();
        int perimeter = 0;
        int area = 1;

        queue.add(new int[]{r, c});
        visited[r][c] = true;

        while (!queue.isEmpty()) {
            int[] plant = queue.poll();
            int x = plant[0];
            int y = plant[1];

            for (int i = 0; i < directions.length - 1; i++) {

                int dr = x + directions[i];
                int dc = y + directions[i + 1];

                if (dr < 0 || dc < 0 || dr >= n || dc >= m) {
                    perimeter++;
                    continue;
                }

                if (garden[dr][dc] != garden[x][y]) {
                    perimeter++;
                }

                if (visited[dr][dc]) continue;

                if (garden[dr][dc] == garden[x][y]) {
                    visited[dr][dc] = true;
                    area++;
                    queue.add(new int[]{dr, dc});
                }
            }
        }

        for (int i = 0; i < garden.length; i++) {
            for (int j = 0; j < garden[0].length; j++) {
                if(visited[i][j]){
                    garden[i][j] = '.';
                }
            }
        }
        return new int[]{area, perimeter};
    }

    //part 1
    public static int multi_source_bfs(char[][] garden) {
        int product_sum = 0;

        for (int i = 0; i < garden.length; i++) {
            for (int j = 0; j < garden[0].length; j++) {
                if(garden[i][j] != '.'){
                    int[] perimeter_and_area = perimeter_and_area(garden, i, j);
                    product_sum += perimeter_and_area[0] * perimeter_and_area[1];
                }
            }
        }
        return product_sum;
    }

    // part 2
    public static int[] sides_and_area(char[][] garden, int r, int c) {
        int n = garden.length;
        int m = garden[0].length;
        boolean[][] visited = new boolean[n][m];
        int[] directions = {-1, 0, 1, 0, -1};
        Queue<int[]> queue = new LinkedList<>();
        int corners = corners(garden, r, c);
        int area = 1;

        queue.add(new int[]{r, c});
        visited[r][c] = true;

        while (!queue.isEmpty()) {
            int[] plant = queue.poll();
            int x = plant[0];
            int y = plant[1];

            for (int i = 0; i < directions.length - 1; i++) {

                int dr = x + directions[i];
                int dc = y + directions[i + 1];

                if (dr < 0 || dc < 0 || dr >= n || dc >= m) {
                    continue;
                }

                if (visited[dr][dc]) continue;

                if (garden[dr][dc] == garden[x][y]) {
                    visited[dr][dc] = true;
                    area++;
                    corners += corners(garden, dr, dc);
                    queue.add(new int[]{dr, dc});
                }
            }
        }

        for (int i = 0; i < garden.length; i++) {
            for (int j = 0; j < garden[0].length; j++) {
                if (visited[i][j]) {
                    garden[i][j] = '.';
                }
            }
        }
        return new int[]{area, corners};
    }

    public static char edge_check(int dr, int dc, int n, int m, char[][] garden){
        return dr < 0 || dc < 0 || dr >= n || dc >= m ? '.' : garden[dr][dc];
    }

    public static int corners(char[][] garden, int i, int j) {
        int n = garden.length;
        int m = garden[0].length;

        int corner = 0;
        int[] directions = {-1, 0, 1, 0, -1};
        int[] rotated_direction = {0, 1, 0, -1, 0};
        int[] diagonal = {-1, 1, 1, -1, -1};

        for (int k = 0; k < 4; k++) {
            int di = i + directions[k];
            int dj = j + directions[k + 1];

            int dii = i + rotated_direction[k];
            int djj = j + rotated_direction[k + 1];

            int diag_i = i + diagonal[k];
            int diag_j = j + diagonal[k + 1];

            char current = garden[i][j];

            char neighbour = edge_check(di, dj, n, m, garden);
            char diagonal_neighbour = edge_check(diag_i, diag_j, n, m, garden);
            char rotated_neighbour = edge_check(dii, djj, n, m, garden);

            if (current == neighbour) {
                if (neighbour != diagonal_neighbour && neighbour == rotated_neighbour) {
                    corner++;
                }
            }

            if (current != neighbour && current != rotated_neighbour) {
                corner++;
            }
        }
        return corner;
    }

    public static int multi_source_bfs2(char[][] garden) {
        int product_sum = 0;

        for (int i = 0; i < garden.length; i++) {
            for (int j = 0; j < garden[0].length; j++) {
                if (garden[i][j] != '.') {
                    int[] sides_and_area = sides_and_area(garden, i, j);
                    product_sum += sides_and_area[0] * sides_and_area[1];
                }
            }
        }
        return product_sum;
    }

    public static void main(String[] args) throws IOException {
        List<String> read = read("");
        char[][] board1 = convert_board(read);
        char[][] board2 = convert_board(read);

        //part 1
        System.out.println(multi_source_bfs(board1));

        //part 2
        System.out.println(multi_source_bfs2(board2));

    }
}
