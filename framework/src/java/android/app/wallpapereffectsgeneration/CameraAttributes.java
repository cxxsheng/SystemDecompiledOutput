package android.app.wallpapereffectsgeneration;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class CameraAttributes implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.wallpapereffectsgeneration.CameraAttributes> CREATOR = new android.os.Parcelable.Creator<android.app.wallpapereffectsgeneration.CameraAttributes>() { // from class: android.app.wallpapereffectsgeneration.CameraAttributes.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.wallpapereffectsgeneration.CameraAttributes createFromParcel(android.os.Parcel parcel) {
            return new android.app.wallpapereffectsgeneration.CameraAttributes(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.wallpapereffectsgeneration.CameraAttributes[] newArray(int i) {
            return new android.app.wallpapereffectsgeneration.CameraAttributes[i];
        }
    };
    private float[] mAnchorPointInOutputUvSpace;
    private float[] mAnchorPointInWorldSpace;
    private float mCameraOrbitPitchDegrees;
    private float mCameraOrbitYawDegrees;
    private float mDollyDistanceInWorldSpace;
    private float mFrustumFarInWorldSpace;
    private float mFrustumNearInWorldSpace;
    private float mVerticalFovDegrees;

    private CameraAttributes(android.os.Parcel parcel) {
        this.mCameraOrbitYawDegrees = parcel.readFloat();
        this.mCameraOrbitPitchDegrees = parcel.readFloat();
        this.mDollyDistanceInWorldSpace = parcel.readFloat();
        this.mVerticalFovDegrees = parcel.readFloat();
        this.mFrustumNearInWorldSpace = parcel.readFloat();
        this.mFrustumFarInWorldSpace = parcel.readFloat();
        this.mAnchorPointInWorldSpace = parcel.createFloatArray();
        this.mAnchorPointInOutputUvSpace = parcel.createFloatArray();
    }

    private CameraAttributes(float[] fArr, float[] fArr2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.mAnchorPointInWorldSpace = fArr;
        this.mAnchorPointInOutputUvSpace = fArr2;
        this.mCameraOrbitYawDegrees = f;
        this.mCameraOrbitPitchDegrees = f2;
        this.mDollyDistanceInWorldSpace = f3;
        this.mVerticalFovDegrees = f4;
        this.mFrustumNearInWorldSpace = f5;
        this.mFrustumFarInWorldSpace = f6;
    }

    public float[] getAnchorPointInWorldSpace() {
        return this.mAnchorPointInWorldSpace;
    }

    public float[] getAnchorPointInOutputUvSpace() {
        return this.mAnchorPointInOutputUvSpace;
    }

    public float getCameraOrbitYawDegrees() {
        return this.mCameraOrbitYawDegrees;
    }

    public float getCameraOrbitPitchDegrees() {
        return this.mCameraOrbitPitchDegrees;
    }

    public float getDollyDistanceInWorldSpace() {
        return this.mDollyDistanceInWorldSpace;
    }

    public float getVerticalFovDegrees() {
        return this.mVerticalFovDegrees;
    }

    public float getFrustumNearInWorldSpace() {
        return this.mFrustumNearInWorldSpace;
    }

    public float getFrustumFarInWorldSpace() {
        return this.mFrustumFarInWorldSpace;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeFloat(this.mCameraOrbitYawDegrees);
        parcel.writeFloat(this.mCameraOrbitPitchDegrees);
        parcel.writeFloat(this.mDollyDistanceInWorldSpace);
        parcel.writeFloat(this.mVerticalFovDegrees);
        parcel.writeFloat(this.mFrustumNearInWorldSpace);
        parcel.writeFloat(this.mFrustumFarInWorldSpace);
        parcel.writeFloatArray(this.mAnchorPointInWorldSpace);
        parcel.writeFloatArray(this.mAnchorPointInOutputUvSpace);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private float[] mAnchorPointInOutputUvSpace;
        private float[] mAnchorPointInWorldSpace;
        private float mCameraOrbitPitchDegrees;
        private float mCameraOrbitYawDegrees;
        private float mDollyDistanceInWorldSpace;
        private float mFrustumFarInWorldSpace;
        private float mFrustumNearInWorldSpace;
        private float mVerticalFovDegrees;

        @android.annotation.SystemApi
        public Builder(float[] fArr, float[] fArr2) {
            this.mAnchorPointInWorldSpace = fArr;
            this.mAnchorPointInOutputUvSpace = fArr2;
        }

        public android.app.wallpapereffectsgeneration.CameraAttributes.Builder setCameraOrbitYawDegrees(float f) {
            this.mCameraOrbitYawDegrees = f;
            return this;
        }

        public android.app.wallpapereffectsgeneration.CameraAttributes.Builder setCameraOrbitPitchDegrees(float f) {
            this.mCameraOrbitPitchDegrees = f;
            return this;
        }

        public android.app.wallpapereffectsgeneration.CameraAttributes.Builder setDollyDistanceInWorldSpace(float f) {
            this.mDollyDistanceInWorldSpace = f;
            return this;
        }

        public android.app.wallpapereffectsgeneration.CameraAttributes.Builder setVerticalFovDegrees(float f) {
            this.mVerticalFovDegrees = f;
            return this;
        }

        public android.app.wallpapereffectsgeneration.CameraAttributes.Builder setFrustumNearInWorldSpace(float f) {
            this.mFrustumNearInWorldSpace = f;
            return this;
        }

        public android.app.wallpapereffectsgeneration.CameraAttributes.Builder setFrustumFarInWorldSpace(float f) {
            this.mFrustumFarInWorldSpace = f;
            return this;
        }

        public android.app.wallpapereffectsgeneration.CameraAttributes build() {
            return new android.app.wallpapereffectsgeneration.CameraAttributes(this.mAnchorPointInWorldSpace, this.mAnchorPointInOutputUvSpace, this.mCameraOrbitYawDegrees, this.mCameraOrbitPitchDegrees, this.mDollyDistanceInWorldSpace, this.mVerticalFovDegrees, this.mFrustumNearInWorldSpace, this.mFrustumFarInWorldSpace);
        }
    }
}
