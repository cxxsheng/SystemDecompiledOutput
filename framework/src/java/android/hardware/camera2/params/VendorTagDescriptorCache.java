package android.hardware.camera2.params;

/* loaded from: classes.dex */
public final class VendorTagDescriptorCache implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.camera2.params.VendorTagDescriptorCache> CREATOR = new android.os.Parcelable.Creator<android.hardware.camera2.params.VendorTagDescriptorCache>() { // from class: android.hardware.camera2.params.VendorTagDescriptorCache.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.params.VendorTagDescriptorCache createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.camera2.params.VendorTagDescriptorCache(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.params.VendorTagDescriptorCache[] newArray(int i) {
            return new android.hardware.camera2.params.VendorTagDescriptorCache[i];
        }
    };
    private static final java.lang.String TAG = "VendorTagDescriptorCache";

    private VendorTagDescriptorCache(android.os.Parcel parcel) {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (parcel == null) {
            throw new java.lang.IllegalArgumentException("dest must not be null");
        }
    }
}
