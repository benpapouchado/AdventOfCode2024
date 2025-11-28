package Advent2024;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DaySeven extends Read {

    //thank you leetcode practice
    public static List<List<String>> expressionPermutations(String[] operators, String[] numbers){
        List<List<String>> result = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        temp.add(numbers[0]);
        backtrack(result, temp, 1, operators, numbers);
        return result;
    }

    private static void backtrack(List<List<String>> result, List<String> temp,
                                  int index, String[] operators, String[] numbers) {
        if(index == numbers.length){
            result.add(new ArrayList<>(temp));
            return;
        }

        for(String operator : operators){
            temp.add(operator);
            temp.add(numbers[index]);
            backtrack(result, temp,index + 1, operators, numbers);
            temp.remove(temp.size() - 1);
            temp.remove(temp.size() - 1);
        }
    }

    public static long calculateExpression(List<String> expression){
        long result = Long.parseLong(expression.get(0));

        for (int i = 1; i < expression.size(); i += 2) {
            String operator = expression.get(i);
            String string_of_num = expression.get(i + 1);
            long num = Long.parseLong(string_of_num);

            if (operator.equals("+")){
                result += num;
            }

            if (operator.equals("*")){
                result *= num;
            }

            if(operator.equals("||")){
                String temp_result = result + string_of_num;
                result = Long.parseLong(temp_result);
            }
        }
        return result;
    }

    public static long compareCalculations(String line, String[] operators){
        long match = 0;
        String[] split = line.split(":");

        long test_value = Long.parseLong(split[0]);
        String[] numbers_strings = split[1].trim().split(" ");

        List<List<String>> expressions = expressionPermutations(operators, numbers_strings);

        for(List<String> expression : expressions){
            long calculation = calculateExpression(expression);
            if (calculation == test_value){
                match = calculation;
            }
        }
        return match;
    }

    // part 1 + part 2 depending on operators used
    public static long calibrations(List<String> lines, String[] operators){
        long sum_of_matches = 0;

        for(String line : lines){
            sum_of_matches += compareCalculations(line, operators);
        }
        return sum_of_matches;
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = read("");

        //223 ms
        String[] operators = {"+", "*"};
        System.out.println(calibrations(lines, operators));

        //11101 ms
        String[] operators2 = {"+", "*", "||"};
        System.out.println(calibrations(lines, operators2));
    }
}
