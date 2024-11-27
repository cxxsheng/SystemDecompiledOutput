package android.media;

/* loaded from: classes2.dex */
public final class AudioHalVersionInfo implements android.os.Parcelable, java.lang.Comparable<android.media.AudioHalVersionInfo> {
    public static final int AUDIO_HAL_TYPE_AIDL = 1;
    public static final int AUDIO_HAL_TYPE_HIDL = 0;
    private static final java.lang.String TAG = "AudioHalVersionInfo";
    private android.media.AudioHalVersion mHalVersion;
    public static final android.media.AudioHalVersionInfo AIDL_1_0 = new android.media.AudioHalVersionInfo(1, 1, 0);
    public static final android.media.AudioHalVersionInfo HIDL_7_1 = new android.media.AudioHalVersionInfo(0, 7, 1);
    public static final android.media.AudioHalVersionInfo HIDL_7_0 = new android.media.AudioHalVersionInfo(0, 7, 0);
    public static final android.media.AudioHalVersionInfo HIDL_6_0 = new android.media.AudioHalVersionInfo(0, 6, 0);
    public static final android.media.AudioHalVersionInfo HIDL_5_0 = new android.media.AudioHalVersionInfo(0, 5, 0);
    public static final android.media.AudioHalVersionInfo HIDL_4_0 = new android.media.AudioHalVersionInfo(0, 4, 0);
    public static final android.media.AudioHalVersionInfo HIDL_2_0 = new android.media.AudioHalVersionInfo(0, 2, 0);
    public static final java.util.List<android.media.AudioHalVersionInfo> VERSIONS = java.util.List.of(AIDL_1_0, HIDL_7_1, HIDL_7_0, HIDL_6_0, HIDL_5_0);
    public static final android.os.Parcelable.Creator<android.media.AudioHalVersionInfo> CREATOR = new android.os.Parcelable.Creator<android.media.AudioHalVersionInfo>() { // from class: android.media.AudioHalVersionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioHalVersionInfo createFromParcel(android.os.Parcel parcel) {
            return new android.media.AudioHalVersionInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioHalVersionInfo[] newArray(int i) {
            return new android.media.AudioHalVersionInfo[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AudioHalType {
    }

    public int getHalType() {
        return this.mHalVersion.type;
    }

    public int getMajorVersion() {
        return this.mHalVersion.major;
    }

    public int getMinorVersion() {
        return this.mHalVersion.minor;
    }

    private static java.lang.String typeToString(int i) {
        if (i == 0) {
            return "HIDL";
        }
        if (i == 1) {
            return "AIDL";
        }
        return "INVALID";
    }

    private static java.lang.String toString(int i, int i2, int i3) {
        return typeToString(i) + ":" + java.lang.Integer.toString(i2) + android.media.MediaMetrics.SEPARATOR + java.lang.Integer.toString(i3);
    }

    private AudioHalVersionInfo(int i, int i2, int i3) {
        this.mHalVersion = new android.media.AudioHalVersion();
        this.mHalVersion.type = i;
        this.mHalVersion.major = i2;
        this.mHalVersion.minor = i3;
    }

    private AudioHalVersionInfo(android.os.Parcel parcel) {
        this.mHalVersion = new android.media.AudioHalVersion();
        this.mHalVersion = (android.media.AudioHalVersion) parcel.readTypedObject(android.media.AudioHalVersion.CREATOR);
    }

    public java.lang.String toString() {
        return toString(this.mHalVersion.type, this.mHalVersion.major, this.mHalVersion.minor);
    }

    @Override // java.lang.Comparable
    public int compareTo(android.media.AudioHalVersionInfo audioHalVersionInfo) {
        int indexOf = VERSIONS.indexOf(audioHalVersionInfo);
        int indexOf2 = VERSIONS.indexOf(this);
        if (indexOf2 < 0 || indexOf < 0) {
            return indexOf2 - indexOf;
        }
        return indexOf - indexOf2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mHalVersion, i);
    }
}
