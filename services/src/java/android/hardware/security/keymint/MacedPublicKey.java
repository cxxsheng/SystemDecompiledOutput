package android.hardware.security.keymint;

/* loaded from: classes.dex */
public class MacedPublicKey implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.security.keymint.MacedPublicKey> CREATOR = new android.os.Parcelable.Creator<android.hardware.security.keymint.MacedPublicKey>() { // from class: android.hardware.security.keymint.MacedPublicKey.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.security.keymint.MacedPublicKey createFromParcel(android.os.Parcel parcel) {
            android.hardware.security.keymint.MacedPublicKey macedPublicKey = new android.hardware.security.keymint.MacedPublicKey();
            macedPublicKey.readFromParcel(parcel);
            return macedPublicKey;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.security.keymint.MacedPublicKey[] newArray(int i) {
            return new android.hardware.security.keymint.MacedPublicKey[i];
        }
    };
    public byte[] macedKey;

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByteArray(this.macedKey);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(android.os.Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new android.os.BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.macedKey = parcel.createByteArray();
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            }
        } catch (java.lang.Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new android.os.BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
