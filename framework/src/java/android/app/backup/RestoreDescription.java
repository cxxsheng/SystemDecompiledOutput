package android.app.backup;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public class RestoreDescription implements android.os.Parcelable {
    public static final int TYPE_FULL_STREAM = 2;
    public static final int TYPE_KEY_VALUE = 1;
    private final int mDataType;
    private final java.lang.String mPackageName;
    private static final java.lang.String NO_MORE_PACKAGES_SENTINEL = "NO_MORE_PACKAGES";
    public static final android.app.backup.RestoreDescription NO_MORE_PACKAGES = new android.app.backup.RestoreDescription(NO_MORE_PACKAGES_SENTINEL, 0);
    public static final android.os.Parcelable.Creator<android.app.backup.RestoreDescription> CREATOR = new android.os.Parcelable.Creator<android.app.backup.RestoreDescription>() { // from class: android.app.backup.RestoreDescription.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.backup.RestoreDescription createFromParcel(android.os.Parcel parcel) {
            android.app.backup.RestoreDescription restoreDescription = new android.app.backup.RestoreDescription(parcel);
            if (!android.app.backup.RestoreDescription.NO_MORE_PACKAGES_SENTINEL.equals(restoreDescription.mPackageName)) {
                return restoreDescription;
            }
            return android.app.backup.RestoreDescription.NO_MORE_PACKAGES;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.backup.RestoreDescription[] newArray(int i) {
            return new android.app.backup.RestoreDescription[i];
        }
    };

    public java.lang.String toString() {
        return "RestoreDescription{" + this.mPackageName + " : " + (this.mDataType == 1 ? "KEY_VALUE" : "STREAM") + '}';
    }

    public RestoreDescription(java.lang.String str, int i) {
        this.mPackageName = str;
        this.mDataType = i;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public int getDataType() {
        return this.mDataType;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mPackageName);
        parcel.writeInt(this.mDataType);
    }

    private RestoreDescription(android.os.Parcel parcel) {
        this.mPackageName = parcel.readString();
        this.mDataType = parcel.readInt();
    }
}
