package com.siwen;

public abstract class Token {
    static int NUMBER = 1;
    static int OPERATOR = 2;
    static int FUNCTION = 3;
    static int UNKNOWN = -1;

    static int LEFT = 7;
    static int RIGHT = 8;

    abstract int getType();
    abstract void print();

}
