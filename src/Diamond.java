public class Diamond {

    public static String create_diamond_shape(char letter, char lower_bound, char upper_bound, String message) {
        if (letter < lower_bound || letter > upper_bound) {
            throw new IllegalArgumentException("Wrong character type. Enter a " + message + " between " +
                    lower_bound + " and " + upper_bound);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = lower_bound; i <= (int) letter; i++) {

            sb.append(" ".repeat((int) letter - i));
            sb.append((char) i);
            if (i > lower_bound) {
                sb.append(" ".repeat((2 * (i - lower_bound) - 1)));
                sb.append((char) i);
            }
            sb.append("\n");
        }

        for (int i = letter - 1; i >= lower_bound; i--) {
            sb.append(" ".repeat((int) letter - i));
            sb.append((char) i);
            if (i > lower_bound) {
                sb.append(" ".repeat((2 * (i - lower_bound) - 1)));
                sb.append((char) i);
            }
            sb.append("\n");
        }
        return sb.toString().stripTrailing();
    }

    public static String upper_case_diamond(char letter) {
        return create_diamond_shape(letter, 'A', 'Z', "letter");
    }

    public static String lower_case_diamond(char letter) {
        return create_diamond_shape(letter, 'a', 'z', "letter");
    }

    public static String number_diamond(char letter) {
        return create_diamond_shape(letter, '0', '9', "number");
    }

    public static void main(String[] args) {
        System.out.println(upper_case_diamond('G'));
        System.out.println(lower_case_diamond('d'));
        System.out.println(number_diamond('4'));
    }
}
