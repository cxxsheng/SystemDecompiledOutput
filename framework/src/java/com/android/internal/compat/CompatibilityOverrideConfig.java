package com.android.internal.compat;

/* loaded from: classes4.dex */
public final class CompatibilityOverrideConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.compat.CompatibilityOverrideConfig> CREATOR = new android.os.Parcelable.Creator<com.android.internal.compat.CompatibilityOverrideConfig>() { // from class: com.android.internal.compat.CompatibilityOverrideConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.compat.CompatibilityOverrideConfig createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.compat.CompatibilityOverrideConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.compat.CompatibilityOverrideConfig[] newArray(int i) {
            return new com.android.internal.compat.CompatibilityOverrideConfig[i];
        }
    };
    public final java.util.Map<java.lang.Long, android.app.compat.PackageOverride> overrides;

    public CompatibilityOverrideConfig(java.util.Map<java.lang.Long, android.app.compat.PackageOverride> map) {
        this.overrides = map;
    }

    private CompatibilityOverrideConfig(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        this.overrides = new java.util.HashMap();
        for (int i = 0; i < readInt; i++) {
            this.overrides.put(java.lang.Long.valueOf(parcel.readLong()), android.app.compat.PackageOverride.createFromParcel(parcel));
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.overrides.size());
        for (java.lang.Long l : this.overrides.keySet()) {
            parcel.writeLong(l.longValue());
            this.overrides.get(l).writeToParcel(parcel);
        }
    }
}
