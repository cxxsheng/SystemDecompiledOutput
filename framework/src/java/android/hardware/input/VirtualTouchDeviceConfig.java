package android.hardware.input;

/* loaded from: classes2.dex */
abstract class VirtualTouchDeviceConfig extends android.hardware.input.VirtualInputDeviceConfig {
    private final int mHeight;
    private final int mWidth;

    VirtualTouchDeviceConfig(android.hardware.input.VirtualTouchDeviceConfig.Builder<? extends android.hardware.input.VirtualTouchDeviceConfig.Builder<?>> builder) {
        super(builder);
        this.mWidth = ((android.hardware.input.VirtualTouchDeviceConfig.Builder) builder).mWidth;
        this.mHeight = ((android.hardware.input.VirtualTouchDeviceConfig.Builder) builder).mHeight;
    }

    VirtualTouchDeviceConfig(android.os.Parcel parcel) {
        super(parcel);
        this.mWidth = parcel.readInt();
        this.mHeight = parcel.readInt();
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // android.hardware.input.VirtualInputDeviceConfig, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mWidth);
        parcel.writeInt(this.mHeight);
    }

    @Override // android.hardware.input.VirtualInputDeviceConfig
    java.lang.String additionalFieldsToString() {
        return " width=" + this.mWidth + " height=" + this.mHeight;
    }

    static abstract class Builder<T extends android.hardware.input.VirtualTouchDeviceConfig.Builder<T>> extends android.hardware.input.VirtualInputDeviceConfig.Builder<T> {
        private final int mHeight;
        private final int mWidth;

        Builder(int i, int i2) {
            if (i2 <= 0 || i <= 0) {
                throw new java.lang.IllegalArgumentException("Cannot create a virtual touch-based device, dimensions must be positive. Got: (" + i2 + ", " + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
            this.mHeight = i2;
            this.mWidth = i;
        }
    }
}
