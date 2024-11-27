package android.content.pm;

/* loaded from: classes.dex */
public final class VersionedPackage implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.VersionedPackage> CREATOR = new android.os.Parcelable.Creator<android.content.pm.VersionedPackage>() { // from class: android.content.pm.VersionedPackage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.VersionedPackage createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.VersionedPackage(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.VersionedPackage[] newArray(int i) {
            return new android.content.pm.VersionedPackage[i];
        }
    };
    private final java.lang.String mPackageName;
    private final long mVersionCode;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VersionCode {
    }

    public VersionedPackage(java.lang.String str, int i) {
        this.mPackageName = str;
        this.mVersionCode = i;
    }

    public VersionedPackage(java.lang.String str, long j) {
        this.mPackageName = str;
        this.mVersionCode = j;
    }

    private VersionedPackage(android.os.Parcel parcel) {
        this.mPackageName = parcel.readString8();
        this.mVersionCode = parcel.readLong();
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    @java.lang.Deprecated
    public int getVersionCode() {
        return (int) (this.mVersionCode & 2147483647L);
    }

    public long getLongVersionCode() {
        return this.mVersionCode;
    }

    public java.lang.String toString() {
        return "VersionedPackage[" + this.mPackageName + "/" + this.mVersionCode + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj instanceof android.content.pm.VersionedPackage) {
            android.content.pm.VersionedPackage versionedPackage = (android.content.pm.VersionedPackage) obj;
            if (versionedPackage.mPackageName.equals(this.mPackageName) && versionedPackage.mVersionCode == this.mVersionCode) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (this.mPackageName.hashCode() * 31) + java.lang.Long.hashCode(this.mVersionCode);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.mPackageName);
        parcel.writeLong(this.mVersionCode);
    }
}
