package com.siwen;

public class Operator extends Token{

    public Operator (String op) {
        _op = op;
    }

    @Override
    int getType() {
        return OPERATOR;
    }

    @Override
    void print() {
        System.out.println(_op);
    }

    public String op() {
        return _op;
    }


    public int get_precedence() {
        switch (_op) {
            case "+":
            case "-":
                return 2;
            case "*":
            case "/":
                return 3;
            case "^":
                return 4;
            default:
                return -1;
        }
    }


    public int get_associativity() {
        switch (_op) {
            case "+":
            case "-":
            case "*":
            case "/":
                return LEFT;
            case "^":
                return RIGHT;
            default:
                return -1;
        }
    }

    private final String _op;
}
