package android.hardware.biometrics;

/* loaded from: classes.dex */
public enum BiometricSourceType implements android.os.Parcelable {
    FINGERPRINT,
    FACE,
    IRIS;

    public static final android.os.Parcelable.Creator<android.hardware.biometrics.BiometricSourceType> CREATOR = new android.os.Parcelable.Creator<android.hardware.biometrics.BiometricSourceType>() { // from class: android.hardware.biometrics.BiometricSourceType.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.biometrics.BiometricSourceType createFromParcel(android.os.Parcel parcel) {
            return android.hardware.biometrics.BiometricSourceType.valueOf(parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.biometrics.BiometricSourceType[] newArray(int i) {
            return new android.hardware.biometrics.BiometricSourceType[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(name());
    }
}
