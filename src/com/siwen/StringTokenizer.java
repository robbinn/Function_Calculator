package com.siwen;

public class StringTokenizer {

    final static int MAX_COLUMNS = 150;
    final static int MAX_ROWS = 42;
    final static int MAX_BUFFER = 150;

    public StringTokenizer() {
        _pos = 0;
        _bufferLength = 0;
        makeTable();
        _token = "";
    }

    public StringTokenizer(char[] userInput) {
        _pos = 0;
        _buffer = userInput.clone();
        _bufferLength = _buffer.length;
        makeTable();
        _token = "";
    }

    public StringToken extractToken() {
        _successState = 0;
        _token = "";
        getToken();

        return new StringToken(_token,_successState);
    }

    public void set_token(String token) {
        this._token = token;
    }

    public String show_token() {
        return _token;
    }

    public boolean done() {
        return _pos >= _bufferLength;
    }

    public boolean more() {
        return _pos < _bufferLength;
    }

    public void setString(char[] userInput) {
        _buffer = userInput.clone();
    }


    private void makeTable(){
        for(int i=0;i<MAX_ROWS;i++) {
            for(int j=0;j<MAX_COLUMNS;j++) {
                _table[i][j]=-1;
            }
        }

        //success state
        _table[0][0] = 0;
        _table[1][0] = 1;
        _table[2][0] = 1;
        _table[5][0] = 1;
        _table[7][0] = 1;

        //Num 0-9
        for (int i=48; i<=57; i++)
        {
            _table[0][i] = 1;
            _table[1][i] = 1;
            _table[2][i] = 1;
        }

        //dot followed by numbers
        _table[1]['.'] = 2;

        //operators
        _table[0]['+'] = 5;
        _table[0]['-'] = 5;
        _table[0]['*'] = 5;
        _table[0]['/'] = 5;
        _table[0]['^'] = 5;

        //parenthesis
        _table[0]['('] = 7;
        _table[0][')'] = 7;

//        //success state, column 0.
//        _table[0][0] = 0;
//        _table[1][0] = 1;
//        _table[2][0] = 0;
//        _table[3][0] = 1;
//        _table[11][0] = 1;
//        _table[12][0] = 0;
//        _table[13][0] = 1;
//        _table[14][0] = 0;
//        _table[15][0] = 0;
//        _table[16][0] = 0;
//        _table[17][0] = 1;
//        _table[31][0] = 1;
//        _table[41][0] = 1;
//
//        //Alpha A-Z
//        for (int i = 65; i<=90 ; i++)
//        {
//            _table[0][i] = 1;
//            _table[1][i] = 1;
//            _table[2][i] = 3;
//            _table[3][i] = 3;
//        }
//
//        //Alpha a-z
//        for (int i = 97; i<=122; i++)
//        {
//            _table[0][i] = 1;
//            _table[1][i] = 1;
//            _table[2][i] = 3;
//            _table[3][i] = 3;
//        }
//
//        //Num 0-9
//        for (int i=48; i<=57; i++)
//        {
//            _table[0][i] = 11;
//            _table[11][i] = 11;
//            _table[12][i] = 13;
//            _table[13][i] = 13;
//            _table[14][i] = 15;
//            _table[15][i] = 15;
//            _table[16][i] = 17;
//            _table[17][i] = 17;
//        }
//
//        //punctuation 1
//        for (int i=33; i<=47; i++)
//        {
//            _table[0][i] = 31;
//        }
//
//        //punctuation 2
//        for (int i=58; i<=64; i++)
//        {
//            _table[0][i] = 31;
//        }
//
//        //punctuation 3
//        for (int i=91; i<=96; i++)
//        {
//            _table[0][i] = 31;
//        }
//
//        //punctuation 4
//        for (int i=123; i<=126; i++)
//        {
//            _table[0][i] = 31;
//        }
//
//        //punctuation to recognize phone and double numbers.
//        _table[1]['\''] = 2;
//        _table[11]['-'] = 14;
//        _table[15]['-'] = 16;
//        _table[11]['.'] = 12;
//
//        //space
//        _table[0][9] = 41; //TAB
//        _table[0][11] = 41; //VT
//        _table[0][32] = 41; //space
//        _table[41][9] = 41; //TAB
//        _table[41][11] = 41; //VT
//        _table[41][32] = 41; //space
//
//        _table[31][61] = 31;

    }

    public boolean getToken(){
        if(_pos>_bufferLength)
            return false;

        //record the last success state
        int record = 0;

        //always check state from 0.
        int state = 0;

        boolean flag = false;

        while(_pos != _bufferLength && _table[state][_buffer[_pos]] != -1) {
            flag = true;
            _token += _buffer[_pos];
            state = _table[state][_buffer[_pos]];
            _pos++;

            //if state get a very large number, means it is out of map.
            //which means it is a unknown element.
            if (state>MAX_ROWS)
            {
                _successState = -1;
                _pos++;
                return true;
            }

            if(_table[state][0] == 1)
            {
                record = _pos;
                _successState = state;
            }
        }


        if (_table[state][0] != 1)
        {
            //if there is a token generated already
            if(record!=0)
            {
                //_pos go back to the last success state
                _pos = record;
                _token = _token.substring(0,record);
            }

            //if record = 0, means there is no available token
            else
            {
                //if the first character is the unknown character.
                if(!flag)
                {
                    _token = Character.toString(_buffer[_pos]);
                    _pos++;

                    //make the type is -1
                    _successState = -1;
                }
            }

        }
        return true;
    }

    public int get_successState() {
        return _successState;
    }

    public void set_successState(int _successState) {
        this._successState = _successState;
    }


    private char[] _buffer = new char[MAX_BUFFER];
    private int _pos;
    private static final int[][] _table = new int[MAX_ROWS][MAX_COLUMNS];
    private final int _bufferLength;
    private String _token;
    private int _successState;
}
