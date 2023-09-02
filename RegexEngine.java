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
        State previous = start;
        //iterate through the regex input, creating states and simple transition for each
        //only for characters, no operators so far
        //operators are * + | ( )
        for (int i = 0; i < input.size(); i++) {
            char c = input.get(i);
    
            if (c == '*') {
                current.addTransition(previous, 'ε');
                current = previous;
            }

            else {
            State newState = new State(c);
            current.addTransition(newState, c);
            previous = current;
            current = newState;
  
            }

        }

        //once end of expression is reached, add one last transition to accepting state
        State accept = new State('A');
        current.addTransition(accept, 'ε');

        return start;
    }

    //Epsilon Closure for each state
    public static Set<State> epsilonClosure(State state) {
        Set<State> closure = new HashSet<>();
        epsilonClosureHelper(state, closure);
        return closure;
    }

    private static void epsilonClosureHelper(State state, Set<State> closure) {
        if (!closure.contains(state)) {
            closure.add(state);
            
            //iterate over transitions of current state, if there is an epsilon transition
            //recursive call and check the transitions of that state
            for (Transition transition : state.transitions) {
                if (transition.inputChar == 'ε') {
                    epsilonClosureHelper(transition.targetState, closure);
                }
            }
        }
    }


    //matching method
    public static boolean match(State start, String input) {
        //epsilon closure of initial state
        Set<State> currentStates = epsilonClosure(start);

        for (char c : input.toCharArray()) {
            Set<State> newStates = new HashSet<>();

            for (State state : currentStates) {
                //check each transition from current state
                for (Transition transition : state.transitions) {
                    //if the transition input matches the current input character,
                    //the epsilon closure of the target state to the new states is added
                    if (transition.inputChar == c) {
                        newStates.addAll(epsilonClosure(transition.targetState));
                    }
                }
            }

            currentStates = newStates;
        }
        // Check if any of the final states in the current set of states are accepting states
        for (State state : currentStates) {
            if (state.label == 'A') {
                return true;
            }
        }

        return false;
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
                boolean isMatch = match(startState, testCase);
                System.out.println(isMatch);

                //here I can test each input to the parsed regular expression
                //need to create a match function
            }
        }
    }
}