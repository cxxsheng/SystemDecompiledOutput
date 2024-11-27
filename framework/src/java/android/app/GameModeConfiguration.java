package android.app;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class GameModeConfiguration implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.GameModeConfiguration> CREATOR = new android.os.Parcelable.Creator<android.app.GameModeConfiguration>() { // from class: android.app.GameModeConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.GameModeConfiguration createFromParcel(android.os.Parcel parcel) {
            return new android.app.GameModeConfiguration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.GameModeConfiguration[] newArray(int i) {
            return new android.app.GameModeConfiguration[i];
        }
    };
    public static final int FPS_OVERRIDE_NONE = 0;
    private final int mFpsOverride;
    private final float mScalingFactor;

    @android.annotation.SystemApi
    public static final class Builder {
        private int mFpsOverride;
        private float mScalingFactor;

        public Builder() {
        }

        public Builder(android.app.GameModeConfiguration gameModeConfiguration) {
            this.mFpsOverride = gameModeConfiguration.mFpsOverride;
            this.mScalingFactor = gameModeConfiguration.mScalingFactor;
        }

        public android.app.GameModeConfiguration.Builder setScalingFactor(float f) {
            double d = f;
            com.android.internal.util.Preconditions.checkArgument(d >= 0.1d && d <= 1.0d, "Scaling factor should fall between 0.1 and 1.0 (inclusive)");
            this.mScalingFactor = f;
            return this;
        }

        public android.app.GameModeConfiguration.Builder setFpsOverride(int i) {
            com.android.internal.util.Preconditions.checkArgument(i >= 0, "FPS override should be non-negative");
            this.mFpsOverride = i;
            return this;
        }

        public android.app.GameModeConfiguration build() {
            return new android.app.GameModeConfiguration(this.mScalingFactor, this.mFpsOverride);
        }
    }

    GameModeConfiguration(float f, int i) {
        this.mScalingFactor = f;
        this.mFpsOverride = i;
    }

    GameModeConfiguration(android.os.Parcel parcel) {
        this.mScalingFactor = parcel.readFloat();
        this.mFpsOverride = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeFloat(this.mScalingFactor);
        parcel.writeInt(this.mFpsOverride);
    }

    public float getScalingFactor() {
        return this.mScalingFactor;
    }

    public int getFpsOverride() {
        return this.mFpsOverride;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof android.app.GameModeConfiguration)) {
            return false;
        }
        android.app.GameModeConfiguration gameModeConfiguration = (android.app.GameModeConfiguration) obj;
        return gameModeConfiguration.mFpsOverride == this.mFpsOverride && gameModeConfiguration.mScalingFactor == this.mScalingFactor;
    }

    public int hashCode() {
        return ((217 + this.mFpsOverride) * 31) + java.lang.Float.floatToIntBits(this.mScalingFactor);
    }
}
