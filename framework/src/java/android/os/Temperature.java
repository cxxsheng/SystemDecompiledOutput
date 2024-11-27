package android.os;

/* loaded from: classes3.dex */
public final class Temperature implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.Temperature> CREATOR = new android.os.Parcelable.Creator<android.os.Temperature>() { // from class: android.os.Temperature.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.Temperature createFromParcel(android.os.Parcel parcel) {
            return new android.os.Temperature(parcel.readFloat(), parcel.readInt(), parcel.readString(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.Temperature[] newArray(int i) {
            return new android.os.Temperature[i];
        }
    };
    public static final int THROTTLING_CRITICAL = 4;
    public static final int THROTTLING_EMERGENCY = 5;
    public static final int THROTTLING_LIGHT = 1;
    public static final int THROTTLING_MODERATE = 2;
    public static final int THROTTLING_NONE = 0;
    public static final int THROTTLING_SEVERE = 3;
    public static final int THROTTLING_SHUTDOWN = 6;
    public static final int TYPE_AMBIENT = 18;
    public static final int TYPE_BATTERY = 2;
    public static final int TYPE_BCL_CURRENT = 7;
    public static final int TYPE_BCL_PERCENTAGE = 8;
    public static final int TYPE_BCL_VOLTAGE = 6;
    public static final int TYPE_CAMERA = 15;
    public static final int TYPE_CPU = 0;
    public static final int TYPE_DISPLAY = 11;
    public static final int TYPE_FLASHLIGHT = 16;
    public static final int TYPE_GPU = 1;
    public static final int TYPE_MODEM = 12;
    public static final int TYPE_NPU = 9;
    public static final int TYPE_POGO = 19;
    public static final int TYPE_POWER_AMPLIFIER = 5;
    public static final int TYPE_SKIN = 3;
    public static final int TYPE_SOC = 13;
    public static final int TYPE_SPEAKER = 17;
    public static final int TYPE_TPU = 10;
    public static final int TYPE_UNKNOWN = -1;
    public static final int TYPE_USB_PORT = 4;
    public static final int TYPE_WIFI = 14;
    private final java.lang.String mName;
    private final int mStatus;
    private final int mType;
    private final float mValue;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ThrottlingStatus {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Type {
    }

    public static boolean isValidType(int i) {
        return i >= -1 && i <= 19;
    }

    public static boolean isValidStatus(int i) {
        return i >= 0 && i <= 6;
    }

    public Temperature(float f, int i, java.lang.String str, int i2) {
        com.android.internal.util.Preconditions.checkArgument(isValidType(i), "Invalid Type");
        com.android.internal.util.Preconditions.checkArgument(isValidStatus(i2), "Invalid Status");
        this.mValue = f;
        this.mType = i;
        this.mName = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        this.mStatus = i2;
    }

    public float getValue() {
        return this.mValue;
    }

    public int getType() {
        return this.mType;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public java.lang.String toString() {
        return "Temperature{mValue=" + this.mValue + ", mType=" + this.mType + ", mName=" + this.mName + ", mStatus=" + this.mStatus + "}";
    }

    public int hashCode() {
        return (((((this.mName.hashCode() * 31) + java.lang.Float.hashCode(this.mValue)) * 31) + this.mType) * 31) + this.mStatus;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.os.Temperature)) {
            return false;
        }
        android.os.Temperature temperature = (android.os.Temperature) obj;
        return temperature.mValue == this.mValue && temperature.mType == this.mType && temperature.mName.equals(this.mName) && temperature.mStatus == this.mStatus;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeFloat(this.mValue);
        parcel.writeInt(this.mType);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mStatus);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
