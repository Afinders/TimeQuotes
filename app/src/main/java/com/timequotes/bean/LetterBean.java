package com.timequotes.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class LetterBean extends LitePalSupport implements Serializable{

    private int id;
    private int receiveType;
    private String letterContent;
    private String letterSaveTime;
    private String letterSendTime;

    public int getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(int receiveType) {
        this.receiveType = receiveType;
    }

    private String letterReceiveTime;
    private String letterReceiveMailBox;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLetterContent() {
        return letterContent;
    }

    public void setLetterContent(String letterContent) {
        this.letterContent = letterContent;
    }

    public String getLetterSendTime() {
        return letterSendTime;
    }

    public void setLetterSendTime(String letterSendTime) {
        this.letterSendTime = letterSendTime;
    }

    public String getLetterReceiveTime() {
        return letterReceiveTime;
    }

    public void setLetterReceiveTime(String letterReceiveTime) {
        this.letterReceiveTime = letterReceiveTime;
    }

    public String getLetterSaveTime() {
        return letterSaveTime;
    }

    public void setLetterSaveTime(String letterSaveTime) {
        this.letterSaveTime = letterSaveTime;
    }

    public String getLetterReceiveMailBox() {
        return letterReceiveMailBox;
    }

    public void setLetterReceiveMailBox(String letterReceiveMailBox) {
        this.letterReceiveMailBox = letterReceiveMailBox;
    }
}
