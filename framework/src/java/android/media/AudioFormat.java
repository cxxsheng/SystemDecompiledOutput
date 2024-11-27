package android.media;

/* loaded from: classes2.dex */
public final class AudioFormat implements android.os.Parcelable {
    public static final int AUDIO_FORMAT_HAS_PROPERTY_CHANNEL_INDEX_MASK = 8;
    public static final int AUDIO_FORMAT_HAS_PROPERTY_CHANNEL_MASK = 4;
    public static final int AUDIO_FORMAT_HAS_PROPERTY_ENCODING = 1;
    public static final int AUDIO_FORMAT_HAS_PROPERTY_NONE = 0;
    public static final int AUDIO_FORMAT_HAS_PROPERTY_SAMPLE_RATE = 2;

    @java.lang.Deprecated
    public static final int CHANNEL_CONFIGURATION_DEFAULT = 1;

    @java.lang.Deprecated
    public static final int CHANNEL_CONFIGURATION_INVALID = 0;

    @java.lang.Deprecated
    public static final int CHANNEL_CONFIGURATION_MONO = 2;

    @java.lang.Deprecated
    public static final int CHANNEL_CONFIGURATION_STEREO = 3;
    public static final int CHANNEL_INVALID = 0;
    public static final int CHANNEL_IN_2POINT0POINT2 = 6291468;
    public static final int CHANNEL_IN_2POINT1POINT2 = 7340044;
    public static final int CHANNEL_IN_3POINT0POINT2 = 6553612;
    public static final int CHANNEL_IN_3POINT1POINT2 = 7602188;
    public static final int CHANNEL_IN_5POINT1 = 1507340;
    public static final int CHANNEL_IN_BACK = 32;
    public static final int CHANNEL_IN_BACK_LEFT = 65536;
    public static final int CHANNEL_IN_BACK_PROCESSED = 512;
    public static final int CHANNEL_IN_BACK_RIGHT = 131072;
    public static final int CHANNEL_IN_CENTER = 262144;
    public static final int CHANNEL_IN_DEFAULT = 1;
    public static final int CHANNEL_IN_FRONT = 16;
    public static final int CHANNEL_IN_FRONT_BACK = 48;
    public static final int CHANNEL_IN_FRONT_PROCESSED = 256;
    public static final int CHANNEL_IN_LEFT = 4;
    public static final int CHANNEL_IN_LEFT_PROCESSED = 64;
    public static final int CHANNEL_IN_LOW_FREQUENCY = 1048576;
    public static final int CHANNEL_IN_MONO = 16;
    public static final int CHANNEL_IN_PRESSURE = 1024;
    public static final int CHANNEL_IN_RIGHT = 8;
    public static final int CHANNEL_IN_RIGHT_PROCESSED = 128;
    public static final int CHANNEL_IN_STEREO = 12;
    public static final int CHANNEL_IN_TOP_LEFT = 2097152;
    public static final int CHANNEL_IN_TOP_RIGHT = 4194304;
    public static final int CHANNEL_IN_VOICE_DNLINK = 32768;
    public static final int CHANNEL_IN_VOICE_UPLINK = 16384;
    public static final int CHANNEL_IN_X_AXIS = 2048;
    public static final int CHANNEL_IN_Y_AXIS = 4096;
    public static final int CHANNEL_IN_Z_AXIS = 8192;
    public static final int CHANNEL_OUT_13POINT_360RA = 30136348;
    public static final int CHANNEL_OUT_22POINT2 = 67108860;
    public static final int CHANNEL_OUT_5POINT1 = 252;
    public static final int CHANNEL_OUT_5POINT1POINT2 = 3145980;
    public static final int CHANNEL_OUT_5POINT1POINT4 = 737532;
    public static final int CHANNEL_OUT_5POINT1_SIDE = 6204;
    public static final int CHANNEL_OUT_6POINT1 = 1276;

    @java.lang.Deprecated
    public static final int CHANNEL_OUT_7POINT1 = 1020;
    public static final int CHANNEL_OUT_7POINT1POINT2 = 3152124;
    public static final int CHANNEL_OUT_7POINT1POINT4 = 743676;
    public static final int CHANNEL_OUT_7POINT1_SURROUND = 6396;
    public static final int CHANNEL_OUT_9POINT1POINT4 = 202070268;
    public static final int CHANNEL_OUT_9POINT1POINT6 = 205215996;
    public static final int CHANNEL_OUT_BACK_CENTER = 1024;
    public static final int CHANNEL_OUT_BACK_LEFT = 64;
    public static final int CHANNEL_OUT_BACK_RIGHT = 128;
    public static final int CHANNEL_OUT_BOTTOM_FRONT_CENTER = 8388608;
    public static final int CHANNEL_OUT_BOTTOM_FRONT_LEFT = 4194304;
    public static final int CHANNEL_OUT_BOTTOM_FRONT_RIGHT = 16777216;
    public static final int CHANNEL_OUT_DEFAULT = 1;
    public static final int CHANNEL_OUT_FRONT_CENTER = 16;
    public static final int CHANNEL_OUT_FRONT_LEFT = 4;
    public static final int CHANNEL_OUT_FRONT_LEFT_OF_CENTER = 256;
    public static final int CHANNEL_OUT_FRONT_RIGHT = 8;
    public static final int CHANNEL_OUT_FRONT_RIGHT_OF_CENTER = 512;
    public static final int CHANNEL_OUT_FRONT_WIDE_LEFT = 67108864;
    public static final int CHANNEL_OUT_FRONT_WIDE_RIGHT = 134217728;
    public static final int CHANNEL_OUT_HAPTIC_A = 536870912;
    public static final int CHANNEL_OUT_HAPTIC_B = 268435456;
    public static final int CHANNEL_OUT_LOW_FREQUENCY = 32;
    public static final int CHANNEL_OUT_LOW_FREQUENCY_2 = 33554432;
    public static final int CHANNEL_OUT_MONO = 4;
    public static final int CHANNEL_OUT_QUAD = 204;
    public static final int CHANNEL_OUT_QUAD_SIDE = 6156;
    public static final int CHANNEL_OUT_SIDE_LEFT = 2048;
    public static final int CHANNEL_OUT_SIDE_RIGHT = 4096;
    public static final int CHANNEL_OUT_STEREO = 12;
    public static final int CHANNEL_OUT_SURROUND = 1052;
    public static final int CHANNEL_OUT_TOP_BACK_CENTER = 262144;
    public static final int CHANNEL_OUT_TOP_BACK_LEFT = 131072;
    public static final int CHANNEL_OUT_TOP_BACK_RIGHT = 524288;
    public static final int CHANNEL_OUT_TOP_CENTER = 8192;
    public static final int CHANNEL_OUT_TOP_FRONT_CENTER = 32768;
    public static final int CHANNEL_OUT_TOP_FRONT_LEFT = 16384;
    public static final int CHANNEL_OUT_TOP_FRONT_RIGHT = 65536;
    public static final int CHANNEL_OUT_TOP_SIDE_LEFT = 1048576;
    public static final int CHANNEL_OUT_TOP_SIDE_RIGHT = 2097152;
    public static final int ENCODING_AAC_ELD = 15;
    public static final int ENCODING_AAC_HE_V1 = 11;
    public static final int ENCODING_AAC_HE_V2 = 12;
    public static final int ENCODING_AAC_LC = 10;
    public static final int ENCODING_AAC_XHE = 16;
    public static final int ENCODING_AC3 = 5;
    public static final int ENCODING_AC4 = 17;
    public static final int ENCODING_DEFAULT = 1;
    public static final int ENCODING_DOLBY_MAT = 19;
    public static final int ENCODING_DOLBY_TRUEHD = 14;
    public static final int ENCODING_DRA = 28;
    public static final int ENCODING_DSD = 31;
    public static final int ENCODING_DTS = 7;
    public static final int ENCODING_DTS_HD = 8;
    public static final int ENCODING_DTS_HD_MA = 29;

    @java.lang.Deprecated
    public static final int ENCODING_DTS_UHD = 27;
    public static final int ENCODING_DTS_UHD_P1 = 27;
    public static final int ENCODING_DTS_UHD_P2 = 30;
    public static final int ENCODING_E_AC3 = 6;
    public static final int ENCODING_E_AC3_JOC = 18;
    public static final int ENCODING_IEC61937 = 13;
    public static final int ENCODING_INVALID = 0;
    public static final int ENCODING_LEGACY_SHORT_ARRAY_THRESHOLD = 20;
    public static final int ENCODING_MP3 = 9;
    public static final int ENCODING_MPEGH_BL_L3 = 23;
    public static final int ENCODING_MPEGH_BL_L4 = 24;
    public static final int ENCODING_MPEGH_LC_L3 = 25;
    public static final int ENCODING_MPEGH_LC_L4 = 26;
    public static final int ENCODING_OPUS = 20;
    public static final int ENCODING_PCM_16BIT = 2;
    public static final int ENCODING_PCM_24BIT_PACKED = 21;
    public static final int ENCODING_PCM_32BIT = 22;
    public static final int ENCODING_PCM_8BIT = 3;
    public static final int ENCODING_PCM_FLOAT = 4;
    public static final int SAMPLE_RATE_UNSPECIFIED = 0;
    private final int mChannelCount;
    private final int mChannelIndexMask;
    private final int mChannelMask;
    private final int mEncoding;
    private final int mFrameSizeInBytes;
    private final int mPropertySetMask;
    private final int mSampleRate;
    public static final int SAMPLE_RATE_HZ_MIN = android.media.AudioSystem.SAMPLE_RATE_HZ_MIN;
    public static final int SAMPLE_RATE_HZ_MAX = android.media.AudioSystem.SAMPLE_RATE_HZ_MAX;
    public static final android.os.Parcelable.Creator<android.media.AudioFormat> CREATOR = new android.os.Parcelable.Creator<android.media.AudioFormat>() { // from class: android.media.AudioFormat.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioFormat createFromParcel(android.os.Parcel parcel) {
            return new android.media.AudioFormat(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioFormat[] newArray(int i) {
            return new android.media.AudioFormat[i];
        }
    };
    public static final int[] SURROUND_SOUND_ENCODING = {5, 6, 7, 8, 10, 14, 17, 18, 19, 23, 24, 25, 26, 27, 28, 29, 30};

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ChannelOut {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Encoding {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EncodingCanBeInvalid {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SurroundSoundEncoding {
    }

    public static java.lang.String toLogFriendlyEncoding(int i) {
        switch (i) {
            case 0:
                return "ENCODING_INVALID";
            case 1:
            default:
                return "invalid encoding " + i;
            case 2:
                return "ENCODING_PCM_16BIT";
            case 3:
                return "ENCODING_PCM_8BIT";
            case 4:
                return "ENCODING_PCM_FLOAT";
            case 5:
                return "ENCODING_AC3";
            case 6:
                return "ENCODING_E_AC3";
            case 7:
                return "ENCODING_DTS";
            case 8:
                return "ENCODING_DTS_HD";
            case 9:
                return "ENCODING_MP3";
            case 10:
                return "ENCODING_AAC_LC";
            case 11:
                return "ENCODING_AAC_HE_V1";
            case 12:
                return "ENCODING_AAC_HE_V2";
            case 13:
                return "ENCODING_IEC61937";
            case 14:
                return "ENCODING_DOLBY_TRUEHD";
            case 15:
                return "ENCODING_AAC_ELD";
            case 16:
                return "ENCODING_AAC_XHE";
            case 17:
                return "ENCODING_AC4";
            case 18:
                return "ENCODING_E_AC3_JOC";
            case 19:
                return "ENCODING_DOLBY_MAT";
            case 20:
                return "ENCODING_OPUS";
            case 21:
                return "ENCODING_PCM_24BIT_PACKED";
            case 22:
                return "ENCODING_PCM_32BIT";
            case 23:
                return "ENCODING_MPEGH_BL_L3";
            case 24:
                return "ENCODING_MPEGH_BL_L4";
            case 25:
                return "ENCODING_MPEGH_LC_L3";
            case 26:
                return "ENCODING_MPEGH_LC_L4";
            case 27:
                return "ENCODING_DTS_UHD_P1";
            case 28:
                return "ENCODING_DRA";
            case 29:
                return "ENCODING_DTS_HD_MA";
            case 30:
                return "ENCODING_DTS_UHD_P2";
            case 31:
                return "ENCODING_DSD";
        }
    }

    public static int inChannelMaskFromOutChannelMask(int i) throws java.lang.IllegalArgumentException {
        if (i == 1) {
            throw new java.lang.IllegalArgumentException("Illegal CHANNEL_OUT_DEFAULT channel mask for input.");
        }
        switch (channelCountFromOutChannelMask(i)) {
            case 1:
                return 16;
            case 2:
                return 12;
            default:
                throw new java.lang.IllegalArgumentException("Unsupported channel configuration for input.");
        }
    }

    public static int channelCountFromInChannelMask(int i) {
        return java.lang.Integer.bitCount(i);
    }

    public static int channelCountFromOutChannelMask(int i) {
        return java.lang.Integer.bitCount(i);
    }

    public static int convertChannelOutMaskToNativeMask(int i) {
        return i >> 2;
    }

    public static int convertNativeChannelMaskToOutMask(int i) {
        return i << 2;
    }

    public static int getBytesPerSample(int i) {
        switch (i) {
            case 1:
            case 2:
            case 13:
                return 2;
            case 3:
                return 1;
            case 4:
            case 22:
                return 4;
            case 21:
                return 3;
            default:
                throw new java.lang.IllegalArgumentException("Bad audio format " + i);
        }
    }

    public static boolean isValidEncoding(int i) {
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
                return true;
            default:
                return false;
        }
    }

    public static boolean isPublicEncoding(int i) {
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
                return true;
            default:
                return false;
        }
    }

    public static boolean isEncodingLinearPcm(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 21:
            case 22:
                return true;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
                return false;
            default:
                throw new java.lang.IllegalArgumentException("Bad audio format " + i);
        }
    }

    public static boolean isEncodingLinearFrames(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 13:
            case 21:
            case 22:
                return true;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
                return false;
            default:
                throw new java.lang.IllegalArgumentException("Bad audio format " + i);
        }
    }

    public static int[] filterPublicFormats(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        int[] copyOf = java.util.Arrays.copyOf(iArr, iArr.length);
        int i = 0;
        for (int i2 = 0; i2 < copyOf.length; i2++) {
            if (isPublicEncoding(copyOf[i2])) {
                if (i != i2) {
                    copyOf[i] = copyOf[i2];
                }
                i++;
            }
        }
        return java.util.Arrays.copyOf(copyOf, i);
    }

    public AudioFormat() {
        throw new java.lang.UnsupportedOperationException("There is no valid usage of this constructor");
    }

    private AudioFormat(int i, int i2, int i3, int i4) {
        this(15, i, i2, i3, i4);
    }

    private AudioFormat(int i, int i2, int i3, int i4, int i5) {
        int i6;
        this.mPropertySetMask = i;
        int i7 = 0;
        this.mEncoding = (i & 1) == 0 ? 0 : i2;
        this.mSampleRate = (i & 2) == 0 ? 0 : i3;
        this.mChannelMask = (i & 4) == 0 ? 0 : i4;
        this.mChannelIndexMask = (i & 8) == 0 ? 0 : i5;
        int bitCount = java.lang.Integer.bitCount(getChannelIndexMask());
        int channelCountFromOutChannelMask = channelCountFromOutChannelMask(getChannelMask());
        if (channelCountFromOutChannelMask == 0) {
            i7 = bitCount;
        } else if (channelCountFromOutChannelMask == bitCount || bitCount == 0) {
            i7 = channelCountFromOutChannelMask;
        }
        this.mChannelCount = i7;
        try {
            i6 = getBytesPerSample(this.mEncoding) * i7;
        } catch (java.lang.IllegalArgumentException e) {
            i6 = 1;
        }
        this.mFrameSizeInBytes = i6 != 0 ? i6 : 1;
    }

    public int getEncoding() {
        return this.mEncoding;
    }

    public int getSampleRate() {
        return this.mSampleRate;
    }

    public int getChannelMask() {
        return this.mChannelMask;
    }

    public int getChannelIndexMask() {
        return this.mChannelIndexMask;
    }

    public int getChannelCount() {
        return this.mChannelCount;
    }

    public int getFrameSizeInBytes() {
        return this.mFrameSizeInBytes;
    }

    public int getPropertySetMask() {
        return this.mPropertySetMask;
    }

    public java.lang.String toLogFriendlyString() {
        return java.lang.String.format("%dch %dHz %s", java.lang.Integer.valueOf(this.mChannelCount), java.lang.Integer.valueOf(this.mSampleRate), toLogFriendlyEncoding(this.mEncoding));
    }

    public static class Builder {
        private int mChannelIndexMask;
        private int mChannelMask;
        private int mEncoding;
        private int mPropertySetMask;
        private int mSampleRate;

        public Builder() {
            this.mEncoding = 0;
            this.mSampleRate = 0;
            this.mChannelMask = 0;
            this.mChannelIndexMask = 0;
            this.mPropertySetMask = 0;
        }

        public Builder(android.media.AudioFormat audioFormat) {
            this.mEncoding = 0;
            this.mSampleRate = 0;
            this.mChannelMask = 0;
            this.mChannelIndexMask = 0;
            this.mPropertySetMask = 0;
            this.mEncoding = audioFormat.mEncoding;
            this.mSampleRate = audioFormat.mSampleRate;
            this.mChannelMask = audioFormat.mChannelMask;
            this.mChannelIndexMask = audioFormat.mChannelIndexMask;
            this.mPropertySetMask = audioFormat.mPropertySetMask;
        }

        public android.media.AudioFormat build() {
            return new android.media.AudioFormat(this.mPropertySetMask, this.mEncoding, this.mSampleRate, this.mChannelMask, this.mChannelIndexMask);
        }

        public android.media.AudioFormat.Builder setEncoding(int i) throws java.lang.IllegalArgumentException {
            switch (i) {
                case 1:
                    this.mEncoding = 2;
                    break;
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                    this.mEncoding = i;
                    break;
                default:
                    throw new java.lang.IllegalArgumentException("Invalid encoding " + i);
            }
            this.mPropertySetMask |= 1;
            return this;
        }

        public android.media.AudioFormat.Builder setChannelMask(int i) {
            if (i == 0) {
                throw new java.lang.IllegalArgumentException("Invalid zero channel mask");
            }
            if (this.mChannelIndexMask != 0 && java.lang.Integer.bitCount(i) != java.lang.Integer.bitCount(this.mChannelIndexMask)) {
                throw new java.lang.IllegalArgumentException("Mismatched channel count for mask " + java.lang.Integer.toHexString(i).toUpperCase());
            }
            this.mChannelMask = i;
            this.mPropertySetMask |= 4;
            return this;
        }

        public android.media.AudioFormat.Builder setChannelIndexMask(int i) {
            if (i == 0) {
                throw new java.lang.IllegalArgumentException("Invalid zero channel index mask");
            }
            if (this.mChannelMask != 0 && java.lang.Integer.bitCount(i) != java.lang.Integer.bitCount(this.mChannelMask)) {
                throw new java.lang.IllegalArgumentException("Mismatched channel count for index mask " + java.lang.Integer.toHexString(i).toUpperCase());
            }
            this.mChannelIndexMask = i;
            this.mPropertySetMask |= 8;
            return this;
        }

        public android.media.AudioFormat.Builder setSampleRate(int i) throws java.lang.IllegalArgumentException {
            if ((i < android.media.AudioFormat.SAMPLE_RATE_HZ_MIN || i > android.media.AudioFormat.SAMPLE_RATE_HZ_MAX) && i != 0) {
                throw new java.lang.IllegalArgumentException("Invalid sample rate " + i);
            }
            this.mSampleRate = i;
            this.mPropertySetMask |= 2;
            return this;
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.media.AudioFormat audioFormat = (android.media.AudioFormat) obj;
        if (this.mPropertySetMask == audioFormat.mPropertySetMask) {
            if (((this.mPropertySetMask & 1) == 0 || this.mEncoding == audioFormat.mEncoding) && (((this.mPropertySetMask & 2) == 0 || this.mSampleRate == audioFormat.mSampleRate) && (((this.mPropertySetMask & 4) == 0 || this.mChannelMask == audioFormat.mChannelMask) && ((this.mPropertySetMask & 8) == 0 || this.mChannelIndexMask == audioFormat.mChannelIndexMask)))) {
                return true;
            }
            return false;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mPropertySetMask), java.lang.Integer.valueOf(this.mSampleRate), java.lang.Integer.valueOf(this.mEncoding), java.lang.Integer.valueOf(this.mChannelMask), java.lang.Integer.valueOf(this.mChannelIndexMask));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mPropertySetMask);
        parcel.writeInt(this.mEncoding);
        parcel.writeInt(this.mSampleRate);
        parcel.writeInt(this.mChannelMask);
        parcel.writeInt(this.mChannelIndexMask);
    }

    private AudioFormat(android.os.Parcel parcel) {
        this(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
    }

    public java.lang.String toString() {
        return new java.lang.String("AudioFormat: props=" + this.mPropertySetMask + " enc=" + this.mEncoding + " chan=0x" + java.lang.Integer.toHexString(this.mChannelMask).toUpperCase() + " chan_index=0x" + java.lang.Integer.toHexString(this.mChannelIndexMask).toUpperCase() + " rate=" + this.mSampleRate);
    }

    public static java.lang.String toDisplayName(int i) {
        switch (i) {
            case 5:
                return "Dolby Digital";
            case 6:
                return "Dolby Digital Plus";
            case 7:
                return "DTS";
            case 8:
                return "DTS HD";
            case 9:
            case 11:
            case 12:
            case 13:
            case 15:
            case 16:
            case 20:
            case 21:
            case 22:
            default:
                return "Unknown surround sound format";
            case 10:
                return "AAC";
            case 14:
                return "Dolby TrueHD";
            case 17:
                return "Dolby AC-4";
            case 18:
                return "Dolby Atmos in Dolby Digital Plus";
            case 19:
                return "Dolby MAT";
            case 23:
                return "MPEG-H 3D Audio baseline profile level 3";
            case 24:
                return "MPEG-H 3D Audio baseline profile level 4";
            case 25:
                return "MPEG-H 3D Audio low complexity profile level 3";
            case 26:
                return "MPEG-H 3D Audio low complexity profile level 4";
            case 27:
                return "DTS UHD Profile 1";
            case 28:
                return "DRA";
            case 29:
                return "DTS HD Master Audio";
            case 30:
                return "DTS UHD Profile 2";
        }
    }
}
