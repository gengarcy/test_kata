import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class StringCalculator {
    private final String input;

    public StringCalculator(String input) throws Exception {
        if (input.length() > 10) {
            throw new Exception("Ввод не должен превышать 10 символов.");
        }
        this.input = input;
    }

    public String calculate() throws Exception {
        Parser parser = new Parser(input);
        Operation operation = parser.parse();
        return operation.execute();
    }
}

class Parser {
    private final String input;
    private String str1;
    private String operator;
    private String str2;

    public Parser(String input) {
        this.input = input;
    }

    public Operation parse() throws Exception {
        Pattern pattern = Pattern.compile("\"([^\"]+)\"\\s*([+\\-*/])\\s*(\"[^\"]+\"|\\d+)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new Exception("Неподходящее выражение.");
        }
        str1 = matcher.group(1);
        operator = matcher.group(2);
        str2 = matcher.group(3).replace("\"", "");
        return createOperation();
    }

    private Operation createOperation() throws Exception {
        switch (operator) {
            case "+" -> {
                return new ConcatenationOperation(str1, str2);
            }
            case "-" -> {
                return new SubtractionOperation(str1, str2);
            }
            case "*" -> {
                return new MultiplicationOperation(str1, str2);
            }
            case "/" -> {
                return new DivisionOperation(str1, str2);
            }
            default -> throw new Exception("Неподдерживаемая операция.");
        }
    }
}

abstract class Operation {
    protected String str1;
    protected String str2;

    public Operation(String str1, String str2) {
        this.str1 = str1;
        this.str2 = str2;
    }

    public abstract String execute() throws Exception;
}

class ConcatenationOperation extends Operation {
    public ConcatenationOperation(String str1, String str2) {
        super(str1, str2);
    }

    @Override
    public String execute() {
        return str1 + str2;
    }
}

class SubtractionOperation extends Operation {
    public SubtractionOperation(String str1, String str2) {
        super(str1, str2);
    }

    @Override
    public String execute() {
        return str1.replace(str2, "");
    }
}

class MultiplicationOperation extends Operation {
    public MultiplicationOperation(String str1, String str2) throws Exception {
        super(str1, str2);
        validateMultiplier(Integer.parseInt(str2));
    }

    @Override
    public String execute() {
        int multiplier = Integer.parseInt(str2);
        return str1.repeat(multiplier);
    }

    private void validateMultiplier(int multiplier) throws Exception {
        if (multiplier < 1 || multiplier > 10) {
            throw new Exception("Число для умножения должно быть от 1 до 10.");
        }
    }
}

class DivisionOperation extends Operation {
    public DivisionOperation(String str1, String str2) throws Exception {
        super(str1, str2);
        validateDivisor(Integer.parseInt(str2));
    }

    @Override
    public String execute() {
        int divisor = Integer.parseInt(str2);
        return str1.length() < divisor ? "" : str1.substring(0, str1.length() / divisor);
    }

    private void validateDivisor(int divisor) throws Exception {
        if (divisor < 1 || divisor > 10) {
            throw new Exception("Число для деления должно быть от 1 до 10.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение (например, \"hello\" + \"world\"): ");
        String input = scanner.nextLine();
        try {
            StringCalculator calculator = new StringCalculator(input);
            String result = calculator.calculate();
            System.out.println("Результат: " + (result.length() > 40 ? result.substring(0, 40) + "..." : result));
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

}