package android.hardware.camera2.params;

/* loaded from: classes.dex */
public final class VendorTagDescriptor implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.camera2.params.VendorTagDescriptor> CREATOR = new android.os.Parcelable.Creator<android.hardware.camera2.params.VendorTagDescriptor>() { // from class: android.hardware.camera2.params.VendorTagDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.params.VendorTagDescriptor createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.camera2.params.VendorTagDescriptor(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.params.VendorTagDescriptor[] newArray(int i) {
            return new android.hardware.camera2.params.VendorTagDescriptor[i];
        }
    };
    private static final java.lang.String TAG = "VendorTagDescriptor";

    private VendorTagDescriptor(android.os.Parcel parcel) {
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
