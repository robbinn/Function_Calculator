package com.siwen;

public class Number extends Token {

    public Number (double num) {
        _num = num;
    }

    public Number (String num) {
        if (num.equals("x"))
        {
            _variable = num;
        }

        else
        {
            _num = Double.parseDouble(num);
        }
    }

    public double num() {
        return _num;
    }

    public String var() {
        return _variable;
    }

    @Override
    int getType() {
        return NUMBER;
    }

    @Override
    void print() {
        System.out.println(_num);
    }


    private double _num;
    private String _variable;
}
