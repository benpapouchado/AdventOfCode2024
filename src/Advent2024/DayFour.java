package Advent2024;

import java.io.IOException;
import java.util.List;

public class DayFour extends Read{

    public static boolean exist(char[][] board, String word, int x, int y, int index, int dx, int dy) {
        if (index == word.length()) return true;

        if (x < 0 || x >= board.length || y < 0 || y >= board[0].length || board[x][y] != word.charAt(index)) {
            return false;
        }

        char temp = board[x][y];
        board[x][y] = '*';

        boolean present = exist(board, word, x + dx, y + dy, index + 1, dx, dy);
        board[x][y] = temp;
        return present;
    }

    public static int find(char[][] board, String word) {
        int count = 0;

        int[][] dirs = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1},
                {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                for (int[] directions : dirs) {
                    if (exist(board, word, i, j, 0, directions[0], directions[1])) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static boolean findMAS(char[][] board, int x, int y) {
        if (x + 2 >= board.length || y + 2 >= board[0].length) {
            return false;
        }

        char topLeft = board[x][y];
        char topRight = board[x][y + 2];
        char center = board[x + 1][y + 1];
        char bottomLeft = board[x + 2][y];
        char bottomRight = board[x + 2][y + 2];

        if (center == 'A') {
            return (topLeft == 'M' && bottomRight == 'S' && topRight == 'M' && bottomLeft == 'S') ||
                    (topLeft == 'S' && bottomRight == 'M' && topRight == 'M' && bottomLeft == 'S') ||
                    (topLeft == 'S' && bottomRight == 'M' && topRight == 'S' && bottomLeft == 'M') ||
                    (topLeft == 'M' && bottomRight == 'S' && topRight == 'S' && bottomLeft == 'M');

        }
        return false;
    }

    public static int countMAS(char[][] board) {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (findMAS(board, i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = read("");
        char[][] grid = convertGrid(lines);
        System.out.println(countMAS(grid));

    }
}
