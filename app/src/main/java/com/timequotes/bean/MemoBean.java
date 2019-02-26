package com.timequotes.bean;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * 该类为使用litepal操作数据库的bean类
 */
public class MemoBean extends LitePalSupport implements Serializable{

    private int id;
    private String memoTitle;
    private String memoContent;
    private String memoSaveTime;
    private String memoRemindTime;

    public String getMemoTitle() {
        return memoTitle;
    }

    public void setMemoTitle(String memoTitle) {
        this.memoTitle = memoTitle;
    }

    public String getMemoContent() {
        return memoContent;
    }

    public void setMemoContent(String memoContent) {
        this.memoContent = memoContent;
    }

    public String getMemoSaveTime() {
        return memoSaveTime;
    }

    public void setMemoSaveTime(String memoSaveTime) {
        this.memoSaveTime = memoSaveTime;
    }

    public String getMemoRemindTime() {
        return memoRemindTime;
    }

    public void setMemoRemindTime(String memoRemindTime) {
        this.memoRemindTime = memoRemindTime;
    }

    public int getId(){

        return id;
    }
}
