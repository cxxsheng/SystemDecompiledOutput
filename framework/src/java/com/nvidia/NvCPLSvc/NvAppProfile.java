package com.nvidia.NvCPLSvc;

/* loaded from: classes5.dex */
public class NvAppProfile implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.nvidia.NvCPLSvc.NvAppProfile> CREATOR = new android.os.Parcelable.Creator<com.nvidia.NvCPLSvc.NvAppProfile>() { // from class: com.nvidia.NvCPLSvc.NvAppProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.nvidia.NvCPLSvc.NvAppProfile createFromParcel(android.os.Parcel parcel) {
            return com.nvidia.NvCPLSvc.NvAppProfile.createFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.nvidia.NvCPLSvc.NvAppProfile[] newArray(int i) {
            return new com.nvidia.NvCPLSvc.NvAppProfile[i];
        }
    };
    public final java.lang.String pkgName;
    public final java.lang.String pkgVersion;
    public android.util.SparseArray<java.lang.String> settings;
    public final int typeId;

    public NvAppProfile(int i, java.lang.String str, java.lang.String str2, android.util.SparseArray<java.lang.String> sparseArray) {
        this.typeId = i;
        this.pkgName = str;
        this.pkgVersion = str2;
        this.settings = sparseArray;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.nvidia.NvCPLSvc.NvAppProfile createFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        java.lang.String decodeNull = decodeNull(parcel.readString());
        java.lang.String decodeNull2 = decodeNull(parcel.readString());
        int readInt2 = parcel.readInt();
        android.util.SparseArray sparseArray = new android.util.SparseArray();
        for (int i = 0; i < readInt2; i++) {
            sparseArray.append(parcel.readInt(), parcel.readString());
        }
        return new com.nvidia.NvCPLSvc.NvAppProfile(readInt, decodeNull, decodeNull2, sparseArray);
    }

    private static java.lang.String encodeNull(java.lang.String str) {
        return str != null ? str : "";
    }

    private static java.lang.String decodeNull(java.lang.String str) {
        if (str.equals("")) {
            return null;
        }
        return str;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.typeId);
        parcel.writeString(encodeNull(this.pkgName));
        parcel.writeString(encodeNull(this.pkgVersion));
        parcel.writeInt(this.settings.size());
        for (int i2 = 0; i2 < this.settings.size(); i2++) {
            parcel.writeInt(this.settings.keyAt(i2));
            parcel.writeString(this.settings.valueAt(i2));
        }
    }
}
