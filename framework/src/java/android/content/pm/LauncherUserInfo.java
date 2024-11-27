package android.content.pm;

/* loaded from: classes.dex */
public final class LauncherUserInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.LauncherUserInfo> CREATOR = new android.os.Parcelable.Creator<android.content.pm.LauncherUserInfo>() { // from class: android.content.pm.LauncherUserInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.LauncherUserInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.LauncherUserInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.LauncherUserInfo[] newArray(int i) {
            return new android.content.pm.LauncherUserInfo[i];
        }
    };
    private final int mUserSerialNumber;
    private final java.lang.String mUserType;

    public java.lang.String getUserType() {
        return this.mUserType;
    }

    public int getUserSerialNumber() {
        return this.mUserSerialNumber;
    }

    private LauncherUserInfo(android.os.Parcel parcel) {
        this.mUserType = parcel.readString16NoHelper();
        this.mUserSerialNumber = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString16NoHelper(this.mUserType);
        parcel.writeInt(this.mUserSerialNumber);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private final int mUserSerialNumber;
        private final java.lang.String mUserType;

        public Builder(java.lang.String str, int i) {
            this.mUserType = str;
            this.mUserSerialNumber = i;
        }

        public android.content.pm.LauncherUserInfo build() {
            return new android.content.pm.LauncherUserInfo(this.mUserType, this.mUserSerialNumber);
        }
    }

    private LauncherUserInfo(java.lang.String str, int i) {
        this.mUserType = str;
        this.mUserSerialNumber = i;
    }
}
