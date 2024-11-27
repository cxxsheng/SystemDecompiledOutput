package android.hardware.display;

/* loaded from: classes2.dex */
public final class BrightnessInfo implements android.os.Parcelable {
    public static final int BRIGHTNESS_MAX_REASON_NONE = 0;
    public static final int BRIGHTNESS_MAX_REASON_POWER_IC = 2;
    public static final int BRIGHTNESS_MAX_REASON_THERMAL = 1;
    public static final int BRIGHTNESS_MAX_REASON_WEAR_BEDTIME_MODE = 3;
    public static final android.os.Parcelable.Creator<android.hardware.display.BrightnessInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.display.BrightnessInfo>() { // from class: android.hardware.display.BrightnessInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.display.BrightnessInfo createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.display.BrightnessInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.display.BrightnessInfo[] newArray(int i) {
            return new android.hardware.display.BrightnessInfo[i];
        }
    };
    public static final int HIGH_BRIGHTNESS_MODE_HDR = 2;
    public static final int HIGH_BRIGHTNESS_MODE_OFF = 0;
    public static final int HIGH_BRIGHTNESS_MODE_SUNLIGHT = 1;
    public final float adjustedBrightness;
    public final float brightness;
    public final int brightnessMaxReason;
    public final float brightnessMaximum;
    public final float brightnessMinimum;
    public final int highBrightnessMode;
    public final float highBrightnessTransitionPoint;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BrightnessMaxReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface HighBrightnessMode {
    }

    public BrightnessInfo(float f, float f2, float f3, int i, float f4, int i2) {
        this(f, f, f2, f3, i, f4, i2);
    }

    public BrightnessInfo(float f, float f2, float f3, float f4, int i, float f5, int i2) {
        this.brightness = f;
        this.adjustedBrightness = f2;
        this.brightnessMinimum = f3;
        this.brightnessMaximum = f4;
        this.highBrightnessMode = i;
        this.highBrightnessTransitionPoint = f5;
        this.brightnessMaxReason = i2;
    }

    public static java.lang.String hbmToString(int i) {
        switch (i) {
            case 0:
                return "off";
            case 1:
                return "sunlight";
            case 2:
                return android.hardware.Camera.Parameters.SCENE_MODE_HDR;
            default:
                return "invalid";
        }
    }

    public static java.lang.String briMaxReasonToString(int i) {
        switch (i) {
            case 0:
                return "none";
            case 1:
                return android.os.PowerManager.SHUTDOWN_THERMAL_STATE;
            case 2:
                return "power IC";
            default:
                return "invalid";
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeFloat(this.brightness);
        parcel.writeFloat(this.adjustedBrightness);
        parcel.writeFloat(this.brightnessMinimum);
        parcel.writeFloat(this.brightnessMaximum);
        parcel.writeInt(this.highBrightnessMode);
        parcel.writeFloat(this.highBrightnessTransitionPoint);
        parcel.writeInt(this.brightnessMaxReason);
    }

    private BrightnessInfo(android.os.Parcel parcel) {
        this.brightness = parcel.readFloat();
        this.adjustedBrightness = parcel.readFloat();
        this.brightnessMinimum = parcel.readFloat();
        this.brightnessMaximum = parcel.readFloat();
        this.highBrightnessMode = parcel.readInt();
        this.highBrightnessTransitionPoint = parcel.readFloat();
        this.brightnessMaxReason = parcel.readInt();
    }
}
