package android.content.pm.permission;

/* loaded from: classes.dex */
public class SplitPermissionInfoParcelable implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.permission.SplitPermissionInfoParcelable> CREATOR = new android.os.Parcelable.Creator<android.content.pm.permission.SplitPermissionInfoParcelable>() { // from class: android.content.pm.permission.SplitPermissionInfoParcelable.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.permission.SplitPermissionInfoParcelable[] newArray(int i) {
            return new android.content.pm.permission.SplitPermissionInfoParcelable[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.permission.SplitPermissionInfoParcelable createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.permission.SplitPermissionInfoParcelable(parcel);
        }
    };
    private final java.util.List<java.lang.String> mNewPermissions;
    private final java.lang.String mSplitPermission;
    private final int mTargetSdk;

    private void onConstructed() {
        com.android.internal.util.Preconditions.checkCollectionElementsNotNull(this.mNewPermissions, "newPermissions");
    }

    public SplitPermissionInfoParcelable(java.lang.String str, java.util.List<java.lang.String> list, int i) {
        this.mSplitPermission = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSplitPermission);
        this.mNewPermissions = list;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mNewPermissions);
        this.mTargetSdk = i;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mTargetSdk, "from", 0L);
        onConstructed();
    }

    public java.lang.String getSplitPermission() {
        return this.mSplitPermission;
    }

    public java.util.List<java.lang.String> getNewPermissions() {
        return this.mNewPermissions;
    }

    public int getTargetSdk() {
        return this.mTargetSdk;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.content.pm.permission.SplitPermissionInfoParcelable splitPermissionInfoParcelable = (android.content.pm.permission.SplitPermissionInfoParcelable) obj;
        if (java.util.Objects.equals(this.mSplitPermission, splitPermissionInfoParcelable.mSplitPermission) && java.util.Objects.equals(this.mNewPermissions, splitPermissionInfoParcelable.mNewPermissions) && this.mTargetSdk == splitPermissionInfoParcelable.mTargetSdk) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((java.util.Objects.hashCode(this.mSplitPermission) + 31) * 31) + java.util.Objects.hashCode(this.mNewPermissions)) * 31) + this.mTargetSdk;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mSplitPermission);
        parcel.writeStringList(this.mNewPermissions);
        parcel.writeInt(this.mTargetSdk);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected SplitPermissionInfoParcelable(android.os.Parcel parcel) {
        java.lang.String readString = parcel.readString();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readStringList(arrayList);
        int readInt = parcel.readInt();
        this.mSplitPermission = readString;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSplitPermission);
        this.mNewPermissions = arrayList;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mNewPermissions);
        this.mTargetSdk = readInt;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mTargetSdk, "from", 0L);
        onConstructed();
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
