package ru.ivanov.spring.calculator;

public class Symbol {
    String value;  // сам символ из строки
    CharacterType type; // типа принимаемого символа

    public Symbol(CharacterType type, String value) {
        this.value = value;
        this.type = type;
    }

    public Symbol(CharacterType type, Character value) {
        this.value = value.toString();
        this.type = type;
    }

    @Override
    public String toString() {
        return "Symbol type = " + type + " value = " + value + '\'';
    }

}
