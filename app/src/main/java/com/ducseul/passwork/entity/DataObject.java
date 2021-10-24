package com.ducseul.passwork.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class DataObject implements Parcelable {
    private String PIN_resolve;
    private String[] recoveryComb;
    private ArrayList<AccountRecord> listData;

    public DataObject() {
        PIN_resolve = "";
        recoveryComb = new String[]{};
        listData = new ArrayList<>();
    }

    protected DataObject(Parcel in) {
        PIN_resolve = in.readString();
        recoveryComb = in.createStringArray();
        listData = in.createTypedArrayList(AccountRecord.CREATOR);
    }

    public static final Creator<DataObject> CREATOR = new Creator<DataObject>() {
        @Override
        public DataObject createFromParcel(Parcel in) {
            return new DataObject(in);
        }

        @Override
        public DataObject[] newArray(int size) {
            return new DataObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(PIN_resolve);
        dest.writeStringArray(recoveryComb);
        dest.writeTypedList(listData);
    }
}
