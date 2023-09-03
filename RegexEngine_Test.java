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

    @Test
    public void KleeneStar_Test() {
        RegexEngine regexEngine = new RegexEngine();
        RegexParser parser = new RegexParser("ab*");
        List<Character> input = parser.parse();
        State startState = regexEngine.buildNFA(input);
        boolean case1 = RegexEngine.match(startState, "ab");
        boolean case2 = RegexEngine.match(startState, "a");
        boolean case3 = RegexEngine.match(startState, "");
        boolean case4 = RegexEngine.match(startState, "abbbb");
        boolean case5 = RegexEngine.match(startState, "b");
        Assert.assertEquals(case1, true);
        Assert.assertEquals(case2, true);
        Assert.assertEquals(case3, false);
        Assert.assertEquals(case4, true);
        Assert.assertEquals(case5,false);
    }

    @Test
    public void MultipleKleeneStar_Test() {
        RegexEngine regexEngine = new RegexEngine();
        RegexParser parser = new RegexParser("ab*c*d");
        List<Character> input = parser.parse();
        State startState = regexEngine.buildNFA(input);
        boolean case1 = RegexEngine.match(startState, "abcd");
        boolean case2 = RegexEngine.match(startState, "acd");
        boolean case3 = RegexEngine.match(startState, "");
        boolean case4 = RegexEngine.match(startState, "abbbbccccd");
        boolean case5 = RegexEngine.match(startState, "bdc");
        boolean case6 = RegexEngine.match(startState, "ad");
        Assert.assertEquals(case1, true);
        Assert.assertEquals(case2, true);
        Assert.assertEquals(case3, false);
        Assert.assertEquals(case4, true);
        Assert.assertEquals(case5,false);
        Assert.assertEquals(case6,true);

    }

    @Test
    public void KleenePlus_Test() {
        RegexEngine regexEngine = new RegexEngine();
        RegexParser parser = new RegexParser("ab+");
        List<Character> input = parser.parse();
        State startState = regexEngine.buildNFA(input);
        boolean case1 = RegexEngine.match(startState, "ab");
        boolean case2 = RegexEngine.match(startState, "a");
        boolean case3 = RegexEngine.match(startState, "");
        boolean case4 = RegexEngine.match(startState, "abbbb");
        boolean case5 = RegexEngine.match(startState, "b");
        Assert.assertEquals(case1, true);
        Assert.assertEquals(case2, false);
        Assert.assertEquals(case3, false);
        Assert.assertEquals(case4, true);
        Assert.assertEquals(case5,false);
    }

    @Test
    public void MultipleKleenePlus_Test() {
        RegexEngine regexEngine = new RegexEngine();
        RegexParser parser = new RegexParser("ab+c+d");
        List<Character> input = parser.parse();
        State startState = regexEngine.buildNFA(input);
        boolean case1 = RegexEngine.match(startState, "abcd");
        boolean case2 = RegexEngine.match(startState, "abbccd");
        boolean case3 = RegexEngine.match(startState, "");
        boolean case4 = RegexEngine.match(startState, "abbbb");
        boolean case5 = RegexEngine.match(startState, "dbca");
        boolean case6 = RegexEngine.match(startState, "ad");
        Assert.assertEquals(case1, true);
        Assert.assertEquals(case2, true);
        Assert.assertEquals(case3, false);
        Assert.assertEquals(case4, false);
        Assert.assertEquals(case5,false);
        Assert.assertEquals(case6,false);

    }

}
