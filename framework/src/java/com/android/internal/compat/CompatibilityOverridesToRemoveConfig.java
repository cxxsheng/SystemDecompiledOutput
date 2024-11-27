package com.android.internal.compat;

/* loaded from: classes4.dex */
public final class CompatibilityOverridesToRemoveConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.compat.CompatibilityOverridesToRemoveConfig> CREATOR = new android.os.Parcelable.Creator<com.android.internal.compat.CompatibilityOverridesToRemoveConfig>() { // from class: com.android.internal.compat.CompatibilityOverridesToRemoveConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.compat.CompatibilityOverridesToRemoveConfig createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.compat.CompatibilityOverridesToRemoveConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.compat.CompatibilityOverridesToRemoveConfig[] newArray(int i) {
            return new com.android.internal.compat.CompatibilityOverridesToRemoveConfig[i];
        }
    };
    public final java.util.Set<java.lang.Long> changeIds;

    public CompatibilityOverridesToRemoveConfig(java.util.Set<java.lang.Long> set) {
        this.changeIds = set;
    }

    private CompatibilityOverridesToRemoveConfig(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        this.changeIds = new java.util.HashSet();
        for (int i = 0; i < readInt; i++) {
            this.changeIds.add(java.lang.Long.valueOf(parcel.readLong()));
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.changeIds.size());
        java.util.Iterator<java.lang.Long> it = this.changeIds.iterator();
        while (it.hasNext()) {
            parcel.writeLong(it.next().longValue());
        }
    }
}
