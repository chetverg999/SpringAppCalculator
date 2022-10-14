package ru.ivanov.spring.calculator;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;

@Component
public class LexicalAnalysis {

    private final ArrayList<Symbol> list;

    public LexicalAnalysis() {
        this.list = new ArrayList<>();
    }

    public ArrayList<Symbol> analysis(String text) {
        int pos = 0; // позиция на которой сейчас смотрим символ
        while (pos < text.length()) {
            char c = text.charAt(pos);  // символ на данной позиции
            switch (c) {   // проверка какой символ поступает на вход
                case '+':
                    list.add(new Symbol(CharacterType.PLUS, c));
                    pos++;
                    continue;
                case '-':
                    list.add(new Symbol(CharacterType.MINUS, c));
                    pos++;
                    continue;
                case '=':
                    list.add(new Symbol(CharacterType.EQUALLY, c));
                    pos++;
                    continue;
                case '*':
                    list.add(new Symbol(CharacterType.MULTIPLICATION, c));
                    pos++;
                    continue;
                case '/':
                    list.add(new Symbol(CharacterType.DIVISION, c));
                    pos++;
                    continue;
                case '(':
                    list.add(new Symbol(CharacterType.LEFT_BRACKET, c));
                    pos++;
                    continue;
                case ')':
                    list.add(new Symbol(CharacterType.RIGHT_BRACKET, c));
                    pos++;
                    continue;
                case '^':
                    list.add(new Symbol(CharacterType.DEGREE, c));
                    pos++;
                    continue;
                default:
                    if (c <= '9' && c >= '0') {  // случай встречи числа
                        StringBuilder sb = new StringBuilder();
                        do {
                            sb.append(c); // добавляем цифру в стрингбилдер
                            pos++; // смещаем позицию вправо
                            if (pos >= text.length()) {  // проверка на конец строки
                                break;
                            }
                            c = text.charAt(pos); // получаем символ со следующей позиции
                        } while (c <= '9' && c >= '0');  // склеиваем все цифры в стрингбилдер
                        list.add(new Symbol(CharacterType.NUMBER, sb.toString()));  // записываем стрингбилдер как одно число
                    } else {
                        if (c != ' ') { // если там не пробел, то это ошибка синтаксиса
                            throw new RuntimeException("Ошибка синтаксиса: неизвестный символ " + c);
                        }
                        pos++;  // пропускаем пробелы
                    }

            }
        }
        list.add(new Symbol(CharacterType.END, ""));
        return list;
    }


    public int expression(Buffer list) { // проверяем конец строки
        Symbol symbol = list.next(); //
        if (symbol.type == CharacterType.END) {
            return 0;
        } else {
            list.back();
            return plusMinus(list);
        }
    }

    public int factor(Buffer list) { // получаем выражение для арифметических дествий
        Symbol symbol = list.next();
        switch (symbol.type) {

            case LEFT_BRACKET:
                int value = plusMinus(list); // проверяем наличие действий в скобках (умножение и деление)
                symbol = list.next(); // проверка правой скобки
                if (symbol.type != CharacterType.RIGHT_BRACKET) {
                    throw new RuntimeException("Unexpected token: " + list.getPos());
                }
                return value; // возвращаем результат из скобок

            case NUMBER:
                return Integer.parseInt(symbol.value); // возвращаем просто число если нет скобок

            default:
                throw new RuntimeException("Unexpected token: " + list.getPos()); // любой другой случай
        }
    }

    public int plusMinus(Buffer list) { // сложение и вычитание
        int value = divMult(list); // получаем левый множитель

        while (true) {
            Symbol symbol = list.next();
            switch (symbol.type) {

                case PLUS:
                    value = value + divMult(list); // складываем с перемноженными, поделенными либо просто с числами
                    break;
                case MINUS:
                    value = value - divMult(list);
                    break;

                case END:
                case RIGHT_BRACKET:

                    list.back();
                    return value;

                default:
                    throw new RuntimeException("Unexpected token: " + list.getPos()); // любой другой случай
            }
        }
    }

    public int degree(Buffer list) { // сложение и вычитание
        int value = factor(list); // получаем левый множитель

        while (true) {
            Symbol symbol = list.next();
            switch (symbol.type) {

                case DEGREE:
                    BigInteger a = new BigInteger(String.valueOf(value));
                    value = a.pow(factor(list)).intValue();
                    break;

                case END:
                case RIGHT_BRACKET:
                case PLUS:
                case MINUS:
                case DIVISION:
                case MULTIPLICATION:
                    list.back();
                    return value;

                default:
                    throw new RuntimeException("Unexpected token: " + list.getPos()); // любой другой случай
            }
        }
    }

    public int divMult(Buffer list) { // умножение и деление
        int value = degree(list); // получаем левый множитель
        while (true) {
            Symbol symbol = list.next();
            switch (symbol.type) {

                case MULTIPLICATION:
                    value = value * degree(list);
                    break;

                case DIVISION:
                    value = value / degree(list);
                    break;

                case END:
                case RIGHT_BRACKET:
                case PLUS:
                case MINUS:
                    list.back();
                    return value;

                default:
                    throw new RuntimeException("Unexpected token: " + list.getPos()); // любой другой случай
            }
        }
    }

    public String read() {
        Buffer buffer = new Buffer(list);
        return String.valueOf(expression(buffer));
    }

    public void clear() {
        list.clear();
    }
}
