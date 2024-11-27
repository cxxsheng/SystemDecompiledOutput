package android.os;

/* loaded from: classes3.dex */
public final class CoolingDevice implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.CoolingDevice> CREATOR = new android.os.Parcelable.Creator<android.os.CoolingDevice>() { // from class: android.os.CoolingDevice.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.CoolingDevice createFromParcel(android.os.Parcel parcel) {
            return new android.os.CoolingDevice(parcel.readLong(), parcel.readInt(), parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.CoolingDevice[] newArray(int i) {
            return new android.os.CoolingDevice[i];
        }
    };
    public static final int TYPE_BATTERY = 1;
    public static final int TYPE_CAMERA = 12;
    public static final int TYPE_COMPONENT = 6;
    public static final int TYPE_CPU = 2;
    public static final int TYPE_DISPLAY = 9;
    public static final int TYPE_FAN = 0;
    public static final int TYPE_FLASHLIGHT = 13;
    public static final int TYPE_GPU = 3;
    public static final int TYPE_MODEM = 4;
    public static final int TYPE_NPU = 5;
    public static final int TYPE_POWER_AMPLIFIER = 8;
    public static final int TYPE_SPEAKER = 10;
    public static final int TYPE_TPU = 7;
    public static final int TYPE_USB_PORT = 14;
    public static final int TYPE_WIFI = 11;
    private final java.lang.String mName;
    private final int mType;
    private final long mValue;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Type {
    }

    public static boolean isValidType(int i) {
        return i >= 0 && i <= 14;
    }

    public CoolingDevice(long j, int i, java.lang.String str) {
        com.android.internal.util.Preconditions.checkArgument(isValidType(i), "Invalid Type");
        this.mValue = j;
        this.mType = i;
        this.mName = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str);
    }

    public long getValue() {
        return this.mValue;
    }

    public int getType() {
        return this.mType;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public java.lang.String toString() {
        return "CoolingDevice{mValue=" + this.mValue + ", mType=" + this.mType + ", mName=" + this.mName + "}";
    }

    public int hashCode() {
        return (((this.mName.hashCode() * 31) + java.lang.Long.hashCode(this.mValue)) * 31) + this.mType;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.os.CoolingDevice)) {
            return false;
        }
        android.os.CoolingDevice coolingDevice = (android.os.CoolingDevice) obj;
        return coolingDevice.mValue == this.mValue && coolingDevice.mType == this.mType && coolingDevice.mName.equals(this.mName);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mValue);
        parcel.writeInt(this.mType);
        parcel.writeString(this.mName);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
