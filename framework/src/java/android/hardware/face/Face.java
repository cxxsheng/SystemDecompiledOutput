package android.hardware.face;

/* loaded from: classes2.dex */
public final class Face extends android.hardware.biometrics.BiometricAuthenticator.Identifier {
    public static final android.os.Parcelable.Creator<android.hardware.face.Face> CREATOR = new android.os.Parcelable.Creator<android.hardware.face.Face>() { // from class: android.hardware.face.Face.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.face.Face createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.face.Face(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.face.Face[] newArray(int i) {
            return new android.hardware.face.Face[i];
        }
    };

    public Face(java.lang.CharSequence charSequence, int i, long j) {
        super(charSequence, i, j);
    }

    private Face(android.os.Parcel parcel) {
        super(parcel.readString(), parcel.readInt(), parcel.readLong());
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
    }
}
