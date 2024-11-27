package android.hardware.display;

/* loaded from: classes2.dex */
public final class HdrConversionMode implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.display.HdrConversionMode> CREATOR = new android.os.Parcelable.Creator<android.hardware.display.HdrConversionMode>() { // from class: android.hardware.display.HdrConversionMode.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.display.HdrConversionMode createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.display.HdrConversionMode(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.display.HdrConversionMode[] newArray(int i) {
            return new android.hardware.display.HdrConversionMode[i];
        }
    };
    public static final int HDR_CONVERSION_FORCE = 3;
    public static final int HDR_CONVERSION_PASSTHROUGH = 1;
    public static final int HDR_CONVERSION_SYSTEM = 2;
    public static final int HDR_CONVERSION_UNSUPPORTED = 0;
    private final int mConversionMode;
    private int mPreferredHdrOutputType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ConversionMode {
    }

    public HdrConversionMode(int i, int i2) {
        if ((i == 1 || i == 0) && i2 != -1) {
            throw new java.lang.IllegalArgumentException("preferredHdrOutputType must not be set if the conversion mode is " + hdrConversionModeString(i));
        }
        this.mConversionMode = i;
        this.mPreferredHdrOutputType = i2;
    }

    public HdrConversionMode(int i) {
        this.mConversionMode = i;
        this.mPreferredHdrOutputType = -1;
    }

    private HdrConversionMode(android.os.Parcel parcel) {
        this(parcel.readInt(), parcel.readInt());
    }

    public int getConversionMode() {
        return this.mConversionMode;
    }

    public int getPreferredHdrOutputType() {
        return this.mPreferredHdrOutputType;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mConversionMode);
        parcel.writeInt(this.mPreferredHdrOutputType);
    }

    public boolean equals(java.lang.Object obj) {
        return (obj instanceof android.hardware.display.HdrConversionMode) && equals((android.hardware.display.HdrConversionMode) obj);
    }

    public int hashCode() {
        return 0;
    }

    public java.lang.String toString() {
        return "HdrConversionMode{ConversionMode=" + hdrConversionModeString(getConversionMode()) + ", PreferredHdrOutputType=" + android.view.Display.HdrCapabilities.hdrTypeToString(getPreferredHdrOutputType()) + "}";
    }

    private boolean equals(android.hardware.display.HdrConversionMode hdrConversionMode) {
        return hdrConversionMode != null && this.mConversionMode == hdrConversionMode.getConversionMode() && this.mPreferredHdrOutputType == hdrConversionMode.getPreferredHdrOutputType();
    }

    private static java.lang.String hdrConversionModeString(int i) {
        switch (i) {
            case 1:
                return "HDR_CONVERSION_PASSTHROUGH";
            case 2:
                return "HDR_CONVERSION_SYSTEM";
            case 3:
                return "HDR_CONVERSION_FORCE";
            default:
                return "HDR_CONVERSION_UNSUPPORTED";
        }
    }
}
