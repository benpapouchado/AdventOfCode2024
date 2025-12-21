public class Diamond {

    public static String letters(char letter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 'A'; i <= (int) letter; i++) {

            sb.append(" ".repeat((int) letter - i));
            sb.append((char) i);
            if (i > 'A') {
                sb.append(" ".repeat((2 * (i - 'A') - 1)));
                sb.append((char) i);
            }
            sb.append("\n");
        }

        for(int i = letter - 1; i >= 'A'; i--){
            sb.append(" ".repeat((int) letter - i));
            sb.append((char) i);
            if (i > 'A') {
                sb.append(" ".repeat((2 * (i - 'A') - 1)));
                sb.append((char) i);
            }

            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(letters('D'));

    }
}
