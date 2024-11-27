package android.hardware.security.keymint;

/* loaded from: classes2.dex */
public class KeyCreationResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.security.keymint.KeyCreationResult> CREATOR = new android.os.Parcelable.Creator<android.hardware.security.keymint.KeyCreationResult>() { // from class: android.hardware.security.keymint.KeyCreationResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.security.keymint.KeyCreationResult createFromParcel(android.os.Parcel parcel) {
            android.hardware.security.keymint.KeyCreationResult keyCreationResult = new android.hardware.security.keymint.KeyCreationResult();
            keyCreationResult.readFromParcel(parcel);
            return keyCreationResult;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.security.keymint.KeyCreationResult[] newArray(int i) {
            return new android.hardware.security.keymint.KeyCreationResult[i];
        }
    };
    public android.hardware.security.keymint.Certificate[] certificateChain;
    public byte[] keyBlob;
    public android.hardware.security.keymint.KeyCharacteristics[] keyCharacteristics;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByteArray(this.keyBlob);
        parcel.writeTypedArray(this.keyCharacteristics, i);
        parcel.writeTypedArray(this.certificateChain, i);
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
                return;
            }
            this.keyCharacteristics = (android.hardware.security.keymint.KeyCharacteristics[]) parcel.createTypedArray(android.hardware.security.keymint.KeyCharacteristics.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.certificateChain = (android.hardware.security.keymint.Certificate[]) parcel.createTypedArray(android.hardware.security.keymint.Certificate.CREATOR);
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
        return describeContents(this.keyCharacteristics) | 0 | describeContents(this.certificateChain);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof java.lang.Object[]) {
            int i = 0;
            for (java.lang.Object obj2 : (java.lang.Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (!(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
