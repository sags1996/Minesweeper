package com.example.admin.minesweeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {
       EditText editText;
    TextView nameTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        setTitle("*MINESWEEPER*");
        editText=(EditText)findViewById(R.id.nameEditText);
        Button startButton=(Button)findViewById(R.id.startbutton);
        nameTextView = (TextView)findViewById(R.id.textView);
        final SharedPreferences sharedPreferences= getSharedPreferences("Minesweeper",MODE_PRIVATE);
        String name= sharedPreferences.getString("username",null);
        if(name==null){
            nameTextView.setText(" *WELCOME USER *");
        }
        else{
            nameTextView.setText("* WELCOME"+name+"*");
        }
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= editText.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(StartActivity.this,"Enter Name!!",Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putString("username",name);
                editor.commit();
                Intent i= new Intent(StartActivity.this,MainActivity.class);
                i.putExtra("user_name",name);
                startActivity(i);

            }
        });
    }
}
