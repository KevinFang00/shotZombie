package com.example.shoot;

import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity implements PauseFragment.PauseListener  {

    int[] a = new int[7];
    int score = 0;
    boolean is_stop = false;
    int count = 30 *100;
    int level = 1;
    int killval = 0;
    ImageView[] imageViews = new ImageView[21];
    ImageView[] buttons = new ImageView[3];
    ImageView pause_button;
    TextView notice_textView;
    TextView count_textView;
    ThreadHandler thread_handler;
    PauseFragment pauseFragment = new PauseFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | SYSTEM_UI_FLAG_IMMERSIVE;
        getWindow().setAttributes(params);

        imageViews[0]   = findViewById(R.id.imageView1);
        imageViews[1]   = findViewById(R.id.imageView2);
        imageViews[2]   = findViewById(R.id.imageView3);
        imageViews[3]   = findViewById(R.id.imageView4);
        imageViews[4]   = findViewById(R.id.imageView5);
        imageViews[5]   = findViewById(R.id.imageView6);
        imageViews[6]   = findViewById(R.id.imageView7);
        imageViews[7]   = findViewById(R.id.imageView8);
        imageViews[8]   = findViewById(R.id.imageView9);
        imageViews[9]   = findViewById(R.id.imageView10);
        imageViews[10]  = findViewById(R.id.imageView11);
        imageViews[11]  = findViewById(R.id.imageView12);
        imageViews[12]  = findViewById(R.id.imageView13);
        imageViews[13]  = findViewById(R.id.imageView14);
        imageViews[14]  = findViewById(R.id.imageView15);
        imageViews[15]  = findViewById(R.id.imageView16);
        imageViews[16]  = findViewById(R.id.imageView17);
        imageViews[17]  = findViewById(R.id.imageView18);
        imageViews[18]  = findViewById(R.id.imageView19);
        imageViews[19]  = findViewById(R.id.imageView20);
        imageViews[20]  = findViewById(R.id.imageView21);
        buttons[0]      = findViewById(R.id.button1);
        buttons[1]      = findViewById(R.id.button2);
        buttons[2]      = findViewById(R.id.button3);
        pause_button    = findViewById(R.id.pause_button);
        notice_textView = findViewById(R.id.notice_textView);
        count_textView  = findViewById(R.id.count_textView);

        buttons[0].setEnabled(false);
        buttons[1].setEnabled(false);
        buttons[2].setEnabled(false);
        pause_button.setEnabled(false);

        ButtonHandler handler = new ButtonHandler();
        buttons[0] .setOnClickListener(handler);
        buttons[1].setOnClickListener(handler);
        buttons[2].setOnClickListener(handler);
        pause_button.setOnClickListener(handler);

        thread_handler = new ThreadHandler();

        StartThread startthread = new StartThread();
        Thread thread = new Thread(startthread);
        thread.start();


    }

    @SuppressLint("HandlerLeak")
    public class ThreadHandler extends Handler {

        @SuppressLint("DefaultLocale")
        public void handleMessage(Message msg){
            Bundle bundle = msg.getData();
            String start = bundle.getString("start");
            String wrong = bundle.getString("wrong");
            String wrongmove = bundle.getString("wrongmove");
            String startcount = bundle.getString("startcount");
            String count = bundle.getString("count");
            String endtext = bundle.getString("endtext");
            String end = bundle.getString("end");
            if(wrongmove != null){
                imageViews[18].setTranslationY(-2 * Integer.parseInt(wrongmove));
                imageViews[19].setTranslationY(-2 * Integer.parseInt(wrongmove));
                imageViews[20].setTranslationY(-2 * Integer.parseInt(wrongmove));
            }
            else if(wrong != null){
                if(notice_textView.getText().toString().equals("遊戲結束"))return;
                buttons[0].setEnabled(true);
                buttons[1].setEnabled(true);
                buttons[2].setEnabled(true);
            }
            else if(start != null){
                imageViews[Integer.parseInt(start)].setVisibility(View.VISIBLE);
            }
            else if(startcount != null){
                if(startcount.equals("")){
                    buttons[0].setEnabled(true);
                    buttons[1].setEnabled(true);
                    buttons[2].setEnabled(true);
                    pause_button.setEnabled(true);
                }
                notice_textView.setText(startcount);
            }
            else if(count != null){
                count_textView.setText(count);
            }
            else if(endtext != null){
                buttons[0].setEnabled(false);
                buttons[1].setEnabled(false);
                buttons[2].setEnabled(false);
                notice_textView.setText(endtext);
            }
            else if(end != null){
                Intent intent = new Intent(new Intent(GameActivity.this , EndActivity.class));
                intent.putExtra("score", String.format("%d" , score));
                startActivity(intent);
                finish();
            }
        }
    }

    public class StartThread implements Runnable{
        @SuppressLint("DefaultLocale")
        public void run(){

            for(int i = 0 ; i < 7 ; i++){
                a[i] = (int)(Math.random()*3);
                Bundle bundle = new Bundle();
                bundle.putString("start", String.format("%d" , 3*i + a[i]));
                Message message = thread_handler.obtainMessage();
                message.setData(bundle);
                thread_handler.sendMessage(message);
            }
            for(int i = 3 ; i > 0 ; i--){
                Bundle bundle = new Bundle();
                bundle.putString("startcount", String.format("%d" , i));
                Message message = thread_handler.obtainMessage();
                message.setData(bundle);
                thread_handler.sendMessage(message);
                long startTime = System.currentTimeMillis();
                while(System.currentTimeMillis() - startTime < 1000);
            }
            Bundle bundle = new Bundle();
            bundle.putString("startcount", "");
            Message message = thread_handler.obtainMessage();
            message.setData(bundle);
            thread_handler.sendMessage(message);
            CountThread countthread = new CountThread();
            Thread thread = new Thread(countthread);
            thread.start();
        }
    }

    public class CountThread implements Runnable{

        @SuppressLint("DefaultLocale")
        public void run(){
            Bundle bundle = new Bundle();
            is_stop = false;
            for(int i = count - 1 ; i >= 0  ; i--){
                if(is_stop) return;
                bundle.putString("count", String.format("%d" , i/100));
                Message message = thread_handler.obtainMessage();
                message.setData(bundle);
                thread_handler.sendMessage(message);
                long startTime = System.currentTimeMillis();
                while(System.currentTimeMillis() - startTime < 10);
                count--;
            }

            EndThread endthread = new EndThread();
            Thread thread = new Thread(endthread);
            thread.start();
        }
    }
    public class WrongThread implements Runnable {
        @SuppressLint("DefaultLocale")
        public void run(){
            long startTime;

            for(int i = 10 ; i <= 100 ; i += 10){
                Bundle bundle2 = new Bundle();
                Message message2 = thread_handler.obtainMessage();
                bundle2.putString("wrongmove", String.format("%d" , i));
                message2.setData(bundle2);
                thread_handler.sendMessage(message2);
                startTime = System.currentTimeMillis();
                while(System.currentTimeMillis() - startTime < 25);
            }
            for(int i = 90 ; i >= 0 ; i -= 10){
                Bundle bundle2 = new Bundle();
                Message message2 = thread_handler.obtainMessage();
                bundle2.putString("wrongmove", String.format("%d" , i));
                message2.setData(bundle2);
                thread_handler.sendMessage(message2);
                startTime = System.currentTimeMillis();
                while(System.currentTimeMillis() - startTime < 25);
            }
            Bundle bundle = new Bundle();
            bundle.putString("wrong", "1");
            Message message = thread_handler.obtainMessage();
            message.setData(bundle);
            thread_handler.sendMessage(message);
        }
    }

    public class EndThread implements Runnable{
        public void run(){
            Bundle bundle = new Bundle();
            bundle.putString("endtext", "遊戲結束");
            Message message = thread_handler.obtainMessage();
            message.setData(bundle);
            thread_handler.sendMessage(message);
            long startTime = System.currentTimeMillis();
            while(System.currentTimeMillis() - startTime <= 1000);
            Bundle bundle2 = new Bundle();
            bundle2.putString("end", "遊戲結束");
            Message message2 = thread_handler.obtainMessage();
            message2.setData(bundle2);
            thread_handler.sendMessage(message2);

        }
    }
    private class ButtonHandler implements View.OnClickListener {
        WrongThread wrong = new WrongThread();
        public void onClick(View v) {
            if(v == buttons[0]){
                if(a[6] == 0) kill();
                else{
                    buttons[0].setEnabled(false);
                    buttons[1].setEnabled(false);
                    buttons[2].setEnabled(false);

                    Thread thread = new Thread(wrong);
                    thread.start();

                }
            }
            else if(v == buttons[1]){
                if(a[6] == 1) kill();
                else{
                    buttons[0].setEnabled(false);
                    buttons[1].setEnabled(false);
                    buttons[2].setEnabled(false);
                    Thread thread = new Thread(wrong);
                    thread.start();
                }
            }
            else if(v == buttons[2]){
                if(a[6] == 2) kill();
                else{
                    buttons[0].setEnabled(false);
                    buttons[1].setEnabled(false);
                    buttons[2].setEnabled(false);
                    Thread thread = new Thread(wrong);
                    thread.start();
                }
            }
            else if(v == pause_button){
                buttons[0].setEnabled(false);
                buttons[1].setEnabled(false);
                buttons[2].setEnabled(false);
                pause_button.setEnabled(false);
                is_stop = true;

                getSupportFragmentManager().beginTransaction().replace(R.id.contain , pauseFragment).commit();
            }
        }
    }

    public void kill(){
        if(43<=killval && killval<=50){
            change(1 , killval % 43);
        }
        else if(93<=killval && killval<=100){
            change(2 , killval % 93);
        }
        else if(143<=killval && killval<=150){
            change(3 , killval % 143);
        }
        else if(193<=killval && killval<=200){
            change(4 , killval % 193);
        }
        for(int i = 0 ; i < 7 ; i++){
            imageViews[3*i + a[i]].setVisibility(View.INVISIBLE);
        }
        for(int i = 5 ; i >= 0 ; i--){
            a[i + 1] = a[i];
        }
        a[0] = (int)(Math.random()*3);
        for(int i = 0 ; i < 7 ; i++){
            imageViews[3*i + a[i]].setVisibility(View.VISIBLE);
        }
        score += level;
        killval++;
    }

    public void change(int type , int position){
        if(position == 7){
            level *= 10;
            return;
        }
        switch (type){
            case 1:
                for(int i = 0 ; i < 3 ; i++)
                    imageViews[3 * position + i].setImageResource(R.drawable.e2);
                break;
            case 2:
                for(int i = 0 ; i < 3 ; i++)
                    imageViews[3 * position + i].setImageResource(R.drawable.e3);
                break;
            case 3:
                for(int i = 0 ; i < 3 ; i++)
                    imageViews[3 * position + i].setImageResource(R.drawable.e4);
                break;
            case 4:
                for(int i = 0 ; i < 3 ; i++)
                    imageViews[3 * position + i].setImageResource(R.drawable.e5);
                break;
        }
    }

    public void back(){
        startActivity(new Intent(GameActivity.this , MainActivity.class));
        finish();
    }

    public void restart(){
        startActivity(new Intent(GameActivity.this , GameActivity.class));
        finish();
    }

    public void keep(){
        buttons[0].setEnabled(true);
        buttons[1].setEnabled(true);
        buttons[2].setEnabled(true);
        pause_button.setEnabled(true);
        CountThread countthread = new CountThread();
        Thread thread = new Thread(countthread);
        thread.start();
        getSupportFragmentManager().beginTransaction().remove(pauseFragment).commit();
    }
}