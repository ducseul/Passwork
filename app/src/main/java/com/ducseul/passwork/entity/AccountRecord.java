package com.ducseul.passwork.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

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
public class AccountRecord implements Parcelable {
    private UUID uuid;
    private String accountTitle;
    private String websiteUrl;
    private String accountDescription;
    private String accountUsername;
    private String accountPassword;
    private Date accountCreateDate;

    public AccountRecord() {
        uuid = UUID.randomUUID();
    }

    protected AccountRecord(Parcel in) {
        accountTitle = in.readString();
        websiteUrl = in.readString();
        accountDescription = in.readString();
        accountUsername = in.readString();
        accountPassword = in.readString();
    }

    public static final Creator<AccountRecord> CREATOR = new Creator<AccountRecord>() {
        @Override
        public AccountRecord createFromParcel(Parcel in) {
            return new AccountRecord(in);
        }

        @Override
        public AccountRecord[] newArray(int size) {
            return new AccountRecord[size];
        }
    };

    public void generateCreateDate(){
        accountCreateDate = new Date();
    }

    public void generateUUID(){
        uuid = UUID.randomUUID();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(accountTitle);
        dest.writeString(websiteUrl);
        dest.writeString(accountDescription);
        dest.writeString(accountUsername);
        dest.writeString(accountPassword);
    }
}
