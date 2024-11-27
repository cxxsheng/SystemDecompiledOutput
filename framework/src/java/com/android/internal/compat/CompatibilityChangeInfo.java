package com.android.internal.compat;

/* loaded from: classes4.dex */
public class CompatibilityChangeInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.compat.CompatibilityChangeInfo> CREATOR = new android.os.Parcelable.Creator<com.android.internal.compat.CompatibilityChangeInfo>() { // from class: com.android.internal.compat.CompatibilityChangeInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.compat.CompatibilityChangeInfo createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.compat.CompatibilityChangeInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.compat.CompatibilityChangeInfo[] newArray(int i) {
            return new com.android.internal.compat.CompatibilityChangeInfo[i];
        }
    };
    private final long mChangeId;
    private final java.lang.String mDescription;
    private final boolean mDisabled;
    private final int mEnableSinceTargetSdk;
    private final boolean mLoggingOnly;
    private final java.lang.String mName;
    private final boolean mOverridable;

    public long getId() {
        return this.mChangeId;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public int getEnableSinceTargetSdk() {
        return this.mEnableSinceTargetSdk;
    }

    public boolean getDisabled() {
        return this.mDisabled;
    }

    public boolean getLoggingOnly() {
        return this.mLoggingOnly;
    }

    public java.lang.String getDescription() {
        return this.mDescription;
    }

    public boolean getOverridable() {
        return this.mOverridable;
    }

    public CompatibilityChangeInfo(java.lang.Long l, java.lang.String str, int i, int i2, boolean z, boolean z2, java.lang.String str2, boolean z3) {
        this.mChangeId = l.longValue();
        this.mName = str;
        if (i > 0) {
            this.mEnableSinceTargetSdk = i + 1;
        } else if (i2 > 0) {
            this.mEnableSinceTargetSdk = i2;
        } else {
            this.mEnableSinceTargetSdk = -1;
        }
        this.mDisabled = z;
        this.mLoggingOnly = z2;
        this.mDescription = str2;
        this.mOverridable = z3;
    }

    public CompatibilityChangeInfo(com.android.internal.compat.CompatibilityChangeInfo compatibilityChangeInfo) {
        this.mChangeId = compatibilityChangeInfo.mChangeId;
        this.mName = compatibilityChangeInfo.mName;
        this.mEnableSinceTargetSdk = compatibilityChangeInfo.mEnableSinceTargetSdk;
        this.mDisabled = compatibilityChangeInfo.mDisabled;
        this.mLoggingOnly = compatibilityChangeInfo.mLoggingOnly;
        this.mDescription = compatibilityChangeInfo.mDescription;
        this.mOverridable = compatibilityChangeInfo.mOverridable;
    }

    private CompatibilityChangeInfo(android.os.Parcel parcel) {
        this.mChangeId = parcel.readLong();
        this.mName = parcel.readString();
        this.mEnableSinceTargetSdk = parcel.readInt();
        this.mDisabled = parcel.readBoolean();
        this.mLoggingOnly = parcel.readBoolean();
        this.mDescription = parcel.readString();
        this.mOverridable = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mChangeId);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mEnableSinceTargetSdk);
        parcel.writeBoolean(this.mDisabled);
        parcel.writeBoolean(this.mLoggingOnly);
        parcel.writeString(this.mDescription);
        parcel.writeBoolean(this.mOverridable);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder append = new java.lang.StringBuilder("CompatibilityChangeInfo(").append(getId());
        if (getName() != null) {
            append.append("; name=").append(getName());
        }
        if (getEnableSinceTargetSdk() != -1) {
            append.append("; enableSinceTargetSdk=").append(getEnableSinceTargetSdk());
        }
        if (getDisabled()) {
            append.append("; disabled");
        }
        if (getLoggingOnly()) {
            append.append("; loggingOnly");
        }
        if (getOverridable()) {
            append.append("; overridable");
        }
        return append.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END).toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof com.android.internal.compat.CompatibilityChangeInfo)) {
            return false;
        }
        com.android.internal.compat.CompatibilityChangeInfo compatibilityChangeInfo = (com.android.internal.compat.CompatibilityChangeInfo) obj;
        if (this.mChangeId == compatibilityChangeInfo.mChangeId && this.mName.equals(compatibilityChangeInfo.mName) && this.mEnableSinceTargetSdk == compatibilityChangeInfo.mEnableSinceTargetSdk && this.mDisabled == compatibilityChangeInfo.mDisabled && this.mLoggingOnly == compatibilityChangeInfo.mLoggingOnly && this.mDescription.equals(compatibilityChangeInfo.mDescription) && this.mOverridable == compatibilityChangeInfo.mOverridable) {
            return true;
        }
        return false;
    }
}
