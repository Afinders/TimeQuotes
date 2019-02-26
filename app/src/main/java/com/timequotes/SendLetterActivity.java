package com.timequotes;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.timequotes.adapter.SendLetterFragmentAdapter;
import com.timequotes.fragment.AppLetterFragment;
import com.timequotes.fragment.EMailLetterFragment;
import com.timequotes.fragment.LocalLetterFragment;

import java.util.ArrayList;
import java.util.List;

public class SendLetterActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_letter);

        mTabLayout = findViewById(R.id.send_letter_tab_layout);
        mViewPager = findViewById(R.id.send_letter_viewpager);
        initViewPager();

        // TODO: 2019/2/25 获取传递的信息
        Intent intent = getIntent();
        String letterContent = intent.getStringExtra("letterContent");
    }

    //初始化页面
    private void initViewPager(){
        List<String> tabTitles = new ArrayList<>();
        tabTitles.add("邮箱信件");
        tabTitles.add("本地信件");
        tabTitles.add("应用信件");
        for (String str : tabTitles){
            mTabLayout.addTab(mTabLayout.newTab().setText(str));
        }
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(EMailLetterFragment.newInstance());
        fragmentList.add(LocalLetterFragment.newInstance());
        fragmentList.add(AppLetterFragment.newInstance());
        SendLetterFragmentAdapter adapter = new SendLetterFragmentAdapter(getSupportFragmentManager(), fragmentList, tabTitles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
