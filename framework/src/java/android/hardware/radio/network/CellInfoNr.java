package android.hardware.radio.network;

/* loaded from: classes2.dex */
public class CellInfoNr implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.network.CellInfoNr> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.network.CellInfoNr>() { // from class: android.hardware.radio.network.CellInfoNr.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.CellInfoNr createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.network.CellInfoNr cellInfoNr = new android.hardware.radio.network.CellInfoNr();
            cellInfoNr.readFromParcel(parcel);
            return cellInfoNr;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.CellInfoNr[] newArray(int i) {
            return new android.hardware.radio.network.CellInfoNr[i];
        }
    };
    public android.hardware.radio.network.CellIdentityNr cellIdentityNr;
    public android.hardware.radio.network.NrSignalStrength signalStrengthNr;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.cellIdentityNr, i);
        parcel.writeTypedObject(this.signalStrengthNr, i);
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
            this.cellIdentityNr = (android.hardware.radio.network.CellIdentityNr) parcel.readTypedObject(android.hardware.radio.network.CellIdentityNr.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.signalStrengthNr = (android.hardware.radio.network.NrSignalStrength) parcel.readTypedObject(android.hardware.radio.network.NrSignalStrength.CREATOR);
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
        stringJoiner.add("cellIdentityNr: " + java.util.Objects.toString(this.cellIdentityNr));
        stringJoiner.add("signalStrengthNr: " + java.util.Objects.toString(this.signalStrengthNr));
        return "CellInfoNr" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.cellIdentityNr) | 0 | describeContents(this.signalStrengthNr);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
