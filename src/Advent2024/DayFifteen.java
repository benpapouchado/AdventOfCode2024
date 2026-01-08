package Advent2024;

import java.io.IOException;
import java.util.List;

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
            default: throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    public static int distance(char[][] grid){
        int sum = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == 'O'){
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

        return distance(grid);
    }

    public static void main(String[] args) throws IOException {
        List<String> read = read("/Users/benjaminpapouchado/Documents/Projects/src/input.txt");

        //part 1
        int distance_sum = calculate_sum(read);
        System.out.println(distance_sum);
    }
}
