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
import com.timequotes.bean.LetterBean;

import java.util.List;

public class LetterRecyclerAdapter extends RecyclerView.Adapter<LetterRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private List<LetterBean> letterList;
    private letterOnClickListener letterOnClickListener;

    public LetterRecyclerAdapter(List<LetterBean> letterList, Context context){
        mContext = context;
        this.letterList = letterList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.letter_recycler_view_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        // TODO: 2019/2/27 需要修改
        int type = letterList.get(i).getReceiveType();
        if (type != 0 || type != 1 || type != 2){
            viewHolder.letterContentTv.setText(letterList.get(i).getLetterContent());
        }
        if (letterOnClickListener != null){
            viewHolder.letterCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = viewHolder.getLayoutPosition();
                    letterOnClickListener.itemClick(pos);
                }
            });
            viewHolder.letterCardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = viewHolder.getLayoutPosition();
                    letterOnClickListener.itemLongClick(pos);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (letterList.size() > 0){
            return letterList.size();
        }else {
            return 0;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView letterCardView;
        TextView letterContentTv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            letterCardView = itemView.findViewById(R.id.letter_recycler_card_view);
            letterContentTv = itemView.findViewById(R.id.letter_draft_content_TV);
        }
    }

    public void setLetterOnClickListener(letterOnClickListener letterOnClickListener){
        this.letterOnClickListener = letterOnClickListener;
    }

    public interface letterOnClickListener{
        void itemClick(int pos);
        void itemLongClick(int pos);
    }

    public void remove(int pos){
        letterList.remove(pos);
        notifyItemRemoved(pos);
    }
}
