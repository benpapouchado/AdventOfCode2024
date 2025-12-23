package Advent2024;

import java.io.IOException;
import java.util.*;

public class DayNine extends Read {
    public static List<Integer> process_line(String line) {
        List<Integer> disk = new ArrayList<>();
        int file_id = 0;

        for (int i = 0; i < line.length(); i++) {
            int count = line.charAt(i) - '0';

            if (i % 2 == 0) {
                for (int j = 0; j < count; j++) {
                    disk.add(file_id);
                }
                file_id++;
            } else {
                for (int j = 0; j < count; j++) {
                    disk.add(-1);
                }
            }
        }

        return disk;
    }

    public static List<Integer> organise_line(List<Integer> line) {
        int left = 0;
        int right = line.size() - 1;

        while (left < right) {
            while (left < right && line.get(left) != -1) {
                left++;
            }
            while (left < right && line.get(right) == -1) {
                right--;
            }
            if (left < right) {
                line.set(left, line.get(right));
                line.set(right, -1);
                left++;
                right--;
            }
        }
        return line;
    }

    //part 1
    public static long filesystem_checksum(List<Integer> line) {
        long calculate = 0;
        for (int i = 0; i < line.size(); i++) {
            if (line.get(i) != -1) {
                calculate += line.get(i) * i;
            }
        }
        return calculate;
    }

    //part 2
    public static List<Integer> track_dots_and_free_space(List<Integer> line) {
        Map<Integer, int[]> file_id = new TreeMap<>();
        Map<Integer, Integer> free_space = new TreeMap<>(); //with -1
        Map<Integer, int[]> file_segments = new TreeMap<>(Collections.reverseOrder());

        for (int n = 0; n < line.size(); n++) {
            int num = line.get(n);
            if (num != -1) {
                if (file_id.containsKey(num)) {
                    int[] index_and_id = file_id.get(num);
                    index_and_id[1]++;
                } else {
                    file_id.put(num, new int[]{n, 1});
                }
            } else {
                int i = n;
                int length = 0;
                while (i < line.size() && line.get(i) == -1) {
                    length++;
                    i++;
                }
                free_space.put(n, length);
                n = i - 1;
            }
        }

        for (Map.Entry<Integer, int[]> file : file_id.entrySet()) {
            int key = file.getKey();
            int[] value = file.getValue();
            file_segments.put(value[0], new int[]{key, value[1]});
        }

        for (Map.Entry<Integer, int[]> file : file_segments.entrySet()) {

            int file_start = file.getKey();
            int fileID = file.getValue()[0];
            int file_length = file.getValue()[1];

            for (Map.Entry<Integer, Integer> space : free_space.entrySet()) {

                int free_start = space.getKey();
                int free_length = space.getValue();

                if (file_length <= free_length && free_start < file_start) {
                    for(int i = 0; i < file.getValue()[1]; i++){
                        line.set(free_start + i, fileID);
                    }

                    for(int i = 0; i < file_length; i++){
                        line.set(file_start + i, -1);
                    }

                    free_space.remove(free_start);

                    if (free_length > file_length) {
                        free_space.put(free_start + file_length, free_length - file_length);
                    }
                    free_space.put(file_start, file_length);
                    break;
                }
            }
        }
        return line;
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = read("");
        String one = lines.get(0);
        String two = lines.get(0);

        List<Integer> expanded_one = process_line(one);
        List<Integer> expanded_two = process_line(two);

        List<Integer> organise = organise_line(expanded_one);
        List<Integer> track_dots = track_dots_and_free_space(expanded_two);

        //part1
        long checksum_part1 = filesystem_checksum(organise);
        System.out.println(checksum_part1);

        //part2
        long checksum_part2 = filesystem_checksum(track_dots);
        System.out.println(checksum_part2);

    }
}
