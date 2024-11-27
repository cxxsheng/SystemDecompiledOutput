package android.hardware.display;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class BrightnessChangeEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.display.BrightnessChangeEvent> CREATOR = new android.os.Parcelable.Creator<android.hardware.display.BrightnessChangeEvent>() { // from class: android.hardware.display.BrightnessChangeEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.display.BrightnessChangeEvent createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.display.BrightnessChangeEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.display.BrightnessChangeEvent[] newArray(int i) {
            return new android.hardware.display.BrightnessChangeEvent[i];
        }
    };
    public final float batteryLevel;
    public final float brightness;
    public final long colorSampleDuration;
    public final int colorTemperature;
    public final long[] colorValueBuckets;
    public final boolean isDefaultBrightnessConfig;
    public final boolean isUserSetBrightness;
    public final float lastBrightness;
    public final long[] luxTimestamps;
    public final float[] luxValues;
    public final boolean nightMode;
    public final java.lang.String packageName;
    public final float powerBrightnessFactor;
    public final boolean reduceBrightColors;
    public final float reduceBrightColorsOffset;
    public final int reduceBrightColorsStrength;
    public final long timeStamp;
    public final java.lang.String uniqueDisplayId;
    public final int userId;

    private BrightnessChangeEvent(float f, long j, java.lang.String str, int i, java.lang.String str2, float[] fArr, long[] jArr, float f2, float f3, boolean z, int i2, boolean z2, int i3, float f4, float f5, boolean z3, boolean z4, long[] jArr2, long j2) {
        this.brightness = f;
        this.timeStamp = j;
        this.packageName = str;
        this.userId = i;
        this.uniqueDisplayId = str2;
        this.luxValues = fArr;
        this.luxTimestamps = jArr;
        this.batteryLevel = f2;
        this.powerBrightnessFactor = f3;
        this.nightMode = z;
        this.colorTemperature = i2;
        this.reduceBrightColors = z2;
        this.reduceBrightColorsStrength = i3;
        this.reduceBrightColorsOffset = f4;
        this.lastBrightness = f5;
        this.isDefaultBrightnessConfig = z3;
        this.isUserSetBrightness = z4;
        this.colorValueBuckets = jArr2;
        this.colorSampleDuration = j2;
    }

    public BrightnessChangeEvent(android.hardware.display.BrightnessChangeEvent brightnessChangeEvent, boolean z) {
        this.brightness = brightnessChangeEvent.brightness;
        this.timeStamp = brightnessChangeEvent.timeStamp;
        this.packageName = z ? null : brightnessChangeEvent.packageName;
        this.userId = brightnessChangeEvent.userId;
        this.uniqueDisplayId = brightnessChangeEvent.uniqueDisplayId;
        this.luxValues = brightnessChangeEvent.luxValues;
        this.luxTimestamps = brightnessChangeEvent.luxTimestamps;
        this.batteryLevel = brightnessChangeEvent.batteryLevel;
        this.powerBrightnessFactor = brightnessChangeEvent.powerBrightnessFactor;
        this.nightMode = brightnessChangeEvent.nightMode;
        this.colorTemperature = brightnessChangeEvent.colorTemperature;
        this.reduceBrightColors = brightnessChangeEvent.reduceBrightColors;
        this.reduceBrightColorsStrength = brightnessChangeEvent.reduceBrightColorsStrength;
        this.reduceBrightColorsOffset = brightnessChangeEvent.reduceBrightColorsOffset;
        this.lastBrightness = brightnessChangeEvent.lastBrightness;
        this.isDefaultBrightnessConfig = brightnessChangeEvent.isDefaultBrightnessConfig;
        this.isUserSetBrightness = brightnessChangeEvent.isUserSetBrightness;
        this.colorValueBuckets = brightnessChangeEvent.colorValueBuckets;
        this.colorSampleDuration = brightnessChangeEvent.colorSampleDuration;
    }

    private BrightnessChangeEvent(android.os.Parcel parcel) {
        this.brightness = parcel.readFloat();
        this.timeStamp = parcel.readLong();
        this.packageName = parcel.readString();
        this.userId = parcel.readInt();
        this.uniqueDisplayId = parcel.readString();
        this.luxValues = parcel.createFloatArray();
        this.luxTimestamps = parcel.createLongArray();
        this.batteryLevel = parcel.readFloat();
        this.powerBrightnessFactor = parcel.readFloat();
        this.nightMode = parcel.readBoolean();
        this.colorTemperature = parcel.readInt();
        this.reduceBrightColors = parcel.readBoolean();
        this.reduceBrightColorsStrength = parcel.readInt();
        this.reduceBrightColorsOffset = parcel.readFloat();
        this.lastBrightness = parcel.readFloat();
        this.isDefaultBrightnessConfig = parcel.readBoolean();
        this.isUserSetBrightness = parcel.readBoolean();
        this.colorValueBuckets = parcel.createLongArray();
        this.colorSampleDuration = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeFloat(this.brightness);
        parcel.writeLong(this.timeStamp);
        parcel.writeString(this.packageName);
        parcel.writeInt(this.userId);
        parcel.writeString(this.uniqueDisplayId);
        parcel.writeFloatArray(this.luxValues);
        parcel.writeLongArray(this.luxTimestamps);
        parcel.writeFloat(this.batteryLevel);
        parcel.writeFloat(this.powerBrightnessFactor);
        parcel.writeBoolean(this.nightMode);
        parcel.writeInt(this.colorTemperature);
        parcel.writeBoolean(this.reduceBrightColors);
        parcel.writeInt(this.reduceBrightColorsStrength);
        parcel.writeFloat(this.reduceBrightColorsOffset);
        parcel.writeFloat(this.lastBrightness);
        parcel.writeBoolean(this.isDefaultBrightnessConfig);
        parcel.writeBoolean(this.isUserSetBrightness);
        parcel.writeLongArray(this.colorValueBuckets);
        parcel.writeLong(this.colorSampleDuration);
    }

    public java.lang.String toString() {
        return "BrightnessChangeEvent{brightness: " + this.brightness + ", timeStamp: " + this.timeStamp + ", packageName: " + this.packageName + ", userId: " + this.userId + ", uniqueDisplayId: " + this.uniqueDisplayId + ", luxValues: " + java.util.Arrays.toString(this.luxValues) + ", luxTimestamps: " + java.util.Arrays.toString(this.luxTimestamps) + ", batteryLevel: " + this.batteryLevel + ", powerBrightnessFactor: " + this.powerBrightnessFactor + ", nightMode: " + this.nightMode + ", colorTemperature: " + this.colorTemperature + ", reduceBrightColors: " + this.reduceBrightColors + ", reduceBrightColorsStrength: " + this.reduceBrightColorsStrength + ", reduceBrightColorsOffset: " + this.reduceBrightColorsOffset + ", lastBrightness: " + this.lastBrightness + ", isDefaultBrightnessConfig: " + this.isDefaultBrightnessConfig + ", isUserSetBrightness: " + this.isUserSetBrightness + ", colorValueBuckets: " + java.util.Arrays.toString(this.colorValueBuckets) + ", colorSampleDuration: " + this.colorSampleDuration + "}";
    }

    public static class Builder {
        private float mBatteryLevel;
        private float mBrightness;
        private long mColorSampleDuration;
        private int mColorTemperature;
        private long[] mColorValueBuckets;
        private boolean mIsDefaultBrightnessConfig;
        private boolean mIsUserSetBrightness;
        private float mLastBrightness;
        private long[] mLuxTimestamps;
        private float[] mLuxValues;
        private boolean mNightMode;
        private java.lang.String mPackageName;
        private float mPowerBrightnessFactor;
        private boolean mReduceBrightColors;
        private float mReduceBrightColorsOffset;
        private int mReduceBrightColorsStrength;
        private long mTimeStamp;
        private java.lang.String mUniqueDisplayId;
        private int mUserId;

        public android.hardware.display.BrightnessChangeEvent.Builder setBrightness(float f) {
            this.mBrightness = f;
            return this;
        }

        public android.hardware.display.BrightnessChangeEvent.Builder setTimeStamp(long j) {
            this.mTimeStamp = j;
            return this;
        }

        public android.hardware.display.BrightnessChangeEvent.Builder setPackageName(java.lang.String str) {
            this.mPackageName = str;
            return this;
        }

        public android.hardware.display.BrightnessChangeEvent.Builder setUserId(int i) {
            this.mUserId = i;
            return this;
        }

        public android.hardware.display.BrightnessChangeEvent.Builder setUniqueDisplayId(java.lang.String str) {
            this.mUniqueDisplayId = str;
            return this;
        }

        public android.hardware.display.BrightnessChangeEvent.Builder setLuxValues(float[] fArr) {
            this.mLuxValues = fArr;
            return this;
        }

        public android.hardware.display.BrightnessChangeEvent.Builder setLuxTimestamps(long[] jArr) {
            this.mLuxTimestamps = jArr;
            return this;
        }

        public android.hardware.display.BrightnessChangeEvent.Builder setBatteryLevel(float f) {
            this.mBatteryLevel = f;
            return this;
        }

        public android.hardware.display.BrightnessChangeEvent.Builder setPowerBrightnessFactor(float f) {
            this.mPowerBrightnessFactor = f;
            return this;
        }

        public android.hardware.display.BrightnessChangeEvent.Builder setNightMode(boolean z) {
            this.mNightMode = z;
            return this;
        }

        public android.hardware.display.BrightnessChangeEvent.Builder setColorTemperature(int i) {
            this.mColorTemperature = i;
            return this;
        }

        public android.hardware.display.BrightnessChangeEvent.Builder setReduceBrightColors(boolean z) {
            this.mReduceBrightColors = z;
            return this;
        }

        public android.hardware.display.BrightnessChangeEvent.Builder setReduceBrightColorsStrength(int i) {
            this.mReduceBrightColorsStrength = i;
            return this;
        }

        public android.hardware.display.BrightnessChangeEvent.Builder setReduceBrightColorsOffset(float f) {
            this.mReduceBrightColorsOffset = f;
            return this;
        }

        public android.hardware.display.BrightnessChangeEvent.Builder setLastBrightness(float f) {
            this.mLastBrightness = f;
            return this;
        }

        public android.hardware.display.BrightnessChangeEvent.Builder setIsDefaultBrightnessConfig(boolean z) {
            this.mIsDefaultBrightnessConfig = z;
            return this;
        }

        public android.hardware.display.BrightnessChangeEvent.Builder setUserBrightnessPoint(boolean z) {
            this.mIsUserSetBrightness = z;
            return this;
        }

        public android.hardware.display.BrightnessChangeEvent.Builder setColorValues(long[] jArr, long j) {
            java.util.Objects.requireNonNull(jArr);
            this.mColorValueBuckets = jArr;
            this.mColorSampleDuration = j;
            return this;
        }

        public android.hardware.display.BrightnessChangeEvent build() {
            return new android.hardware.display.BrightnessChangeEvent(this.mBrightness, this.mTimeStamp, this.mPackageName, this.mUserId, this.mUniqueDisplayId, this.mLuxValues, this.mLuxTimestamps, this.mBatteryLevel, this.mPowerBrightnessFactor, this.mNightMode, this.mColorTemperature, this.mReduceBrightColors, this.mReduceBrightColorsStrength, this.mReduceBrightColorsOffset, this.mLastBrightness, this.mIsDefaultBrightnessConfig, this.mIsUserSetBrightness, this.mColorValueBuckets, this.mColorSampleDuration);
        }
    }
}
