import org.junit.Assert;
import org.junit.Test;

public class DiamondTest {

    @Test
    public void lower_case_diamond_test(){
        String diamond =
                "   a\n" +
                "  b b\n" +
                " c   c\n" +
                "d     d\n" +
                " c   c\n" +
                "  b b\n" +
                "   a";
        String test_diamond = Diamond.lower_case_diamond('d');
        Assert.assertEquals(diamond, test_diamond);

        String single_letter = "a";
        String test_single_letter = Diamond.lower_case_diamond('a');
        Assert.assertEquals(single_letter, test_single_letter);
    }

    @Test
    public void upper_case_diamond_test(){
        String diamond = "      A\n" +
                        "     B B\n" +
                        "    C   C\n" +
                        "   D     D\n" +
                        "  E       E\n" +
                        " F         F\n" +
                        "G           G\n" +
                        " F         F\n" +
                        "  E       E\n" +
                        "   D     D\n" +
                        "    C   C\n" +
                        "     B B\n" +
                        "      A";

        String test_diamond = Diamond.upper_case_diamond('G');
        Assert.assertEquals(diamond, test_diamond);
    }

    @Test
    public void number_diamond_test(){
        String diamond =
                "    0\n" +
                "   1 1\n" +
                "  2   2\n" +
                " 3     3\n" +
                "4       4\n" +
                " 3     3\n" +
                "  2   2\n" +
                "   1 1\n" +
                "    0";

        String test_diamond = Diamond.number_diamond('4');
        Assert.assertEquals(diamond, test_diamond);
    }

    @Test(expected = IllegalArgumentException.class)
    public void error_test() throws IllegalArgumentException{
        Diamond.upper_case_diamond('s');
    }
}
