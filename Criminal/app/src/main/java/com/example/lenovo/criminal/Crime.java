package com.example.lenovo.criminal;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Lenovo on 2018/4/6.
 */

public class Crime {
    private String mTitle;
    private UUID mId;
    private Date mDate;
    private boolean mSolved;
    private boolean mRequiresPolice;

    public String getSuspect() {
        return mSuspect;
    }

    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }

    private String mSuspect;

    public boolean isRequiresPolice() {
        return mRequiresPolice;
    }

    public void setRequiresPolice(boolean requiresPolice) {
        mRequiresPolice = requiresPolice;
    }



    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public Crime(){
        mId=UUID.randomUUID();
        mDate=new Date();

    }

}
