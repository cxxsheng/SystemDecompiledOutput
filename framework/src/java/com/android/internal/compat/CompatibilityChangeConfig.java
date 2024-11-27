package com.android.internal.compat;

/* loaded from: classes4.dex */
public final class CompatibilityChangeConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.compat.CompatibilityChangeConfig> CREATOR = new android.os.Parcelable.Creator<com.android.internal.compat.CompatibilityChangeConfig>() { // from class: com.android.internal.compat.CompatibilityChangeConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.compat.CompatibilityChangeConfig createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.compat.CompatibilityChangeConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.compat.CompatibilityChangeConfig[] newArray(int i) {
            return new com.android.internal.compat.CompatibilityChangeConfig[i];
        }
    };
    private final android.compat.Compatibility.ChangeConfig mChangeConfig;

    public CompatibilityChangeConfig(android.compat.Compatibility.ChangeConfig changeConfig) {
        this.mChangeConfig = changeConfig;
    }

    public java.util.Set<java.lang.Long> enabledChanges() {
        return this.mChangeConfig.getEnabledSet();
    }

    public java.util.Set<java.lang.Long> disabledChanges() {
        return this.mChangeConfig.getDisabledSet();
    }

    public boolean isChangeEnabled(long j) {
        if (this.mChangeConfig.isForceEnabled(j)) {
            return true;
        }
        if (this.mChangeConfig.isForceDisabled(j)) {
            return false;
        }
        throw new java.lang.IllegalStateException("Change " + j + " is not defined.");
    }

    private CompatibilityChangeConfig(android.os.Parcel parcel) {
        this.mChangeConfig = new android.compat.Compatibility.ChangeConfig(toLongSet(parcel.createLongArray()), toLongSet(parcel.createLongArray()));
    }

    private static java.util.Set<java.lang.Long> toLongSet(long[] jArr) {
        java.util.HashSet hashSet = new java.util.HashSet();
        for (long j : jArr) {
            hashSet.add(java.lang.Long.valueOf(j));
        }
        return hashSet;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        long[] enabledChangesArray = this.mChangeConfig.getEnabledChangesArray();
        long[] disabledChangesArray = this.mChangeConfig.getDisabledChangesArray();
        parcel.writeLongArray(enabledChangesArray);
        parcel.writeLongArray(disabledChangesArray);
    }
}
