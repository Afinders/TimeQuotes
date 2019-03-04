package com.timequotes.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timequotes.InBoxActivity;
import com.timequotes.LetterEditActivity;
import com.timequotes.R;
import com.timequotes.adapter.LetterRecyclerAdapter;
import com.timequotes.bean.LetterBean;

import org.litepal.LitePal;

import java.util.List;

public class LetterFragment extends Fragment {

    private String TAG = "信件碎片";
    private RecyclerView draftRecyclerView;
    private LetterRecyclerAdapter adapter;
    private List<LetterBean> letterList;

    public static LetterFragment newInstance(){

        return new LetterFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        AppCompatActivity appCompatActivity= (AppCompatActivity) getActivity();
        if (appCompatActivity != null){
            Toolbar toolbar = appCompatActivity.findViewById(R.id.main_toolbar);
            appCompatActivity.setSupportActionBar(toolbar);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_letter, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_letter_toolbar, menu);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        draftRecyclerView = view.findViewById(R.id.letter_draft_recycler_view);
        FloatingActionButton inBoxFab = view.findViewById(R.id.letter_inbox_fab);
        inBoxFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null){
                    Intent intent = new Intent(getActivity(), InBoxActivity.class);
                    startActivity(intent);
                }
            }
        });
//        initDraftRecyclerView();
    }

    private void initDraftRecyclerView(){
        letterList = LitePal.findAll(LetterBean.class);
        LinearLayoutManager layoutManager = null;
        if (getContext() != null){
            layoutManager = new LinearLayoutManager(getContext());
            adapter = new LetterRecyclerAdapter(letterList, getContext());
        }
        draftRecyclerView.setLayoutManager(layoutManager);
        draftRecyclerView.setAdapter(adapter);

        adapter.setLetterOnClickListener(new LetterRecyclerAdapter.letterOnClickListener() {
            @Override
            public void itemClick(int pos) {
                if (getContext() != null){
                    Intent intent = new Intent(getContext(), LetterEditActivity.class);
                    LetterBean letter = letterList.get(pos);
                    intent.putExtra("letter", letter);
                    getContext().startActivity(intent);
                }
            }

            @Override
            public void itemLongClick(final int pos) {
                if (getContext() != null){
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setTitle("删除").setMessage("是否删除该草稿");
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            LitePal.delete(LetterBean.class, letterList.get(pos).getId());
                            adapter.remove(pos);
                        }
                    });
                    dialog.show();
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
//        initDraftRecyclerView();
    }
}
