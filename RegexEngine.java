import java.util.*;

//state class, each state needs transition table
class State {
    Set<State> transitions = new HashSet<>();
    char label;

    public State(char label) {
        this.label = label;
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
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter a regular expression: ");
            String regex = scanner.nextLine();

            if (regex.isEmpty()) {
                System.out.println("Error");
                break;
            }
            System.out.println("Ready");
            RegexParser parser = new RegexParser(regex);
            while(scanner.hasNextLine()) {
                String testCase = scanner.nextLine();
                //here I can test each input to the parsed regular expression
            }
        }
    }
}