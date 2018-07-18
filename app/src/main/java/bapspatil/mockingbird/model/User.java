package bapspatil.mockingbird.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import android.os.Parcel;
import android.os.Parcelable;

/*
 ** Created by Bapusaheb Patil {@link https://bapspatil.com}
 */

public class User extends RealmObject implements Parcelable {
    @PrimaryKey private int userID;
    private String name;

    public User() {
    }

    public User(int userID, String name) {
        this.userID = userID;
        this.name = name;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userID);
        dest.writeString(this.name);
    }

    protected User(Parcel in) {
        this.userID = in.readInt();
        this.name = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
