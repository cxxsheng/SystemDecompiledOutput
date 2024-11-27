package android.hardware.input;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class VirtualStylusConfig extends android.hardware.input.VirtualTouchDeviceConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.input.VirtualStylusConfig> CREATOR = new android.os.Parcelable.Creator<android.hardware.input.VirtualStylusConfig>() { // from class: android.hardware.input.VirtualStylusConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.VirtualStylusConfig createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.input.VirtualStylusConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.VirtualStylusConfig[] newArray(int i) {
            return new android.hardware.input.VirtualStylusConfig[i];
        }
    };

    @Override // android.hardware.input.VirtualTouchDeviceConfig
    public /* bridge */ /* synthetic */ int getHeight() {
        return super.getHeight();
    }

    @Override // android.hardware.input.VirtualTouchDeviceConfig
    public /* bridge */ /* synthetic */ int getWidth() {
        return super.getWidth();
    }

    private VirtualStylusConfig(android.hardware.input.VirtualStylusConfig.Builder builder) {
        super(builder);
    }

    private VirtualStylusConfig(android.os.Parcel parcel) {
        super(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.hardware.input.VirtualTouchDeviceConfig, android.hardware.input.VirtualInputDeviceConfig, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }

    public static final class Builder extends android.hardware.input.VirtualTouchDeviceConfig.Builder<android.hardware.input.VirtualStylusConfig.Builder> {
        public Builder(int i, int i2) {
            super(i, i2);
        }

        public android.hardware.input.VirtualStylusConfig build() {
            return new android.hardware.input.VirtualStylusConfig(this);
        }
    }
}
