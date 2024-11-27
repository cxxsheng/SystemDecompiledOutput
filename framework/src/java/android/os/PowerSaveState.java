package android.os;

/* loaded from: classes3.dex */
public class PowerSaveState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.PowerSaveState> CREATOR = new android.os.Parcelable.Creator<android.os.PowerSaveState>() { // from class: android.os.PowerSaveState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.PowerSaveState createFromParcel(android.os.Parcel parcel) {
            return new android.os.PowerSaveState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.PowerSaveState[] newArray(int i) {
            return new android.os.PowerSaveState[i];
        }
    };
    public final boolean batterySaverEnabled;
    public final float brightnessFactor;
    public final boolean globalBatterySaverEnabled;
    public final int locationMode;
    public final int soundTriggerMode;

    public PowerSaveState(android.os.PowerSaveState.Builder builder) {
        this.batterySaverEnabled = builder.mBatterySaverEnabled;
        this.locationMode = builder.mLocationMode;
        this.soundTriggerMode = builder.mSoundTriggerMode;
        this.brightnessFactor = builder.mBrightnessFactor;
        this.globalBatterySaverEnabled = builder.mGlobalBatterySaverEnabled;
    }

    public PowerSaveState(android.os.Parcel parcel) {
        this.batterySaverEnabled = parcel.readByte() != 0;
        this.globalBatterySaverEnabled = parcel.readByte() != 0;
        this.locationMode = parcel.readInt();
        this.soundTriggerMode = parcel.readInt();
        this.brightnessFactor = parcel.readFloat();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte(this.batterySaverEnabled ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.globalBatterySaverEnabled ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.locationMode);
        parcel.writeInt(this.soundTriggerMode);
        parcel.writeFloat(this.brightnessFactor);
    }

    public static final class Builder {
        private boolean mBatterySaverEnabled = false;
        private boolean mGlobalBatterySaverEnabled = false;
        private int mLocationMode = 0;
        private int mSoundTriggerMode = 0;
        private float mBrightnessFactor = 0.5f;

        public android.os.PowerSaveState.Builder setBatterySaverEnabled(boolean z) {
            this.mBatterySaverEnabled = z;
            return this;
        }

        public android.os.PowerSaveState.Builder setGlobalBatterySaverEnabled(boolean z) {
            this.mGlobalBatterySaverEnabled = z;
            return this;
        }

        public android.os.PowerSaveState.Builder setSoundTriggerMode(int i) {
            this.mSoundTriggerMode = i;
            return this;
        }

        public android.os.PowerSaveState.Builder setLocationMode(int i) {
            this.mLocationMode = i;
            return this;
        }

        public android.os.PowerSaveState.Builder setBrightnessFactor(float f) {
            this.mBrightnessFactor = f;
            return this;
        }

        public android.os.PowerSaveState build() {
            return new android.os.PowerSaveState(this);
        }
    }
}
