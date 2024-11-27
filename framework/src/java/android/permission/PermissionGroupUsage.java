package android.permission;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class PermissionGroupUsage implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.permission.PermissionGroupUsage> CREATOR = new android.os.Parcelable.Creator<android.permission.PermissionGroupUsage>() { // from class: android.permission.PermissionGroupUsage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.permission.PermissionGroupUsage[] newArray(int i) {
            return new android.permission.PermissionGroupUsage[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.permission.PermissionGroupUsage createFromParcel(android.os.Parcel parcel) {
            return new android.permission.PermissionGroupUsage(parcel);
        }
    };
    private final boolean mActive;
    private final java.lang.CharSequence mAttributionLabel;
    private final java.lang.CharSequence mAttributionTag;
    private final long mLastAccessTimeMillis;
    private final java.lang.String mPackageName;
    private final java.lang.String mPermissionGroupName;
    private final java.lang.String mPersistentDeviceId;
    private final boolean mPhoneCall;
    private final java.lang.CharSequence mProxyLabel;
    private final int mUid;

    public PermissionGroupUsage(java.lang.String str, int i, long j, java.lang.String str2, boolean z, boolean z2, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, java.lang.CharSequence charSequence3, java.lang.String str3) {
        this.mPackageName = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mPackageName);
        this.mUid = i;
        this.mLastAccessTimeMillis = j;
        this.mPermissionGroupName = str2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mPermissionGroupName);
        this.mActive = z;
        this.mPhoneCall = z2;
        this.mAttributionTag = charSequence;
        this.mAttributionLabel = charSequence2;
        this.mProxyLabel = charSequence3;
        this.mPersistentDeviceId = str3;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mPersistentDeviceId);
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public int getUid() {
        return this.mUid;
    }

    public long getLastAccessTimeMillis() {
        return this.mLastAccessTimeMillis;
    }

    public java.lang.String getPermissionGroupName() {
        return this.mPermissionGroupName;
    }

    public boolean isActive() {
        return this.mActive;
    }

    public boolean isPhoneCall() {
        return this.mPhoneCall;
    }

    public java.lang.CharSequence getAttributionTag() {
        return this.mAttributionTag;
    }

    public java.lang.CharSequence getAttributionLabel() {
        return this.mAttributionLabel;
    }

    public java.lang.CharSequence getProxyLabel() {
        return this.mProxyLabel;
    }

    public java.lang.String getPersistentDeviceId() {
        return this.mPersistentDeviceId;
    }

    public java.lang.String toString() {
        return "PermissionGroupUsage { packageName = " + this.mPackageName + ", uid = " + this.mUid + ", lastAccessTimeMillis = " + this.mLastAccessTimeMillis + ", permissionGroupName = " + this.mPermissionGroupName + ", active = " + this.mActive + ", phoneCall = " + this.mPhoneCall + ", attributionTag = " + ((java.lang.Object) this.mAttributionTag) + ", attributionLabel = " + ((java.lang.Object) this.mAttributionLabel) + ", proxyLabel = " + ((java.lang.Object) this.mProxyLabel) + ", persistentDeviceId = " + this.mPersistentDeviceId + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.permission.PermissionGroupUsage permissionGroupUsage = (android.permission.PermissionGroupUsage) obj;
        if (java.util.Objects.equals(this.mPackageName, permissionGroupUsage.mPackageName) && this.mUid == permissionGroupUsage.mUid && this.mLastAccessTimeMillis == permissionGroupUsage.mLastAccessTimeMillis && java.util.Objects.equals(this.mPermissionGroupName, permissionGroupUsage.mPermissionGroupName) && this.mActive == permissionGroupUsage.mActive && this.mPhoneCall == permissionGroupUsage.mPhoneCall && java.util.Objects.equals(this.mAttributionTag, permissionGroupUsage.mAttributionTag) && java.util.Objects.equals(this.mAttributionLabel, permissionGroupUsage.mAttributionLabel) && java.util.Objects.equals(this.mProxyLabel, permissionGroupUsage.mProxyLabel) && java.util.Objects.equals(this.mPersistentDeviceId, permissionGroupUsage.mPersistentDeviceId)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((((((((java.util.Objects.hashCode(this.mPackageName) + 31) * 31) + this.mUid) * 31) + java.lang.Long.hashCode(this.mLastAccessTimeMillis)) * 31) + java.util.Objects.hashCode(this.mPermissionGroupName)) * 31) + java.lang.Boolean.hashCode(this.mActive)) * 31) + java.lang.Boolean.hashCode(this.mPhoneCall)) * 31) + java.util.Objects.hashCode(this.mAttributionTag)) * 31) + java.util.Objects.hashCode(this.mAttributionLabel)) * 31) + java.util.Objects.hashCode(this.mProxyLabel)) * 31) + java.util.Objects.hashCode(this.mPersistentDeviceId);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        int i2 = this.mActive ? 16 : 0;
        if (this.mPhoneCall) {
            i2 |= 32;
        }
        if (this.mAttributionTag != null) {
            i2 |= 64;
        }
        if (this.mAttributionLabel != null) {
            i2 |= 128;
        }
        if (this.mProxyLabel != null) {
            i2 |= 256;
        }
        parcel.writeInt(i2);
        parcel.writeString(this.mPackageName);
        parcel.writeInt(this.mUid);
        parcel.writeLong(this.mLastAccessTimeMillis);
        parcel.writeString(this.mPermissionGroupName);
        if (this.mAttributionTag != null) {
            parcel.writeCharSequence(this.mAttributionTag);
        }
        if (this.mAttributionLabel != null) {
            parcel.writeCharSequence(this.mAttributionLabel);
        }
        if (this.mProxyLabel != null) {
            parcel.writeCharSequence(this.mProxyLabel);
        }
        parcel.writeString(this.mPersistentDeviceId);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    PermissionGroupUsage(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        boolean z = (readInt & 16) != 0;
        boolean z2 = (readInt & 32) != 0;
        java.lang.String readString = parcel.readString();
        int readInt2 = parcel.readInt();
        long readLong = parcel.readLong();
        java.lang.String readString2 = parcel.readString();
        java.lang.CharSequence readCharSequence = (readInt & 64) == 0 ? null : parcel.readCharSequence();
        java.lang.CharSequence readCharSequence2 = (readInt & 128) == 0 ? null : parcel.readCharSequence();
        java.lang.CharSequence readCharSequence3 = (readInt & 256) == 0 ? null : parcel.readCharSequence();
        java.lang.String readString3 = parcel.readString();
        this.mPackageName = readString;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mPackageName);
        this.mUid = readInt2;
        this.mLastAccessTimeMillis = readLong;
        this.mPermissionGroupName = readString2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mPermissionGroupName);
        this.mActive = z;
        this.mPhoneCall = z2;
        this.mAttributionTag = readCharSequence;
        this.mAttributionLabel = readCharSequence2;
        this.mProxyLabel = readCharSequence3;
        this.mPersistentDeviceId = readString3;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mPersistentDeviceId);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
