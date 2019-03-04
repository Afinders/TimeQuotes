package com.timequotes.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timequotes.R;

public class LocalLetterFragment extends Fragment {

    private String TAG = "碎片中得到信件的内容为：";

    public static LocalLetterFragment newInstance(){

        return new LocalLetterFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_local_letter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = view.findViewById(R.id.send_local_letter_TV);
        Bundle bundle = getArguments();
        if (bundle != null){
            String letterContent = bundle.getString("letterContent");
            if (letterContent != null){
                textView.setText(letterContent);
            }
        }
    }
}
