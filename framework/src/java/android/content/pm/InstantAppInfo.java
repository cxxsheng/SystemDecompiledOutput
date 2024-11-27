package android.content.pm;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class InstantAppInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.InstantAppInfo> CREATOR = new android.os.Parcelable.Creator<android.content.pm.InstantAppInfo>() { // from class: android.content.pm.InstantAppInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.InstantAppInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.InstantAppInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.InstantAppInfo[] newArray(int i) {
            return new android.content.pm.InstantAppInfo[0];
        }
    };
    private final android.content.pm.ApplicationInfo mApplicationInfo;
    private final java.lang.String[] mGrantedPermissions;
    private final java.lang.CharSequence mLabelText;
    private final java.lang.String mPackageName;
    private final java.lang.String[] mRequestedPermissions;

    public InstantAppInfo(android.content.pm.ApplicationInfo applicationInfo, java.lang.String[] strArr, java.lang.String[] strArr2) {
        this.mApplicationInfo = applicationInfo;
        this.mPackageName = null;
        this.mLabelText = null;
        this.mRequestedPermissions = strArr;
        this.mGrantedPermissions = strArr2;
    }

    public InstantAppInfo(java.lang.String str, java.lang.CharSequence charSequence, java.lang.String[] strArr, java.lang.String[] strArr2) {
        this.mApplicationInfo = null;
        this.mPackageName = str;
        this.mLabelText = charSequence;
        this.mRequestedPermissions = strArr;
        this.mGrantedPermissions = strArr2;
    }

    private InstantAppInfo(android.os.Parcel parcel) {
        this.mPackageName = parcel.readString();
        this.mLabelText = parcel.readCharSequence();
        this.mRequestedPermissions = parcel.readStringArray();
        this.mGrantedPermissions = parcel.createStringArray();
        this.mApplicationInfo = (android.content.pm.ApplicationInfo) parcel.readParcelable(null, android.content.pm.ApplicationInfo.class);
    }

    public android.content.pm.ApplicationInfo getApplicationInfo() {
        return this.mApplicationInfo;
    }

    public java.lang.String getPackageName() {
        if (this.mApplicationInfo != null) {
            return this.mApplicationInfo.packageName;
        }
        return this.mPackageName;
    }

    public java.lang.CharSequence loadLabel(android.content.pm.PackageManager packageManager) {
        if (this.mApplicationInfo != null) {
            return this.mApplicationInfo.loadLabel(packageManager);
        }
        return this.mLabelText;
    }

    public android.graphics.drawable.Drawable loadIcon(android.content.pm.PackageManager packageManager) {
        if (this.mApplicationInfo != null) {
            return this.mApplicationInfo.loadIcon(packageManager);
        }
        return packageManager.getInstantAppIcon(this.mPackageName);
    }

    public java.lang.String[] getRequestedPermissions() {
        return this.mRequestedPermissions;
    }

    public java.lang.String[] getGrantedPermissions() {
        return this.mGrantedPermissions;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mPackageName);
        parcel.writeCharSequence(this.mLabelText);
        parcel.writeStringArray(this.mRequestedPermissions);
        parcel.writeStringArray(this.mGrantedPermissions);
        parcel.writeParcelable(this.mApplicationInfo, i);
    }
}
