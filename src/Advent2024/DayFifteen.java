package Advent2024;

import java.io.IOException;
import java.util.*;

public class DayFifteen extends Read {

    public static String instructions(List<String> read){
        StringBuilder stringBuilder = new StringBuilder();
        int n = gap(read);

        for(int i = n; i < read.size(); i++){
            stringBuilder.append(read.get(i));
        }
        return stringBuilder.toString();
    }

    public static int gap(List<String> read){
        for (int i = 0; i < read.size(); i++) {
            if(read.get(i).isEmpty()){
                return i;
            }
        }
        return -1;
    }

    public static char[][] grid(List<String> read){
        int n = gap(read);
        char[][] grid = new char[n][read.get(0).length()];

        for (int i = 0; i < n; i++) {
            grid[i] = read.get(i).toCharArray();
        }
        return grid;
    }

    public static int[] start(char[][] grid){
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == '@'){
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    public static void left(char[][] grid, int[] location){
        int x = location[0];
        int y = location[1];
        int spaces = -1;
        for(int i = y; i >= 0; i--){
            if(grid[x][i] == '.'){
                spaces = i;
                break;
            }
            if(grid[x][i] == '#'){
                break;

            }
        }

        if(spaces > 0) {
            location[1]--;
            char temp = grid[x][spaces];
            for (int i = spaces + 1; i <= y; i++) {
               grid[x][i - 1] = grid[x][i];
            }
            grid[x][y] = temp;
        }
    }

    public static void down(char[][] grid, int[] location){
        int x = location[0];
        int y = location[1];
        int spaces = -1;
        for(int i = x; i < grid.length; i++){
            if(grid[i][y] == '.'){
                spaces = i;
                break;
            }
            if(grid[i][y] == '#'){
                break;
            }
        }

        if(spaces > 0) {
            location[0]++;
            char temp = grid[spaces][y];
            for (int i = spaces - 1; i >= x; i--) {
                grid[i + 1][y] = grid[i][y];
            }
            grid[x][y] = temp;
        }
    }

    public static void up(char[][] grid, int[] location){
        int x = location[0];
        int y = location[1];
        int spaces = -1;
        for(int i = x; i >= 0; i--){
            if(grid[i][y] == '.'){
                spaces = i;
                break;
            }
            if(grid[i][y] == '#'){
                break;
            }
        }

        if(spaces > 0) {
            location[0]--;
            char temp = grid[spaces][y];
            for (int i = spaces + 1; i <= x; i++) {
                grid[i - 1][y] = grid[i][y];
            }
            grid[x][y] = temp;
        }
    }

    public static void right(char[][] grid, int[] location){
        int x = location[0];
        int y = location[1];
        int spaces = -1;
        for(int i = y; i < grid[0].length; i++){
            if(grid[x][i] == '.'){
                spaces = i;
                break;
            }
            if(grid[x][i] == '#'){
                break;
            }
        }

        if(spaces > 0) {
            location[1]++;
            char temp = grid[x][spaces];
            for (int i = spaces - 1; i >= y; i--) {
                grid[x][i + 1] = grid[x][i];
            }
            grid[x][y] = temp;
        }
    }

    public static void instruction(char[][] grid, int[] location, char direction){

        switch (direction) {
            case '^':
                up(grid, location);
            break;
            case '>':
                right(grid, location);
            break;
            case 'v':
                down(grid, location);
            break;
            case '<':
                left(grid, location);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    public static int distance(char[][] grid, char box) {
        int sum = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == box) {
                    sum += (i * 100) + j;
                }
            }
        }
        return sum;
    }

    //part 1
    public static int calculate_sum(List<String> lines){
        char[][] grid = grid(lines);
        int[] location = start(grid);
        String instructions = instructions(lines);
        for (int i = 0; i < instructions.length(); i++) {
            instruction(grid, location, instructions.charAt(i));
        }
        for (char[] row : grid) {
            System.out.println();
            for (char i : row) {
                System.out.print(i + " ");
            }
        }
        return distance(grid, 'O');
    }

    public static char[][] reimagine_grid(List<String> read) {
        int n = gap(read);
        int m = read.get(0).length() * 2;
        char[][] grid = new char[n][m];

        for (int i = 0; i < n; i++) {
            char[] row = read.get(i).toCharArray();
            for (int j = 0; j < row.length; j++) {

                if (row[j] == '#') {
                    grid[i][2 * j] = '#';
                    grid[i][(2 * j) + 1] = '#';
                }

                if (row[j] == '@') {
                    grid[i][2 * j] = '@';
                    grid[i][(2 * j) + 1] = '.';
                }

                if (row[j] == 'O') {
                    grid[i][2 * j] = '[';
                    grid[i][(2 * j) + 1] = ']';
                }

                if (row[j] == '.') {
                    grid[i][2 * j] = '.';
                    grid[i][(2 * j) + 1] = '.';
                }
            }
        }
        return grid;
    }

    //left and right work as normal. up and down need to be re-written
    public static List<List<Integer>> bfs_vertical(char[][] grid, int[] location, int plane) {
        Map<Character, Integer> directions = new HashMap<>();

        directions.put('[', 1);
        directions.put(']', -1);

        boolean[][] visited = new boolean[grid.length][grid[0].length];
        List<List<Integer>> island = new ArrayList<>();
        Queue<int[]> queue = new LinkedList<>();

        queue.add(new int[]{location[0] + plane, location[1]});

        while (!queue.isEmpty()) {
            int[] current_location = queue.poll();
            int x = current_location[0];
            int y = current_location[1];

            if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length) continue;

            if (visited[x][y]) {
                continue;
            }
            visited[x][y] = true;

            char bracket = grid[x][y];

            if (bracket == '#') {
                return null;
            }
            if (bracket == '.') {
                continue;
            }

            if (!directions.containsKey(bracket)) {
                continue;
            }

            int dy = y + directions.get(bracket);

            List<Integer> left = List.of(x, Math.min(y, dy));
            List<Integer> right = List.of(x, Math.max(y, dy));

            if (!island.contains(left)) {
                island.add(left);
            }
            if (!island.contains(right)){
                island.add(right);
            }

            int dx = x + plane;

            if (directions.containsKey(bracket)) {

                if (dx >= 0 && dx < grid.length) {
                    if (grid[dx][y] != '#' && !visited[dx][y]) {
                        queue.add(new int[]{dx, y});
                    }
                    if (grid[dx][dy] != '#' && !visited[dx][dy]) {
                        queue.add(new int[]{dx, dy});
                    }
                }
            }
        }
        return island;
    }

    public static void move_island_up(char[][] grid, List<List<Integer>> island, int[] location) {
        island.sort(Comparator.comparingInt(a -> a.get(0)));

        for (List<Integer> isle : island) {
            int x = isle.get(0);
            int y = isle.get(1);

            if (x - 1 < 0 || grid[x - 1][y] == '#') {
                return;
            }
        }

        for (List<Integer> isle : island) {
            int x = isle.get(0);
            int y = isle.get(1);

            char bracket = grid[x][y];
            grid[x][y] = '.';
            grid[x - 1][y] = bracket;
        }

        int dx = location[0];
        int dy = location[1];

        grid[dx][dy] = '.';
        grid[dx - 1][dy] = '@';
        location[0]--;
    }


    public static void move_island_down(char[][] grid, List<List<Integer>> island, int[] location) {
        island.sort((a, b) -> Integer.compare(b.get(0), a.get(0)));

        for (List<Integer> isle : island) {
            int x = isle.get(0);
            int y = isle.get(1);

            if (grid[x + 1][y] == '#') {
                return;
            }
        }

        for (List<Integer> isle : island) {
            int x = isle.get(0);
            int y = isle.get(1);

            char bracket = grid[x][y];
            grid[x][y] = '.';
            grid[x + 1][y] = bracket;
        }

        int dx = location[0];
        int dy = location[1];

        grid[dx][dy] = '.';
        grid[dx + 1][dy] = '@';
        location[0]++;
    }


    public static void instructions_for_islands(char[][] grid, int[] location, char direction) {

        switch (direction) {
            case '^':
                List<List<Integer>> island_up = bfs_vertical(grid, location, -1);
                if (island_up != null) {
                    move_island_up(grid, island_up, location);
                }
                break;
            case '>':
                right(grid, location);
                break;
            case 'v':
                List<List<Integer>> island_down = bfs_vertical(grid, location, 1);
                if (island_down != null) {
                    move_island_down(grid, island_down, location);
                }
                break;
            case '<':
                left(grid, location);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    public static int calculate_islands_sum(List<String> lines){
        char[][] grid = reimagine_grid(lines);
        int[] start = start(grid);
        String instructions = instructions(lines);

        for (int i = 0; i < instructions.length(); i++) {
            instructions_for_islands(grid, start, instructions.charAt(i));
        }

        for (char[] row : grid) {
            System.out.println();
            for (char i : row) {
                System.out.print(i + " ");
            }
        }

        return distance(grid, '[');
    }

    public static void main(String[] args) throws IOException {
        List<String> read = read("/Users/benjaminpapouchado/Documents/Projects/src/input.txt");

        //part 1
        int distance_sum = calculate_sum(read);
        System.out.println(distance_sum);

        //part 2
        int islands_distance_sum = calculate_islands_sum(read);
        System.out.println(islands_distance_sum);

    }
}
