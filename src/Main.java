// Создай консольное приложение "Калькулятор".
// Приложение должно читать из консоли введенные пользователем строки, числа, арифметические операции проводимые между ними и выводить в консоль результат их выполнения.
// Реализуй класс Main с методом public static String calc(String input).
// Метод должен принимать строку с арифметическим выражением между двумя числами и возвращать строку с результатом их выполнения.
// Ты можешь добавлять свои импорты, классы и методы. Добавленные классы не должны иметь модификаторы доступа (public или другие).

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine().trim(); // sc.nextLine(); // ввод сроки выражения
        sc.close();

        System.out.println(calc(str));
    }

    public static String calc(String input) {  //метод, который необходимо реализовать
        Operations operations = new Operations(); // создаем объект класса Operations

        int index = operations.index_znak(input); // возвращаем индекс знака операции в заданном выражении, а также проверяем количество знаков опреаций (если больше 1, то возвращаем -1)
        if (index == -1) { //выброс исключения по количетсву знаков опереаций в заданном выражении
            return "throws Exception";
        }

        String type_znak = String.valueOf(input.charAt(index)); // выделение знака операции в выражении
        String a1 = input.substring(0, index).trim(); // выделение 1-го числа, которое справа от знака
        String a2 = input.substring(index + 1).trim(); // выделение 2-го числа, которое слева от знака

        if (a2.equals("0") && type_znak.equals("/")) { //выброс исключения "деление на 0"
            return "throws Exception";
        }

        String type_a1 = operations.digit(a1); // определяем тип цифр в числе a1 и корректность ввода числа
        String type_a2 = operations.digit(a2); // определяем тип цифр в числе a2 и корректность ввода числа

        if (!(type_a1.equals(type_a2)) || type_a1.equals("false") || type_a2.equals("false")) { //выброс исключения по не соответствию типа цифр и корректности ввода числа
            return "throws Exception";
        }

        int rezult = 0;
        int x1 = 0;
        int x2 = 0;

        if (type_a1.equals(type_a2) && type_a1.equals("arabia")) {
            x1 = Integer.parseInt(a1); // перевод из строкового типа данных в целые
            x2 = Integer.parseInt(a2); // перевод из строкового типа данных в целые
        }
        if (type_a1.equals(type_a2) && type_a1.equals("roma")) {
            x1 = operations.to_arabia(a1); // перевод из римских цифр в арабские
            x2 = operations.to_arabia(a2); // перевод из римских цифр в арабские
        }
        if (x1 == -1 || x2 == -1) { //выброс исключения: число не является римским
            return "throws Exception";
        }

        switch (type_znak) { // расчет значения в арабских числах
            case "+":
                rezult = x1 + x2;
                break;
            case "-":
                rezult = x1 - x2;
                break;
            case "*":
                rezult = x1 * x2;
                break;
            case "/":
                rezult = x1 / x2;
                break;
        }
        String s1;
        if (type_a1.equals("arabia")) {  //вывод результатов, если были арабские числа
            s1 = String.valueOf(rezult);
            return (a1 + " " + type_znak + " " + a2 + " = " + s1);
        }

        if (type_a1.equals("roma")) { //вывод результатов, если были римские числа
            if (rezult < 1) {
                return "throws Exception"; //выброс исключения: ответ меньше 1 для римских чисел
            } else {
                String rezult_roma = operations.to_roma(rezult);
                return (a1 + " " + type_znak + " " + a2 + " = " + rezult_roma);
            }

        }
        return "throws Exception";
    }
}


class Operations {
    public int index_znak(String str) {
        int count_znak = 0, index_znak = 3;
        String[] z = {"+", "-", "*", "/"}; // массив возможных знаков операций в заданном выражении

        for (int i = 0; i < z.length; i++) { //цикл определения индекса знака в введенной строке и количество вхождений знаков в строку
            index_znak += str.indexOf(z[i]); //определяем индекс
            count_znak += str.length() - str.replace(z[i], "").length(); // определяем количество вхождений различных знаков в введенную строку
        }
        if (count_znak == 0 || count_znak > 1) {
            return -1;
        } else {
            return index_znak;
        }
    }
    public String digit(String x) { // проверка чисел на тип цифр, входящих в их состав
        String arabia = "1234567890"; // строка арабских цифр
        String roma = "IVX"; // строка римских цифр
        String dig = "false"; // переменная, которая возвращает из метода digit тип цифр входящих в состав числа
        switch (x.length()) { // проверка на арабские цифры
            case 1:
                if (arabia.contains(x)) {
                    dig = "arabia";
                    return dig;
                }
                break;
            case 2:
                if (x.charAt(0) == '1' && x.charAt(1) == '0') {
                    dig = "arabia";
                    return dig;
                }
        }
        if (x.length() < 4) { // проверка на римские цифры
            for (int i = 0; i < x.length(); i++) {
                if (roma.contains(String.valueOf(x.charAt(i)))) {
                    dig = "roma";
                } else {
                    dig = "false";
                    return dig;
                }
            }
        }
        return dig;
    }
    public int to_arabia(String x) { // перевод из римских в арабские цифры
        String[] digit_roma = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        for ( int i = 0; i < digit_roma.length; i++) {
            if (digit_roma[i].equals(x)) {
                return (i + 1);
            }
        }
        return -1;
    }

    public String to_roma(int x) {//перевод из арабских в римские цифры
        String r = "";
        switch (x / 100) {
            case 1:
                r += "C";
                break;
            case 2:
                r += "CC";
                break;
            case 3:
                r += "CCC";
                break;
            case 4:
                r += "CD";
                break;
            case 5:
                r += "D";
                break;
            case 6:
                r += "DC";
                break;
            case 7:
                r += "DCC";
                break;
            case 8:
                r += "DCCC";
                break;
            case 9:
                r += "CM";
                break;
        }

        switch (x / 10 - x / 100 * 10) {
            case 1:
                r += "X";
                break;
            case 2:
                r += "XX";
                break;
            case 3:
                r += "XXX";
                break;
            case 4:
                r += "XL";
                break;
            case 5:
                r += "L";
                break;
            case 6:
                r += "LX";
                break;
            case 7:
                r += "LXX";
                break;
            case 8:
                r += "LXXX";
                break;
            case 9:
                r += "XC";
                break;
        }
        switch (x % 10) {
            case 1:
                r += "I";
                break;
            case 2:
                r += "II";
                break;
            case 3:
                r += "III";
                break;
            case 4:
                r += "IV";
                break;
            case 5:
                r += "V";
                break;
            case 6:
                r += "VI";
                break;
            case 7:
                r += "VII";
                break;
            case 8:
                r += "VIII";
                break;
            case 9:
                r += "IX";
                break;
        }
        return r;
    }
}