package android.hardware.security.secretkeeper;

/* loaded from: classes.dex */
public class SecretId implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.security.secretkeeper.SecretId> CREATOR = new android.os.Parcelable.Creator<android.hardware.security.secretkeeper.SecretId>() { // from class: android.hardware.security.secretkeeper.SecretId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.security.secretkeeper.SecretId createFromParcel(android.os.Parcel parcel) {
            android.hardware.security.secretkeeper.SecretId secretId = new android.hardware.security.secretkeeper.SecretId();
            secretId.readFromParcel(parcel);
            return secretId;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.security.secretkeeper.SecretId[] newArray(int i) {
            return new android.hardware.security.secretkeeper.SecretId[i];
        }
    };
    public byte[] id;

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeFixedArray(this.id, i, 64);
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
                this.id = (byte[]) parcel.createFixedArray(byte[].class, 64);
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
