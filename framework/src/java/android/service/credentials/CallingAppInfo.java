package android.service.credentials;

/* loaded from: classes3.dex */
public final class CallingAppInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.credentials.CallingAppInfo> CREATOR = new android.os.Parcelable.Creator<android.service.credentials.CallingAppInfo>() { // from class: android.service.credentials.CallingAppInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.credentials.CallingAppInfo createFromParcel(android.os.Parcel parcel) {
            return new android.service.credentials.CallingAppInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.credentials.CallingAppInfo[] newArray(int i) {
            return new android.service.credentials.CallingAppInfo[i];
        }
    };
    private final java.lang.String mOrigin;
    private final java.lang.String mPackageName;
    private final android.content.pm.SigningInfo mSigningInfo;

    public CallingAppInfo(java.lang.String str, android.content.pm.SigningInfo signingInfo) {
        this(str, signingInfo, null);
    }

    public CallingAppInfo(java.lang.String str, android.content.pm.SigningInfo signingInfo, java.lang.String str2) {
        this.mPackageName = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str, "package namemust not be null or empty");
        this.mSigningInfo = (android.content.pm.SigningInfo) java.util.Objects.requireNonNull(signingInfo);
        this.mOrigin = str2;
    }

    private CallingAppInfo(android.os.Parcel parcel) {
        this.mPackageName = parcel.readString8();
        this.mSigningInfo = (android.content.pm.SigningInfo) parcel.readTypedObject(android.content.pm.SigningInfo.CREATOR);
        this.mOrigin = parcel.readString8();
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public android.content.pm.SigningInfo getSigningInfo() {
        return this.mSigningInfo;
    }

    public java.lang.String getOrigin() {
        return this.mOrigin;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.mPackageName);
        parcel.writeTypedObject(this.mSigningInfo, i);
        parcel.writeString8(this.mOrigin);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("CallingAppInfo {packageName= " + this.mPackageName);
        if (this.mSigningInfo != null) {
            sb.append(", mSigningInfo : No. of signatures: " + this.mSigningInfo.getApkContentsSigners().length);
        } else {
            sb.append(", mSigningInfo: null");
        }
        sb.append(",mOrigin: " + this.mOrigin);
        sb.append(" }");
        return sb.toString();
    }
}
