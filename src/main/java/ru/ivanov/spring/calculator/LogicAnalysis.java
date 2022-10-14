package ru.ivanov.spring.calculator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class LogicAnalysis {

    private ArrayList<Symbol> list;

    public LogicAnalysis() {

        this.list = new ArrayList<>();
    }


    public ArrayList<Symbol> analysis(String text) {
        int pos = 0; // позиция на которой сейчас смотрим символ
        while (pos < text.length()) {
            char c = text.charAt(pos);  // символ на данной позиции
            switch (c) {   // проверка какой символ поступает на вход
                case '|':
                    list.add(new Symbol(CharacterType.OR, c));
                    pos++;
                    continue;
                case '&':
                    list.add(new Symbol(CharacterType.AND, c));
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
                case '@':
                    list.add(new Symbol(CharacterType.XOR, c));
                    pos++;
                    continue;
                case 't':
                    list.add(new Symbol(CharacterType.TRUE, c));
                    pos++;
                    continue;
                case 'f':
                    list.add(new Symbol(CharacterType.FALSE, c));
                    pos++;
                    continue;
                default:
                    if (c != ' ') { // если там не пробел, то это ошибка синтаксиса
                        throw new RuntimeException("Ошибка синтаксиса: неизвестный символ " + c);
                    }
                    pos++;  // пропускаем пробелы
            }
        }
        list.add(new Symbol(CharacterType.END, ""));
        return list;
    }


    public boolean expression(Buffer list) { // проверяем конец строки
        Symbol symbol = list.next();
        if (symbol.type == CharacterType.END) {
            return false;
        } else {
            list.back();
            return orXor(list);
        }
    }

    public boolean factor(Buffer list) { // получаем выражение для логических дествий
        Symbol symbol = list.next();
        switch (symbol.type) {

            case LEFT_BRACKET:
                boolean value = orXor(list); // проверяем наличие действий в скобках
                symbol = list.next(); // проверка правой скобки
                if (symbol.type != CharacterType.RIGHT_BRACKET) {
                    throw new RuntimeException("Unexpected token: " + list.getPos());
                }
                return value; // возвращаем результат из скобок

            case TRUE:
                return true; // возвращаем просто число если нет скобок
            case FALSE:
                return false; // возвращаем просто число если нет скобок

            default:
                throw new RuntimeException("Unexpected token: " + list.getPos()); // любой другой случай
        }
    }

    public boolean orXor(Buffer list) { // ор и ксор
        boolean value = and(list); // получаем левый множитель

        while (true) {
            Symbol symbol = list.next();
            switch (symbol.type) {

                case OR:
                    value = value | and(list); // делаем or
                    break;
                case XOR:
                    value = value ^ and(list);
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


    public boolean and(Buffer list) { // умножение
        boolean value = factor(list); // получаем левый множитель
        while (true) {
            Symbol symbol = list.next();
            switch (symbol.type) {

                case AND:
                    value = value & factor(list);
                    break;

                case XOR:
                case OR:
                case END:
                case RIGHT_BRACKET:
                    list.back();
                    return value;

                default:
                    throw new RuntimeException("Unexpected token: " + list.getPos()); // любой другой случай
            }
        }
    }

    public String readLogic() {
        Buffer buffer = new Buffer(list);
        return String.valueOf(expression(buffer));
    }

    public void clearLogic() {
        list.clear();
    }
}
