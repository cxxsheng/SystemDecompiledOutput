package android.accessibilityservice;

/* loaded from: classes.dex */
public final class MagnificationConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.accessibilityservice.MagnificationConfig> CREATOR = new android.os.Parcelable.Creator<android.accessibilityservice.MagnificationConfig>() { // from class: android.accessibilityservice.MagnificationConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.accessibilityservice.MagnificationConfig createFromParcel(android.os.Parcel parcel) {
            return new android.accessibilityservice.MagnificationConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.accessibilityservice.MagnificationConfig[] newArray(int i) {
            return new android.accessibilityservice.MagnificationConfig[i];
        }
    };
    public static final int MAGNIFICATION_MODE_DEFAULT = 0;
    public static final int MAGNIFICATION_MODE_FULLSCREEN = 1;
    public static final int MAGNIFICATION_MODE_WINDOW = 2;
    private boolean mActivated;
    private float mCenterX;
    private float mCenterY;
    private int mMode;
    private float mScale;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface MagnificationMode {
    }

    private MagnificationConfig() {
        this.mMode = 0;
        this.mActivated = false;
        this.mScale = Float.NaN;
        this.mCenterX = Float.NaN;
        this.mCenterY = Float.NaN;
    }

    private MagnificationConfig(android.os.Parcel parcel) {
        this.mMode = 0;
        this.mActivated = false;
        this.mScale = Float.NaN;
        this.mCenterX = Float.NaN;
        this.mCenterY = Float.NaN;
        this.mMode = parcel.readInt();
        this.mActivated = parcel.readBoolean();
        this.mScale = parcel.readFloat();
        this.mCenterX = parcel.readFloat();
        this.mCenterY = parcel.readFloat();
    }

    public int getMode() {
        return this.mMode;
    }

    public boolean isActivated() {
        return this.mActivated;
    }

    public float getScale() {
        return this.mScale;
    }

    public float getCenterX() {
        return this.mCenterX;
    }

    public float getCenterY() {
        return this.mCenterY;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("MagnificationConfig[");
        sb.append("mode: ").append(getMode());
        sb.append(", ");
        sb.append("activated: ").append(isActivated());
        sb.append(", ");
        sb.append("scale: ").append(getScale());
        sb.append(", ");
        sb.append("centerX: ").append(getCenterX());
        sb.append(", ");
        sb.append("centerY: ").append(getCenterY());
        sb.append("] ");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mMode);
        parcel.writeBoolean(this.mActivated);
        parcel.writeFloat(this.mScale);
        parcel.writeFloat(this.mCenterX);
        parcel.writeFloat(this.mCenterY);
    }

    public static final class Builder {
        private int mMode = 0;
        private boolean mActivated = true;
        private float mScale = Float.NaN;
        private float mCenterX = Float.NaN;
        private float mCenterY = Float.NaN;

        public android.accessibilityservice.MagnificationConfig.Builder setMode(int i) {
            this.mMode = i;
            return this;
        }

        public android.accessibilityservice.MagnificationConfig.Builder setActivated(boolean z) {
            this.mActivated = z;
            return this;
        }

        public android.accessibilityservice.MagnificationConfig.Builder setScale(float f) {
            this.mScale = f;
            return this;
        }

        public android.accessibilityservice.MagnificationConfig.Builder setCenterX(float f) {
            this.mCenterX = f;
            return this;
        }

        public android.accessibilityservice.MagnificationConfig.Builder setCenterY(float f) {
            this.mCenterY = f;
            return this;
        }

        public android.accessibilityservice.MagnificationConfig build() {
            android.accessibilityservice.MagnificationConfig magnificationConfig = new android.accessibilityservice.MagnificationConfig();
            magnificationConfig.mMode = this.mMode;
            magnificationConfig.mActivated = this.mActivated;
            magnificationConfig.mScale = this.mScale;
            magnificationConfig.mCenterX = this.mCenterX;
            magnificationConfig.mCenterY = this.mCenterY;
            return magnificationConfig;
        }
    }
}
