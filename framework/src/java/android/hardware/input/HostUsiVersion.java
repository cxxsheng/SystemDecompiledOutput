package android.hardware.input;

/* loaded from: classes2.dex */
public final class HostUsiVersion implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.input.HostUsiVersion> CREATOR = new android.os.Parcelable.Creator<android.hardware.input.HostUsiVersion>() { // from class: android.hardware.input.HostUsiVersion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.HostUsiVersion[] newArray(int i) {
            return new android.hardware.input.HostUsiVersion[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.HostUsiVersion createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.input.HostUsiVersion(parcel);
        }
    };
    private final int mMajorVersion;
    private final int mMinorVersion;

    public boolean isValid() {
        return this.mMajorVersion >= 0 && this.mMinorVersion >= 0;
    }

    public HostUsiVersion(int i, int i2) {
        this.mMajorVersion = i;
        this.mMinorVersion = i2;
    }

    public int getMajorVersion() {
        return this.mMajorVersion;
    }

    public int getMinorVersion() {
        return this.mMinorVersion;
    }

    public java.lang.String toString() {
        return "HostUsiVersion { majorVersion = " + this.mMajorVersion + ", minorVersion = " + this.mMinorVersion + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.hardware.input.HostUsiVersion hostUsiVersion = (android.hardware.input.HostUsiVersion) obj;
        if (this.mMajorVersion == hostUsiVersion.mMajorVersion && this.mMinorVersion == hostUsiVersion.mMinorVersion) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((this.mMajorVersion + 31) * 31) + this.mMinorVersion;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mMajorVersion);
        parcel.writeInt(this.mMinorVersion);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    HostUsiVersion(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        this.mMajorVersion = readInt;
        this.mMinorVersion = readInt2;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
