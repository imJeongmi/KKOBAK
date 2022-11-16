package com.example.kkobak.ui.dashboard;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

public class ChallengeInfo implements Parcelable {
    private String name;
    private int image;

    private String imgUrl;
    private boolean done;

    private int id;
    private int detailCategoryId;
    private int goal;
    private String title;
    private String endTime;

    private String contents;
    private boolean watch;

    public ChallengeInfo(String name, int image, String imgUrl, boolean done, int detailCategoryId, int goal, String title, String endTime, String contents, boolean watch, int id) {
        this.name = name;
        this.image = image;
        this.imgUrl = imgUrl;
        this.done = done;
        this.detailCategoryId = detailCategoryId;
        this.goal = goal;
        this.title = title;
        this.endTime = endTime;
        this.contents = contents;
        this.watch = watch;
        this.id= id;
    }

    public ChallengeInfo(){

    }

    public ChallengeInfo(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setDetailCategoryId(int detailCategoryId) {
        this.detailCategoryId = detailCategoryId;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setWatch(boolean watch) {
        this.watch = watch;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public boolean isDone() {
        return done;
    }

    public int getId() {
        return id;
    }

    public int getDetailCategoryId() {
        return detailCategoryId;
    }

    public int getGoal() {
        return goal;
    }

    public String getTitle() {
        return title;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getContents() {
        return contents;
    }

    public boolean isWatch() {
        return watch;
    }

    protected ChallengeInfo(Parcel in) {
        name = in.readString();
        image = in.readInt();

        imgUrl = in.readString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            done = in.readBoolean();
            watch = in.readBoolean();
        }
        detailCategoryId = in.readInt();
        goal = in.readInt();
        title = in.readString();
        endTime = in.readString();
        contents = in.readString();
        id = in.readInt();
    }

    public static final Creator<ChallengeInfo> CREATOR = new Creator<ChallengeInfo>() {
        @Override
        public ChallengeInfo createFromParcel(Parcel in) {
            return new ChallengeInfo(in);
        }

        @Override
        public ChallengeInfo[] newArray(int size) {
            return new ChallengeInfo[size];
        }
    };

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(image);
        dest.writeString(imgUrl);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeBoolean(done);
            dest.writeBoolean(watch);
        }
        dest.writeInt(detailCategoryId);
        dest.writeInt(goal);
        dest.writeString(title);
        dest.writeString(endTime);
        dest.writeString(contents);
        dest.writeInt(id);
    }
}