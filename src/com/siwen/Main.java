package com.siwen;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        String userIn = "2+1*5";
        char[] charStr = userIn.toCharArray();
        Queue<StringToken> queue = new LinkedList<>();

        StringTokenizer stk = new StringTokenizer(charStr);

        do {
            StringToken t = stk.extractToken();
            queue.add(t);
            //System.out.printf("%-14s%s%n", t.stringType(),t);
        } while (stk.more());

        Queue<Token> std = ShuntingYard.translate(ShuntingYard.tokenize(queue));

        std.poll().print();
        std.poll().print();
        std.poll().print();
        std.poll().print();
        std.poll().print();
//        std.poll().print();
//        std.poll().print();

    }

}
