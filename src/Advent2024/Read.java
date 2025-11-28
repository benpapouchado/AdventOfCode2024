package Advent2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Read {

    public static List<String> read(String file) throws IOException {
        List<String> list = new ArrayList<>();
        FileReader fr = new FileReader(file);
        BufferedReader bf = new BufferedReader(fr);
        String line;
        while ((line = bf.readLine()) != null) {
            list.add(line);
        }
        return list;
    }

    public static char[][] convertGrid(List<String> lines) {
        char[][] grid = new char[lines.size()][lines.get(0).length()];

        for (int i = 0; i < lines.size(); i++) {
            grid[i] = lines.get(i).toCharArray();
        }
        return grid;
    }

    public static int[] convertStringArrayToIntArray(String line, String splitter){
        return Arrays.stream(line.split(splitter))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
