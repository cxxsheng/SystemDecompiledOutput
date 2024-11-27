package android.hardware.radio.network;

/* loaded from: classes2.dex */
public class EmergencyRegResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.network.EmergencyRegResult> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.network.EmergencyRegResult>() { // from class: android.hardware.radio.network.EmergencyRegResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.EmergencyRegResult createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.network.EmergencyRegResult emergencyRegResult = new android.hardware.radio.network.EmergencyRegResult();
            emergencyRegResult.readFromParcel(parcel);
            return emergencyRegResult;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.EmergencyRegResult[] newArray(int i) {
            return new android.hardware.radio.network.EmergencyRegResult[i];
        }
    };
    public int accessNetwork;
    public int emcDomain;
    public int regState;
    public boolean isVopsSupported = false;
    public boolean isEmcBearerSupported = false;
    public byte nwProvidedEmc = 0;
    public byte nwProvidedEmf = 0;
    public java.lang.String mcc = "";
    public java.lang.String mnc = "";

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.accessNetwork);
        parcel.writeInt(this.regState);
        parcel.writeInt(this.emcDomain);
        parcel.writeBoolean(this.isVopsSupported);
        parcel.writeBoolean(this.isEmcBearerSupported);
        parcel.writeByte(this.nwProvidedEmc);
        parcel.writeByte(this.nwProvidedEmf);
        parcel.writeString(this.mcc);
        parcel.writeString(this.mnc);
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
            this.accessNetwork = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.regState = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.emcDomain = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.isVopsSupported = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.isEmcBearerSupported = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.nwProvidedEmc = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.nwProvidedEmf = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.mcc = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.mnc = parcel.readString();
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
        stringJoiner.add("accessNetwork: " + android.hardware.radio.AccessNetwork$$.toString(this.accessNetwork));
        stringJoiner.add("regState: " + android.hardware.radio.network.RegState$$.toString(this.regState));
        stringJoiner.add("emcDomain: " + android.hardware.radio.network.Domain$$.toString(this.emcDomain));
        stringJoiner.add("isVopsSupported: " + this.isVopsSupported);
        stringJoiner.add("isEmcBearerSupported: " + this.isEmcBearerSupported);
        stringJoiner.add("nwProvidedEmc: " + ((int) this.nwProvidedEmc));
        stringJoiner.add("nwProvidedEmf: " + ((int) this.nwProvidedEmf));
        stringJoiner.add("mcc: " + java.util.Objects.toString(this.mcc));
        stringJoiner.add("mnc: " + java.util.Objects.toString(this.mnc));
        return "EmergencyRegResult" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
