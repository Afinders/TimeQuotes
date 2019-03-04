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
    private LetterBean letter, newLetter;
    private int toolbarStatus, sendNewLetter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_letter_edit_toolbar, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //如果toolbarStatus为1，则显示发送按钮；如果为2.则显示确认按钮
        switch (toolbarStatus){
            case 1:
                menu.findItem(R.id.menu_letter_edit_send).setVisible(true);
                menu.findItem(R.id.menu_letter_edit_check).setVisible(false);
                break;
            case 2:
                menu.findItem(R.id.menu_letter_edit_send).setVisible(false);
                menu.findItem(R.id.menu_letter_edit_check).setVisible(true);
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
                checkLetter();
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

        Toolbar toolbar = findViewById(R.id.letter_edit_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null){
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.drawable.outline_arrow_back_24);
//        }

        letterET = findViewById(R.id.letter_edit_content_et);
        //监听edittext
        letterET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                letterET.setFocusable(true);
                letterET.setFocusableInTouchMode(true);
                letterET.requestFocus();
                letterET.findFocus();
                toolbarStatus = 2;
                invalidateOptionsMenu();
            }
        });


        Intent intent = getIntent();
        letter = (LetterBean) intent.getSerializableExtra("letter");
        if (letter != null){
            letterET.setFocusable(false);
            toolbarStatus = 1;
            oldLetterContentStr = letter.getLetterContent();
            letterET.setText(oldLetterContentStr);
        }else {
            toolbarStatus = 2;
        }
    }

    // TODO: 2019/2/27 跳转到发送信件界面
    private void sendLetter(){
        if (sendNewLetter == 1){
            Intent intent = new Intent(this, SendLetterActivity.class);
            intent.putExtra("letter", newLetter);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, SendLetterActivity.class);
            intent.putExtra("letter", letter);
            startActivity(intent);
        }
        finish();   //结束编辑页面活动
    }

    private void checkLetter(){
        String letterContent = letterET.getText().toString();
        letterET.setFocusable(false);
        //从草稿进入
        if (letter != null){
            if (TextUtils.isEmpty(letterContent)){
                LitePal.delete(LetterBean.class, letter.getId());
                finish();
            }else {
                if (letterContent.equals(oldLetterContentStr)){
                    // TODO: 2019/2/27 更新状态栏
                    toolbarStatus = 1;
                    invalidateOptionsMenu();
                }else {
                    toolbarStatus = 1;
                    invalidateOptionsMenu();
                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                    Date curDate = new Date(System.currentTimeMillis());
                    String letterSaveTime = dateFormat.format(curDate);
                    ContentValues values = new ContentValues();
                    values.put("letterContent", letterContent);
                    values.put("letterSaveTime", letterSaveTime);
                    LitePal.update(LetterBean.class, values, letter.getId());
                }
            }
        }else { //直接进入编辑
            if (TextUtils.isEmpty(letterContent)){
                finish();
            }else {
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                Date curDate = new Date(System.currentTimeMillis());
                String letterSaveTime = dateFormat.format(curDate);
                newLetter = new LetterBean();
                newLetter.setLetterContent(letterContent);
                newLetter.setLetterSaveTime(letterSaveTime);
                newLetter.save();
                sendNewLetter = 1;
                // TODO: 2019/2/27 更新状态栏
                toolbarStatus = 1;
                invalidateOptionsMenu();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
