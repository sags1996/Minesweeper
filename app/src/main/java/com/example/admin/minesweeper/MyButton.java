package com.example.admin.minesweeper;

import android.content.Context;
import android.widget.Button;

/**
 * Created by Admin on 6/16/2017.
 */

public class MyButton extends android.support.v7.widget.AppCompatButton {

    public int count_bomb=0;
    public String symbol="";
    public boolean isClicked=false;
    public boolean isflag;
    public int x;
    public int y;
    public boolean MineStatus;
    public MyButton(Context context) {
        super(context);
    }
}