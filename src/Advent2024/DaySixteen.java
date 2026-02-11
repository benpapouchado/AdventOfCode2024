package Advent2024;

import java.io.IOException;
import java.util.*;

public class DaySixteen extends Read {

    public static char[][] grid(List<String> read) {
        int n = read.size();
        int m = read.get(0).length();
        char[][] grid = new char[n][m];

        for (int i = 0; i < n; i++) {
            grid[i] = read.get(i).toCharArray();
        }
        return grid;
    }

    //not shortest path
    public static boolean path(char[][] grid, int sx, int sy, int[] end, List<List<Integer>> path) {
        boolean shift;
        int ex = end[0];
        int ey = end[1];

        List<Integer> location = List.of(sx, sy);

        if (sx < 0 || sy < 0 || sx >= grid.length || sy >= grid[0].length) {
            return false;
        }

        if (sx == ex && sy == ey) {
            path.add(location);
            return true;
        }

        if (grid[sx][sy] != '.') {
            return false;
        }

        if (path.contains(location)) {
            return false;
        }

        path.add(location);

        shift = path(grid, sx + 1, sy, end, path) ||
                path(grid, sx - 1, sy, end, path) ||
                path(grid, sx, sy + 1, end, path) ||
                path(grid, sx, sy - 1, end, path);

        if (!shift) {
            path.remove(path.size() - 1);
        }
        return shift;
    }

    //shortest path but not fewest turns
    public static List<int[]> maze(char[][] board, int[] start, int[] end) {
        int rows = board.length;
        int cols = board[0].length;
        int[] directions = {-1, 0, 1, 0, -1};

        boolean[][] visited = new boolean[rows][cols];
        int[][][] parent = new int[rows][cols][2];
        List<int[]> path = new ArrayList<>();
        Queue<int[]> queue = new LinkedList<>();
        queue.add(start);
        visited[start[0]][start[1]] = true;
        parent[start[0]][start[1]] = new int[]{-1, -1};

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];

            if (x == end[0] && y == end[1]) {
                break;
            }

            for (int i = 0; i < directions.length - 1; i++) {
                int nx = x + directions[i];
                int ny = y + directions[i + 1];

                if (nx < 0 || ny < 0 || nx >= rows || ny >= cols) continue;
                if (board[nx][ny] == '#' || visited[nx][ny]) continue;

                visited[nx][ny] = true;
                parent[nx][ny] = new int[]{x, y};
                queue.add(new int[]{nx, ny});
            }
        }

        int dx = end[0];
        int dy = end[1];

        if(!visited[dx][dy]){
            return path;
        }

        while (dx != -1 && dy != -1) {
            path.add(new int[]{dx, dy});
            int px = parent[dx][dy][0];
            int py = parent[dx][dy][1];
            dx = px;
            dy = py;

        }
        Collections.reverse(path);
        return path;
    }

    public static boolean isValid(int r, int c, int N, int M) {
        return r >= 0 && c >= 0 && r < N && c < M;
    }

    static int[][] directions = {
            {-1, 0}, // UP
            {0, 1},  // RIGHT
            {1, 0},  // DOWN
            {0, -1}  // LEFT
    };

    public static int solve(char[][] maze, int initialDirection) {
        int N = maze.length;
        int M = maze[0].length;

        int[][][] dist = new int[N][M][4];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++)
                Arrays.fill(dist[i][j], Integer.MAX_VALUE);

        PriorityQueue<int[]> pq =
                new PriorityQueue<>(Comparator.comparingInt(a -> a[3]));

        int sr = N - 2, sc = 1;
        dist[sr][sc][initialDirection] = 0;
        pq.add(new int[]{sr, sc, initialDirection, 0});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int r = cur[0], c = cur[1], dir = cur[2], cost = cur[3];

            if (cost > dist[r][c][dir]) continue;

            for (int i = 0; i < 4; i++) {
                int nr = r + directions[i][0];
                int nc = c + directions[i][1];

                if (!isValid(nr, nc, N, M) || maze[nr][nc] != '.')
                    continue;

                int moveCost = (i == dir ? 1 : 1001);
                int newCost = cost + moveCost;

                if (dist[nr][nc][i] > newCost) {
                    dist[nr][nc][i] = newCost;
                    pq.add(new int[]{nr, nc, i, newCost});
                }
            }
        }

        int tr = 1, tc = M - 2;
        int answer = Integer.MAX_VALUE;
        for (int d = 0; d < 4; d++) {
            answer = Math.min(answer, dist[tr][tc][d]);
        }
        return answer;
    }

    // Method to find the minimum number of turns
    public static int minimumTurns(char[][] maze) {
        // Array to move to an adjacent cell
        int[][] directions = {
                {0, 1}, {1, 0}, {0, -1}, {-1, 0}
        };

        int f = solve(maze, 0);
        int s = solve(maze, 1);
        int t = solve(maze, 2);
        int ft = solve(maze, 3);

        System.out.println("First: " + f);
        System.out.println("Second: " + s);
        System.out.println("Third: " + t );
        System.out.println("Fourth: " + ft);

        // Explore all four directions as the initial direction
        int res = Math.min(Math.min(
                        solve(maze, 0),
                        solve(maze, 1)),
                Math.min(
                        solve(maze, 2),
                        solve(maze, 3)
                )
        );

        System.out.println("RES: " + res);
        return res == Integer.MAX_VALUE ? -1 : res;
    }


    public static void main(String[] args) throws IOException {
        List<String> read = read("/Users/benjaminpapouchado/Documents/Projects/src/input.txt");

        char[][] grid = grid(read);
        int[] start = {grid.length - 2, 1};
        int[] end = {1, grid[1].length - 2};

        System.out.println(grid[grid.length - 2][1]);
        System.out.println(grid[1][grid[1].length - 2]);

        List<int[]> path = maze(grid, start, end);

        System.out.println(minimumTurns(grid));

        for (int[] location : path) {
            grid[location[0]][location[1]] = 'X';
        }

        for (char[] row : grid) {
            System.out.println();
            for (char c : row) {
                System.out.print(c + " ");
            }
        }

    }
}
