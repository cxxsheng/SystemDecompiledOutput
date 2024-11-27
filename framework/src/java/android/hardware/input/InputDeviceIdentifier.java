package android.hardware.input;

/* loaded from: classes2.dex */
public final class InputDeviceIdentifier implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.input.InputDeviceIdentifier> CREATOR = new android.os.Parcelable.Creator<android.hardware.input.InputDeviceIdentifier>() { // from class: android.hardware.input.InputDeviceIdentifier.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.InputDeviceIdentifier createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.input.InputDeviceIdentifier(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.InputDeviceIdentifier[] newArray(int i) {
            return new android.hardware.input.InputDeviceIdentifier[i];
        }
    };
    private final java.lang.String mDescriptor;
    private final int mProductId;
    private final int mVendorId;

    public InputDeviceIdentifier(java.lang.String str, int i, int i2) {
        this.mDescriptor = str;
        this.mVendorId = i;
        this.mProductId = i2;
    }

    private InputDeviceIdentifier(android.os.Parcel parcel) {
        this.mDescriptor = parcel.readString();
        this.mVendorId = parcel.readInt();
        this.mProductId = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mDescriptor);
        parcel.writeInt(this.mVendorId);
        parcel.writeInt(this.mProductId);
    }

    public java.lang.String getDescriptor() {
        return this.mDescriptor;
    }

    public int getVendorId() {
        return this.mVendorId;
    }

    public int getProductId() {
        return this.mProductId;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.hardware.input.InputDeviceIdentifier)) {
            return false;
        }
        android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier = (android.hardware.input.InputDeviceIdentifier) obj;
        if (this.mVendorId == inputDeviceIdentifier.mVendorId && this.mProductId == inputDeviceIdentifier.mProductId && android.text.TextUtils.equals(this.mDescriptor, inputDeviceIdentifier.mDescriptor)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mDescriptor, java.lang.Integer.valueOf(this.mVendorId), java.lang.Integer.valueOf(this.mProductId));
    }

    public java.lang.String toString() {
        return "InputDeviceIdentifier: vendorId: " + this.mVendorId + ", productId: " + this.mProductId + ", descriptor: " + this.mDescriptor;
    }
}
