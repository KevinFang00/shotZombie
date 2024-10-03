package com.example.shoot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class PauseFragment extends Fragment {
    View view;
    ImageButton keep_button;
    ImageButton restart_button;
    ImageButton backmenu_button;
    PauseListener pauseListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pause, container, false);

        keep_button = view.findViewById(R.id.keep_button);
        restart_button = view.findViewById(R.id.restart_button);
        backmenu_button = view.findViewById(R.id.backmenu_button);

        ButtonHandler handler = new ButtonHandler();
        keep_button.setOnClickListener(handler);
        restart_button.setOnClickListener(handler);
        backmenu_button.setOnClickListener(handler);
        return view;
    }

    public interface PauseListener{
        public void back();
        public void restart();
        public void keep();
    }
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            pauseListener = (PauseListener) context;
        }catch(ClassCastException e){}// End of try-catch
    }

    private class ButtonHandler implements View.OnClickListener{
        public void onClick(View v){
            if(v == keep_button){
                pauseListener.keep();
            }
            else if(v == restart_button){
                pauseListener.restart();
            }
            else if(v == backmenu_button){
                pauseListener.back();
            }
        }
    }
}