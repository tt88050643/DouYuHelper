package com.example.zhaimeng.douyuhelper.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhaimeng on 2016/4/26.
 */
public class DanMu implements Parcelable{
    private String nickname;
    private String content;
    private int level;
    private String roomID;
    private String danmuGroupID;

    public String getNickname() {
        return nickname;
    }

    public String getContent() {
        return content;
    }

    public int getLevel() {
        return level;
    }

    public String getRoomID() {
        return roomID;
    }

    public String getDanmuGroupID() {
        return danmuGroupID;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public void setDanmuGroupID(String danmuGroupID) {
        this.danmuGroupID = danmuGroupID;
    }

    public void clear(){
        nickname = null;
        content = null;
        level = 0;
        roomID = null;
        danmuGroupID = null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(nickname);
        out.writeString(content);
        out.writeInt(level);
        out.writeString(roomID);
        out.writeString(danmuGroupID);
    }

    public static final Parcelable.Creator<DanMu> CREATOR = new Parcelable.Creator<DanMu>(){

        @Override
        public DanMu createFromParcel(Parcel in) {
            return new DanMu(in);
        }

        @Override
        public DanMu[] newArray(int size) {
            return new DanMu[size];
        }
    };


    private DanMu(Parcel in) {
        nickname = in.readString();
        content = in.readString();
        level = in.readInt();
        roomID = in.readString();
        danmuGroupID = in.readString();
    }

    public DanMu(){

    }
}