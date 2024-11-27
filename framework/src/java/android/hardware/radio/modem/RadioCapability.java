package android.hardware.radio.modem;

/* loaded from: classes2.dex */
public class RadioCapability implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.modem.RadioCapability> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.modem.RadioCapability>() { // from class: android.hardware.radio.modem.RadioCapability.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.modem.RadioCapability createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.modem.RadioCapability radioCapability = new android.hardware.radio.modem.RadioCapability();
            radioCapability.readFromParcel(parcel);
            return radioCapability;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.modem.RadioCapability[] newArray(int i) {
            return new android.hardware.radio.modem.RadioCapability[i];
        }
    };
    public static final int PHASE_APPLY = 2;
    public static final int PHASE_CONFIGURED = 0;
    public static final int PHASE_FINISH = 4;
    public static final int PHASE_START = 1;
    public static final int PHASE_UNSOL_RSP = 3;
    public static final int STATUS_FAIL = 2;
    public static final int STATUS_NONE = 0;
    public static final int STATUS_SUCCESS = 1;
    public java.lang.String logicalModemUuid;
    public int session = 0;
    public int phase = 0;
    public int raf = 0;
    public int status = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.session);
        parcel.writeInt(this.phase);
        parcel.writeInt(this.raf);
        parcel.writeString(this.logicalModemUuid);
        parcel.writeInt(this.status);
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
            this.session = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.phase = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.raf = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.logicalModemUuid = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.status = parcel.readInt();
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
        stringJoiner.add("session: " + this.session);
        stringJoiner.add("phase: " + this.phase);
        stringJoiner.add("raf: " + this.raf);
        stringJoiner.add("logicalModemUuid: " + java.util.Objects.toString(this.logicalModemUuid));
        stringJoiner.add("status: " + this.status);
        return "RadioCapability" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
