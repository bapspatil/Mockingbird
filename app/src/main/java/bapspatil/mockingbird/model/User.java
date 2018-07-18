package bapspatil.mockingbird.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import android.os.Parcel;
import android.os.Parcelable;

/*
 ** Created by Bapusaheb Patil {@link https://bapspatil.com}
 */

public class User extends RealmObject implements Parcelable {
    @PrimaryKey private String name;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    protected User(Parcel in) {
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
