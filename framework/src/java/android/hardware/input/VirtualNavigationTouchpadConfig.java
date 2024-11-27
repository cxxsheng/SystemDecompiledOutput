package android.hardware.input;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class VirtualNavigationTouchpadConfig extends android.hardware.input.VirtualInputDeviceConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.input.VirtualNavigationTouchpadConfig> CREATOR = new android.os.Parcelable.Creator<android.hardware.input.VirtualNavigationTouchpadConfig>() { // from class: android.hardware.input.VirtualNavigationTouchpadConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.VirtualNavigationTouchpadConfig createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.input.VirtualNavigationTouchpadConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.VirtualNavigationTouchpadConfig[] newArray(int i) {
            return new android.hardware.input.VirtualNavigationTouchpadConfig[i];
        }
    };
    private final int mHeight;
    private final int mWidth;

    private VirtualNavigationTouchpadConfig(android.hardware.input.VirtualNavigationTouchpadConfig.Builder builder) {
        super(builder);
        this.mHeight = builder.mHeight;
        this.mWidth = builder.mWidth;
    }

    private VirtualNavigationTouchpadConfig(android.os.Parcel parcel) {
        super(parcel);
        this.mHeight = parcel.readInt();
        this.mWidth = parcel.readInt();
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getWidth() {
        return this.mWidth;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.hardware.input.VirtualInputDeviceConfig, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mHeight);
        parcel.writeInt(this.mWidth);
    }

    @Override // android.hardware.input.VirtualInputDeviceConfig
    java.lang.String additionalFieldsToString() {
        return " width=" + this.mWidth + " height=" + this.mHeight;
    }

    public static final class Builder extends android.hardware.input.VirtualInputDeviceConfig.Builder<android.hardware.input.VirtualNavigationTouchpadConfig.Builder> {
        private final int mHeight;
        private final int mWidth;

        public Builder(int i, int i2) {
            if (i2 <= 0 || i <= 0) {
                throw new java.lang.IllegalArgumentException("Cannot create a virtual navigation touchpad, touchpad dimensions must be positive. Got: (" + i2 + ", " + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
            this.mHeight = i2;
            this.mWidth = i;
        }

        public android.hardware.input.VirtualNavigationTouchpadConfig build() {
            return new android.hardware.input.VirtualNavigationTouchpadConfig(this);
        }
    }
}
