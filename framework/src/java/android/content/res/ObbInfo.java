package android.content.res;

/* loaded from: classes.dex */
public class ObbInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.res.ObbInfo> CREATOR = new android.os.Parcelable.Creator<android.content.res.ObbInfo>() { // from class: android.content.res.ObbInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.res.ObbInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.res.ObbInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.res.ObbInfo[] newArray(int i) {
            return new android.content.res.ObbInfo[i];
        }
    };
    public static final int OBB_OVERLAY = 1;
    public java.lang.String filename;
    public int flags;
    public java.lang.String packageName;
    public byte[] salt;
    public int version;

    ObbInfo() {
    }

    public java.lang.String toString() {
        return "ObbInfo{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " packageName=" + this.packageName + ",version=" + this.version + ",flags=" + this.flags + '}';
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.filename);
        parcel.writeString(this.packageName);
        parcel.writeInt(this.version);
        parcel.writeInt(this.flags);
        parcel.writeByteArray(this.salt);
    }

    private ObbInfo(android.os.Parcel parcel) {
        this.filename = parcel.readString();
        this.packageName = parcel.readString();
        this.version = parcel.readInt();
        this.flags = parcel.readInt();
        this.salt = parcel.createByteArray();
    }
}
