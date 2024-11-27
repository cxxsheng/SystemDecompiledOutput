package android.security.rkp;

/* loaded from: classes3.dex */
public class RemotelyProvisionedKey implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.security.rkp.RemotelyProvisionedKey> CREATOR = new android.os.Parcelable.Creator<android.security.rkp.RemotelyProvisionedKey>() { // from class: android.security.rkp.RemotelyProvisionedKey.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.rkp.RemotelyProvisionedKey createFromParcel(android.os.Parcel parcel) {
            android.security.rkp.RemotelyProvisionedKey remotelyProvisionedKey = new android.security.rkp.RemotelyProvisionedKey();
            remotelyProvisionedKey.readFromParcel(parcel);
            return remotelyProvisionedKey;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.rkp.RemotelyProvisionedKey[] newArray(int i) {
            return new android.security.rkp.RemotelyProvisionedKey[i];
        }
    };
    public byte[] encodedCertChain;
    public byte[] keyBlob;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByteArray(this.keyBlob);
        parcel.writeByteArray(this.encodedCertChain);
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
                return;
            }
            this.keyBlob = parcel.createByteArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.encodedCertChain = parcel.createByteArray();
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
