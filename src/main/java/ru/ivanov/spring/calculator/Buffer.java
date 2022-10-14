package ru.ivanov.spring.calculator;

import java.util.ArrayList;

public class Buffer {
        private int pos;

        public ArrayList<Symbol> list;

        public Buffer(ArrayList<Symbol> list) {
            this.list = list;
        }

        public Symbol next() {
            return list.get(pos++);
        }

        public void back() {
            pos--;
        }

        public int getPos() {
            return pos;
        }
    }
