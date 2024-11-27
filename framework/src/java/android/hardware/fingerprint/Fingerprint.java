package android.hardware.fingerprint;

/* loaded from: classes2.dex */
public final class Fingerprint extends android.hardware.biometrics.BiometricAuthenticator.Identifier {
    public static final android.os.Parcelable.Creator<android.hardware.fingerprint.Fingerprint> CREATOR = new android.os.Parcelable.Creator<android.hardware.fingerprint.Fingerprint>() { // from class: android.hardware.fingerprint.Fingerprint.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.fingerprint.Fingerprint createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.fingerprint.Fingerprint(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.fingerprint.Fingerprint[] newArray(int i) {
            return new android.hardware.fingerprint.Fingerprint[i];
        }
    };
    private int mGroupId;

    public Fingerprint(java.lang.CharSequence charSequence, int i, int i2, long j) {
        super(charSequence, i2, j);
        this.mGroupId = i;
    }

    public Fingerprint(java.lang.CharSequence charSequence, int i, long j) {
        super(charSequence, i, j);
    }

    private Fingerprint(android.os.Parcel parcel) {
        super(parcel.readString(), parcel.readInt(), parcel.readLong());
        this.mGroupId = parcel.readInt();
    }

    public int getGroupId() {
        return this.mGroupId;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(getName().toString());
        parcel.writeInt(getBiometricId());
        parcel.writeLong(getDeviceId());
        parcel.writeInt(this.mGroupId);
    }
}
