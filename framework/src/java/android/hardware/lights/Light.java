package android.hardware.lights;

/* loaded from: classes2.dex */
public final class Light implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.lights.Light> CREATOR = new android.os.Parcelable.Creator<android.hardware.lights.Light>() { // from class: android.hardware.lights.Light.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.lights.Light createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.lights.Light(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.lights.Light[] newArray(int i) {
            return new android.hardware.lights.Light[i];
        }
    };
    public static final int LIGHT_CAPABILITY_BRIGHTNESS = 1;
    public static final int LIGHT_CAPABILITY_COLOR_RGB = 2;

    @java.lang.Deprecated
    public static final int LIGHT_CAPABILITY_RGB = 0;
    public static final int LIGHT_TYPE_CAMERA = 9;
    public static final int LIGHT_TYPE_INPUT = 10001;
    public static final int LIGHT_TYPE_KEYBOARD_BACKLIGHT = 10003;
    public static final int LIGHT_TYPE_MICROPHONE = 8;
    public static final int LIGHT_TYPE_PLAYER_ID = 10002;
    private final int mCapabilities;
    private final int mId;
    private final java.lang.String mName;
    private final int mOrdinal;
    private final int[] mPreferredBrightnessLevels;
    private final int mType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LightCapability {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LightType {
    }

    public Light(int i, int i2, int i3) {
        this(i, "Light", i2, i3, 0, null);
    }

    public Light(int i, java.lang.String str, int i2, int i3, int i4) {
        this(i, str, i2, i3, i4, null);
    }

    public Light(int i, java.lang.String str, int i2, int i3, int i4, int[] iArr) {
        this.mId = i;
        this.mName = str;
        this.mOrdinal = i2;
        this.mType = i3;
        this.mCapabilities = i4;
        this.mPreferredBrightnessLevels = iArr;
    }

    private Light(android.os.Parcel parcel) {
        this.mId = parcel.readInt();
        this.mName = parcel.readString();
        this.mOrdinal = parcel.readInt();
        this.mType = parcel.readInt();
        this.mCapabilities = parcel.readInt();
        this.mPreferredBrightnessLevels = parcel.createIntArray();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mOrdinal);
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mCapabilities);
        parcel.writeIntArray(this.mPreferredBrightnessLevels);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.hardware.lights.Light)) {
            return false;
        }
        android.hardware.lights.Light light = (android.hardware.lights.Light) obj;
        return this.mId == light.mId && this.mOrdinal == light.mOrdinal && this.mType == light.mType && this.mCapabilities == light.mCapabilities;
    }

    public int hashCode() {
        return this.mId;
    }

    public java.lang.String toString() {
        return "[Name=" + this.mName + " Id=" + this.mId + " Type=" + this.mType + " Capabilities=" + this.mCapabilities + " Ordinal=" + this.mOrdinal + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    public int getId() {
        return this.mId;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public int getOrdinal() {
        return this.mOrdinal;
    }

    public int getType() {
        return this.mType;
    }

    public int getCapabilities() {
        return this.mCapabilities;
    }

    public boolean hasBrightnessControl() {
        return (this.mCapabilities & 1) == 1;
    }

    public boolean hasRgbControl() {
        return (this.mCapabilities & 2) == 2;
    }

    public int[] getPreferredBrightnessLevels() {
        return this.mPreferredBrightnessLevels;
    }
}
