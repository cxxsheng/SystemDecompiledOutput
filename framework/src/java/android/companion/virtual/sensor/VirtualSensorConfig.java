package android.companion.virtual.sensor;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class VirtualSensorConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.companion.virtual.sensor.VirtualSensorConfig> CREATOR = new android.os.Parcelable.Creator<android.companion.virtual.sensor.VirtualSensorConfig>() { // from class: android.companion.virtual.sensor.VirtualSensorConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.virtual.sensor.VirtualSensorConfig createFromParcel(android.os.Parcel parcel) {
            return new android.companion.virtual.sensor.VirtualSensorConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.virtual.sensor.VirtualSensorConfig[] newArray(int i) {
            return new android.companion.virtual.sensor.VirtualSensorConfig[i];
        }
    };
    private static final int DIRECT_CHANNEL_SHIFT = 10;
    private static final int DIRECT_REPORT_MASK = 896;
    private static final int DIRECT_REPORT_SHIFT = 7;
    private static final java.lang.String TAG = "VirtualSensorConfig";
    private final int mFlags;
    private final int mMaxDelay;
    private final float mMaximumRange;
    private final int mMinDelay;
    private final java.lang.String mName;
    private final float mPower;
    private final float mResolution;
    private final int mType;
    private final java.lang.String mVendor;

    private VirtualSensorConfig(int i, java.lang.String str, java.lang.String str2, float f, float f2, float f3, int i2, int i3, int i4) {
        this.mType = i;
        this.mName = str;
        this.mVendor = str2;
        this.mMaximumRange = f;
        this.mResolution = f2;
        this.mPower = f3;
        this.mMinDelay = i2;
        this.mMaxDelay = i3;
        this.mFlags = i4;
    }

    private VirtualSensorConfig(android.os.Parcel parcel) {
        this.mType = parcel.readInt();
        this.mName = parcel.readString8();
        this.mVendor = parcel.readString8();
        this.mMaximumRange = parcel.readFloat();
        this.mResolution = parcel.readFloat();
        this.mPower = parcel.readFloat();
        this.mMinDelay = parcel.readInt();
        this.mMaxDelay = parcel.readInt();
        this.mFlags = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeString8(this.mName);
        parcel.writeString8(this.mVendor);
        parcel.writeFloat(this.mMaximumRange);
        parcel.writeFloat(this.mResolution);
        parcel.writeFloat(this.mPower);
        parcel.writeInt(this.mMinDelay);
        parcel.writeInt(this.mMaxDelay);
        parcel.writeInt(this.mFlags);
    }

    public java.lang.String toString() {
        return "VirtualSensorConfig{mType=" + this.mType + ", mName='" + this.mName + android.text.format.DateFormat.QUOTE + '}';
    }

    public int getType() {
        return this.mType;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public java.lang.String getVendor() {
        return this.mVendor;
    }

    public float getMaximumRange() {
        return this.mMaximumRange;
    }

    public float getResolution() {
        return this.mResolution;
    }

    public float getPower() {
        return this.mPower;
    }

    public int getMinDelay() {
        return this.mMinDelay;
    }

    public int getMaxDelay() {
        return this.mMaxDelay;
    }

    public int getHighestDirectReportRateLevel() {
        int i = (this.mFlags & 896) >> 7;
        if (i <= 3) {
            return i;
        }
        return 3;
    }

    public int getDirectChannelTypesSupported() {
        int i;
        if ((this.mFlags & 1024) <= 0) {
            i = 0;
        } else {
            i = 1;
        }
        if ((this.mFlags & 2048) > 0) {
            return i | 2;
        }
        return i;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public static final class Builder {
        private static final int FLAG_MEMORY_FILE_DIRECT_CHANNEL_SUPPORTED = 1024;
        private int mFlags;
        int mHighestDirectReportRateLevel;
        private int mMaxDelay;
        private float mMaximumRange;
        private int mMinDelay;
        private final java.lang.String mName;
        private float mPower;
        private float mResolution;
        private final int mType;
        private java.lang.String mVendor;

        public Builder(int i, java.lang.String str) {
            if (i <= 0) {
                throw new java.lang.IllegalArgumentException("Virtual sensor type must be positive");
            }
            this.mType = i;
            this.mName = (java.lang.String) java.util.Objects.requireNonNull(str);
        }

        public android.companion.virtual.sensor.VirtualSensorConfig build() {
            if (this.mHighestDirectReportRateLevel > 0) {
                if ((this.mFlags & 1024) == 0) {
                    throw new java.lang.IllegalArgumentException("Setting direct channel type is required for sensors with direct channel support.");
                }
                this.mFlags |= this.mHighestDirectReportRateLevel << 7;
            }
            if ((this.mFlags & 1024) > 0 && this.mHighestDirectReportRateLevel == 0) {
                throw new java.lang.IllegalArgumentException("Highest direct report rate level is required for sensors with direct channel support.");
            }
            return new android.companion.virtual.sensor.VirtualSensorConfig(this.mType, this.mName, this.mVendor, this.mMaximumRange, this.mResolution, this.mPower, this.mMinDelay, this.mMaxDelay, this.mFlags);
        }

        public android.companion.virtual.sensor.VirtualSensorConfig.Builder setVendor(java.lang.String str) {
            this.mVendor = str;
            return this;
        }

        public android.companion.virtual.sensor.VirtualSensorConfig.Builder setMaximumRange(float f) {
            this.mMaximumRange = f;
            return this;
        }

        public android.companion.virtual.sensor.VirtualSensorConfig.Builder setResolution(float f) {
            this.mResolution = f;
            return this;
        }

        public android.companion.virtual.sensor.VirtualSensorConfig.Builder setPower(float f) {
            this.mPower = f;
            return this;
        }

        public android.companion.virtual.sensor.VirtualSensorConfig.Builder setMinDelay(int i) {
            this.mMinDelay = i;
            return this;
        }

        public android.companion.virtual.sensor.VirtualSensorConfig.Builder setMaxDelay(int i) {
            this.mMaxDelay = i;
            return this;
        }

        public android.companion.virtual.sensor.VirtualSensorConfig.Builder setHighestDirectReportRateLevel(int i) {
            this.mHighestDirectReportRateLevel = i;
            return this;
        }

        public android.companion.virtual.sensor.VirtualSensorConfig.Builder setDirectChannelTypesSupported(int i) {
            if ((i & 1) > 0) {
                this.mFlags |= 1024;
            } else {
                this.mFlags &= -1025;
            }
            if ((i & (-2)) > 0) {
                throw new java.lang.IllegalArgumentException("Only TYPE_MEMORY_FILE direct channels can be supported for virtual sensors.");
            }
            return this;
        }
    }
}
