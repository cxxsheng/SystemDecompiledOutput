package android.os;

/* loaded from: classes3.dex */
public final class VibrationAttributes implements android.os.Parcelable {
    public static final int CATEGORY_KEYBOARD = 1;
    public static final int CATEGORY_UNKNOWN = 0;
    public static final android.os.Parcelable.Creator<android.os.VibrationAttributes> CREATOR = new android.os.Parcelable.Creator<android.os.VibrationAttributes>() { // from class: android.os.VibrationAttributes.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.VibrationAttributes createFromParcel(android.os.Parcel parcel) {
            return new android.os.VibrationAttributes(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.VibrationAttributes[] newArray(int i) {
            return new android.os.VibrationAttributes[i];
        }
    };
    public static final int FLAG_ALL_SUPPORTED = 31;
    public static final int FLAG_BYPASS_INTERRUPTION_POLICY = 1;
    public static final int FLAG_BYPASS_USER_VIBRATION_INTENSITY_OFF = 2;
    public static final int FLAG_BYPASS_USER_VIBRATION_INTENSITY_SCALE = 16;
    public static final int FLAG_INVALIDATE_SETTINGS_CACHE = 4;
    public static final int FLAG_PIPELINED_EFFECT = 8;
    private static final java.lang.String TAG = "VibrationAttributes";
    public static final int USAGE_ACCESSIBILITY = 66;
    public static final int USAGE_ALARM = 17;
    public static final int USAGE_CLASS_ALARM = 1;
    public static final int USAGE_CLASS_FEEDBACK = 2;
    public static final int USAGE_CLASS_MASK = 15;
    public static final int USAGE_CLASS_MEDIA = 3;
    public static final int USAGE_CLASS_UNKNOWN = 0;
    public static final int USAGE_COMMUNICATION_REQUEST = 65;
    public static final int USAGE_FILTER_MATCH_ALL = -1;
    public static final int USAGE_HARDWARE_FEEDBACK = 50;
    public static final int USAGE_MEDIA = 19;
    public static final int USAGE_NOTIFICATION = 49;
    public static final int USAGE_PHYSICAL_EMULATION = 34;
    public static final int USAGE_RINGTONE = 33;
    public static final int USAGE_TOUCH = 18;
    public static final int USAGE_UNKNOWN = 0;
    private final int mCategory;
    private final int mFlags;
    private final int mOriginalAudioUsage;
    private final int mUsage;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Category {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Flag {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Usage {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UsageClass {
    }

    public static android.os.VibrationAttributes createForUsage(int i) {
        return new android.os.VibrationAttributes.Builder().setUsage(i).build();
    }

    private VibrationAttributes(int i, int i2, int i3, int i4) {
        this.mUsage = i;
        this.mOriginalAudioUsage = i2;
        this.mFlags = i3 & 31;
        this.mCategory = i4;
    }

    public int getUsageClass() {
        return this.mUsage & 15;
    }

    public int getUsage() {
        return this.mUsage;
    }

    public int getOriginalAudioUsage() {
        return this.mOriginalAudioUsage;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int getCategory() {
        return this.mCategory;
    }

    public boolean isFlagSet(int i) {
        return (i & this.mFlags) > 0;
    }

    public int getAudioUsage() {
        if (this.mOriginalAudioUsage != 0) {
            return this.mOriginalAudioUsage;
        }
        switch (this.mUsage) {
            case 17:
                return 4;
            case 18:
                return 13;
            case 19:
                return 1;
            case 33:
                return 6;
            case 49:
                return 5;
            case 65:
                return 2;
            case 66:
                return 11;
            default:
                return 0;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mUsage);
        parcel.writeInt(this.mOriginalAudioUsage);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mCategory);
    }

    private VibrationAttributes(android.os.Parcel parcel) {
        this.mUsage = parcel.readInt();
        this.mOriginalAudioUsage = parcel.readInt();
        this.mFlags = parcel.readInt();
        this.mCategory = parcel.readInt();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.os.VibrationAttributes vibrationAttributes = (android.os.VibrationAttributes) obj;
        if (this.mUsage == vibrationAttributes.mUsage && this.mOriginalAudioUsage == vibrationAttributes.mOriginalAudioUsage && this.mFlags == vibrationAttributes.mFlags && this.mCategory == vibrationAttributes.mCategory) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mUsage), java.lang.Integer.valueOf(this.mOriginalAudioUsage), java.lang.Integer.valueOf(this.mFlags), java.lang.Integer.valueOf(this.mCategory));
    }

    public java.lang.String toString() {
        return "VibrationAttributes{mUsage=" + usageToString() + ", mAudioUsage= " + android.media.AudioAttributes.usageToString(this.mOriginalAudioUsage) + ", mCategory=" + categoryToString() + ", mFlags=" + this.mFlags + '}';
    }

    public java.lang.String usageToString() {
        return usageToString(this.mUsage);
    }

    public static java.lang.String usageToString(int i) {
        switch (i) {
            case 0:
                return "UNKNOWN";
            case 17:
                return "ALARM";
            case 18:
                return "TOUCH";
            case 19:
                return "MEDIA";
            case 33:
                return "RINGTONE";
            case 34:
                return "PHYSICAL_EMULATION";
            case 49:
                return android.app.admin.DevicePolicyResources.Drawables.Source.NOTIFICATION;
            case 50:
                return "HARDWARE_FEEDBACK";
            case 65:
                return "COMMUNICATION_REQUEST";
            case 66:
                return "ACCESSIBILITY";
            default:
                return "unknown usage " + i;
        }
    }

    public java.lang.String categoryToString() {
        return categoryToString(this.mCategory);
    }

    public static java.lang.String categoryToString(int i) {
        switch (i) {
            case 0:
                return "UNKNOWN";
            case 1:
                return "KEYBOARD";
            default:
                return "unknown category " + i;
        }
    }

    public static final class Builder {
        private int mCategory;
        private int mFlags;
        private int mOriginalAudioUsage;
        private int mUsage;

        public Builder() {
            this.mUsage = 0;
            this.mOriginalAudioUsage = 0;
            this.mFlags = 0;
            this.mCategory = 0;
        }

        public Builder(android.os.VibrationAttributes vibrationAttributes) {
            this.mUsage = 0;
            this.mOriginalAudioUsage = 0;
            this.mFlags = 0;
            this.mCategory = 0;
            if (vibrationAttributes != null) {
                this.mUsage = vibrationAttributes.mUsage;
                this.mOriginalAudioUsage = vibrationAttributes.mOriginalAudioUsage;
                this.mFlags = vibrationAttributes.mFlags;
                this.mCategory = vibrationAttributes.mCategory;
            }
        }

        public Builder(android.media.AudioAttributes audioAttributes) {
            this.mUsage = 0;
            this.mOriginalAudioUsage = 0;
            this.mFlags = 0;
            this.mCategory = 0;
            setUsage(audioAttributes);
            setFlags(audioAttributes);
        }

        private void setUsage(android.media.AudioAttributes audioAttributes) {
            this.mOriginalAudioUsage = audioAttributes.getUsage();
            switch (audioAttributes.getUsage()) {
                case 1:
                case 14:
                    this.mUsage = 19;
                    break;
                case 2:
                case 3:
                case 12:
                case 16:
                    this.mUsage = 65;
                    break;
                case 4:
                    this.mUsage = 17;
                    break;
                case 5:
                case 7:
                case 8:
                case 9:
                case 10:
                    this.mUsage = 49;
                    break;
                case 6:
                    this.mUsage = 33;
                    break;
                case 11:
                    this.mUsage = 66;
                    break;
                case 13:
                    this.mUsage = 18;
                    break;
                case 15:
                default:
                    this.mUsage = 0;
                    break;
            }
        }

        private void setFlags(android.media.AudioAttributes audioAttributes) {
            if ((audioAttributes.getAllFlags() & 64) != 0) {
                this.mFlags |= 1;
            }
            if ((audioAttributes.getAllFlags() & 128) != 0) {
                this.mFlags |= 2;
            }
        }

        public android.os.VibrationAttributes build() {
            return new android.os.VibrationAttributes(this.mUsage, this.mOriginalAudioUsage, this.mFlags, this.mCategory);
        }

        public android.os.VibrationAttributes.Builder setUsage(int i) {
            this.mOriginalAudioUsage = 0;
            this.mUsage = i;
            return this;
        }

        public android.os.VibrationAttributes.Builder setCategory(int i) {
            this.mCategory = i;
            return this;
        }

        public android.os.VibrationAttributes.Builder setFlags(int i, int i2) {
            int i3 = i2 & 31;
            this.mFlags = (i & i3) | (this.mFlags & (~i3));
            return this;
        }

        public android.os.VibrationAttributes.Builder setFlags(int i) {
            return setFlags(i, 31);
        }
    }
}
