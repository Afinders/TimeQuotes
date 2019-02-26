package com.timequotes.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timequotes.R;
import com.timequotes.bean.MemoBean;

import java.util.List;

public class MemoRecyclerAdapter extends RecyclerView.Adapter<MemoRecyclerAdapter.MemoRecyclerViewHolder> {

    private Context mContext;
    private List<MemoBean> memoList;
    private memoOnClickListener memoOnClickListener;

    public MemoRecyclerAdapter(Context context, List<MemoBean> memoList){
        mContext = context;
        this.memoList = memoList;
    }

    @NonNull
    @Override
    public MemoRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        MemoRecyclerViewHolder viewHolder = new MemoRecyclerViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.memo_recycler_view_item, viewGroup, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MemoRecyclerViewHolder viewHolder, int i) {
        MemoBean memo = memoList.get(i);
        viewHolder.memoTitle.setText(memo.getMemoTitle());
        viewHolder.memoContent.setText(memo.getMemoContent());
        viewHolder.memoSaveTime.setText(memo.getMemoSaveTime());
        // TODO: 2019/2/22  设置提醒时间
        viewHolder.memoRemindTime.setText("");
        if (memoOnClickListener != null){
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = viewHolder.getLayoutPosition();
                    memoOnClickListener.itemClick(pos);
                }
            });
            viewHolder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = viewHolder.getLayoutPosition();
                    memoOnClickListener.itemLongClick(pos);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return memoList.size();
    }

    static class MemoRecyclerViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView memoTitle;
        TextView memoContent;
        TextView memoSaveTime;
        TextView memoRemindTime;

        MemoRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.memo_recycler_card_view);
            memoTitle = itemView.findViewById(R.id.memoTitle_tv);
            memoContent = itemView.findViewById(R.id.memoContent_tv);
            memoSaveTime = itemView.findViewById(R.id.memoSaveTime_tv);
            memoRemindTime = itemView.findViewById(R.id.memoRemindTime_tv);
        }
    }

    public void setItemListener(memoOnClickListener itemListener){
        memoOnClickListener = itemListener;
    }

    public interface memoOnClickListener{
        void itemClick(int pos);
        void itemLongClick(int pos);
    }

    public void remove(int pos){
        memoList.remove(pos);
        notifyItemRemoved(pos);
    }
}
