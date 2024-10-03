package com.example.shoot;

import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class EndActivity extends AppCompatActivity {
    private final String file_name = "a.txt";
    int score;
    String scoreStr;

    ImageButton regame_button;
    ImageButton back_button;
    TextView score_textView;
    TextView highest_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        regame_button = (ImageButton) findViewById(R.id.regame_button);
        back_button = (ImageButton) findViewById(R.id.back_button);
        score_textView = (TextView)findViewById(R.id.score_textView);
        highest_textView = (TextView)findViewById(R.id.highest_textView);

        ButtonHandler handler = new ButtonHandler();
        regame_button.setOnClickListener(handler);
        back_button.setOnClickListener(handler);

        getscore();
        load();
    }

    private class ButtonHandler implements View.OnClickListener {
        public void onClick(View v) {
            if(v == regame_button){
                startActivity(new Intent(EndActivity.this , GameActivity.class));
                finish();
            }
            else if (v == back_button){
                startActivity(new Intent(EndActivity.this , MainActivity.class));
                finish();
            }
        }
    }
    private void getscore(){
        Bundle extra = getIntent().getExtras();
        scoreStr = extra.getString("score");
        score_textView.setText("分數\n" + scoreStr);
        score = Integer.parseInt(scoreStr);
    }
    private void load(){
        try{
            File directory = getExternalFilesDir(null);
            File file = new File(directory, file_name);
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
            String highest = (String) input.readObject();
            if(highest != null){
                if(Integer.parseInt(highest) > score){
                    highest_textView.setText("最高分: " + highest);
                }
                else{
                    highest_textView.setText("最高紀錄!!");
                    save();
                }
            }
            else{
                highest_textView.setText("最高紀錄!!");
                save();
            }
            if(input != null)
                input.close();
        }catch(Exception e){
            highest_textView.setText("最高紀錄!!");
            save();

        }
    }

    private void save(){
        try{
            File directory = getExternalFilesDir(null);
            File file = new File(directory, file_name);
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
            output.writeObject(String.format("%d" , score));
            output.close();
        }catch (Exception e){
            highest_textView.setText("錯誤");
        }
    }
}