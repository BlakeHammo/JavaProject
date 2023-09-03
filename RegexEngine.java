import java.util.*;
import java.util.stream.Collectors;

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
        State openBracket = start;
        State bracketTemp = new State('T');
        boolean bracketFlag = false;
        boolean insideBrackets = false;
        boolean alternatorFlag = false;
        State accept = new State('Δ');

        for (int i = 0; i < input.size(); i++) {
            char c = input.get(i);
    
            //kleene star
            if (c == '*') {
                if (insideBrackets) {
                    current.addTransition(previous, 'ε');
                    current = previous;
                } 
                else if (bracketFlag) {
                    current.addTransition(openBracket, 'ε');
                    current = openBracket;
                    bracketFlag = false;
                } 
                else {
                current.addTransition(previous, 'ε');
                current = previous;
                }
            }
            //kleene plus
            else if (c == '+') {
                if (insideBrackets) {
                    current.addTransition(previous, 'ε');
                }
                else if (bracketFlag) {
                    current.addTransition(openBracket, 'ε');
                    bracketFlag = false;
                }
                else {
                current.addTransition(previous, 'ε');
                }
            }
            //alternation operator
            //need to keep track of next char, loop back to previous or something to only alternate between the two characters around the | operator
            else if (c == '|') {
                if (insideBrackets) {
                    current.addTransition(bracketTemp, 'ε');
                    current = openBracket;
                    State newState = new State(c);
                    current.addTransition(newState, 'ε');
                    previous = current;
                    current = newState;
                    alternatorFlag = true;

                }
                else {
                    current.addTransition(accept, 'ε');
                    current = start;
                }
            }
            else if (c == '(') {
                State newState = new State(c);
                current.addTransition(newState, 'ε');
                openBracket = newState;
                current = newState;
                bracketFlag = true;
                insideBrackets = true;
            }
            else if (c == ')') {
                if (alternatorFlag) {
                    current.addTransition(bracketTemp, 'ε');
                    current = bracketTemp;
                    bracketTemp = new State('T');
                    alternatorFlag = false;
                    insideBrackets = false;
                }
                else {
                    current.addTransition(openBracket, 'ε');
                    insideBrackets = false;
                }


            }
            else {
            State newState = new State(c);
            current.addTransition(newState, c);
            previous = current;
            current = newState;  
            }

        }
        //once end of expression is reached, add one last transition to accepting state
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
            if (state.label == 'Δ') {
                return true;
            }
        }

        return false;
    }

    public static Set<State> getAllStates(State start) {
        // Create a set to store all states reachable from the start state
        Set<State> allStates = new HashSet<>();
        Queue<State> queue = new LinkedList<>();
    
        queue.add(start);
    
        while (!queue.isEmpty()) {
            State state = queue.poll();
            allStates.add(state);
    
            for (Transition transition : state.transitions) {
                State targetState = transition.targetState;
                if (!allStates.contains(targetState)) {
                    queue.add(targetState);
                }
            }
        }
    
        return allStates;
    }
    
    public static List<Character> getAllPossibleTransitions(State start) {
        Set<Character> transitionSet = new HashSet<>();
    
        // Iterate through all states in the NFA
        for (State state : getAllStates(start)) {
            // Iterate through transitions of the current state
            for (Transition transition : state.transitions) {
                char inputChar = transition.inputChar;
                // Check if it's a non-epsilon transition and add it to the set
                if (inputChar != 'ε') {
                    transitionSet.add(inputChar);
                }
            }
        }
        
        transitionSet.add('ε');

        // Convert the set of unique transitions to a list
        List<Character> possibleTransitions = new ArrayList<>(transitionSet);
        
        
        return possibleTransitions;
    }
    

    public static void printTransitionTable(State start) {
        Set<State> allStates = getAllStates(start);
        List<Character> possibleTransitions = getAllPossibleTransitions(start);

        // Create a table header
        System.out.print("\t");
        for (char c : possibleTransitions) {
            System.out.print(c + "\t");
        }
        System.out.println();

        // Print the transition table
        for (State state : allStates) {
            System.out.print(state.label + "\t");
            for (char c : possibleTransitions) {
                Set<State> targetStates = new HashSet<>();
                for (Transition transition : state.transitions) {
                    if (transition.inputChar == c) {
                        targetStates.add(transition.targetState);
                    }
                }
                String targetStateLabels = targetStates.stream()
                        .map(targetState -> String.valueOf(targetState.label))
                        .collect(Collectors.joining(", "));
                System.out.print(targetStateLabels + "\t");
            }
            System.out.println();
        }
    }
    

    public static void main(String[] args) {
        boolean verboseMode = true;
        
        // Check if the '-v' option is provided
        if (args.length > 0 && args[0].equals("-v")) {
            verboseMode = true;
        }
        
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

            //Build the NFA for the input
            State startState = buildNFA(input);
            
            Boolean isMatch = false;
            String testCase = "";
            //if verboseMode is flagged, print transition table of NFA
            if (verboseMode) {
                printTransitionTable(startState);
                isMatch = match(startState, testCase);
                System.out.println(isMatch);
                while(scanner.hasNextLine()) {
                    testCase = testCase + scanner.nextLine();
                    isMatch = match(startState, testCase);
                    System.out.println(isMatch);
            }
            //if verboseMode is flagged, 

            System.out.println("Ready");
            while(scanner.hasNextLine()) {
                testCase = scanner.nextLine();
                isMatch = match(startState, testCase);
                System.out.println(isMatch);
            }
            }
        }
    }
}