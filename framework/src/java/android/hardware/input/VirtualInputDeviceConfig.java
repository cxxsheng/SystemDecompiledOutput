package android.hardware.input;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public abstract class VirtualInputDeviceConfig {
    private static final int DEVICE_NAME_MAX_LENGTH = 80;
    private final int mAssociatedDisplayId;
    private final java.lang.String mInputDeviceName;
    private final int mProductId;
    private final int mVendorId;

    protected VirtualInputDeviceConfig(android.hardware.input.VirtualInputDeviceConfig.Builder<? extends android.hardware.input.VirtualInputDeviceConfig.Builder<?>> builder) {
        this.mVendorId = ((android.hardware.input.VirtualInputDeviceConfig.Builder) builder).mVendorId;
        this.mProductId = ((android.hardware.input.VirtualInputDeviceConfig.Builder) builder).mProductId;
        this.mAssociatedDisplayId = ((android.hardware.input.VirtualInputDeviceConfig.Builder) builder).mAssociatedDisplayId;
        this.mInputDeviceName = (java.lang.String) java.util.Objects.requireNonNull(((android.hardware.input.VirtualInputDeviceConfig.Builder) builder).mInputDeviceName);
        if (this.mAssociatedDisplayId == -1) {
            throw new java.lang.IllegalArgumentException("Display association is required for virtual input devices.");
        }
        if (this.mInputDeviceName.getBytes(java.nio.charset.StandardCharsets.UTF_8).length >= 80) {
            throw new java.lang.IllegalArgumentException("Input device name exceeds maximum length of 80bytes: " + this.mInputDeviceName);
        }
    }

    protected VirtualInputDeviceConfig(android.os.Parcel parcel) {
        this.mVendorId = parcel.readInt();
        this.mProductId = parcel.readInt();
        this.mAssociatedDisplayId = parcel.readInt();
        this.mInputDeviceName = (java.lang.String) java.util.Objects.requireNonNull(parcel.readString8());
    }

    public int getVendorId() {
        return this.mVendorId;
    }

    public int getProductId() {
        return this.mProductId;
    }

    public int getAssociatedDisplayId() {
        return this.mAssociatedDisplayId;
    }

    public java.lang.String getInputDeviceName() {
        return this.mInputDeviceName;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mVendorId);
        parcel.writeInt(this.mProductId);
        parcel.writeInt(this.mAssociatedDisplayId);
        parcel.writeString8(this.mInputDeviceName);
    }

    public java.lang.String toString() {
        return getClass().getName() + "(  name=" + this.mInputDeviceName + " vendorId=" + this.mVendorId + " productId=" + this.mProductId + " associatedDisplayId=" + this.mAssociatedDisplayId + additionalFieldsToString() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }

    java.lang.String additionalFieldsToString() {
        return "";
    }

    public static abstract class Builder<T extends android.hardware.input.VirtualInputDeviceConfig.Builder<T>> {
        private int mAssociatedDisplayId = -1;
        private java.lang.String mInputDeviceName;
        private int mProductId;
        private int mVendorId;

        public T setVendorId(int i) {
            this.mVendorId = i;
            return self();
        }

        public T setProductId(int i) {
            this.mProductId = i;
            return self();
        }

        public T setAssociatedDisplayId(int i) {
            this.mAssociatedDisplayId = i;
            return self();
        }

        public T setInputDeviceName(java.lang.String str) {
            this.mInputDeviceName = (java.lang.String) java.util.Objects.requireNonNull(str);
            return self();
        }

        T self() {
            return this;
        }
    }
}
