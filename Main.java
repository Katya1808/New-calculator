import Converter.java.Converter;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Converter converter = new Converter();
        String[] actions = {"+", "-", "/", "*"};
        String[] regexActions = {"\\+", "-", "/", "\\*"};
        Scanner scn = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String input = scn.nextLine();
        System.out.print(Main.calc(input, converter, actions, regexActions));
    }

    public static String calc(String input, Converter converter, String[] actions, String[] regexActions) {
        try {
            //Определяем арифметическое действие:
            int actionIndex = -1;
            for (int i = 0; i < actions.length; i++) {
                if (input.contains(actions[i])) {
                    actionIndex = i;
                    break;
                }
            }
            //Если не нашли арифметического действия, завершаем программу
            if (actionIndex == -1) {
                throw new ArithmeticException("Некорректное выражение");
            }
            //Делим строчку по найденному арифметическому знаку
            String[] data = input.split(regexActions[actionIndex]);
            //Определяем есть ли еще арифм. действие:
            int actionIndex_2 = -1;
            for (int i = 0; i < actions.length; i++) {
                if (data[1].contains(actions[i])) {
                    actionIndex_2 = i;
                    break;
                }
            }
            //Если не нашли арифметического действия, завершаем программу
            if (actionIndex_2 != -1 || data.length > 2) {
                throw new ArithmeticException("Формат математической операции не удовлетворяет заданию - два операнда и один оператор(+,-,/,*)");
            }
            //Определяем, находятся ли числа в одном формате (оба римские или оба арабские)
            if (converter.isRoman(data[0]) == converter.isRoman(data[1])) {
                int value1, value2;
                //Определяем, римские ли это числа
                boolean isRoman = converter.isRoman(data[0]);
                if (isRoman) {
                    //если римские, то конвертируем их в арабские
                    value1 = converter.romanToInt(data[0]);
                    value2 = converter.romanToInt(data[1]);
                } else {
                    //если арабские, конвертируем их из строки в число
                    value1 = Integer.parseInt(data[0]);
                    value2 = Integer.parseInt(data[1]);
                }
                if (!(value1 > 0 && value1 < 11 && value2 > 0 && value2 < 11)) {
                    throw new ArithmeticException("Некорректное выражение");
                }
                //выполняем с числами арифметическое действие
                int result;
                switch (actions[actionIndex]) {
                    case "+":
                        result = value1 + value2;
                        break;
                    case "-":
                        result = value1 - value2;
                        break;
                    case "*":
                        result = value1 * value2;
                        break;
                    default:
                        result = value1 / value2;
                        break;
                }
                if (isRoman) {
                    //если числа были римские, возвращаем результат в римском числе
                    return converter.intToRoman(result);
                } else {
                    //если числа были арабские, возвращаем результат в арабском числе
                    return Integer.toString(result);
                }
            } else {
                throw new ArithmeticException("Числа должны быть в одном формате");
            }
        } catch (ArithmeticException e) {
            System.out.println(e); // вывести исключение, созданное в блоке try
        }
        return input;
    }
}


