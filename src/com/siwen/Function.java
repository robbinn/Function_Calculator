package com.siwen;

public class Function extends Token{

    public Function (String func) {
        _func = func;
    }

    @Override
    int getType() {
        return FUNCTION;
    }

    @Override
    void print() {
        System.out.println(func());
    }


    public String func() {
        return _func;
    }

    private final String _func;
}
