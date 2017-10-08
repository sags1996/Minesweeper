package com.example.admin.minesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener {
    MyButton button[][];//button object
    LinearLayout rows[];//create horizontal layout
    LinearLayout MainLayout;//vertical main layout
    int n=9;
    int countMines;
    int MineConst=10;
    boolean gameOver;
    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainLayout=(LinearLayout)findViewById(R.id.activity_main);
        Intent i2=getIntent();
        userName=i2.getStringExtra("user_name");
        Toast.makeText(this,"Name:"+userName,Toast.LENGTH_SHORT).show();
        setUpBoard();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mainenu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id= item.getItemId();
        if(id==R.id.newGame){
            setUpBoard();
        }
        return true;
    }
    public void setUpBoard() {
        button = new MyButton[n][n];
        rows = new LinearLayout[n];
        gameOver = false;
        MainLayout.removeAllViews();
        countMines = 0;
        //set up board
        for (int i = 0; i < n; i++) {
            rows[i] = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            params.setMargins(1, 1, 1, 1);
            rows[i].setLayoutParams(params);
            rows[i].setOrientation(LinearLayout.HORIZONTAL);
            MainLayout.addView(rows[i]);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                button[i][j] = new MyButton(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                params.setMargins(1, 1, 1, 1);
                button[i][j].setLayoutParams(params);
                button[i][j].setText("");
                button[i][j].x = i;
                button[i][j].y = j;
                button[i][j].setOnClickListener(this);
                button[i][j].setOnLongClickListener(this);
                rows[i].addView(button[i][j]);
            }
        }
         generate();
    }   //placing mines
        Random r = new Random();
  public  void generate(){

        int row=0;//mine row
        int col=0;//mine col
   int [] srow ={-1,0,1,1,1,0,-1,-1};
      int [] scol={-1,-1,-1,0,1,1,1,0};
        for (int i = 0; i <MineConst; i++) {
            Random r = new Random();
            row = r.nextInt(n);
            col = r.nextInt(n);
            if (button[row][col].MineStatus == false) {
               // button[row][col].symbol = "*";
               countMines++;

              button[row][col].MineStatus = true;
            }
            else{
               i--;
            }}
//ALTERNATE BETTER METHOD AND CAN BE USED INSTEAD
          /* for(int q=0; q<8;q++){
            int x2= row+srow[q];
            int y2= col+scol[q];
                if(Range(x2,y2,n)){
                    if(button[x2][y2].MineStatus==true){
                        continue;
                    }
                    else{
                        button[x2][y2].count_bomb++;
                    }
                }}}*/
        for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (button[i][j].MineStatus == true) {
                        //if (i - 1 >= 0 && j - 1 >= 0) {
                        if(Range(i-1,j-1,n)){
                            button[i - 1][j - 1].count_bomb++;
                        }
                      //  if (i - 1 >= 0 && j < n ) {
                        if(Range(i-1,j,n)){
                            button[i - 1][j].count_bomb++;
                        }
                       // if (i - 1 >= 0 && j + 1 < n) {
                        if(Range(i-1,j+1,n)){
                            button[i - 1][j + 1].count_bomb++;
                        }
                       // if (i < n && j + 1 < n && i>=0) {
                        if(Range(i,j+1,n)){
                            button[i][j + 1].count_bomb++;
                        }
                       // if (i + 1 < n && j + 1 < n) {
                        if(Range(i+1,j+1,n)){
                            button[i + 1][j + 1].count_bomb++;
                        }
                        //if (i + 1 < n && j < n && j>=0)
                        if(Range(i+1,j,n)){
                            button[i + 1][j].count_bomb++;
                        }
                       // if (i + 1 < n && j - 1 >= 0) {
                        if (Range(i+1,j-1,n)){
                            button[i + 1][j - 1].count_bomb++;
                        }

                     //   if (i < n && j - 1 >= 0 && i>=0) {
                        if(Range(i,j-1,n)){
                            button[i][j - 1].count_bomb++;
                        }
                    }

                }}}





    @Override
    public void onClick(View v) {
        MyButton b=(MyButton) v;
        if(gameOver){
            return;
        }
        if(b.isClicked){
            Toast.makeText(this,"Invalid Move",Toast.LENGTH_SHORT).show();
            return;
      }
      b.isClicked=true;
        if(b.MineStatus==true){
            b.setText("*");
            gameOver=true;
            Toast.makeText(this,"Game Over!!!",Toast.LENGTH_SHORT).show();
            RevealBoard();
            return;
        }
        int status=checkGameStatus();
        if(status==1){
            Toast.makeText(this,"WINNER!!!",Toast.LENGTH_SHORT).show();
            gameOver=true;
           return;

        }

        if(b.count_bomb!=0){
            b.setText(""+b.count_bomb);
            b.isClicked=true;
        }
    /* if(b.count_bomb==0){
            b.setText("ww");}*/

        //Recursive function for zero mines
      if(b.count_bomb==0){
            DispTile(b);
        }



    }

    private void DispTile(MyButton p) {
       /*if(Range(x,y,n)){
            return;*/

       // if(button[x][y].count_bomb!=0){
         //  return;
        //}
    /*    if(button[x][y].isClicked==true || button[x][y].isflag==true || button[x][y].MineStatus==true){
            return;
        }
        button[x][y].setText(""+button[x][y].count_bomb);
        button[x][y].isClicked=true;

        DispTile(x+1,y);
        DispTile(x-1,y);
        DispTile(x,y+1);
        DispTile(x,y-1);
*/
    int i= p.x;
        int j= p.y;
        p.setText(""+p.count_bomb);
        int [] srow ={-1,0,1,1,1,0,-1,-1};
        int [] scol={-1,-1,-1,0,1,1,1,0};
        for(int q=0; q<8;q++){
            int x2= i+srow[q];
            int y2= j+scol[q];
            if (Range(x2,y2,n)){
                if(button[x2][y2].isflag==true){
                    return;
                }
                else if(button[x2][y2].count_bomb!=0 && ! button[x2][y2].isClicked){
                   button[x2][y2].setText(""+button[x2][y2].count_bomb);
                    button[x2][y2].isClicked=true;
                   continue;
                }
               else  if(button[x2][y2].count_bomb==0 && button[x2][y2].isClicked==false){
                    button[x2][y2].setText(""+button[x2][y2].count_bomb);
                    button[x2][y2].isClicked=true;
                    DispTile(button[x2][y2]);
                }


            } }}

  /*if(button[rx][cy].MineStatus==true || button[rx][cy].isflag==true){
      return;
  }
  button[rx][cy].setText(""+button[rx][cy].count_bomb);
        if(button[rx][cy].count_bomb!=0){
            return;
        }
        for(int row=0;row<3;row++){
            for(int col=0;col<3;col++){
                if(  (rx+row-1>=0) &&(cy +col-1 >=0) && (rx+row-1<n) && (cy+col-1<n)&& button[rx+row-1][cy+col-1].isClicked==false ){
                    DispTile(rx+row-1,cy+col-1);
                }
            }
        }
  return;*/


    public boolean Range(int x2, int y2, int n) {
        if(x2>=0&&x2<n&&y2>=0&&y2<n){
            return true;
        }
        return false;
    }

    private int checkGameStatus(){
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(button[i][j].MineStatus==false){
                    if(button[i][j].isClicked==false){
                        return 0;
                    }
                }

                }
            }
            return 1;
        }

//Placing flags
    @Override
    public boolean onLongClick(View v) {
        MyButton b1=(MyButton) v;
        if(gameOver==true){
            return false;
        }
        if(b1.isClicked==false) {
            if (b1.isflag == false) {
                b1.setText("F");
                b1.isflag = true;

            } else {
                b1.setText("");
                b1.isflag=false;

            }
        }
        else{
            Toast.makeText(this,"Invalid Move!",Toast.LENGTH_SHORT);

        }
       return true;
    }


public void RevealBoard(){
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            if(button[i][j].MineStatus==true && button[i][j].isClicked==false){
                button[i][j].setText("*");
            }
            }
        }
    }
}



