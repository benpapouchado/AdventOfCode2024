package Advent2024;

import java.io.IOException;
import java.util.*;

public class DaySix extends Read {

    public static int[] start(char[][] board){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                switch (board[i][j]) {
                    case '^':
                    case '>':
                    case 'v':
                    case '<':
                        return new int[]{i, j};
                }
            }
        }
        return new int[]{-1,-1};
    }

    public static int rotate(char pointer, char[] pointers){
        int i;
        for (i = 0; i < pointers.length; i++) {
            if(pointers[i] == pointer){
                i++;
                break;
            }
        }

        if(i >= pointers.length){
            i = 0;
        }
        return i;
    }

    public static int orientation(char pointer, char[] pointers){
        for (int i = 0; i < pointers.length; i++) {
            if(pointers[i] == pointer){
                return i;
            }
        }
        return -1;
    }

    //stack overflow for large input
    public static void stepForwardStackOverflow(char[][] board, int x, int y, int dx, int dy,
            char pointer, int[][] directions,
            char[] pointers, Set<String> visited) {
        int nx = x + dx;
        int ny = y + dy;

        if (nx < 0 || nx >= board.length || ny < 0 || ny >= board[0].length) {
            return;
        }

        if (board[nx][ny] == '#') {
            int newOri = rotate(pointer, pointers);
            int newDx = directions[newOri][1];
            int newDy = directions[newOri][0];
            char newPointer = pointers[newOri];
            stepForwardStackOverflow(board, x, y, newDx, newDy, newPointer, directions, pointers, visited);
            return;
        }

        visited.add(nx + ", " + ny);

        stepForwardStackOverflow(board, nx, ny, dx, dy, pointer, directions, pointers, visited);
    }

    public static int numberOfSteps(char[][] board, char[] pointers, int[][] directions){
        int[] start = start(board);
        int x = start[0];
        int y = start[1];
        char startingPointer = board[x][y];
        int dydx = orientation(startingPointer, pointers);
        Set<String> visited =  new HashSet<>();
        stepForwardStackOverflow(board, x, y, directions[dydx][1], directions[dydx][0], startingPointer, directions,
                pointers, visited);

        return visited.size();
    }

    //part 1 - iterative version with no stack overflow for large input
    public static int stepForward(char[][] board, int x, int y,
                                   char pointer, int[][] directions,
                                   char[] pointers){
        int rows = board.length;
        int cols = board[0].length;

        int orientation = orientation(pointer, pointers);
        int dx = directions[orientation][0];
        int dy = directions[orientation][1];
        board[x][y] = 'X';

        int visited = 1;

        while(true) {
            int nx = x + dx;
            int ny = y + dy;

            if (nx < 0 || nx >= rows || ny < 0 || ny >= cols) {
                break;
            }

            if(board[nx][ny] == '#'){
                int rotate = rotate(pointer, pointers);
                int[] newDir = directions[rotate];
                pointer = pointers[rotate];
                dx = newDir[0];
                dy = newDir[1];
                continue;
            }

            if (board[nx][ny] == '.') {
                board[nx][ny] = 'X';
                visited++;
            }

            x = nx;
            y = ny;
        }
        return visited;
    }

    //part 2 checking for loops
    public static int loopChecking(char[][] board, int x, int y,
                                  char pointer, int[][] directions,
                                  char[] pointers){
        int rows = board.length;
        int cols = board[0].length;

        board[x][y] = 'X';
        int guards = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Set<String> stops = new LinkedHashSet<>();
                if (board[i][j] == '#') continue;

                int r = x, c = y;
                char dir = pointer;

                while(true){
                    int ori = orientation(dir, pointers);
                    String state = r + "," + c + "," + ori;

                    if(stops.contains(state)){
                        guards++;
                        break;
                    }
                    stops.add(state);

                    int dx = directions[ori][0];
                    int dy = directions[ori][1];

                    int nx = r + dx;
                    int ny = c + dy;

                    if (nx < 0 || nx >= rows || ny < 0 || ny >= cols) {
                        break;
                    }

                    if(board[nx][ny] == '#' || (i == nx && j == ny)){
                        int rotate = rotate(dir, pointers);
                        dir = pointers[rotate];
                    } else {
                        r = nx;
                        c = ny;
                    }
                }
            }
        }
        return guards;
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = read("");
        char[][] board = convertGrid(lines);
        char[] pointers = {'^', '>', 'v', '<'};
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}, };
        int[] start = start(board);
        int x = start[0];
        int y = start[1];
        char startingPointer = board[x][y];

        System.out.println(stepForward(board, x, y, startingPointer,  directions, pointers));
        System.out.println(loopChecking(board, x, y, startingPointer,  directions, pointers));

    }
}
