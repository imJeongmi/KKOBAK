package com.example.kkobak.ui.dashboard;

import android.os.Parcel;
import android.os.Parcelable;

public class ChallengeInfo implements Parcelable {
    private String name;
    private int image;

    public ChallengeInfo(String name, int image) {
        this.name = name;
        this.image = image;
    }

    protected ChallengeInfo(Parcel in) {
        name = in.readString();
        image = in.readInt();
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
    }
}
