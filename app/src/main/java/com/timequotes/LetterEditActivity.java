package com.timequotes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.timequotes.bean.LetterBean;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LetterEditActivity extends AppCompatActivity {

    private EditText letterET;
    private String oldLetterContentStr;
    private LetterBean letter;
    private int toolbarStatus;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_letter_edit_toolbar, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (letter != null){
            menu.findItem(R.id.menu_letter_edit_send).setVisible(true);
            menu.findItem(R.id.menu_letter_edit_check).setVisible(false);
        }else {
            menu.findItem(R.id.menu_letter_edit_send).setVisible(false);
            menu.findItem(R.id.menu_letter_edit_check).setVisible(true);
        }
        switch (toolbarStatus){
            case 1:
                menu.findItem(R.id.menu_letter_edit_send).setVisible(false);
                menu.findItem(R.id.menu_letter_edit_check).setVisible(true);
                break;
            case 2:
                menu.findItem(R.id.menu_letter_edit_send).setVisible(true);
                menu.findItem(R.id.menu_letter_edit_check).setVisible(false);
                break;
                default:
                    break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            // TODO: 2019/2/25 修改选择的方法
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_letter_edit_check:
                makeSure();
                break;
            case R.id.menu_letter_edit_send:
                sendLetter();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_edit);

        final Toolbar toolbar = findViewById(R.id.letter_edit_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.outline_arrow_back_24);
        }
        letterET = findViewById(R.id.letter_edit_content_et);

        //监听edittext
        letterET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                letterET.setFocusable(true);
                letterET.setFocusableInTouchMode(true);
                letterET.requestFocus();
                letterET.findFocus();
                if (letter != null){
                    toolbarStatus = 1;
                    invalidateOptionsMenu();
                }else {
                    toolbarStatus = 2;
                    invalidateOptionsMenu();
                }
            }
        });


        Intent intent = getIntent();
        letter = (LetterBean) intent.getSerializableExtra("letter");
        if (letter != null){
            letterET.setFocusable(false);
            oldLetterContentStr = letter.getLetterContent();
            letterET.setText(oldLetterContentStr);
        }
    }

    private void sendLetter(){
        String letterContent = letterET.getText().toString();
        if (letterContent.length() > 0){
            Intent intent = new Intent(this, SendLetterActivity.class);
            intent.putExtra("letter", letter);
            startActivity(intent);
        }else {
            Toast.makeText(this, "不能发送一封为空的信件", Toast.LENGTH_SHORT).show();
        }
    }

    private void letterOperation(){
        String letterContent = letterET.getText().toString();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Date curDate = new Date(System.currentTimeMillis());
        String letterSaveTime = dateFormat.format(curDate);
        if (TextUtils.isEmpty(oldLetterContentStr)){
            if (!TextUtils.isEmpty(letterContent)){
                LetterBean letter = new LetterBean();
                letter.setLetterContent(letterContent);
                letter.setLetterSaveTime(letterSaveTime);
                letter.save();
            }else {
                finish();
            }
        }else {
            if (TextUtils.isEmpty(letterContent)){
                LitePal.delete(LetterBean.class, letter.getId());
            }else {
                ContentValues values = new ContentValues();
                values.put("letterContent", letterContent);
                values.put("letterSaveTime", letterSaveTime);
                LitePal.update(LetterBean.class, values, letter.getId());
            }
        }
    }

    private void makeSure(){
        String letterContentStr = letterET.getText().toString();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Date curDate = new Date(System.currentTimeMillis());
        String letterSaveTime = dateFormat.format(curDate);
        if (TextUtils.isEmpty(oldLetterContentStr)){
            if (TextUtils.isEmpty(letterContentStr)){
                finish();
            }else {
                letterET.setFocusable(false);   //让edittext重新失去焦点
                toolbarStatus = 2;
                invalidateOptionsMenu();
            }
        }else {
            if (TextUtils.isEmpty(letterContentStr)){
                LitePal.delete(LetterBean.class, letter.getId());
                finish();
            }else {
                letterET.setFocusable(false);   //让edittext重新失去焦点
                toolbarStatus = 2;
                invalidateOptionsMenu();
            }
        }
    }

    @Override
    public void onBackPressed() {
        letterOperation();
        super.onBackPressed();
    }
}
