package com.example.shoot;

import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    ImageButton start_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start_button = (ImageButton) findViewById(R.id.start_button);

        ButtonHandler handler = new ButtonHandler();
        start_button.setOnClickListener(handler);
    }

    private class ButtonHandler implements View.OnClickListener{
        public void onClick(View v){
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent);
            finish();
        }
    }
}