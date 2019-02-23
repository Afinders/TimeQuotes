package com.timequotes.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timequotes.MemoEditActivity;
import com.timequotes.MemoRecyclerAdapter;
import com.timequotes.R;
import com.timequotes.bean.MemoBean;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class MemoFragment extends Fragment {

    private static final String TAG = "碎片";
    private List<MemoBean> memoList;
    private RecyclerView recyclerView;
    private FloatingActionButton addFab;

    public static MemoFragment newInstance(){

        return new MemoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_memo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.memo_recycler_view);
        addFab = view.findViewById(R.id.memo_add_fab);
        initRecyclerView();
    }

    private void initRecyclerView(){

        memoList = LitePal.findAll(MemoBean.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        final MemoRecyclerAdapter adapter = new MemoRecyclerAdapter(getContext(), memoList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        adapter.setItemListener(new MemoRecyclerAdapter.memoOnClickListener() {
            @Override
            public void itemClick(int pos) {
                MemoBean memo = memoList.get(pos);
                if (getContext() != null){
                    Intent intent = new Intent(getContext(), MemoEditActivity.class);
                    intent.putExtra("memo", memo);
                    getContext().startActivity(intent);
                }
            }

            @Override
            public void itemLongClick(final int pos) {
                if (getContext() != null){
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setTitle("删除").setMessage("是否删除该便签");
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            LitePal.delete(MemoBean.class, memoList.get(pos).getId());
                            adapter.remove(pos);
                        }
                    });
                    dialog.show();
                }
            }
        });

        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getContext() != null){
                    Intent intent = new Intent(getContext(), MemoEditActivity.class);
                    getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: 暂停");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: 恢复");
        initRecyclerView();
    }
}
