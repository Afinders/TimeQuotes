package com.timequotes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.timequotes.fragment.LetterFragment;
import com.timequotes.fragment.MemoFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.main_drawerlayout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.outline_menu_24);
        }
        final NavigationView navigationView = findViewById(R.id.main_navigation_view);
        if (navigationView != null){
            navigationView.setNavigationItemSelectedListener(new NavigationView.
                    OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    menuItem.setCheckable(true);
                    menuItem.setChecked(true);

                    switch (menuItem.getItemId()){
                        // TODO: 2019/2/17 布局碎片的切换
                        case R.id.menu_left_item_memo:
                            loadFragment(MemoFragment.class.getName());
                            break;
                        case R.id.menu_left_item_letter:
                            loadFragment(LetterFragment.class.getName());
                            break;
                    }
                    mDrawerLayout.closeDrawers();
                    return false;
                }
            });
        }

        //进入app加载的布局
        if (savedInstanceState == null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            MemoFragment fragment = MemoFragment.newInstance();
            transaction.add(R.id.main_fl, fragment);
            transaction.commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.menu_fragment_letter_edit:
                Intent intent = new Intent(this, LetterEditActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START)){
            mDrawerLayout.closeDrawer(Gravity.START);
        }else {
            super.onBackPressed();
        }
    }

    private void loadFragment(String fragmentName){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if ((MemoFragment.class.getName()).equals(fragmentName)){
            MemoFragment fragment = MemoFragment.newInstance();
            transaction.replace(R.id.main_fl, fragment);
        }else if ((LetterFragment.class.getName()).equals(fragmentName)){
            LetterFragment fragment = LetterFragment.newInstance();
            transaction.replace(R.id.main_fl, fragment);
        }
        transaction.commit();
    }
}
