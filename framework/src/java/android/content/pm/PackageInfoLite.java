package android.content.pm;

/* loaded from: classes.dex */
public class PackageInfoLite implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.PackageInfoLite> CREATOR = new android.os.Parcelable.Creator<android.content.pm.PackageInfoLite>() { // from class: android.content.pm.PackageInfoLite.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.PackageInfoLite createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.PackageInfoLite(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.PackageInfoLite[] newArray(int i) {
            return new android.content.pm.PackageInfoLite[i];
        }
    };
    public int baseRevisionCode;
    public boolean debuggable;
    public int installLocation;
    public boolean isSdkLibrary;
    public boolean multiArch;
    public java.lang.String packageName;
    public int recommendedInstallLocation;
    public java.lang.String[] splitNames;
    public int[] splitRevisionCodes;
    public android.content.pm.VerifierInfo[] verifiers;

    @java.lang.Deprecated
    public int versionCode;
    public int versionCodeMajor;

    public long getLongVersionCode() {
        return android.content.pm.PackageInfo.composeLongVersionCode(this.versionCodeMajor, this.versionCode);
    }

    public PackageInfoLite() {
    }

    public java.lang.String toString() {
        return "PackageInfoLite{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + this.packageName + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        parcel.writeStringArray(this.splitNames);
        parcel.writeInt(this.versionCode);
        parcel.writeInt(this.versionCodeMajor);
        parcel.writeInt(this.baseRevisionCode);
        parcel.writeIntArray(this.splitRevisionCodes);
        parcel.writeInt(this.recommendedInstallLocation);
        parcel.writeInt(this.installLocation);
        parcel.writeInt(this.multiArch ? 1 : 0);
        parcel.writeInt(this.debuggable ? 1 : 0);
        if (this.verifiers == null || this.verifiers.length == 0) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(this.verifiers.length);
            parcel.writeTypedArray(this.verifiers, i);
        }
    }

    private PackageInfoLite(android.os.Parcel parcel) {
        this.packageName = parcel.readString();
        this.splitNames = parcel.createStringArray();
        this.versionCode = parcel.readInt();
        this.versionCodeMajor = parcel.readInt();
        this.baseRevisionCode = parcel.readInt();
        this.splitRevisionCodes = parcel.createIntArray();
        this.recommendedInstallLocation = parcel.readInt();
        this.installLocation = parcel.readInt();
        this.multiArch = parcel.readInt() != 0;
        this.debuggable = parcel.readInt() != 0;
        int readInt = parcel.readInt();
        if (readInt == 0) {
            this.verifiers = new android.content.pm.VerifierInfo[0];
        } else {
            this.verifiers = new android.content.pm.VerifierInfo[readInt];
            parcel.readTypedArray(this.verifiers, android.content.pm.VerifierInfo.CREATOR);
        }
    }
}
