package android.media;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes2.dex */
public final class BluetoothProfileConnectionInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.BluetoothProfileConnectionInfo> CREATOR = new android.os.Parcelable.Creator<android.media.BluetoothProfileConnectionInfo>() { // from class: android.media.BluetoothProfileConnectionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.BluetoothProfileConnectionInfo createFromParcel(android.os.Parcel parcel) {
            return new android.media.BluetoothProfileConnectionInfo(parcel.readInt(), parcel.readBoolean(), parcel.readInt(), parcel.readBoolean());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.BluetoothProfileConnectionInfo[] newArray(int i) {
            return new android.media.BluetoothProfileConnectionInfo[i];
        }
    };
    private final boolean mIsLeOutput;
    private final int mProfile;
    private final boolean mSupprNoisy;
    private final int mVolume;

    private BluetoothProfileConnectionInfo(int i, boolean z, int i2, boolean z2) {
        this.mProfile = i;
        this.mSupprNoisy = z;
        this.mVolume = i2;
        this.mIsLeOutput = z2;
    }

    public BluetoothProfileConnectionInfo(int i) {
        this(i, false, -1, false);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mProfile);
        parcel.writeBoolean(this.mSupprNoisy);
        parcel.writeInt(this.mVolume);
        parcel.writeBoolean(this.mIsLeOutput);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static android.media.BluetoothProfileConnectionInfo createA2dpInfo(boolean z, int i) {
        return new android.media.BluetoothProfileConnectionInfo(2, z, i, false);
    }

    public static android.media.BluetoothProfileConnectionInfo createA2dpSinkInfo(int i) {
        return new android.media.BluetoothProfileConnectionInfo(11, true, i, false);
    }

    public static android.media.BluetoothProfileConnectionInfo createHearingAidInfo(boolean z) {
        return new android.media.BluetoothProfileConnectionInfo(21, z, -1, false);
    }

    public static android.media.BluetoothProfileConnectionInfo createLeAudioInfo(boolean z, boolean z2) {
        return new android.media.BluetoothProfileConnectionInfo(22, z, -1, z2);
    }

    public static android.media.BluetoothProfileConnectionInfo createLeAudioOutputInfo(boolean z, int i) {
        return new android.media.BluetoothProfileConnectionInfo(22, z, i, true);
    }

    public int getProfile() {
        return this.mProfile;
    }

    public boolean isSuppressNoisyIntent() {
        return this.mSupprNoisy;
    }

    public int getVolume() {
        return this.mVolume;
    }

    public boolean isLeOutput() {
        return this.mIsLeOutput;
    }

    public static android.media.BluetoothProfileConnectionInfo createHfpInfo() {
        return new android.media.BluetoothProfileConnectionInfo(1, false, -1, false);
    }
}
