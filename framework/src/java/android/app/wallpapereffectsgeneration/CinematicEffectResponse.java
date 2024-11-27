package android.app.wallpapereffectsgeneration;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class CinematicEffectResponse implements android.os.Parcelable {
    public static final int CINEMATIC_EFFECT_STATUS_ANIMATION_FAILURE = 10;
    public static final int CINEMATIC_EFFECT_STATUS_CONTENT_TARGET_ERROR = 8;
    public static final int CINEMATIC_EFFECT_STATUS_CONTENT_TOO_FLAT = 9;
    public static final int CINEMATIC_EFFECT_STATUS_CONTENT_UNSUPPORTED = 7;
    public static final int CINEMATIC_EFFECT_STATUS_ERROR = 0;
    public static final int CINEMATIC_EFFECT_STATUS_FEATURE_DISABLED = 5;
    public static final int CINEMATIC_EFFECT_STATUS_IMAGE_FORMAT_NOT_SUITABLE = 6;
    public static final int CINEMATIC_EFFECT_STATUS_NOT_READY = 2;
    public static final int CINEMATIC_EFFECT_STATUS_OK = 1;
    public static final int CINEMATIC_EFFECT_STATUS_PENDING = 3;
    public static final int CINEMATIC_EFFECT_STATUS_TOO_MANY_REQUESTS = 4;
    public static final android.os.Parcelable.Creator<android.app.wallpapereffectsgeneration.CinematicEffectResponse> CREATOR = new android.os.Parcelable.Creator<android.app.wallpapereffectsgeneration.CinematicEffectResponse>() { // from class: android.app.wallpapereffectsgeneration.CinematicEffectResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.wallpapereffectsgeneration.CinematicEffectResponse createFromParcel(android.os.Parcel parcel) {
            return new android.app.wallpapereffectsgeneration.CinematicEffectResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.wallpapereffectsgeneration.CinematicEffectResponse[] newArray(int i) {
            return new android.app.wallpapereffectsgeneration.CinematicEffectResponse[i];
        }
    };
    public static final int IMAGE_CONTENT_TYPE_LANDSCAPE = 2;
    public static final int IMAGE_CONTENT_TYPE_OTHER = 3;
    public static final int IMAGE_CONTENT_TYPE_PEOPLE_PORTRAIT = 1;
    public static final int IMAGE_CONTENT_TYPE_UNKNOWN = 0;
    private android.app.wallpapereffectsgeneration.CameraAttributes mEndKeyFrame;
    private int mImageContentType;
    private android.app.wallpapereffectsgeneration.CameraAttributes mStartKeyFrame;
    private int mStatusCode;
    private java.lang.String mTaskId;
    private java.util.List<android.app.wallpapereffectsgeneration.TexturedMesh> mTexturedMeshes;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CinematicEffectStatusCode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ImageContentType {
    }

    private CinematicEffectResponse(android.os.Parcel parcel) {
        this.mStatusCode = parcel.readInt();
        this.mTaskId = parcel.readString();
        this.mImageContentType = parcel.readInt();
        this.mTexturedMeshes = new java.util.ArrayList();
        parcel.readTypedList(this.mTexturedMeshes, android.app.wallpapereffectsgeneration.TexturedMesh.CREATOR);
        this.mStartKeyFrame = (android.app.wallpapereffectsgeneration.CameraAttributes) parcel.readTypedObject(android.app.wallpapereffectsgeneration.CameraAttributes.CREATOR);
        this.mEndKeyFrame = (android.app.wallpapereffectsgeneration.CameraAttributes) parcel.readTypedObject(android.app.wallpapereffectsgeneration.CameraAttributes.CREATOR);
    }

    private CinematicEffectResponse(int i, java.lang.String str, int i2, java.util.List<android.app.wallpapereffectsgeneration.TexturedMesh> list, android.app.wallpapereffectsgeneration.CameraAttributes cameraAttributes, android.app.wallpapereffectsgeneration.CameraAttributes cameraAttributes2) {
        this.mStatusCode = i;
        this.mTaskId = str;
        this.mImageContentType = i2;
        this.mStartKeyFrame = cameraAttributes;
        this.mEndKeyFrame = cameraAttributes2;
        this.mTexturedMeshes = list;
    }

    public int getStatusCode() {
        return this.mStatusCode;
    }

    public java.lang.String getTaskId() {
        return this.mTaskId;
    }

    public int getImageContentType() {
        return this.mImageContentType;
    }

    public java.util.List<android.app.wallpapereffectsgeneration.TexturedMesh> getTexturedMeshes() {
        return this.mTexturedMeshes;
    }

    public android.app.wallpapereffectsgeneration.CameraAttributes getStartKeyFrame() {
        return this.mStartKeyFrame;
    }

    public android.app.wallpapereffectsgeneration.CameraAttributes getEndKeyFrame() {
        return this.mEndKeyFrame;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mStatusCode);
        parcel.writeString(this.mTaskId);
        parcel.writeInt(this.mImageContentType);
        parcel.writeTypedList(this.mTexturedMeshes, i);
        parcel.writeTypedObject(this.mStartKeyFrame, i);
        parcel.writeTypedObject(this.mEndKeyFrame, i);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.mTaskId.equals(((android.app.wallpapereffectsgeneration.CinematicEffectResponse) obj).mTaskId);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mTaskId);
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private android.app.wallpapereffectsgeneration.CameraAttributes mEndKeyFrame;
        private int mImageContentType;
        private android.app.wallpapereffectsgeneration.CameraAttributes mStartKeyFrame;
        private int mStatusCode;
        private java.lang.String mTaskId;
        private java.util.List<android.app.wallpapereffectsgeneration.TexturedMesh> mTexturedMeshes;

        @android.annotation.SystemApi
        public Builder(int i, java.lang.String str) {
            this.mStatusCode = i;
            this.mTaskId = str;
        }

        public android.app.wallpapereffectsgeneration.CinematicEffectResponse.Builder setImageContentType(int i) {
            this.mImageContentType = i;
            return this;
        }

        public android.app.wallpapereffectsgeneration.CinematicEffectResponse.Builder setTexturedMeshes(java.util.List<android.app.wallpapereffectsgeneration.TexturedMesh> list) {
            this.mTexturedMeshes = list;
            return this;
        }

        public android.app.wallpapereffectsgeneration.CinematicEffectResponse.Builder setStartKeyFrame(android.app.wallpapereffectsgeneration.CameraAttributes cameraAttributes) {
            this.mStartKeyFrame = cameraAttributes;
            return this;
        }

        public android.app.wallpapereffectsgeneration.CinematicEffectResponse.Builder setEndKeyFrame(android.app.wallpapereffectsgeneration.CameraAttributes cameraAttributes) {
            this.mEndKeyFrame = cameraAttributes;
            return this;
        }

        public android.app.wallpapereffectsgeneration.CinematicEffectResponse build() {
            if (this.mTexturedMeshes == null) {
                this.mTexturedMeshes = new java.util.ArrayList(0);
            }
            return new android.app.wallpapereffectsgeneration.CinematicEffectResponse(this.mStatusCode, this.mTaskId, this.mImageContentType, this.mTexturedMeshes, this.mStartKeyFrame, this.mEndKeyFrame);
        }
    }
}
