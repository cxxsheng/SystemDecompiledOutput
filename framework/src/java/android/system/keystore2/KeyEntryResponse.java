package android.system.keystore2;

/* loaded from: classes3.dex */
public class KeyEntryResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.system.keystore2.KeyEntryResponse> CREATOR = new android.os.Parcelable.Creator<android.system.keystore2.KeyEntryResponse>() { // from class: android.system.keystore2.KeyEntryResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.system.keystore2.KeyEntryResponse createFromParcel(android.os.Parcel parcel) {
            android.system.keystore2.KeyEntryResponse keyEntryResponse = new android.system.keystore2.KeyEntryResponse();
            keyEntryResponse.readFromParcel(parcel);
            return keyEntryResponse;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.system.keystore2.KeyEntryResponse[] newArray(int i) {
            return new android.system.keystore2.KeyEntryResponse[i];
        }
    };
    public android.system.keystore2.IKeystoreSecurityLevel iSecurityLevel;
    public android.system.keystore2.KeyMetadata metadata;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeStrongInterface(this.iSecurityLevel);
        parcel.writeTypedObject(this.metadata, i);
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
            this.iSecurityLevel = android.system.keystore2.IKeystoreSecurityLevel.Stub.asInterface(parcel.readStrongBinder());
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.metadata = (android.system.keystore2.KeyMetadata) parcel.readTypedObject(android.system.keystore2.KeyMetadata.CREATOR);
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
        return describeContents(this.metadata) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
