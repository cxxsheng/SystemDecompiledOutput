package android.hardware.input;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class VirtualTouchscreenConfig extends android.hardware.input.VirtualTouchDeviceConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.input.VirtualTouchscreenConfig> CREATOR = new android.os.Parcelable.Creator<android.hardware.input.VirtualTouchscreenConfig>() { // from class: android.hardware.input.VirtualTouchscreenConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.VirtualTouchscreenConfig createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.input.VirtualTouchscreenConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.VirtualTouchscreenConfig[] newArray(int i) {
            return new android.hardware.input.VirtualTouchscreenConfig[i];
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

    private VirtualTouchscreenConfig(android.hardware.input.VirtualTouchscreenConfig.Builder builder) {
        super(builder);
    }

    private VirtualTouchscreenConfig(android.os.Parcel parcel) {
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

    public static final class Builder extends android.hardware.input.VirtualTouchDeviceConfig.Builder<android.hardware.input.VirtualTouchscreenConfig.Builder> {
        public Builder(int i, int i2) {
            super(i, i2);
        }

        public android.hardware.input.VirtualTouchscreenConfig build() {
            return new android.hardware.input.VirtualTouchscreenConfig(this);
        }
    }
}
