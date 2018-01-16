import java.util.*;

public class Parser {
    private static String operators = "+-*/^";
    private static String delimiters = "() " + operators;
    public static boolean flag = true;
    private String inputText;
    private Scanner in = new Scanner(System.in);
    private Byte numbers;

    public Parser() {}

    private static boolean isDelimiter(String token) {
        if (token.length() != 1)
            return false;
        for (int i = 0; i < delimiters.length(); i++) {
            if (token.charAt(0) == delimiters.charAt(i))
                return true;
        }
        return false;
    }

    private static boolean isOperator(String token) {
        if (token.equals("u-")) return true;
        for (int i = 0; i < operators.length(); i++) {
            if (token.charAt(0) == operators.charAt(i)) return true;
        }
        return false;
    }

    private static int priority(String token) {
        if (token.equals("(")) return 1;
        if (token.equals("+") || token.equals("-")) return 2;
        if (token.equals("*") || token.equals("/")) return 3;
        if (token.equals("^")) return 4;
        return 5;
    }

    public static String parse(String infix) {
        Stack<String> stack = new Stack<>();
        StringBuilder stringRPN = new StringBuilder("");

        StringTokenizer tokenizer = new StringTokenizer(infix, delimiters, true);

        String previous = "";
        String current = "";

        while (tokenizer.hasMoreTokens()) {
            current = tokenizer.nextToken();
            if (!tokenizer.hasMoreTokens() && isOperator(current)) {
                System.out.println("Incorrect expression.");
                flag = false;
                return stringRPN.toString();
            }
            if (current.equals(" ")) {
                continue;
            }
            else if (isDelimiter(current)) {
                if (current.equals("(")) {
                    stack.push(current);
                }
                else if (current.equals(")")) {
                    while (!stack.peek().equals("(")) {
                        stringRPN.append(stack.pop());

                        if (stack.isEmpty()) {
                            System.out.println("Brackets not matched.");
                            flag = false;
                            return stringRPN.toString();
                        }
                    }

                    stack.pop();
                } else {
                    while (!stack.isEmpty() && (priority(current) <= priority(stack.peek()))) {
                        stringRPN.append(stack.pop());
                    }

                    stack.push(current);
                }

            } else {
                stringRPN.append(current);
            }
            previous = current;
        }

        while (!stack.isEmpty()) {
            System.out.println("Brackets not matched.");
            flag = false;
            return stringRPN.toString();
        }
        return stringRPN.toString();
    }

    public String stringToRPN() {
        StringBuilder stringBuilder = new StringBuilder("");

        System.out.println("Input the number of expressions:");

        numbers = in.nextByte();
        while(numbers < 0 || numbers > 100) {
            System.out.println("Incorrect length of numbers. Input your numbers again:");
            numbers = in.nextByte();
        }

        System.out.println("Input your expressions:");
        for(int counter = -1; counter < numbers; counter++) {

            inputText = in.nextLine();
            while (inputText.length() > 400) {
                System.out.println("Incorrect text input of length. Input your text again:");
                inputText = in.nextLine();

            }

            stringBuilder.append(parse(inputText) + "\n");
        }

        return stringBuilder.toString();
    }
}