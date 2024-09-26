package stringCalculator;
import java.util.Scanner;

public class StringCalculator {
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
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new Exception("Неверное выражение.");
        }

        String operand1 = parts[0];
        String operator = parts[1];
        String operand2 = parts[2];

        if (!operand1.startsWith("\"")) {
            throw new Exception("Первым аргументом должна быть строка.");
        }

        String strValue = operand1.substring(1, operand1.length() - 1);
        if (strValue.length() > 10) {
            throw new Exception("Длина строки не должна превышать 10 символов.");
        }

        if (operator.equals("+")) {
            return add(strValue, operand2);
        } else if (operator.equals("-")) {
            return subtract(strValue, operand2);
        } else {
            int intValue = getIntegerValue(operand2);
            if (operator.equals("*")) {
                return multiply(strValue, intValue);
            } else if (operator.equals("/")) {
                return divide(strValue, intValue);
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
        if (str.length() > 40) {
            return str.substring(0, 40) + "...";
        }
        return str;
    }
}
