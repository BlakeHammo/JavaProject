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

    @Test
    public void Alternator_Test() {
        RegexEngine regexEngine = new RegexEngine();
        RegexParser parser = new RegexParser("ab|de");
        List<Character> input = parser.parse();
        State startState = regexEngine.buildNFA(input);
        boolean case1 = RegexEngine.match(startState, "ab");
        boolean case2 = RegexEngine.match(startState, "de");
        boolean case3 = RegexEngine.match(startState, "");
        boolean case4 = RegexEngine.match(startState, "ba");
        boolean case5 = RegexEngine.match(startState, "bd");
        boolean case6 = RegexEngine.match(startState, "ae");
        Assert.assertEquals(case1, true);
        Assert.assertEquals(case2, true);
        Assert.assertEquals(case3, false);
        Assert.assertEquals(case4, false);
        Assert.assertEquals(case5,false);
        Assert.assertEquals(case6,false);
    }

    @Test
    public void MultipleAlternator_Test() {
        RegexEngine regexEngine = new RegexEngine();
        RegexParser parser = new RegexParser("ab|de|f|e");
        List<Character> input = parser.parse();
        State startState = regexEngine.buildNFA(input);
        boolean case1 = RegexEngine.match(startState, "ab");
        boolean case2 = RegexEngine.match(startState, "de");
        boolean case3 = RegexEngine.match(startState, "");
        boolean case4 = RegexEngine.match(startState, "e");
        boolean case5 = RegexEngine.match(startState, "def");
        boolean case6 = RegexEngine.match(startState, "ee");
        Assert.assertEquals(case1, true);
        Assert.assertEquals(case2, true);
        Assert.assertEquals(case3, false);
        Assert.assertEquals(case4, true);
        Assert.assertEquals(case5,false);
        Assert.assertEquals(case6,false);
    }

    @Test
    public void Brackets_Test() {
        RegexEngine regexEngine = new RegexEngine();
        RegexParser parser = new RegexParser("(ab)*");
        List<Character> input = parser.parse();
        State startState = regexEngine.buildNFA(input);
        boolean case1 = RegexEngine.match(startState, "ab");
        boolean case2 = RegexEngine.match(startState, "abababababab");
        boolean case3 = RegexEngine.match(startState, "");
        boolean case4 = RegexEngine.match(startState, "aaaaaabbbbbb");
        boolean case5 = RegexEngine.match(startState, "bbbbbbaaaaaa");
        boolean case6 = RegexEngine.match(startState, "babababababa");
        boolean case7 = RegexEngine.match(startState, "aaaaa");
        boolean case8 = RegexEngine.match(startState, "bbbbb");

        Assert.assertEquals(case1, true);
        Assert.assertEquals(case2, true);
        Assert.assertEquals(case3, true);
        Assert.assertEquals(case4, false);
        Assert.assertEquals(case5,false);
        Assert.assertEquals(case6,false);
        Assert.assertEquals(case7,false);
        Assert.assertEquals(case8,false);

    }

    @Test
    public void MultipleBrackets_Test() {
        RegexEngine regexEngine = new RegexEngine();
        RegexParser parser = new RegexParser("(ab)*(cd)*(ef)*");
        List<Character> input = parser.parse();
        State startState = regexEngine.buildNFA(input);
        boolean case1 = RegexEngine.match(startState, "ab");
        boolean case2 = RegexEngine.match(startState, "abababababab");
        boolean case3 = RegexEngine.match(startState, "");
        boolean case4 = RegexEngine.match(startState, "aaaaaabbbbbb");
        boolean case5 = RegexEngine.match(startState, "bbbbbbaaaaaa");
        boolean case6 = RegexEngine.match(startState, "babababababa");
        boolean case7 = RegexEngine.match(startState, "aaaaa");
        boolean case8 = RegexEngine.match(startState, "bbbbb");
        boolean case9 = RegexEngine.match(startState, "abcdef");
        boolean case10 = RegexEngine.match(startState, "abcdcdcdcd");
        boolean case11= RegexEngine.match(startState, "efefefefef");

        Assert.assertEquals(case1, true);
        Assert.assertEquals(case2, true);
        Assert.assertEquals(case3, true);
        Assert.assertEquals(case4, false);
        Assert.assertEquals(case5,false);
        Assert.assertEquals(case6,false);
        Assert.assertEquals(case7,false);
        Assert.assertEquals(case8,false);
        Assert.assertEquals(case9,true);
        Assert.assertEquals(case10,true);
        Assert.assertEquals(case11,true);

    }

    @Test
    public void ManyOperators_Test() {
        RegexEngine regexEngine = new RegexEngine();
        RegexParser parser = new RegexParser("(ab)*|be|(fe)+i*");
        List<Character> input = parser.parse();
        State startState = regexEngine.buildNFA(input);
        boolean case1 = RegexEngine.match(startState, "ab");
        boolean case2 = RegexEngine.match(startState, "fefefeiiiiiii");
        boolean case3 = RegexEngine.match(startState, "");
        boolean case4 = RegexEngine.match(startState, "abbe");
        boolean case5 = RegexEngine.match(startState, "befei");
        boolean case6 = RegexEngine.match(startState, "bebebe");
        boolean case7 = RegexEngine.match(startState, "iiiiiiii");
        boolean case8 = RegexEngine.match(startState, "fe");
        boolean case9 = RegexEngine.match(startState, "befe");
        boolean case10 = RegexEngine.match(startState, "i");
        boolean case11= RegexEngine.match(startState, "be");

        Assert.assertEquals(case1, true);
        Assert.assertEquals(case2, true);
        Assert.assertEquals(case3, true);
        Assert.assertEquals(case4, false);
        Assert.assertEquals(case5,false);
        Assert.assertEquals(case6,false);
        Assert.assertEquals(case7,false);
        Assert.assertEquals(case8,true);
        Assert.assertEquals(case9,false);
        Assert.assertEquals(case10,false);
        Assert.assertEquals(case11,true);

    }

    @Test
    public void AlternatorBrackets_Test() {
        RegexEngine regexEngine = new RegexEngine();
        RegexParser parser = new RegexParser("(a|b)(d|e)*(j|kl)");
        List<Character> input = parser.parse();
        State startState = regexEngine.buildNFA(input);
        boolean case1 = RegexEngine.match(startState, "adddddj");
        boolean case2 = RegexEngine.match(startState, "beeeekl");
        boolean case3 = RegexEngine.match(startState, "");
        boolean case4 = RegexEngine.match(startState, "dedede");
        boolean case5 = RegexEngine.match(startState, "jkl");
        boolean case6 = RegexEngine.match(startState, "beeeeedddddj");
        boolean case7 = RegexEngine.match(startState, "jda");
        boolean case8 = RegexEngine.match(startState, "abdkl");
        boolean case9 = RegexEngine.match(startState, "adjkl");
        boolean case10 = RegexEngine.match(startState, "aj");
        boolean case11= RegexEngine.match(startState, "bk");

        Assert.assertEquals(case1, true);
        Assert.assertEquals(case2, true);
        Assert.assertEquals(case3, false);
        Assert.assertEquals(case4, false);
        Assert.assertEquals(case5,false);
        Assert.assertEquals(case6,true);
        Assert.assertEquals(case7,false);
        Assert.assertEquals(case8,false);
        Assert.assertEquals(case9,false);
        Assert.assertEquals(case10,true);
        Assert.assertEquals(case11,false);

    }

    @Test
    public void Complex_Test() {
        RegexEngine regexEngine = new RegexEngine();
        RegexParser parser = new RegexParser("(a|b)*cd+(e*f)*|g*");
        List<Character> input = parser.parse();
        State startState = regexEngine.buildNFA(input);
        boolean case1 = RegexEngine.match(startState, "");
        boolean case2 = RegexEngine.match(startState, "abbbacdg");
        boolean case3 = RegexEngine.match(startState, "abbbacd");
        boolean case4 = RegexEngine.match(startState, "cdfefefe");
        boolean case5 = RegexEngine.match(startState, "cd");
        boolean case6 = RegexEngine.match(startState, "cdddddg");
        boolean case7 = RegexEngine.match(startState, "cdef");
        boolean case8 = RegexEngine.match(startState, "abcdef");
        boolean case9 = RegexEngine.match(startState, "cddfe");
        boolean case10 = RegexEngine.match(startState, "ggggg");
        boolean case11= RegexEngine.match(startState, "acddd");

        Assert.assertEquals(case1, true);
        Assert.assertEquals(case2, false);
        Assert.assertEquals(case3, true);
        Assert.assertEquals(case4, true);
        Assert.assertEquals(case5,true);
        Assert.assertEquals(case6,false);
        Assert.assertEquals(case7,true);
        Assert.assertEquals(case8,true);
        Assert.assertEquals(case9,true);
        Assert.assertEquals(case10,true);
        Assert.assertEquals(case11,true);

    }

}

