package android.hardware.radio.network;

/* loaded from: classes2.dex */
public class CellInfoTdscdma implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.network.CellInfoTdscdma> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.network.CellInfoTdscdma>() { // from class: android.hardware.radio.network.CellInfoTdscdma.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.CellInfoTdscdma createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.network.CellInfoTdscdma cellInfoTdscdma = new android.hardware.radio.network.CellInfoTdscdma();
            cellInfoTdscdma.readFromParcel(parcel);
            return cellInfoTdscdma;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.CellInfoTdscdma[] newArray(int i) {
            return new android.hardware.radio.network.CellInfoTdscdma[i];
        }
    };
    public android.hardware.radio.network.CellIdentityTdscdma cellIdentityTdscdma;
    public android.hardware.radio.network.TdscdmaSignalStrength signalStrengthTdscdma;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.cellIdentityTdscdma, i);
        parcel.writeTypedObject(this.signalStrengthTdscdma, i);
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
            this.cellIdentityTdscdma = (android.hardware.radio.network.CellIdentityTdscdma) parcel.readTypedObject(android.hardware.radio.network.CellIdentityTdscdma.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.signalStrengthTdscdma = (android.hardware.radio.network.TdscdmaSignalStrength) parcel.readTypedObject(android.hardware.radio.network.TdscdmaSignalStrength.CREATOR);
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
        stringJoiner.add("cellIdentityTdscdma: " + java.util.Objects.toString(this.cellIdentityTdscdma));
        stringJoiner.add("signalStrengthTdscdma: " + java.util.Objects.toString(this.signalStrengthTdscdma));
        return "CellInfoTdscdma" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.cellIdentityTdscdma) | 0 | describeContents(this.signalStrengthTdscdma);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
