package android.companion.virtual.camera;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class VirtualCameraStreamConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.companion.virtual.camera.VirtualCameraStreamConfig> CREATOR = new android.os.Parcelable.Creator<android.companion.virtual.camera.VirtualCameraStreamConfig>() { // from class: android.companion.virtual.camera.VirtualCameraStreamConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.virtual.camera.VirtualCameraStreamConfig createFromParcel(android.os.Parcel parcel) {
            return new android.companion.virtual.camera.VirtualCameraStreamConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.virtual.camera.VirtualCameraStreamConfig[] newArray(int i) {
            return new android.companion.virtual.camera.VirtualCameraStreamConfig[i];
        }
    };
    static final int DIMENSION_UPPER_LIMIT = 2048;
    static final int MAX_FPS_UPPER_LIMIT = 60;
    private final int mFormat;
    private final int mHeight;
    private final int mMaxFps;
    private final int mWidth;

    public VirtualCameraStreamConfig(int i, int i2, int i3, int i4) {
        this.mWidth = i;
        this.mHeight = i2;
        this.mFormat = i3;
        this.mMaxFps = i4;
    }

    private VirtualCameraStreamConfig(android.os.Parcel parcel) {
        this.mWidth = parcel.readInt();
        this.mHeight = parcel.readInt();
        this.mFormat = parcel.readInt();
        this.mMaxFps = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mWidth);
        parcel.writeInt(this.mHeight);
        parcel.writeInt(this.mFormat);
        parcel.writeInt(this.mMaxFps);
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.companion.virtual.camera.VirtualCameraStreamConfig virtualCameraStreamConfig = (android.companion.virtual.camera.VirtualCameraStreamConfig) obj;
        if (this.mWidth == virtualCameraStreamConfig.mWidth && this.mHeight == virtualCameraStreamConfig.mHeight && this.mFormat == virtualCameraStreamConfig.mFormat && this.mMaxFps == virtualCameraStreamConfig.mMaxFps) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mWidth), java.lang.Integer.valueOf(this.mHeight), java.lang.Integer.valueOf(this.mFormat), java.lang.Integer.valueOf(this.mMaxFps));
    }

    public int getFormat() {
        return this.mFormat;
    }

    public int getMaximumFramesPerSecond() {
        return this.mMaxFps;
    }
}
