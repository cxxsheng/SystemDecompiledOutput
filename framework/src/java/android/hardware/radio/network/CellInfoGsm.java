package android.hardware.radio.network;

/* loaded from: classes2.dex */
public class CellInfoGsm implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.network.CellInfoGsm> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.network.CellInfoGsm>() { // from class: android.hardware.radio.network.CellInfoGsm.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.CellInfoGsm createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.network.CellInfoGsm cellInfoGsm = new android.hardware.radio.network.CellInfoGsm();
            cellInfoGsm.readFromParcel(parcel);
            return cellInfoGsm;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.CellInfoGsm[] newArray(int i) {
            return new android.hardware.radio.network.CellInfoGsm[i];
        }
    };
    public android.hardware.radio.network.CellIdentityGsm cellIdentityGsm;
    public android.hardware.radio.network.GsmSignalStrength signalStrengthGsm;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.cellIdentityGsm, i);
        parcel.writeTypedObject(this.signalStrengthGsm, i);
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
            this.cellIdentityGsm = (android.hardware.radio.network.CellIdentityGsm) parcel.readTypedObject(android.hardware.radio.network.CellIdentityGsm.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.signalStrengthGsm = (android.hardware.radio.network.GsmSignalStrength) parcel.readTypedObject(android.hardware.radio.network.GsmSignalStrength.CREATOR);
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
        stringJoiner.add("cellIdentityGsm: " + java.util.Objects.toString(this.cellIdentityGsm));
        stringJoiner.add("signalStrengthGsm: " + java.util.Objects.toString(this.signalStrengthGsm));
        return "CellInfoGsm" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.cellIdentityGsm) | 0 | describeContents(this.signalStrengthGsm);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}