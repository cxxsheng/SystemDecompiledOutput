package android.hardware.radio.messaging;

/* loaded from: classes2.dex */
public class CdmaSmsSubaddress implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.messaging.CdmaSmsSubaddress> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.messaging.CdmaSmsSubaddress>() { // from class: android.hardware.radio.messaging.CdmaSmsSubaddress.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.messaging.CdmaSmsSubaddress createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.messaging.CdmaSmsSubaddress cdmaSmsSubaddress = new android.hardware.radio.messaging.CdmaSmsSubaddress();
            cdmaSmsSubaddress.readFromParcel(parcel);
            return cdmaSmsSubaddress;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.messaging.CdmaSmsSubaddress[] newArray(int i) {
            return new android.hardware.radio.messaging.CdmaSmsSubaddress[i];
        }
    };
    public static final int SUBADDRESS_TYPE_NSAP = 0;
    public static final int SUBADDRESS_TYPE_USER_SPECIFIED = 1;
    public byte[] digits;
    public int subaddressType = 0;
    public boolean odd = false;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.subaddressType);
        parcel.writeBoolean(this.odd);
        parcel.writeByteArray(this.digits);
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
            this.subaddressType = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.odd = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.digits = parcel.createByteArray();
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

    public java.lang.String toString() {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ", "{", "}");
        stringJoiner.add("subaddressType: " + this.subaddressType);
        stringJoiner.add("odd: " + this.odd);
        stringJoiner.add("digits: " + java.util.Arrays.toString(this.digits));
        return "CdmaSmsSubaddress" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
