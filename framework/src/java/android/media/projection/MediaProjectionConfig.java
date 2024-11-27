package android.media.projection;

/* loaded from: classes2.dex */
public final class MediaProjectionConfig implements android.os.Parcelable {
    public static final int CAPTURE_REGION_FIXED_DISPLAY = 1;
    public static final int CAPTURE_REGION_USER_CHOICE = 0;
    public static final android.os.Parcelable.Creator<android.media.projection.MediaProjectionConfig> CREATOR = new android.os.Parcelable.Creator<android.media.projection.MediaProjectionConfig>() { // from class: android.media.projection.MediaProjectionConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.projection.MediaProjectionConfig[] newArray(int i) {
            return new android.media.projection.MediaProjectionConfig[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.projection.MediaProjectionConfig createFromParcel(android.os.Parcel parcel) {
            return new android.media.projection.MediaProjectionConfig(parcel);
        }
    };
    private int mDisplayToCapture;
    private int mRegionToCapture;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CaptureRegion {
    }

    private MediaProjectionConfig() {
        this.mRegionToCapture = 0;
    }

    private MediaProjectionConfig(int i) {
        this.mRegionToCapture = 0;
        this.mRegionToCapture = i;
    }

    public static android.media.projection.MediaProjectionConfig createConfigForDefaultDisplay() {
        android.media.projection.MediaProjectionConfig mediaProjectionConfig = new android.media.projection.MediaProjectionConfig(1);
        mediaProjectionConfig.mDisplayToCapture = 0;
        return mediaProjectionConfig;
    }

    public static android.media.projection.MediaProjectionConfig createConfigForUserChoice() {
        return new android.media.projection.MediaProjectionConfig(0);
    }

    private static java.lang.String captureRegionToString(int i) {
        switch (i) {
            case 0:
                return "CAPTURE_REGION_USERS_CHOICE";
            case 1:
                return "CAPTURE_REGION_GIVEN_DISPLAY";
            default:
                return java.lang.Integer.toHexString(i);
        }
    }

    public java.lang.String toString() {
        return "MediaProjectionConfig { displayToCapture = " + this.mDisplayToCapture + ", regionToCapture = " + captureRegionToString(this.mRegionToCapture) + " }";
    }

    public int getDisplayToCapture() {
        return this.mDisplayToCapture;
    }

    public int getRegionToCapture() {
        return this.mRegionToCapture;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.media.projection.MediaProjectionConfig mediaProjectionConfig = (android.media.projection.MediaProjectionConfig) obj;
        if (this.mDisplayToCapture == mediaProjectionConfig.mDisplayToCapture && this.mRegionToCapture == mediaProjectionConfig.mRegionToCapture) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((this.mDisplayToCapture + 31) * 31) + this.mRegionToCapture;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mDisplayToCapture);
        parcel.writeInt(this.mRegionToCapture);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    MediaProjectionConfig(android.os.Parcel parcel) {
        this.mRegionToCapture = 0;
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        this.mDisplayToCapture = readInt;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mDisplayToCapture, "from", 0L, "to", 0L);
        this.mRegionToCapture = readInt2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.media.projection.MediaProjectionConfig.CaptureRegion.class, (java.lang.annotation.Annotation) null, this.mRegionToCapture);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
