import java.util.*;

//state class, each state needs transition table
class State {
    Set<Transition> transitions = new HashSet<>();
    char label;

    public State(char label) {
        this.label = label;
    }

    public void addTransition(State targetState, char inputChar) {
        transitions.add(new Transition(this, targetState, inputChar));
    }
}

//will need a transition class too
class Transition {
    State sourceState;
    State targetState;
    char inputChar;

    public Transition(State sourceState, State targetState, char inputChar) {
        this.sourceState = sourceState;
        this.targetState = targetState;
        this.inputChar = inputChar;
    }
}

/*alot of thinking about states and transitions from a parsed regular expression
I'll need a start state, and then a state for each character, plus an extra for kleene star, and a final accepting state

eg "ab"
From start, epsilon transition to first state
from first state transition on 'a' to second state
from second state transition on 'b' to third state
from third state transition on epsilon to accepting state

eg ab*c
From start, epsilon transition to first state
from first state transition on 'a' to second state
from second state transition on 'b' to third state
from third state transition on epsilon back to second state
from second state transition on 'c' to fourth state
from fourth state transition on epsilon to accepting state

eg ab+
from start, epsilon transition to first state
from first, transition on 'a' to second state
from second, transition on 'b' to third state
from third, transition on 'b' to third state
from third, transition on epsilon to accepting state


*/
class RegexParser {
    private String input;
    private int index;

    public RegexParser(String input) {
        this.input = input;
        this.index = 0;
    }

    public List<Character> parse() {
        List<Character> components = new ArrayList<>();
        
        //add each character of the regular epression to a list
        while (index < input.length()) {
            char currentChar = input.charAt(index);
            
            components.add(currentChar);
            index++;
        }

        return components;
    }
}

public class RegexEngine {

    public static State buildNFA(List<Character> input) {
        State start = new State('S'); //start state
        State current = start;

        //iterate through the regex input, creating states and simple transition for each
        for (int i = 0; i < input.size(); i++) {
            char c = input.get(i);
    
            State newState = new State(c);
            current.addTransition(newState, c);
            current = newState;
        }

        //once end of expression is reached, add one last transition to accepting state
        State accept = new State('A');
        current.addTransition(accept, 'E');

        return start;
    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter a regular expression: ");
            String regex = scanner.nextLine();

            if (regex.isEmpty()) {
                System.out.println("Error");
                break;
            }


            RegexParser parser = new RegexParser(regex);
            List<Character> input = parser.parse();


            
            //Build the NFA for the 
            State startState = buildNFA(input);



            System.out.println("Ready");
            while(scanner.hasNextLine()) {
                String testCase = scanner.nextLine();
                //here I can test each input to the parsed regular expression
                //need to create a match function
            }
        }
    }
}