package stringCalculator;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение (например, \"hello\" + \"world\"): ");
        String input = scanner.nextLine();
        try {
            String result = calculate(input);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calculate(String input) throws Exception {

        String regex = "\"([^\"]*)\"\\s*([-+*/])\\s*(\"[^\"]*\"|\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (!matcher.matches()) {
            throw new Exception("Неверное выражение.");
        }

        String operand1 = matcher.group(1);
        String operator = matcher.group(2);
        String operand2 = matcher.group(3);

        if (operand1.length() > 10) {
            throw new Exception("Длина строки не должна превышать 10 символов.");
        }


        if (operand2.startsWith("\"") && operand2.endsWith("\"")) {

            String strValue2 = operand2.substring(1, operand2.length() - 1);
            if (operator.equals("+")) {
                return add(operand1, operand2);
            } else if (operator.equals("-")) {
                return subtract(operand1, operand2);
            } else {
                throw new Exception("Неподдерживаемая операция с строкой.");
            }
        } else {

            int intValue = getIntegerValue(operand2);
            if (operator.equals("*")) {
                return multiply(operand1, intValue);
            } else if (operator.equals("/")) {
                return divide(operand1, intValue);
            } else {
                throw new Exception("Неподдерживаемая операция.");
            }
        }
    }

    private static String add(String str1, String str2) {
        String result = str1 + str2.substring(1, str2.length() - 1);
        return truncate(result);
    }

    private static String subtract(String str1, String str2) {
        String str2Value = str2.substring(1, str2.length() - 1);
        String result = str1.replace(str2Value, "");
        return truncate(result);
    }

    private static String multiply(String str, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(str);
        }
        return truncate(sb.toString());
    }

    private static String divide(String str, int n) {
        int newLength = str.length() / n;
        return truncate(str.substring(0, newLength));
    }

    private static int getIntegerValue(String str) throws Exception {
        int value = Integer.parseInt(str);
        if (value < 1 || value > 10) {
            throw new Exception("Число должно быть от 1 до 10.");
        }
        return value;
    }

    private static String truncate(String str) {
        if (str.length() > 40){
            return str.substring(0, 40) + "...";
        }
        return str;
    }
}