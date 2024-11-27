package android.hardware.lights;

/* loaded from: classes2.dex */
public final class LightState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.lights.LightState> CREATOR = new android.os.Parcelable.Creator<android.hardware.lights.LightState>() { // from class: android.hardware.lights.LightState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.lights.LightState createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.lights.LightState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.lights.LightState[] newArray(int i) {
            return new android.hardware.lights.LightState[i];
        }
    };
    private final int mColor;
    private final int mPlayerId;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public LightState(int i) {
        this(i, 0);
    }

    public LightState(int i, int i2) {
        this.mColor = i;
        this.mPlayerId = i2;
    }

    public static final class Builder {
        private int mValue = 0;
        private boolean mIsForPlayerId = false;

        public android.hardware.lights.LightState.Builder setColor(int i) {
            this.mIsForPlayerId = false;
            this.mValue = i;
            return this;
        }

        public android.hardware.lights.LightState.Builder setPlayerId(int i) {
            this.mIsForPlayerId = true;
            this.mValue = i;
            return this;
        }

        public android.hardware.lights.LightState build() {
            if (!this.mIsForPlayerId) {
                return new android.hardware.lights.LightState(this.mValue, 0);
            }
            return new android.hardware.lights.LightState(0, this.mValue);
        }
    }

    private LightState(android.os.Parcel parcel) {
        this.mColor = parcel.readInt();
        this.mPlayerId = parcel.readInt();
    }

    public int getColor() {
        return this.mColor;
    }

    public int getPlayerId() {
        return this.mPlayerId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mColor);
        parcel.writeInt(this.mPlayerId);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "LightState{Color=0x" + java.lang.Integer.toHexString(this.mColor) + ", PlayerId=" + this.mPlayerId + "}";
    }
}
