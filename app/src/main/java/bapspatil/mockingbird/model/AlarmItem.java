package bapspatil.mockingbird.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/*
 ** Created by Bapusaheb Patil {@link https://bapspatil.com}
 */

public class AlarmItem extends RealmObject implements Parcelable {
    @PrimaryKey private int requestCode;
    private long timeSet;
    private String friendlyTimeSet;

    public AlarmItem() {
    }

    public AlarmItem(int requestCode, long timeSet, String friendlyTimeSet) {
        this.requestCode = requestCode;
        this.timeSet = timeSet;
        this.friendlyTimeSet = friendlyTimeSet;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public long getTimeSet() {
        return timeSet;
    }

    public void setTimeSet(long timeSet) {
        this.timeSet = timeSet;
    }

    public String getFriendlyTimeSet() {
        return friendlyTimeSet;
    }

    public void setFriendlyTimeSet(String friendlyTimeSet) {
        this.friendlyTimeSet = friendlyTimeSet;
    }

    @Override
    public String toString() {
        return "AlarmItem{" +
                "requestCode=" + requestCode +
                ", timeSet=" + timeSet +
                ", friendlyTimeSet='" + friendlyTimeSet + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.requestCode);
        dest.writeLong(this.timeSet);
        dest.writeString(this.friendlyTimeSet);
    }

    protected AlarmItem(Parcel in) {
        this.requestCode = in.readInt();
        this.timeSet = in.readLong();
        this.friendlyTimeSet = in.readString();
    }

    public static final Creator<AlarmItem> CREATOR = new Creator<AlarmItem>() {
        @Override
        public AlarmItem createFromParcel(Parcel source) {
            return new AlarmItem(source);
        }

        @Override
        public AlarmItem[] newArray(int size) {
            return new AlarmItem[size];
        }
    };
}
