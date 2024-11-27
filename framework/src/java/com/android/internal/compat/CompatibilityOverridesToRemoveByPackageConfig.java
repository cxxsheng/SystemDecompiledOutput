package com.android.internal.compat;

/* loaded from: classes4.dex */
public final class CompatibilityOverridesToRemoveByPackageConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.compat.CompatibilityOverridesToRemoveByPackageConfig> CREATOR = new android.os.Parcelable.Creator<com.android.internal.compat.CompatibilityOverridesToRemoveByPackageConfig>() { // from class: com.android.internal.compat.CompatibilityOverridesToRemoveByPackageConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.compat.CompatibilityOverridesToRemoveByPackageConfig createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.compat.CompatibilityOverridesToRemoveByPackageConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.compat.CompatibilityOverridesToRemoveByPackageConfig[] newArray(int i) {
            return new com.android.internal.compat.CompatibilityOverridesToRemoveByPackageConfig[i];
        }
    };
    public final java.util.Map<java.lang.String, com.android.internal.compat.CompatibilityOverridesToRemoveConfig> packageNameToOverridesToRemove;

    public CompatibilityOverridesToRemoveByPackageConfig(java.util.Map<java.lang.String, com.android.internal.compat.CompatibilityOverridesToRemoveConfig> map) {
        this.packageNameToOverridesToRemove = map;
    }

    private CompatibilityOverridesToRemoveByPackageConfig(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        this.packageNameToOverridesToRemove = new java.util.HashMap();
        for (int i = 0; i < readInt; i++) {
            this.packageNameToOverridesToRemove.put(parcel.readString(), com.android.internal.compat.CompatibilityOverridesToRemoveConfig.CREATOR.createFromParcel(parcel));
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.packageNameToOverridesToRemove.size());
        for (java.lang.String str : this.packageNameToOverridesToRemove.keySet()) {
            parcel.writeString(str);
            this.packageNameToOverridesToRemove.get(str).writeToParcel(parcel, 0);
        }
    }
}
