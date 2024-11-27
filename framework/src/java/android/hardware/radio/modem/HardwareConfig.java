package android.hardware.radio.modem;

/* loaded from: classes2.dex */
public class HardwareConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.modem.HardwareConfig> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.modem.HardwareConfig>() { // from class: android.hardware.radio.modem.HardwareConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.modem.HardwareConfig createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.modem.HardwareConfig hardwareConfig = new android.hardware.radio.modem.HardwareConfig();
            hardwareConfig.readFromParcel(parcel);
            return hardwareConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.modem.HardwareConfig[] newArray(int i) {
            return new android.hardware.radio.modem.HardwareConfig[i];
        }
    };
    public static final int STATE_DISABLED = 2;
    public static final int STATE_ENABLED = 0;
    public static final int STATE_STANDBY = 1;
    public static final int TYPE_MODEM = 0;
    public static final int TYPE_SIM = 1;
    public android.hardware.radio.modem.HardwareConfigModem[] modem;
    public android.hardware.radio.modem.HardwareConfigSim[] sim;
    public java.lang.String uuid;
    public int type = 0;
    public int state = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.type);
        parcel.writeString(this.uuid);
        parcel.writeInt(this.state);
        parcel.writeTypedArray(this.modem, i);
        parcel.writeTypedArray(this.sim, i);
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
            this.type = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.uuid = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.state = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.modem = (android.hardware.radio.modem.HardwareConfigModem[]) parcel.createTypedArray(android.hardware.radio.modem.HardwareConfigModem.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.sim = (android.hardware.radio.modem.HardwareConfigSim[]) parcel.createTypedArray(android.hardware.radio.modem.HardwareConfigSim.CREATOR);
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
        stringJoiner.add("type: " + this.type);
        stringJoiner.add("uuid: " + java.util.Objects.toString(this.uuid));
        stringJoiner.add("state: " + this.state);
        stringJoiner.add("modem: " + java.util.Arrays.toString(this.modem));
        stringJoiner.add("sim: " + java.util.Arrays.toString(this.sim));
        return "HardwareConfig" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.modem) | 0 | describeContents(this.sim);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof java.lang.Object[]) {
            int i = 0;
            for (java.lang.Object obj2 : (java.lang.Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (!(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
