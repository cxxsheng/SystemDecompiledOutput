package android.media;

/* loaded from: classes2.dex */
public final class MicrophoneInfo {
    public static final int CHANNEL_MAPPING_DIRECT = 1;
    public static final int CHANNEL_MAPPING_PROCESSED = 2;
    public static final int DIRECTIONALITY_BI_DIRECTIONAL = 2;
    public static final int DIRECTIONALITY_CARDIOID = 3;
    public static final int DIRECTIONALITY_HYPER_CARDIOID = 4;
    public static final int DIRECTIONALITY_OMNI = 1;
    public static final int DIRECTIONALITY_SUPER_CARDIOID = 5;
    public static final int DIRECTIONALITY_UNKNOWN = 0;
    public static final int GROUP_UNKNOWN = -1;
    public static final int INDEX_IN_THE_GROUP_UNKNOWN = -1;
    public static final int LOCATION_MAINBODY = 1;
    public static final int LOCATION_MAINBODY_MOVABLE = 2;
    public static final int LOCATION_PERIPHERAL = 3;
    public static final int LOCATION_UNKNOWN = 0;
    public static final float SENSITIVITY_UNKNOWN = -3.4028235E38f;
    public static final float SPL_UNKNOWN = -3.4028235E38f;
    private java.lang.String mAddress;
    private java.util.List<android.util.Pair<java.lang.Integer, java.lang.Integer>> mChannelMapping;
    private java.lang.String mDeviceId;
    private int mDirectionality;
    private java.util.List<android.util.Pair<java.lang.Float, java.lang.Float>> mFrequencyResponse;
    private int mGroup;
    private int mIndexInTheGroup;
    private int mLocation;
    private float mMaxSpl;
    private float mMinSpl;
    private android.media.MicrophoneInfo.Coordinate3F mOrientation;
    private int mPortId;
    private android.media.MicrophoneInfo.Coordinate3F mPosition;
    private float mSensitivity;
    private int mType;
    public static final android.media.MicrophoneInfo.Coordinate3F POSITION_UNKNOWN = new android.media.MicrophoneInfo.Coordinate3F(-3.4028235E38f, -3.4028235E38f, -3.4028235E38f);
    public static final android.media.MicrophoneInfo.Coordinate3F ORIENTATION_UNKNOWN = new android.media.MicrophoneInfo.Coordinate3F(0.0f, 0.0f, 0.0f);

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MicrophoneDirectionality {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MicrophoneLocation {
    }

    MicrophoneInfo(java.lang.String str, int i, java.lang.String str2, int i2, int i3, int i4, android.media.MicrophoneInfo.Coordinate3F coordinate3F, android.media.MicrophoneInfo.Coordinate3F coordinate3F2, java.util.List<android.util.Pair<java.lang.Float, java.lang.Float>> list, java.util.List<android.util.Pair<java.lang.Integer, java.lang.Integer>> list2, float f, float f2, float f3, int i5) {
        this.mDeviceId = str;
        this.mType = i;
        this.mAddress = str2;
        this.mLocation = i2;
        this.mGroup = i3;
        this.mIndexInTheGroup = i4;
        this.mPosition = coordinate3F;
        this.mOrientation = coordinate3F2;
        this.mFrequencyResponse = list;
        this.mChannelMapping = list2;
        this.mSensitivity = f;
        this.mMaxSpl = f2;
        this.mMinSpl = f3;
        this.mDirectionality = i5;
    }

    public java.lang.String getDescription() {
        return this.mDeviceId;
    }

    public int getId() {
        return this.mPortId;
    }

    public int getInternalDeviceType() {
        return this.mType;
    }

    public int getType() {
        return android.media.AudioDeviceInfo.convertInternalDeviceToDeviceType(this.mType);
    }

    public java.lang.String getAddress() {
        return this.mAddress;
    }

    public int getLocation() {
        return this.mLocation;
    }

    public int getGroup() {
        return this.mGroup;
    }

    public int getIndexInTheGroup() {
        return this.mIndexInTheGroup;
    }

    public android.media.MicrophoneInfo.Coordinate3F getPosition() {
        return this.mPosition;
    }

    public android.media.MicrophoneInfo.Coordinate3F getOrientation() {
        return this.mOrientation;
    }

    public java.util.List<android.util.Pair<java.lang.Float, java.lang.Float>> getFrequencyResponse() {
        return this.mFrequencyResponse;
    }

    public java.util.List<android.util.Pair<java.lang.Integer, java.lang.Integer>> getChannelMapping() {
        return this.mChannelMapping;
    }

    public float getSensitivity() {
        return this.mSensitivity;
    }

    public float getMaxSpl() {
        return this.mMaxSpl;
    }

    public float getMinSpl() {
        return this.mMinSpl;
    }

    public int getDirectionality() {
        return this.mDirectionality;
    }

    public void setId(int i) {
        this.mPortId = i;
    }

    public void setChannelMapping(java.util.List<android.util.Pair<java.lang.Integer, java.lang.Integer>> list) {
        this.mChannelMapping = list;
    }

    public static final class Coordinate3F {
        public final float x;
        public final float y;
        public final float z;

        Coordinate3F(float f, float f2, float f3) {
            this.x = f;
            this.y = f2;
            this.z = f3;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.media.MicrophoneInfo.Coordinate3F)) {
                return false;
            }
            android.media.MicrophoneInfo.Coordinate3F coordinate3F = (android.media.MicrophoneInfo.Coordinate3F) obj;
            return this.x == coordinate3F.x && this.y == coordinate3F.y && this.z == coordinate3F.z;
        }
    }
}
