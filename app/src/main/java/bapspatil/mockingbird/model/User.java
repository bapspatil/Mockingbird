package bapspatil.mockingbird.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/*
 ** Created by Bapusaheb Patil {@link https://bapspatil.com}
 */

public class User extends RealmObject implements Parcelable {
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
    @PrimaryKey
    private String name;
    private boolean isQuestionAnswered;

    public User() {
    }

    public User(String name) {
        this.name = name;
        this.isQuestionAnswered = true;
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.isQuestionAnswered = in.readByte() != 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isQuestionAnswered() {
        return isQuestionAnswered;
    }

    public void setQuestionAnswered(boolean questionAnswered) {
        isQuestionAnswered = questionAnswered;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", isQuestionAnswered=" + isQuestionAnswered +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeByte(this.isQuestionAnswered ? (byte) 1 : (byte) 0);
    }
}
