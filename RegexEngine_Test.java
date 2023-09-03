import org.junit.*;
import java.util.*;

public class RegexEngine_Test {
    @Test
    public void RegexEngine_Test() {
        RegexEngine regexEngine = new RegexEngine();
        RegexParser parser = new RegexParser("abc");
        List<Character> input = parser.parse();
        State startState = regexEngine.buildNFA(input);
        boolean case1 = RegexEngine.match(startState, "abc");
        boolean case2 = RegexEngine.match(startState, "ab");
        boolean case3 = RegexEngine.match(startState, "");
        boolean case4 = RegexEngine.match(startState, "cba");
        boolean case5 = RegexEngine.match(startState, "aaaaabc");
        Assert.assertEquals(case1, true);
        Assert.assertEquals(case2, false);
        Assert.assertEquals(case3, false);
        Assert.assertEquals(case4, false);
        Assert.assertEquals(case5,false);
    }
}
