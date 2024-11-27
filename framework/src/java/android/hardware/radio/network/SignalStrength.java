package android.hardware.radio.network;

/* loaded from: classes2.dex */
public class SignalStrength implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.network.SignalStrength> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.network.SignalStrength>() { // from class: android.hardware.radio.network.SignalStrength.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.SignalStrength createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.network.SignalStrength signalStrength = new android.hardware.radio.network.SignalStrength();
            signalStrength.readFromParcel(parcel);
            return signalStrength;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.SignalStrength[] newArray(int i) {
            return new android.hardware.radio.network.SignalStrength[i];
        }
    };
    public android.hardware.radio.network.CdmaSignalStrength cdma;
    public android.hardware.radio.network.EvdoSignalStrength evdo;
    public android.hardware.radio.network.GsmSignalStrength gsm;
    public android.hardware.radio.network.LteSignalStrength lte;
    public android.hardware.radio.network.NrSignalStrength nr;
    public android.hardware.radio.network.TdscdmaSignalStrength tdscdma;
    public android.hardware.radio.network.WcdmaSignalStrength wcdma;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.gsm, i);
        parcel.writeTypedObject(this.cdma, i);
        parcel.writeTypedObject(this.evdo, i);
        parcel.writeTypedObject(this.lte, i);
        parcel.writeTypedObject(this.tdscdma, i);
        parcel.writeTypedObject(this.wcdma, i);
        parcel.writeTypedObject(this.nr, i);
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
            this.gsm = (android.hardware.radio.network.GsmSignalStrength) parcel.readTypedObject(android.hardware.radio.network.GsmSignalStrength.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.cdma = (android.hardware.radio.network.CdmaSignalStrength) parcel.readTypedObject(android.hardware.radio.network.CdmaSignalStrength.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.evdo = (android.hardware.radio.network.EvdoSignalStrength) parcel.readTypedObject(android.hardware.radio.network.EvdoSignalStrength.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.lte = (android.hardware.radio.network.LteSignalStrength) parcel.readTypedObject(android.hardware.radio.network.LteSignalStrength.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.tdscdma = (android.hardware.radio.network.TdscdmaSignalStrength) parcel.readTypedObject(android.hardware.radio.network.TdscdmaSignalStrength.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.wcdma = (android.hardware.radio.network.WcdmaSignalStrength) parcel.readTypedObject(android.hardware.radio.network.WcdmaSignalStrength.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.nr = (android.hardware.radio.network.NrSignalStrength) parcel.readTypedObject(android.hardware.radio.network.NrSignalStrength.CREATOR);
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
        stringJoiner.add("gsm: " + java.util.Objects.toString(this.gsm));
        stringJoiner.add("cdma: " + java.util.Objects.toString(this.cdma));
        stringJoiner.add("evdo: " + java.util.Objects.toString(this.evdo));
        stringJoiner.add("lte: " + java.util.Objects.toString(this.lte));
        stringJoiner.add("tdscdma: " + java.util.Objects.toString(this.tdscdma));
        stringJoiner.add("wcdma: " + java.util.Objects.toString(this.wcdma));
        stringJoiner.add("nr: " + java.util.Objects.toString(this.nr));
        return "SignalStrength" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.gsm) | 0 | describeContents(this.cdma) | describeContents(this.evdo) | describeContents(this.lte) | describeContents(this.tdscdma) | describeContents(this.wcdma) | describeContents(this.nr);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
