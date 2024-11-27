package android.app.backup;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public class RestoreSet implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.backup.RestoreSet> CREATOR = new android.os.Parcelable.Creator<android.app.backup.RestoreSet>() { // from class: android.app.backup.RestoreSet.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.backup.RestoreSet createFromParcel(android.os.Parcel parcel) {
            return new android.app.backup.RestoreSet(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.backup.RestoreSet[] newArray(int i) {
            return new android.app.backup.RestoreSet[i];
        }
    };
    public final int backupTransportFlags;
    public java.lang.String device;
    public java.lang.String name;
    public long token;

    public RestoreSet() {
        this.backupTransportFlags = 0;
    }

    public RestoreSet(java.lang.String str, java.lang.String str2, long j) {
        this(str, str2, j, 0);
    }

    public RestoreSet(java.lang.String str, java.lang.String str2, long j, int i) {
        this.name = str;
        this.device = str2;
        this.token = j;
        this.backupTransportFlags = i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.device);
        parcel.writeLong(this.token);
        parcel.writeInt(this.backupTransportFlags);
    }

    private RestoreSet(android.os.Parcel parcel) {
        this.name = parcel.readString();
        this.device = parcel.readString();
        this.token = parcel.readLong();
        this.backupTransportFlags = parcel.readInt();
    }
}
