package android.permission;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class RuntimePermissionUsageInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.permission.RuntimePermissionUsageInfo> CREATOR = new android.os.Parcelable.Creator<android.permission.RuntimePermissionUsageInfo>() { // from class: android.permission.RuntimePermissionUsageInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.permission.RuntimePermissionUsageInfo createFromParcel(android.os.Parcel parcel) {
            return new android.permission.RuntimePermissionUsageInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.permission.RuntimePermissionUsageInfo[] newArray(int i) {
            return new android.permission.RuntimePermissionUsageInfo[i];
        }
    };
    private final java.lang.String mName;
    private final int mNumUsers;

    public RuntimePermissionUsageInfo(java.lang.String str, int i) {
        com.android.internal.util.Preconditions.checkNotNull(str);
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i);
        this.mName = str;
        this.mNumUsers = i;
    }

    private RuntimePermissionUsageInfo(android.os.Parcel parcel) {
        this(parcel.readString(), parcel.readInt());
    }

    public int getAppAccessCount() {
        return this.mNumUsers;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mName);
        parcel.writeInt(this.mNumUsers);
    }
}
