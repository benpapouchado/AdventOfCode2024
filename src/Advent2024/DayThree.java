package Advent2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayThree extends Read{

    public static Stack<Integer> extractNums(String line){
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)|don't()|do()"); //"mul\\((\\d+),(\\d+)\\)|do\\(\\)|don't\\(\\)"
        Matcher matcher = pattern.matcher(line);
        while(matcher.find()){
            list.add(matcher.group());
        }

        Stack<Integer> stack = new Stack<>();
        boolean halt = false;
        for(String s : list){
            if(s.equals("don't")){
                halt = true;
            }

            if(s.equals("do")){
                halt = false;
            }
            Pattern pattern2 = Pattern.compile("(\\d+)");
            Matcher matcher2 = pattern2.matcher(s);
            while(matcher2.find()){
                if(!halt) {
                    stack.push(Integer.parseInt(matcher2.group()));
                }
            }
        }
        System.out.println(stack);
        return stack;
    }

    public static int processStack(Stack<Integer> stack){
        int result = 0;

        while(!stack.empty()){
            result += stack.pop() * stack.pop();
        }
        return result;
    }

    public static void solve(List<String> lines) {
        Pattern pattern = Pattern.compile(
                "(do\\(\\))|(don't\\(\\))|(mul\\((\\d+),(\\d+)\\))"
        );
        BigInteger result = BigInteger.ZERO;
        boolean enable = true;
        for (String line : lines) {
            Matcher m = pattern.matcher(line);

            while (m.find()) {
                if (m.group(1) != null) {
                    enable = true;
                }
                if (m.group(2) != null) {
                    enable = false;
                }
                if (m.group(3) != null && enable) {
                    int val1 = Integer.parseInt(m.group(4));
                    int val2 = Integer.parseInt(m.group(5));
                    result = result.add(BigInteger.valueOf(val1).multiply(BigInteger.valueOf(val2)));
                }
            }
        }
        System.out.println(result);
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = read("");
        int result = 0;
        int result2 = 0;
        for(String line : lines){
            Stack<Integer> stack = extractNums(line);
            result += processStack(stack);
        }
        System.out.println(result2);
        System.out.println(result);
        solve(lines);
    }
}