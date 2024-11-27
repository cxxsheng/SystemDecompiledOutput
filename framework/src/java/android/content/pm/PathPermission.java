package android.content.pm;

/* loaded from: classes.dex */
public class PathPermission extends android.os.PatternMatcher {
    public static final android.os.Parcelable.Creator<android.content.pm.PathPermission> CREATOR = new android.os.Parcelable.Creator<android.content.pm.PathPermission>() { // from class: android.content.pm.PathPermission.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.PathPermission createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.PathPermission(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.PathPermission[] newArray(int i) {
            return new android.content.pm.PathPermission[i];
        }
    };
    private final java.lang.String mReadPermission;
    private final java.lang.String mWritePermission;

    public PathPermission(java.lang.String str, int i, java.lang.String str2, java.lang.String str3) {
        super(str, i);
        this.mReadPermission = str2;
        this.mWritePermission = str3;
    }

    public java.lang.String getReadPermission() {
        return this.mReadPermission;
    }

    public java.lang.String getWritePermission() {
        return this.mWritePermission;
    }

    @Override // android.os.PatternMatcher, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mReadPermission);
        parcel.writeString(this.mWritePermission);
    }

    public PathPermission(android.os.Parcel parcel) {
        super(parcel);
        this.mReadPermission = parcel.readString();
        this.mWritePermission = parcel.readString();
    }
}
