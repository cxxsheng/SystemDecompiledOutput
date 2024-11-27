package android.hardware.input;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class VirtualDpadConfig extends android.hardware.input.VirtualInputDeviceConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.input.VirtualDpadConfig> CREATOR = new android.os.Parcelable.Creator<android.hardware.input.VirtualDpadConfig>() { // from class: android.hardware.input.VirtualDpadConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.VirtualDpadConfig createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.input.VirtualDpadConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.input.VirtualDpadConfig[] newArray(int i) {
            return new android.hardware.input.VirtualDpadConfig[i];
        }
    };

    private VirtualDpadConfig(android.hardware.input.VirtualDpadConfig.Builder builder) {
        super(builder);
    }

    private VirtualDpadConfig(android.os.Parcel parcel) {
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

    public static final class Builder extends android.hardware.input.VirtualInputDeviceConfig.Builder<android.hardware.input.VirtualDpadConfig.Builder> {
        public android.hardware.input.VirtualDpadConfig build() {
            return new android.hardware.input.VirtualDpadConfig(this);
        }
    }
}
