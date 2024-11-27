package android.hardware.radio.modem;

/* loaded from: classes2.dex */
public class HardwareConfigSim implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.modem.HardwareConfigSim> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.modem.HardwareConfigSim>() { // from class: android.hardware.radio.modem.HardwareConfigSim.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.modem.HardwareConfigSim createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.modem.HardwareConfigSim hardwareConfigSim = new android.hardware.radio.modem.HardwareConfigSim();
            hardwareConfigSim.readFromParcel(parcel);
            return hardwareConfigSim;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.modem.HardwareConfigSim[] newArray(int i) {
            return new android.hardware.radio.modem.HardwareConfigSim[i];
        }
    };
    public java.lang.String modemUuid;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.modemUuid);
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
            } else {
                this.modemUuid = parcel.readString();
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
        stringJoiner.add("modemUuid: " + java.util.Objects.toString(this.modemUuid));
        return "HardwareConfigSim" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
