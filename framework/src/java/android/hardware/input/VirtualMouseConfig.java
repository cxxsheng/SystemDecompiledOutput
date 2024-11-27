package android.hardware.input;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class VirtualMouseConfig extends android.hardware.input.VirtualInputDeviceConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.input.VirtualMouseConfig> CREATOR = new android.os.Parcelable.Creator<android.hardware.input.VirtualMouseConfig>() { // from class: android.hardware.input.VirtualMouseConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.VirtualMouseConfig createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.input.VirtualMouseConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.VirtualMouseConfig[] newArray(int i) {
            return new android.hardware.input.VirtualMouseConfig[i];
        }
    };

    private VirtualMouseConfig(android.hardware.input.VirtualMouseConfig.Builder builder) {
        super(builder);
    }

    private VirtualMouseConfig(android.os.Parcel parcel) {
        super(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.hardware.input.VirtualInputDeviceConfig, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }

    public static final class Builder extends android.hardware.input.VirtualInputDeviceConfig.Builder<android.hardware.input.VirtualMouseConfig.Builder> {
        public android.hardware.input.VirtualMouseConfig build() {
            return new android.hardware.input.VirtualMouseConfig(this);
        }
    }
}
