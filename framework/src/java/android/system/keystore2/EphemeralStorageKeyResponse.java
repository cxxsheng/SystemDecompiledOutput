package android.system.keystore2;

/* loaded from: classes3.dex */
public class EphemeralStorageKeyResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.system.keystore2.EphemeralStorageKeyResponse> CREATOR = new android.os.Parcelable.Creator<android.system.keystore2.EphemeralStorageKeyResponse>() { // from class: android.system.keystore2.EphemeralStorageKeyResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.system.keystore2.EphemeralStorageKeyResponse createFromParcel(android.os.Parcel parcel) {
            android.system.keystore2.EphemeralStorageKeyResponse ephemeralStorageKeyResponse = new android.system.keystore2.EphemeralStorageKeyResponse();
            ephemeralStorageKeyResponse.readFromParcel(parcel);
            return ephemeralStorageKeyResponse;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.system.keystore2.EphemeralStorageKeyResponse[] newArray(int i) {
            return new android.system.keystore2.EphemeralStorageKeyResponse[i];
        }
    };
    public byte[] ephemeralKey;
    public byte[] upgradedBlob;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByteArray(this.ephemeralKey);
        parcel.writeByteArray(this.upgradedBlob);
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
            this.ephemeralKey = parcel.createByteArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.upgradedBlob = parcel.createByteArray();
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
