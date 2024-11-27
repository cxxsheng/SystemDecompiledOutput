package com.android.internal.compat;

/* loaded from: classes4.dex */
public final class CompatibilityOverridesByPackageConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.compat.CompatibilityOverridesByPackageConfig> CREATOR = new android.os.Parcelable.Creator<com.android.internal.compat.CompatibilityOverridesByPackageConfig>() { // from class: com.android.internal.compat.CompatibilityOverridesByPackageConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.compat.CompatibilityOverridesByPackageConfig createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.compat.CompatibilityOverridesByPackageConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.compat.CompatibilityOverridesByPackageConfig[] newArray(int i) {
            return new com.android.internal.compat.CompatibilityOverridesByPackageConfig[i];
        }
    };
    public final java.util.Map<java.lang.String, com.android.internal.compat.CompatibilityOverrideConfig> packageNameToOverrides;

    public CompatibilityOverridesByPackageConfig(java.util.Map<java.lang.String, com.android.internal.compat.CompatibilityOverrideConfig> map) {
        this.packageNameToOverrides = map;
    }

    private CompatibilityOverridesByPackageConfig(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        this.packageNameToOverrides = new java.util.HashMap();
        for (int i = 0; i < readInt; i++) {
            this.packageNameToOverrides.put(parcel.readString(), com.android.internal.compat.CompatibilityOverrideConfig.CREATOR.createFromParcel(parcel));
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.packageNameToOverrides.size());
        for (java.lang.String str : this.packageNameToOverrides.keySet()) {
            parcel.writeString(str);
            this.packageNameToOverrides.get(str).writeToParcel(parcel, 0);
        }
    }
}
