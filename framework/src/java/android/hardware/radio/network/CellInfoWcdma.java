package android.hardware.radio.network;

/* loaded from: classes2.dex */
public class CellInfoWcdma implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.network.CellInfoWcdma> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.network.CellInfoWcdma>() { // from class: android.hardware.radio.network.CellInfoWcdma.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.CellInfoWcdma createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.network.CellInfoWcdma cellInfoWcdma = new android.hardware.radio.network.CellInfoWcdma();
            cellInfoWcdma.readFromParcel(parcel);
            return cellInfoWcdma;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.CellInfoWcdma[] newArray(int i) {
            return new android.hardware.radio.network.CellInfoWcdma[i];
        }
    };
    public android.hardware.radio.network.CellIdentityWcdma cellIdentityWcdma;
    public android.hardware.radio.network.WcdmaSignalStrength signalStrengthWcdma;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.cellIdentityWcdma, i);
        parcel.writeTypedObject(this.signalStrengthWcdma, i);
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
            this.cellIdentityWcdma = (android.hardware.radio.network.CellIdentityWcdma) parcel.readTypedObject(android.hardware.radio.network.CellIdentityWcdma.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.signalStrengthWcdma = (android.hardware.radio.network.WcdmaSignalStrength) parcel.readTypedObject(android.hardware.radio.network.WcdmaSignalStrength.CREATOR);
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
        stringJoiner.add("cellIdentityWcdma: " + java.util.Objects.toString(this.cellIdentityWcdma));
        stringJoiner.add("signalStrengthWcdma: " + java.util.Objects.toString(this.signalStrengthWcdma));
        return "CellInfoWcdma" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.cellIdentityWcdma) | 0 | describeContents(this.signalStrengthWcdma);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
