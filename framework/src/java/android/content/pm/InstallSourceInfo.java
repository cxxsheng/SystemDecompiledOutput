package android.content.pm;

/* loaded from: classes.dex */
public final class InstallSourceInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.InstallSourceInfo> CREATOR = new android.os.Parcelable.Creator<android.content.pm.InstallSourceInfo>() { // from class: android.content.pm.InstallSourceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.InstallSourceInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.InstallSourceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.InstallSourceInfo[] newArray(int i) {
            return new android.content.pm.InstallSourceInfo[i];
        }
    };
    private final java.lang.String mInitiatingPackageName;
    private final android.content.pm.SigningInfo mInitiatingPackageSigningInfo;
    private final java.lang.String mInstallingPackageName;
    private final java.lang.String mOriginatingPackageName;
    private final int mPackageSource;
    private final java.lang.String mUpdateOwnerPackageName;

    public InstallSourceInfo(java.lang.String str, android.content.pm.SigningInfo signingInfo, java.lang.String str2, java.lang.String str3) {
        this(str, signingInfo, str2, str3, null, 0);
    }

    public InstallSourceInfo(java.lang.String str, android.content.pm.SigningInfo signingInfo, java.lang.String str2, java.lang.String str3, java.lang.String str4, int i) {
        this.mInitiatingPackageName = str;
        this.mInitiatingPackageSigningInfo = signingInfo;
        this.mOriginatingPackageName = str2;
        this.mInstallingPackageName = str3;
        this.mUpdateOwnerPackageName = str4;
        this.mPackageSource = i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        if (this.mInitiatingPackageSigningInfo == null) {
            return 0;
        }
        return this.mInitiatingPackageSigningInfo.describeContents();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mInitiatingPackageName);
        parcel.writeParcelable(this.mInitiatingPackageSigningInfo, i);
        parcel.writeString(this.mOriginatingPackageName);
        parcel.writeString(this.mInstallingPackageName);
        parcel.writeString8(this.mUpdateOwnerPackageName);
        parcel.writeInt(this.mPackageSource);
    }

    private InstallSourceInfo(android.os.Parcel parcel) {
        this.mInitiatingPackageName = parcel.readString();
        this.mInitiatingPackageSigningInfo = (android.content.pm.SigningInfo) parcel.readParcelable(android.content.pm.SigningInfo.class.getClassLoader(), android.content.pm.SigningInfo.class);
        this.mOriginatingPackageName = parcel.readString();
        this.mInstallingPackageName = parcel.readString();
        this.mUpdateOwnerPackageName = parcel.readString8();
        this.mPackageSource = parcel.readInt();
    }

    public java.lang.String getInitiatingPackageName() {
        return this.mInitiatingPackageName;
    }

    public android.content.pm.SigningInfo getInitiatingPackageSigningInfo() {
        return this.mInitiatingPackageSigningInfo;
    }

    public java.lang.String getOriginatingPackageName() {
        return this.mOriginatingPackageName;
    }

    public java.lang.String getInstallingPackageName() {
        return this.mInstallingPackageName;
    }

    public java.lang.String getUpdateOwnerPackageName() {
        return this.mUpdateOwnerPackageName;
    }

    public int getPackageSource() {
        return this.mPackageSource;
    }
}
