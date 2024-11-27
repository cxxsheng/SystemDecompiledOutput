package android.hardware.radio.network;

/* loaded from: classes2.dex */
public class NrSignalStrength implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.network.NrSignalStrength> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.network.NrSignalStrength>() { // from class: android.hardware.radio.network.NrSignalStrength.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.NrSignalStrength createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.network.NrSignalStrength nrSignalStrength = new android.hardware.radio.network.NrSignalStrength();
            nrSignalStrength.readFromParcel(parcel);
            return nrSignalStrength;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.NrSignalStrength[] newArray(int i) {
            return new android.hardware.radio.network.NrSignalStrength[i];
        }
    };
    public byte[] csiCqiReport;
    public int ssRsrp = 0;
    public int ssRsrq = 0;
    public int ssSinr = 0;
    public int csiRsrp = 0;
    public int csiRsrq = 0;
    public int csiSinr = 0;
    public int csiCqiTableIndex = 0;
    public int timingAdvance = Integer.MAX_VALUE;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.ssRsrp);
        parcel.writeInt(this.ssRsrq);
        parcel.writeInt(this.ssSinr);
        parcel.writeInt(this.csiRsrp);
        parcel.writeInt(this.csiRsrq);
        parcel.writeInt(this.csiSinr);
        parcel.writeInt(this.csiCqiTableIndex);
        parcel.writeByteArray(this.csiCqiReport);
        parcel.writeInt(this.timingAdvance);
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
            this.ssRsrp = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.ssRsrq = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.ssSinr = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.csiRsrp = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.csiRsrq = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.csiSinr = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.csiCqiTableIndex = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.csiCqiReport = parcel.createByteArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.timingAdvance = parcel.readInt();
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
        stringJoiner.add("ssRsrp: " + this.ssRsrp);
        stringJoiner.add("ssRsrq: " + this.ssRsrq);
        stringJoiner.add("ssSinr: " + this.ssSinr);
        stringJoiner.add("csiRsrp: " + this.csiRsrp);
        stringJoiner.add("csiRsrq: " + this.csiRsrq);
        stringJoiner.add("csiSinr: " + this.csiSinr);
        stringJoiner.add("csiCqiTableIndex: " + this.csiCqiTableIndex);
        stringJoiner.add("csiCqiReport: " + java.util.Arrays.toString(this.csiCqiReport));
        stringJoiner.add("timingAdvance: " + this.timingAdvance);
        return "NrSignalStrength" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
