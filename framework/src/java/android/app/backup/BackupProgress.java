package android.app.backup;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public class BackupProgress implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.backup.BackupProgress> CREATOR = new android.os.Parcelable.Creator<android.app.backup.BackupProgress>() { // from class: android.app.backup.BackupProgress.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.backup.BackupProgress createFromParcel(android.os.Parcel parcel) {
            return new android.app.backup.BackupProgress(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.backup.BackupProgress[] newArray(int i) {
            return new android.app.backup.BackupProgress[i];
        }
    };
    public final long bytesExpected;
    public final long bytesTransferred;

    public BackupProgress(long j, long j2) {
        this.bytesExpected = j;
        this.bytesTransferred = j2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.bytesExpected);
        parcel.writeLong(this.bytesTransferred);
    }

    private BackupProgress(android.os.Parcel parcel) {
        this.bytesExpected = parcel.readLong();
        this.bytesTransferred = parcel.readLong();
    }
}
