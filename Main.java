import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите пример: ");
        String input = scanner.nextLine();


        Pattern pattern = Pattern.compile("\"([^\"]*)\"\\s*([-+*/])\\s*([^\\s]+)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String firstOperand = matcher.group(1);
            String operator = matcher.group(2);
            String secondOperand = matcher.group(3);


            String[] parts = input.split("[+*/-]");

            // Удаление пробелов
            for (int i = 0; i < parts.length; i++) {
                parts[i] = parts[i].trim();
            }

            String a = parts[0].replaceAll("\"", "");
            String b = parts[1].replaceAll("\"", "");


            if (a.length() > 10 || b.length() > 10 || firstOperand.length() > 10 || secondOperand.length() > 10) {
                System.out.println("Строка должна быть длиной не более 10 символов.");
                return;
            }

            boolean isNumericB = false;


            try {
                Integer.parseInt(parts[0]);
                System.out.println("Первым аргументом должна быть строка, а не число.");
                return;
            } catch (NumberFormatException ex) {
            }

            try {
                int numB = Integer.parseInt(parts[1]);
                if (numB < 1 || numB > 10) {
                    System.out.println("Число B должно быть в диапазоне от 1 до 10.");
                    return;
                }
            } catch (NumberFormatException ex) {
            }


            int n;
            if (operator.equals("+")) {
                String result = plus(a, b);
                System.out.println("Результат: " + result);
            } else if (operator.equals("/")) {

                try {
                    n = Integer.parseInt(secondOperand);
                    if (n < 1 || n > 10) {
                        System.out.println("Число B должно быть в диапазоне от 1 до 10.");
                        return;
                    } else {
                    String result = division(firstOperand, n);
                    System.out.println("Результат: " + "\"" + result + "\"");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: строка '" + secondOperand + "' не является числом.");
                }
            } else if (operator.equals("*")) {
                try {
                   // int n = Integer.parseInt(b);
                    n = Integer.parseInt(secondOperand);
                    if (n < 1 || n > 10) {
                        System.out.println("Второй множитель должен быть в диапазоне от 1 до 10.");
                        return;
                    } else {


                    String result = multiply(firstOperand, n);

                    if (result.length() > 40) {
                        System.out.println("Результат: \"" + result.substring(0, 40) + "..." + "\"");
                    } else {
                        System.out.println("Результат: " +  "\"" + result + "\"");
                    }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: '" + secondOperand + "' не является допустимым числом.");
                }
            } else if (operator.equals("-")) {
                secondOperand = secondOperand.replaceAll("\"", "");
                String result = minus(firstOperand, secondOperand);
                System.out.println("Результат: " + result);
            }
        } else
            System.out.println("Первым аргументом должна быть строка, а не число.");



    }







    private static String plus(String a, String b) {
        return "\"" + (a + b) + "\"";
    }

    private static String division(String a, int b) {
        if (b <= 0) {
            throw new IllegalArgumentException("Делитель должен быть больше нуля.");
        }
        int length = a.length() / b;
        return a.substring(0, length);
    }

    private static String multiply(String a, int b) {
        if (b <= 0) {
            throw new IllegalArgumentException("Множитель должен быть больше нуля.");
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < b; i++) {
            result.append(a);
        }
        return result.toString();
    }

    private static String minus(String a, String b) {
        if (a.contains(b)) {
            return a.replace(b, "");
        } else {
            return  "\"" + a + "\"";
        }
    }



    private static String getOperator(String input) {
        if (input.contains("+")) {
            return "+";
        } else if (input.contains("-")) {
            return "-";
        } else if (input.contains("*")) {
            return "*";
        } else if (input.contains("/")) {
            return "/";
        } else {
            return "Неизвестный оператор";
        }
    }
}