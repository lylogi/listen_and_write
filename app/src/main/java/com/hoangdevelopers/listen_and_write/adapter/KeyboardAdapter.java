package com.hoangdevelopers.listen_and_write.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class KeyboardAdapter {
    private InputAdapter inputAdapter;
    private Button _0;
    private Button _1;
    private Button _2;
    private Button _3;
    private Button _4;
    private Button _5;
    private Button _6;
    private Button _7;
    private Button _8;
    private Button _9;
    private Button Q;
    private Button W;
    private Button E;
    private Button R;
    private Button T;
    private Button Y;
    private Button U;
    private Button I;
    private Button O;
    private Button P;
    private Button A;
    private Button S;
    private Button D;
    private Button F;
    private Button G;
    private Button H;
    private Button J;
    private Button K;
    private Button L;
    private Button Z;
    private Button X;
    private Button C;
    private Button V;
    private Button B;
    private Button N;
    private Button M;
    private Button QUOTE;
    private Button DEL;
    private Activity activity;

    public KeyboardAdapter(Activity _activity) {
        activity = _activity;
        _0 = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_0);
        _1 = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_1);
        _2 = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_2);
        _3 = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_3);
        _4 = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_4);
        _5 = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_5);
        _6 = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_6);
        _7 = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_7);
        _8 = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_8);
        _9 = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_9);

        Q = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_q);
        W = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_w);
        E = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_e);
        R = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_r);
        T = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_t);
        Y = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_y);
        U = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_u);
        I = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_i);
        O = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_o);
        P = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_p);
        A = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_a);
        S = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_s);
        D = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_d);
        F = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_f);
        G = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_g);
        H = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_h);
        J = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_j);
        K = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_k);
        L = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_l);
        Z = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_z);
        X = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_x);
        C = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_c);
        V = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_v);
        B = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_b);
        N = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_n);
        M = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_m);
        DEL = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_delete);
        QUOTE = activity.findViewById(com.hoangdevelopers.listen_and_write.R.id.button_quote);
        /////////////////////
        _0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("0");
            }
        });
        _1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("1");
            }
        });
        _2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("2");
            }
        });
        _3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("3");
            }
        });
        _4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("4");
            }
        });
        _5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("5");
            }
        });
        _6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("6");
            }
        });
        _7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("7");
            }
        });
        _8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("8");
            }
        });
        _9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("9");
            }
        });
        Q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("q");
            }
        });
        Q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("q");
            }
        });
        W.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("w");
            }
        });
        E.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("e");
            }
        });
        R.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("r");
            }
        });
        T.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("t");
            }
        });
        Y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("y");
            }
        });
        U.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("u");
            }
        });
        I.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("i");
            }
        });
        O.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("o");
            }
        });
        P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("p");
            }
        });
        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("a");
            }
        });
        S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("s");
            }
        });
        D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("d");
            }
        });
        F.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("f");
            }
        });
        F.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("f");
            }
        });
        G.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("g");
            }
        });
        H.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("h");
            }
        });
        J.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("j");
            }
        });
        K.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("k");
            }
        });
        L.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("l");
            }
        });
        Z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("z");
            }
        });
        X.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("x");
            }
        });
        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("c");
            }
        });
        V.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("v");
            }
        });
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("b");
            }
        });
        N.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("n");
            }
        });
        M.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("m");
            }
        });
        QUOTE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPress("'");
            }
        });
        DEL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelete();
            }
        });
    }

    private void onPress(String key) {
        if (inputAdapter != null) {
            inputAdapter.type(key);
        }
    }

    private void onDelete() {
        if (inputAdapter != null) {
            inputAdapter.pressDelete();
        }
    }

    public InputAdapter getInputAdapter() {
        return inputAdapter;
    }

    public void setInputAdapter(InputAdapter inputAdapter) {
        this.inputAdapter = inputAdapter;
    }
}
