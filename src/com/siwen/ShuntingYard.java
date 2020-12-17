package com.siwen;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Iterator;
import java.util.Stack;

public class ShuntingYard {

    public static Queue<Token> tokenize (Queue<StringToken> userIn) {
        Queue<Token> infix = new LinkedList<>();

        for (StringToken temp : userIn) {
            String _token = temp.getToken();
            String _stringType = temp.stringType();

//            if (_token.equals("x")) {
//                returnThis.add(new Number("x"));
//            }

            //TODO StringToken class has no FUNCTION string type yet
            if (_stringType.equals("FUNCTION")) {
                infix.add(new Function(_token));
            } else if (_stringType.equals("OPERATOR") || _stringType.equals("PARENTHESIS")) {
                infix.add(new Operator(_token));
            } else {
                infix.add(new Number(_token));
            }

        }

        return infix;
    }

    public static Queue<Token> translate (Queue<Token> infix) {
        Stack<Token> container = new Stack<>();
        Queue<Token> postfix = new LinkedList<>();

        while (!infix.isEmpty()) {
            Token temp = infix.peek();
            if(temp.getType() == Token.NUMBER) {
                postfix.add(temp);
                infix.poll();
            }

            else if(temp.getType() == Token.FUNCTION) {
                container.push(temp);
                infix.poll();
            }

            else if(temp.getType() == Token.OPERATOR) {
                //if the operator at the top of the operator stack has greater
                //or equal precedence
                while(!container.empty() && container.peek().getType()==Token.OPERATOR &&
                        ((Operator)container.peek()).get_precedence() >=
                                ((Operator)infix.peek()).get_precedence() &&
                        !((Operator) container.peek()).op().equals("(")) {

                    if ( ((Operator)container.peek()).get_associativity() == Token.RIGHT &&
                            ((Operator)infix.peek()).get_associativity() == Token.RIGHT) {
                        //push it onto the operator stack
                        container.push(infix.poll());
                    }

                    else {
                        //pop operators from the operator stack onto the output queue
                        postfix.add(container.pop());

                        //push it onto the operator stack
                        container.push(infix.poll());
                    }
                }

                //if "(", push into the stack
                if ( ((Operator)infix.peek()).op().equals("(")){
                    container.push(infix.poll());
                }

                else if(((Operator)infix.peek()).op().equals(")")){
                    //pop the ")" and discard it.
                    infix.remove();

                    while (!((Operator)container.peek()).op().equals("(")){
                        postfix.add(container.pop());
                    }

                    //pop the "(" and discard it.
                    container.pop();
                }

                else if(temp.getType() == Token.OPERATOR) {
                    container.push(infix.poll());
                }
            }
        }

        while (!container.empty()) {
            postfix.add(container.pop());
        }

        return postfix;
    }

    //private Queue<Token> _postfix;

}
