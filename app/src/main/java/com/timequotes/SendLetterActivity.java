package com.timequotes;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.timequotes.bean.LetterBean;
import com.timequotes.fragment.AppLetterFragment;
import com.timequotes.fragment.EMailLetterFragment;
import com.timequotes.fragment.LocalLetterFragment;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class SendLetterActivity extends AppCompatActivity {

    private String TAG = "发送信件";
    private final List<String> spinnerData = new ArrayList<>(Arrays.asList("本地信件", "邮箱信件", "应用信件"));
    private Bundle bundle;
    private TextView datePickerTv;
    private String letterReceiveTime;
    private LetterBean letter;
    private int receiveType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_letter);

        Toolbar toolbar = findViewById(R.id.send_letter_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        NiceSpinner spinner = findViewById(R.id.send_letter_spinner);
        spinner.attachDataSource(spinnerData);
        spinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                receiveType = i;
                loadFragment(i);
            }
        });
        FloatingActionButton sendLetterFab = findViewById(R.id.send_letter_fab);
        sendLetterFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2019/2/27 发送信件
                sendLetter(receiveType);
                finish();
            }
        });
        datePickerTv = findViewById(R.id.send_letter_date_picker_TV);
        datePickerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseDate();
            }
        });

        Intent intent = getIntent();
        letter = (LetterBean) intent.getSerializableExtra("letter");
        bundle = new Bundle();
        bundle.putString("letterContent", letter.getLetterContent());


        if (savedInstanceState == null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            LocalLetterFragment localLetterFragment = LocalLetterFragment.newInstance();
            localLetterFragment.setArguments(bundle);
            fragmentTransaction.add(R.id.send_letter_fl, localLetterFragment);
            fragmentTransaction.commit();
        }
    }

    private void chooseDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                letterReceiveTime = i+"年"+(i1+1)+"月"+i2+"日";
                datePickerTv.setText(letterReceiveTime);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void sendLetter(int receiveTypeId){
        String type = spinnerData.get(receiveTypeId);
        switch (type){
            case "本地信件":
                letter.setReceiveType(0);
                break;
            case "邮箱信件":
                letter.setReceiveType(1);
                break;
            case "应用信件":
                letter.setReceiveType(2);
                break;
        }
        Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
    }

    private void loadFragment(int fragmentId){
        String fragmentName = spinnerData.get(fragmentId);
        Log.d(TAG, "loadFragment: 当前碎片的名字："+fragmentName);
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (fragmentName){
            case "本地信件":
                LocalLetterFragment localLetterFragment = LocalLetterFragment.newInstance();
                localLetterFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.send_letter_fl, localLetterFragment);
                break;
            case "邮箱信件":
                EMailLetterFragment mailLetterFragment = EMailLetterFragment.newInstance();
                mailLetterFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.send_letter_fl, mailLetterFragment);
                break;
            case "应用信件":
                AppLetterFragment appLetterFragment = AppLetterFragment.newInstance();
                appLetterFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.send_letter_fl, appLetterFragment);
                break;
        }
        fragmentTransaction.commit();
    }
}
