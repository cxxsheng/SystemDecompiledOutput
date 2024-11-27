package android.hardware.radio.network;

/* loaded from: classes2.dex */
public class CellInfoLte implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.network.CellInfoLte> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.network.CellInfoLte>() { // from class: android.hardware.radio.network.CellInfoLte.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.CellInfoLte createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.network.CellInfoLte cellInfoLte = new android.hardware.radio.network.CellInfoLte();
            cellInfoLte.readFromParcel(parcel);
            return cellInfoLte;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.CellInfoLte[] newArray(int i) {
            return new android.hardware.radio.network.CellInfoLte[i];
        }
    };
    public android.hardware.radio.network.CellIdentityLte cellIdentityLte;
    public android.hardware.radio.network.LteSignalStrength signalStrengthLte;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.cellIdentityLte, i);
        parcel.writeTypedObject(this.signalStrengthLte, i);
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
            this.cellIdentityLte = (android.hardware.radio.network.CellIdentityLte) parcel.readTypedObject(android.hardware.radio.network.CellIdentityLte.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.signalStrengthLte = (android.hardware.radio.network.LteSignalStrength) parcel.readTypedObject(android.hardware.radio.network.LteSignalStrength.CREATOR);
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
        stringJoiner.add("cellIdentityLte: " + java.util.Objects.toString(this.cellIdentityLte));
        stringJoiner.add("signalStrengthLte: " + java.util.Objects.toString(this.signalStrengthLte));
        return "CellInfoLte" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.cellIdentityLte) | 0 | describeContents(this.signalStrengthLte);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
