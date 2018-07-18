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
    @PrimaryKey @Required private int requestCode;
    private long timeSet;

    public AlarmItem() {
    }

    public AlarmItem(int requestCode, long timeSet) {
        this.requestCode = requestCode;
        this.timeSet = timeSet;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.requestCode);
        dest.writeLong(this.timeSet);
    }

    protected AlarmItem(Parcel in) {
        this.requestCode = in.readInt();
        this.timeSet = in.readLong();
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
