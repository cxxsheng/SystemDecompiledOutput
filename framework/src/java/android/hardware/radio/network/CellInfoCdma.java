package android.hardware.radio.network;

/* loaded from: classes2.dex */
public class CellInfoCdma implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.network.CellInfoCdma> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.network.CellInfoCdma>() { // from class: android.hardware.radio.network.CellInfoCdma.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.CellInfoCdma createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.network.CellInfoCdma cellInfoCdma = new android.hardware.radio.network.CellInfoCdma();
            cellInfoCdma.readFromParcel(parcel);
            return cellInfoCdma;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.CellInfoCdma[] newArray(int i) {
            return new android.hardware.radio.network.CellInfoCdma[i];
        }
    };
    public android.hardware.radio.network.CellIdentityCdma cellIdentityCdma;
    public android.hardware.radio.network.CdmaSignalStrength signalStrengthCdma;
    public android.hardware.radio.network.EvdoSignalStrength signalStrengthEvdo;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.cellIdentityCdma, i);
        parcel.writeTypedObject(this.signalStrengthCdma, i);
        parcel.writeTypedObject(this.signalStrengthEvdo, i);
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
            this.cellIdentityCdma = (android.hardware.radio.network.CellIdentityCdma) parcel.readTypedObject(android.hardware.radio.network.CellIdentityCdma.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.signalStrengthCdma = (android.hardware.radio.network.CdmaSignalStrength) parcel.readTypedObject(android.hardware.radio.network.CdmaSignalStrength.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.signalStrengthEvdo = (android.hardware.radio.network.EvdoSignalStrength) parcel.readTypedObject(android.hardware.radio.network.EvdoSignalStrength.CREATOR);
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
        stringJoiner.add("cellIdentityCdma: " + java.util.Objects.toString(this.cellIdentityCdma));
        stringJoiner.add("signalStrengthCdma: " + java.util.Objects.toString(this.signalStrengthCdma));
        stringJoiner.add("signalStrengthEvdo: " + java.util.Objects.toString(this.signalStrengthEvdo));
        return "CellInfoCdma" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.cellIdentityCdma) | 0 | describeContents(this.signalStrengthCdma) | describeContents(this.signalStrengthEvdo);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}