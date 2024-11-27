package android.content.pm;

/* loaded from: classes.dex */
public final class ModuleInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.ModuleInfo> CREATOR = new android.os.Parcelable.Creator<android.content.pm.ModuleInfo>() { // from class: android.content.pm.ModuleInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.ModuleInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.ModuleInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.ModuleInfo[] newArray(int i) {
            return new android.content.pm.ModuleInfo[i];
        }
    };
    private java.lang.String mApexModuleName;
    private java.util.List<java.lang.String> mApkInApexPackageNames;
    private boolean mHidden;
    private java.lang.CharSequence mName;
    private java.lang.String mPackageName;

    public ModuleInfo() {
    }

    public ModuleInfo(android.content.pm.ModuleInfo moduleInfo) {
        this.mName = moduleInfo.mName;
        this.mPackageName = moduleInfo.mPackageName;
        this.mHidden = moduleInfo.mHidden;
        this.mApexModuleName = moduleInfo.mApexModuleName;
        if (moduleInfo.mApkInApexPackageNames != null) {
            this.mApkInApexPackageNames = java.util.List.copyOf(moduleInfo.mApkInApexPackageNames);
        }
    }

    public android.content.pm.ModuleInfo setName(java.lang.CharSequence charSequence) {
        this.mName = charSequence;
        return this;
    }

    public java.lang.CharSequence getName() {
        return this.mName;
    }

    public android.content.pm.ModuleInfo setPackageName(java.lang.String str) {
        this.mPackageName = str;
        return this;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public android.content.pm.ModuleInfo setHidden(boolean z) {
        this.mHidden = z;
        return this;
    }

    public boolean isHidden() {
        return this.mHidden;
    }

    public android.content.pm.ModuleInfo setApexModuleName(java.lang.String str) {
        this.mApexModuleName = str;
        return this;
    }

    public java.lang.String getApexModuleName() {
        return this.mApexModuleName;
    }

    public android.content.pm.ModuleInfo setApkInApexPackageNames(java.util.Collection<java.lang.String> collection) {
        java.util.Objects.requireNonNull(collection);
        this.mApkInApexPackageNames = java.util.List.copyOf(collection);
        return this;
    }

    public java.util.Collection<java.lang.String> getApkInApexPackageNames() {
        if (this.mApkInApexPackageNames == null) {
            return java.util.Collections.emptyList();
        }
        return this.mApkInApexPackageNames;
    }

    public java.lang.String toString() {
        return "ModuleInfo{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + ((java.lang.Object) this.mName) + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int hashCode() {
        return ((((((((0 + java.util.Objects.hashCode(this.mName)) * 31) + java.util.Objects.hashCode(this.mPackageName)) * 31) + java.util.Objects.hashCode(this.mApexModuleName)) * 31) + java.util.Objects.hashCode(this.mApkInApexPackageNames)) * 31) + java.lang.Boolean.hashCode(this.mHidden);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.content.pm.ModuleInfo)) {
            return false;
        }
        android.content.pm.ModuleInfo moduleInfo = (android.content.pm.ModuleInfo) obj;
        return java.util.Objects.equals(this.mName, moduleInfo.mName) && java.util.Objects.equals(this.mPackageName, moduleInfo.mPackageName) && java.util.Objects.equals(this.mApexModuleName, moduleInfo.mApexModuleName) && java.util.Objects.equals(this.mApkInApexPackageNames, moduleInfo.mApkInApexPackageNames) && this.mHidden == moduleInfo.mHidden;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeCharSequence(this.mName);
        parcel.writeString(this.mPackageName);
        parcel.writeBoolean(this.mHidden);
        parcel.writeString(this.mApexModuleName);
        parcel.writeStringList(this.mApkInApexPackageNames);
    }

    private ModuleInfo(android.os.Parcel parcel) {
        this.mName = parcel.readCharSequence();
        this.mPackageName = parcel.readString();
        this.mHidden = parcel.readBoolean();
        this.mApexModuleName = parcel.readString();
        this.mApkInApexPackageNames = parcel.createStringArrayList();
    }
}
