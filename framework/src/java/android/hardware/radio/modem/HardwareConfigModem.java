package android.hardware.radio.modem;

/* loaded from: classes2.dex */
public class HardwareConfigModem implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.modem.HardwareConfigModem> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.modem.HardwareConfigModem>() { // from class: android.hardware.radio.modem.HardwareConfigModem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.modem.HardwareConfigModem createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.modem.HardwareConfigModem hardwareConfigModem = new android.hardware.radio.modem.HardwareConfigModem();
            hardwareConfigModem.readFromParcel(parcel);
            return hardwareConfigModem;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.modem.HardwareConfigModem[] newArray(int i) {
            return new android.hardware.radio.modem.HardwareConfigModem[i];
        }
    };
    public int rat;
    public int rilModel = 0;
    public int maxVoiceCalls = 0;
    public int maxDataCalls = 0;
    public int maxStandby = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.rilModel);
        parcel.writeInt(this.rat);
        parcel.writeInt(this.maxVoiceCalls);
        parcel.writeInt(this.maxDataCalls);
        parcel.writeInt(this.maxStandby);
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
            this.rilModel = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.rat = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.maxVoiceCalls = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.maxDataCalls = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.maxStandby = parcel.readInt();
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
        stringJoiner.add("rilModel: " + this.rilModel);
        stringJoiner.add("rat: " + android.hardware.radio.RadioTechnology$$.toString(this.rat));
        stringJoiner.add("maxVoiceCalls: " + this.maxVoiceCalls);
        stringJoiner.add("maxDataCalls: " + this.maxDataCalls);
        stringJoiner.add("maxStandby: " + this.maxStandby);
        return "HardwareConfigModem" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
