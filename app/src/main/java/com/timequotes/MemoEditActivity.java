package com.timequotes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.timequotes.bean.MemoBean;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MemoEditActivity extends AppCompatActivity {

    private EditText titleET;
    private EditText contentET;
    private String oldTitleStr;
    private String oldContentStr;
    private MemoBean memo;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_memo_edit_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO: 2019/2/17 菜单选项的监听（设置提醒时间和置顶）
        switch (item.getItemId()){
            case R.id.menu_memo_edit_check:
                memoOperation();
                break;
//            case R.id.clock:
//                break;
//            case R.id.to_top:
//                break;
            case android.R.id.home:
                memoOperation();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_edit);

        Toolbar toolbar = findViewById(R.id.memo_edit_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.outline_arrow_back_24);
        }

        titleET = findViewById(R.id.memo_edit_title_et);
        contentET = findViewById(R.id.memo_edit_content_et);
        Intent intent = getIntent();
        memo = (MemoBean) intent.getSerializableExtra("memo");
        if (memo != null){
            oldTitleStr = memo.getMemoTitle();
            oldContentStr = memo.getMemoContent();
            titleET.setText(oldTitleStr);
            contentET.setText(oldContentStr);
        }
    }

    private void memoOperation(){
        String title = titleET.getText().toString();
        String content = contentET.getText().toString();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Date curDate = new Date(System.currentTimeMillis());
        String saveTimeStr = dateFormat.format(curDate);
        if (TextUtils.isEmpty(oldTitleStr) && TextUtils.isEmpty(oldContentStr)){
            if (!TextUtils.isEmpty(title) || !TextUtils.isEmpty(content)){
                MemoBean memo = new MemoBean();
                memo.setMemoTitle(title);
                memo.setMemoContent(content);
                memo.setMemoSaveTime(saveTimeStr);
                memo.save();
                finish();
            }else {
                finish();
            }
        }else {
            if (TextUtils.isEmpty(title) && TextUtils.isEmpty(content)){
                LitePal.delete(MemoBean.class, memo.getId());
                finish();
            }else {
                ContentValues contentValues =  new ContentValues();
                contentValues.put("memoTitle", title);
                contentValues.put("memoContent", content);
                contentValues.put("memoSaveTime", saveTimeStr);
                LitePal.update(MemoBean.class, contentValues, memo.getId());
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        memoOperation();
    }
}
