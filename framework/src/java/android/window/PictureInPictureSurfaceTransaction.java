package android.window;

/* loaded from: classes4.dex */
public final class PictureInPictureSurfaceTransaction implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.PictureInPictureSurfaceTransaction> CREATOR = new android.os.Parcelable.Creator<android.window.PictureInPictureSurfaceTransaction>() { // from class: android.window.PictureInPictureSurfaceTransaction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.PictureInPictureSurfaceTransaction createFromParcel(android.os.Parcel parcel) {
            return new android.window.PictureInPictureSurfaceTransaction(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.PictureInPictureSurfaceTransaction[] newArray(int i) {
            return new android.window.PictureInPictureSurfaceTransaction[i];
        }
    };
    private static final float NOT_SET = -1.0f;
    public final float mAlpha;
    public final float mCornerRadius;
    public final float[] mFloat9;
    public final android.graphics.PointF mPosition;
    public final float mRotation;
    public final float mShadowRadius;
    private boolean mShouldDisableCanAffectSystemUiFlags;
    private final android.graphics.Rect mWindowCrop;

    private PictureInPictureSurfaceTransaction(android.os.Parcel parcel) {
        this.mAlpha = parcel.readFloat();
        this.mPosition = (android.graphics.PointF) parcel.readTypedObject(android.graphics.PointF.CREATOR);
        this.mFloat9 = new float[9];
        parcel.readFloatArray(this.mFloat9);
        this.mRotation = parcel.readFloat();
        this.mCornerRadius = parcel.readFloat();
        this.mShadowRadius = parcel.readFloat();
        this.mWindowCrop = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
        this.mShouldDisableCanAffectSystemUiFlags = parcel.readBoolean();
    }

    private PictureInPictureSurfaceTransaction(float f, android.graphics.PointF pointF, float[] fArr, float f2, float f3, float f4, android.graphics.Rect rect) {
        this.mAlpha = f;
        this.mPosition = pointF;
        if (fArr == null) {
            this.mFloat9 = new float[9];
            android.graphics.Matrix.IDENTITY_MATRIX.getValues(this.mFloat9);
            this.mRotation = 0.0f;
        } else {
            this.mFloat9 = java.util.Arrays.copyOf(fArr, 9);
            this.mRotation = f2;
        }
        this.mCornerRadius = f3;
        this.mShadowRadius = f4;
        this.mWindowCrop = rect == null ? null : new android.graphics.Rect(rect);
    }

    public PictureInPictureSurfaceTransaction(android.window.PictureInPictureSurfaceTransaction pictureInPictureSurfaceTransaction) {
        this(pictureInPictureSurfaceTransaction.mAlpha, pictureInPictureSurfaceTransaction.mPosition, pictureInPictureSurfaceTransaction.mFloat9, pictureInPictureSurfaceTransaction.mRotation, pictureInPictureSurfaceTransaction.mCornerRadius, pictureInPictureSurfaceTransaction.mShadowRadius, pictureInPictureSurfaceTransaction.mWindowCrop);
        this.mShouldDisableCanAffectSystemUiFlags = pictureInPictureSurfaceTransaction.mShouldDisableCanAffectSystemUiFlags;
    }

    public android.graphics.Matrix getMatrix() {
        android.graphics.Matrix matrix = new android.graphics.Matrix();
        matrix.setValues(this.mFloat9);
        return matrix;
    }

    public boolean hasCornerRadiusSet() {
        return this.mCornerRadius > 0.0f;
    }

    public boolean hasShadowRadiusSet() {
        return this.mShadowRadius > 0.0f;
    }

    public void setShouldDisableCanAffectSystemUiFlags(boolean z) {
        this.mShouldDisableCanAffectSystemUiFlags = z;
    }

    public boolean getShouldDisableCanAffectSystemUiFlags() {
        return this.mShouldDisableCanAffectSystemUiFlags;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.window.PictureInPictureSurfaceTransaction)) {
            return false;
        }
        android.window.PictureInPictureSurfaceTransaction pictureInPictureSurfaceTransaction = (android.window.PictureInPictureSurfaceTransaction) obj;
        return java.util.Objects.equals(java.lang.Float.valueOf(this.mAlpha), java.lang.Float.valueOf(pictureInPictureSurfaceTransaction.mAlpha)) && java.util.Objects.equals(this.mPosition, pictureInPictureSurfaceTransaction.mPosition) && java.util.Arrays.equals(this.mFloat9, pictureInPictureSurfaceTransaction.mFloat9) && java.util.Objects.equals(java.lang.Float.valueOf(this.mRotation), java.lang.Float.valueOf(pictureInPictureSurfaceTransaction.mRotation)) && java.util.Objects.equals(java.lang.Float.valueOf(this.mCornerRadius), java.lang.Float.valueOf(pictureInPictureSurfaceTransaction.mCornerRadius)) && java.util.Objects.equals(java.lang.Float.valueOf(this.mShadowRadius), java.lang.Float.valueOf(pictureInPictureSurfaceTransaction.mShadowRadius)) && java.util.Objects.equals(this.mWindowCrop, pictureInPictureSurfaceTransaction.mWindowCrop) && this.mShouldDisableCanAffectSystemUiFlags == pictureInPictureSurfaceTransaction.mShouldDisableCanAffectSystemUiFlags;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Float.valueOf(this.mAlpha), this.mPosition, java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mFloat9)), java.lang.Float.valueOf(this.mRotation), java.lang.Float.valueOf(this.mCornerRadius), java.lang.Float.valueOf(this.mShadowRadius), this.mWindowCrop, java.lang.Boolean.valueOf(this.mShouldDisableCanAffectSystemUiFlags));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeFloat(this.mAlpha);
        parcel.writeTypedObject(this.mPosition, 0);
        parcel.writeFloatArray(this.mFloat9);
        parcel.writeFloat(this.mRotation);
        parcel.writeFloat(this.mCornerRadius);
        parcel.writeFloat(this.mShadowRadius);
        parcel.writeTypedObject(this.mWindowCrop, 0);
        parcel.writeBoolean(this.mShouldDisableCanAffectSystemUiFlags);
    }

    public java.lang.String toString() {
        return "PictureInPictureSurfaceTransaction( alpha=" + this.mAlpha + " position=" + this.mPosition + " matrix=" + getMatrix().toShortString() + " rotation=" + this.mRotation + " cornerRadius=" + this.mCornerRadius + " shadowRadius=" + this.mShadowRadius + " crop=" + this.mWindowCrop + " shouldDisableCanAffectSystemUiFlags" + this.mShouldDisableCanAffectSystemUiFlags + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }

    public static void apply(android.window.PictureInPictureSurfaceTransaction pictureInPictureSurfaceTransaction, android.view.SurfaceControl surfaceControl, android.view.SurfaceControl.Transaction transaction) {
        transaction.setMatrix(surfaceControl, pictureInPictureSurfaceTransaction.getMatrix(), new float[9]);
        if (pictureInPictureSurfaceTransaction.mPosition != null) {
            transaction.setPosition(surfaceControl, pictureInPictureSurfaceTransaction.mPosition.x, pictureInPictureSurfaceTransaction.mPosition.y);
        }
        if (pictureInPictureSurfaceTransaction.mWindowCrop != null) {
            transaction.setWindowCrop(surfaceControl, pictureInPictureSurfaceTransaction.mWindowCrop);
        }
        if (pictureInPictureSurfaceTransaction.hasCornerRadiusSet()) {
            transaction.setCornerRadius(surfaceControl, pictureInPictureSurfaceTransaction.mCornerRadius);
        }
        if (pictureInPictureSurfaceTransaction.hasShadowRadiusSet()) {
            transaction.setShadowRadius(surfaceControl, pictureInPictureSurfaceTransaction.mShadowRadius);
        }
        if (pictureInPictureSurfaceTransaction.mAlpha != -1.0f) {
            transaction.setAlpha(surfaceControl, pictureInPictureSurfaceTransaction.mAlpha);
        }
    }

    public static class Builder {
        private float[] mFloat9;
        private android.graphics.PointF mPosition;
        private float mRotation;
        private android.graphics.Rect mWindowCrop;
        private float mAlpha = -1.0f;
        private float mCornerRadius = -1.0f;
        private float mShadowRadius = -1.0f;

        public android.window.PictureInPictureSurfaceTransaction.Builder setAlpha(float f) {
            this.mAlpha = f;
            return this;
        }

        public android.window.PictureInPictureSurfaceTransaction.Builder setPosition(float f, float f2) {
            this.mPosition = new android.graphics.PointF(f, f2);
            return this;
        }

        public android.window.PictureInPictureSurfaceTransaction.Builder setTransform(float[] fArr, float f) {
            this.mFloat9 = java.util.Arrays.copyOf(fArr, 9);
            this.mRotation = f;
            return this;
        }

        public android.window.PictureInPictureSurfaceTransaction.Builder setCornerRadius(float f) {
            this.mCornerRadius = f;
            return this;
        }

        public android.window.PictureInPictureSurfaceTransaction.Builder setShadowRadius(float f) {
            this.mShadowRadius = f;
            return this;
        }

        public android.window.PictureInPictureSurfaceTransaction.Builder setWindowCrop(android.graphics.Rect rect) {
            this.mWindowCrop = new android.graphics.Rect(rect);
            return this;
        }

        public android.window.PictureInPictureSurfaceTransaction build() {
            return new android.window.PictureInPictureSurfaceTransaction(this.mAlpha, this.mPosition, this.mFloat9, this.mRotation, this.mCornerRadius, this.mShadowRadius, this.mWindowCrop);
        }
    }
}
