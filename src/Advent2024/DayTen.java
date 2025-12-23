package Advent2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DayTen extends Read {

    public static List<List<Integer>> read_num_lines(String file) throws IOException {
        List<List<Integer>> list = new ArrayList<>();
        FileReader fr = new FileReader(file);
        BufferedReader bf = new BufferedReader(fr);
        String line;
        while((line = bf.readLine()) != null){
            String[] s = line.split(" ");

            for(String x : s){
                list.add(convertStringArrayToIntList(x, ""));
            }
        }
        return list;
    }

    public static int bfs(List<List<Integer>> atlas, int m, int n){
        int r = atlas.size();
        int c = atlas.get(0).size();

        Queue<int[]> queue = new LinkedList<>();
        List<int[]> nines = new ArrayList<>();
        int[] directions = {-1, 0, 1, 0, -1};
        boolean[][] visited = new boolean[r][c];

        queue.add(new int[]{m,n});

        while(!queue.isEmpty()){
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            int height = atlas.get(current[0]).get(current[1]);

            if(height == 9){
                nines.add(current);
                continue;
            }

            for (int i = 0; i < directions.length - 1; i++) {

                int dx = x + directions[i];
                int dy = y + directions[i + 1];

                if (dx < 0 || dy < 0 || dx >= r || dy >= c || visited[dx][dy]) continue;

                if(atlas.get(dx).get(dy) == atlas.get(x).get(y) + 1){
                    visited[dx][dy] = true;
                    queue.add(new int[]{dx, dy});

                }
            }
        }
        return nines.size();
    }

    public static int bfs(List<List<Integer>> atlas){
        int total = 0;
        for (int i = 0; i < atlas.size(); i++) {
            for (int j = 0; j < atlas.get(0).size(); j++) {
                if(atlas.get(i).get(j) == 0){
                    total += bfs(atlas, i, j);
                }
            }
        }
        return total;
    }

    public static void main(String[] args) throws IOException {
        List<List<Integer>> lines = read_num_lines("");

        //part 1
        int trails = bfs(lines);
        System.out.println(trails);
    }
}
